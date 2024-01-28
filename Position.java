import java.util.Comparator;

public class Position {


    //data
private int x,y;

    @Override
    public String toString() {
        return "(" + x + ", " + y +")";
    }
//constructor

    public Position(int x,int y){
        this.x=x;
        this.y=y;
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

    class PositionTakenCompare implements Comparator<Integer> { //compare the distance of 2 pieces
        public int compare (Integer c1, Integer c2){
            if(c1< c2) return -1;
            if(c1> c2) return 1;
            else return 0;
        }
    }



}
