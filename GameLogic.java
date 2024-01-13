import java.util.Objects;

public  class GameLogic implements PlayableLogic {

    /**
     * moves the piece from one point to another.
     *
     * @param a- the current position
     * @param b- the destination position
     * @return True- if the move legal and succeeded,
     */

    //data
    int boardSize = 11;

    private ConcretePiece[][] grid = new ConcretePiece[11][11];

     ConcretePlayer player1= new ConcretePlayer(true);
     ConcretePlayer player2 = new ConcretePlayer(false);



    public GameLogic(){
        reset();
    }


    @Override
    public boolean move(Position a, Position b) {

        int ax = a.getX(), ay = a.getY(), bx = b.getX(), by = b.getY();

        // no diagonal
        if((ax !=bx) && (ay != by)){
            return false;
        }



        //no moving to a full position

        if(grid[b.getY()][b.getX()] != null){ //if position b is full
            return false;
        }

        //no passing through
//        if(ay==by){   //moving in the y axe
//            int min = Math.min(ax,bx);
//            int max = Math.max(ax,bx);
//            for(int i=min;i<=max;i++){
//                if(grid[ay][i] != null){
//                    return false;
//                }
//            }
//
//        }
//
//        if(ax==bx){   //moving in the x axe
//            int min = Math.min(ay,by);
//            int max = Math.max(ay,by);
//            for(int i=min;i<=max;i++){
//                if(grid[i][ax] != null){
//                    return false;
//                }
//            }
//
//        }



        //no exiting the grid

        //if this is not your turn

//        if (grid[ay][ax].owner == player2){
//            if(! isSecondPlayerTurn()){// if the owner is player 2 and its not his turn
//                return false;
//            }
//        }
//
//        if (grid[ay][ax].owner == player1){
//            if(isSecondPlayerTurn()){// if the owner is player 1 and its not his turn
//                return false;
//            }
//        }
        //If a pawn is going to the corners

        if(Objects.equals(grid[ay][ax].type, "♟")) { //maybe there is other way
            if (bx == 0 && (by == 0 || by == 10)) {return false;}
            if (bx == 10 && (by == 0 || by == 10)) {return false;}
        }



        //alow only one move at a turn

        //////the actual moving
        grid[by][bx] = grid[ay][ax];
        grid[ay][ax]=null;

        return true;
    }

    @Override
    public Piece getPieceAtPosition(Position position) {

        return grid[position.getY()] [position.getX()];

    }

    @Override
    public Player getFirstPlayer() {
        return player1;
    }

    @Override
    public Player getSecondPlayer() {
        return player2;
    }

    @Override
    public boolean isGameFinished() {
        return false;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return true;
    }

    @Override
    public void reset() { //reset the board to the start position
        //grid= new ConcretePiece[11][11];

        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                grid[j][i] = null;
            }
        }
        for (int i = 3; i <= 7; i++) {
            grid[0][i] = new Pawn(player2);
            grid[10][i] = new Pawn(player2);
            grid[i][0] = new Pawn(player2);
            grid[i][10] = new Pawn(player2);
            grid[5][i] = new Pawn(player1);
            if (i > 3 && i < 7) {
                grid[4][i] = new Pawn(player1);
                grid[6][i] = new Pawn(player1);
                if(i==5){
                    grid[3][i] = new Pawn(player1);
                    grid[7][i] = new Pawn(player1);
                    grid[i][1] = new Pawn(player2);
                    grid[i][9] = new Pawn(player2);
                    grid[1][i] = new Pawn(player2);
                    grid[9][i] = new Pawn(player2);

                }
            }
            if (i == 5) {
                grid[3][i] = new Pawn(player1);
                grid[7][i] = new Pawn(player1);

            }

        }
        grid[5][5] = new King(player1);

    }

    @Override
    public void undoLastMove() {

    }

    @Override
    public int getBoardSize() {
        return boardSize;
    }
}
