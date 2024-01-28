import java.util.ArrayList;
import java.util.Comparator;

public abstract class ConcretePiece implements Piece{

    //data
    protected Player owner;
    protected String type;    //pawn or king
    protected String name;     //D or A and the number
    protected Position initPos;
    protected int distance =0 ;

    protected ArrayList<Position> map = new ArrayList<Position>();

    //constructor
    public ConcretePiece(){}

    //functions

    protected void setPos(Position b){
        map.add(b);
    }

    protected ArrayList<Position> getPos(){
        return this.map;
    }

    protected void setName(String name){
        this.name = name;
    }

    protected String getName(){
        return this.name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {

        this.distance = this.getDistance()+ distance;
    }

    class distanceCompare implements Comparator<ConcretePiece> { //compare the distance of 2 pieces
        public int compare (ConcretePiece c1, ConcretePiece c2){
            if(c1.getDistance() < c2.getDistance()) return -1;
            if(c1.getDistance() > c2.getDistance()) return 1;
            else return 0;
        }
    }

    public class stappedHistoryCompare2 implements Comparator<ArrayList<Position>> { //compare the history of 2 pieces
        @Override
        public int compare(ArrayList<Position> list1, ArrayList<Position> list2) {
            int size1 = list1.size();
            int size2 = list2.size();

            if (size1 < size2) { return -1;
            } else if (size1 > size2) { return 1;
            } else { return 0; }
        }
    }


}
