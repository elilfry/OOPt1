import java.util.ArrayList;
import java.util.Comparator;

public class Position {


    //data
    private int x, y;
    //private int steppedOnMe;
    ArrayList<ConcretePiece> steppedOnMe = new ArrayList<>();

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
//constructor

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //function

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    public int getSteppedOnMe() {
        return this.steppedOnMe.size();
    }

    public void setSteppedOnMe(ConcretePiece piece) {
        if (!this.steppedOnMe.contains(piece)) {
            this.steppedOnMe.add(piece);
        }
    }
}
class PositionTakenCompare implements Comparator<Position> { //compare the distance of 2 pieces
    public int compare(Position c1, Position c2) {
        if (c1.getSteppedOnMe() < c2.getSteppedOnMe()) return 1;
        if (c1.getSteppedOnMe() > c2.getSteppedOnMe()) return -1;
        else return 0;
    }
}

class xCompare implements Comparator<Position> {
    public int compare (Position c1, Position c2){
        if(c1.getX() < c2.getX()) return -1;
        if(c1.getX() > c2.getX()) return 1;
        else return 0;
    }
}

class yCompare implements Comparator<Position> {
    public int compare (Position c1, Position c2){
        if(c1.getX() < c2.getX()) return -1;
        if(c1.getX() > c2.getX()) return 1;
        else return 0;
    }
}
