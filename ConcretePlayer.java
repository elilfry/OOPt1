public class ConcretePlayer implements Player{
//data

    private boolean playerOne;
    private  int wins = 0;


    public ConcretePlayer( boolean playerOne) {
         this.playerOne=playerOne;

    }


//function
    @Override
    public boolean isPlayerOne() {
        return playerOne;
    }

    @Override
    public int getWins() {
        return wins;
    }

    public void setWins() {
        this.wins = this.getWins() +1;
    }
}
