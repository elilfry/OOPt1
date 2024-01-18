import java.util.Objects;

public  class GameLogic implements PlayableLogic {

    /**
     * moves the piece from one point to another.
     *
     * @param a- the current 
     * @param b- the destination position
     * @return True- if the move legal and succeeded,
     */

    //data
    int boardSize = 11;
    private boolean isPlayer2Turn;

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
        if(ay==by){   //moving in the y axe
            int min = Math.min(ax,bx);
            int max = Math.max(ax,bx);
            for(int i=min+1;i<max;i++){
                if(grid[ay][i] != null){
                    return false;
                }
            }

        }

        if(ax==bx){   //moving in the x axe
            int min = Math.min(ay,by);
            int max = Math.max(ay,by);
            for(int i=min+1;i<max;i++){
                if(grid[i][ax] != null){
                    return false;
                }
            }

        }



        //no exiting the grid
        if(bx>10 || bx<0 || by>10 || by<0) {return false;}

        //if you are moving to the same position
        if(ax == bx && ay == by) {return false;}

        //if this is not your turn

        if (grid[ay][ax].owner == player2){
            if(! isSecondPlayerTurn()){// if the owner is player 2 and its not his turn
                return false;
            }
        }

        if (grid[ay][ax].owner == player1){
            if(isSecondPlayerTurn()){// if the owner is player 1 and its not his turn
                return false;
            }
        }
        //If a pawn is going to the corners

        if(Objects.equals(grid[ay][ax].type, "♟")) {
            if (bx == 0 && (by == 0 || by == 10)) {return false;}
            if (bx == 10 && (by == 0 || by == 10)) {return false;}
        }


        //alow only one move at a turn

        //do not alow moving from null position to null position

        //////the actual moving

        grid[by][bx] = grid[ay][ax];
        grid[ay][ax]=null;


        isPlayer2Turn = !isPlayer2Turn;
        if(bx > 0 && grid[by][bx-1] != null && grid[by][bx] instanceof Pawn) {
            if (grid[by][bx].owner != grid[by][bx - 1].owner) {
                if(bx-2 >= 0 && grid[by][bx - 2] != null) {
                    if (grid[by][bx - 2].owner == grid[by][bx].owner && grid[by][bx-2] instanceof Pawn
                            && grid[by][bx-1] instanceof Pawn) {
                        grid[by][bx - 1] = null;
                    }
                }
               if (bx<2 && grid[by][bx-1] instanceof Pawn){grid[by][bx - 1] = null;}
            }
        }





















        //right
        if(bx < 10 && grid[by][bx+1] != null && grid[by][bx] instanceof Pawn) {
            if (grid[by][bx].owner != grid[by][bx + 1].owner) {
                if(bx+2 <= 10 && grid[by][bx + 2] != null) {
                    if (grid[by][bx + 2].owner == grid[by][bx].owner && grid[by][bx+2] instanceof Pawn
                            && grid[by][bx+1] instanceof Pawn) {
                        grid[by][bx + 1] = null;
                    }
                }
                if (bx>8 && grid[by][bx+1] instanceof Pawn){grid[by][bx + 1] = null;}
            }
        }

        //up
        if(by < 10 && grid[by+1][bx] != null && grid[by][bx] instanceof Pawn) {
            if (grid[by][bx].owner != grid[by+1][bx].owner) {
                if(by+2 <= 10 && grid[by+2][bx] != null) {
                    if (grid[by+2][bx].owner == grid[by][bx].owner && grid[by+2][bx] instanceof Pawn
                            && grid[by+1][bx] instanceof Pawn) {
                        grid[by+1][bx] = null;
                    }
                }
                if (by>8 && grid[by+1][bx] instanceof Pawn){grid[by+1][bx] = null;}
            }
        }

        //dowm
        if(by > 0 && grid[by-1][bx] != null && grid[by][bx] instanceof Pawn) {
            if (grid[by][bx].owner != grid[by-1][bx].owner) {
                if(by-2 >= 0 && grid[by-2][bx] != null) {
                    if (grid[by-2][bx].owner == grid[by][bx].owner && grid[by-2][bx] instanceof Pawn
                            && grid[by-1][bx] instanceof Pawn) {
                        grid[by-1][bx] = null;
                    }
                }
                if (by<2 && grid[by-1][bx] instanceof Pawn){grid[by-1][bx] = null;}
            }
        }
















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
        return isPlayer2Turn;
    }

    /**
     * reset the board to the start position
     */
    @Override
    public void reset() {
        //grid= new ConcretePiece[11][11];

        isPlayer2Turn = true;

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

    private void eat(Position b){
       int bx = b.getX(), by = b.getY();

        //regular 1 from each side (and multi kills)

        //left kill
        if(bx > 0 && grid[by][bx-1] != null) {
            if (grid[by][bx].owner != grid[by][bx - 1].owner) {
                if(bx-2 >= 0) {
                    if (grid[by][bx - 2].owner == grid[by][bx].owner) {
                        grid[by][bx - 1] = null;
                    }
                }
                grid[by][bx - 1] = null;

            }
        }
//        //right kill
//        if(bx < 10 && grid[by][bx+1] != null) {
//            if (grid[by][bx].owner != grid[by][bx + 1].owner) {
//                if (grid[by][bx + 2].owner == grid[by][bx].owner) {
//                    grid[by][bx+1] = null;
//                }
//            }
//        }



        //moving to a position between 2 enemy pieces

        //eat with 1 pawn and the edge

        //the king is unarmed

        //capture the king






    }
}
