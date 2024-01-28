import java.util.ArrayList;

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

    protected Position[] getPos(){
        return (Position[]) map.toArray();
    }

    protected void setName(String name){
        this.name = name;
    }

    protected String getName(){
        return this.name;
    }
}
