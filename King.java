public class King extends ConcretePiece{

   //data


    //constructor
    public King(Player player) {
        owner =player;
        type ="♔";


    }

    //function
    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getType() {
        return type;
    }
}
