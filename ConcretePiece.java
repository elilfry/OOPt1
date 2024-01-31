import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class ConcretePiece implements Piece {

    //data
    protected Player owner;
    protected String type;    //pawn or king
    protected String name;     //D or A and the number
    protected Position initPos;
    protected int distance = 0;

    protected List<Position> map = new ArrayList<Position>();

    //constructor
    public ConcretePiece() {
    }

    //functions

    protected void setPos(Position b) {
        map.add(b);
    }

    protected List<Position> getPos() {
        return this.map;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getName() {
        return this.name;
    }

    protected int getSerialNumber(){
        return Integer.parseInt(this.name.substring(1));
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {

        this.distance = this.getDistance() + distance;
    }

    public List<Position> getMap() {
        return this.map;
    }
}
    class DistanceCompare implements Comparator<ConcretePiece> { //compare the distance of 2 pieces
        public int compare (ConcretePiece c1, ConcretePiece c2){
            if(c1.getDistance() < c2.getDistance()) return 1;
            if(c1.getDistance() > c2.getDistance()) return -1;
            else return 0;
        }
    }

    class SteppedHistoryCompare implements Comparator<ConcretePiece>{
        public int compare(ConcretePiece o1, ConcretePiece o2) {
            return o1.getMap().size() - o2.getMap().size();
        }
    }

    class NameCompare implements  Comparator<ConcretePiece>{
        public int compare(ConcretePiece o1, ConcretePiece o2) {
           if(o1.owner == o2.owner){return 0;}     //same color
           if (GameLogic.getWinner() == 1){     //if the winner is Player1
               if (o1.owner.isPlayerOne()) {return -1;}     //if o1 is the winner's piece pick it first
               else return 1;
           }
           if (GameLogic.getWinner() == 2){     //if the winner is Player2
               if (o1.owner.isPlayerOne()) {return 1;}  //if o1 isn't the winner's piece pick the second one first
               else return -1;
           }
            return 0;   //if there is no current winner do nothing
        }
    }

    class SerialNumberCompare implements  Comparator<ConcretePiece>{
        public int compare(ConcretePiece o1, ConcretePiece o2) {
            if(o1.getSerialNumber() < o2.getSerialNumber()) return -1;
            if(o1.getSerialNumber() > o2.getSerialNumber()) return 1;
            return 0;
        }
    }