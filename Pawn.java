public class Pawn extends ConcretePiece{

//data
protected int kills;


//constructor

public Pawn(Player player){

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

    public int getKills() {
        return kills;
    }

    public void setKills() {
        this.kills = this.getKills() +1;
    }


}
