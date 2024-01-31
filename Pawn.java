import java.util.Comparator;

public class Pawn extends ConcretePiece {

//data
    protected int kills;


//constructor

    public Pawn(Player player){
        owner = player;
        type = getType();
    }

//functions:
    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getType() {
        if (getOwner().isPlayerOne()){
            return "♙";
        }
        else return "♟";
    }

    public int getKills() {
        return kills;
    }

    public void setKills() {
        this.kills = this.getKills() + 1;
    }

}
    class KillesCompare implements Comparator<Pawn> {
        public int compare (Pawn c1, Pawn c2){
        if(c1.getKills() < c2.getKills()) return 1;
        if(c1.getKills() > c2.getKills()) return -1;
        else return 0;
        }
    }

