public class Pawn extends ConcretePiece{

//data

    //constructor

public Pawn(Player player)
{
    owner =player;
    type ="♟";

}


    //copy
    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getType() {
        return type;
    }


}
