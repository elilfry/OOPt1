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

        isPlayer2Turn = !isPlayer2Turn;     //pass the turn

        //////////////////////////////////     Pawn Kill     ////////////////////////////////////////////////////////

        //kill a pawn on the left
        if(bx > 0 && grid[by][bx-1] != null && grid[by][bx] instanceof Pawn) {  //if the current Piece isn't a king and it's not on the left border
            if (grid[by][bx].owner != grid[by][bx - 1].owner) {     //if the Pawn on the left is an enemy
                if(bx-2 >= 0 && grid[by][bx - 2] != null) {     //if the enemy on the left isn't on the border and the that position isn't empty
                    if (grid[by][bx - 2].owner == grid[by][bx].owner && grid[by][bx-2] instanceof Pawn
                            && grid[by][bx-1] instanceof Pawn) {        //if the piece one the left to the enemy is an alli Pawn
                        grid[by][bx - 1] = null;       // KILLLLLLLLLLL!!!!!!
                    }
                }
               if (bx<2 && grid[by][bx-1] instanceof Pawn){grid[by][bx - 1] = null;}       //if the enemy Pawn is on the left border- KILLLLL!!!
                if (((by == 0 && bx == 2) || (by == 10 && bx == 2)) && grid[by][bx-1] instanceof Pawn){grid[by][bx-1] = null;}  //exception for the two left corners
            }
        }

        //kill a pawn on the right
        if(bx < 10 && grid[by][bx+1] != null && grid[by][bx] instanceof Pawn) { //if the current Piece isn't a king and it's not on the right border
            if (grid[by][bx].owner != grid[by][bx + 1].owner) {  //if the Pawn on the right is an enemy
                if(bx+2 <= 10 && grid[by][bx + 2] != null) { //if the enemy on the right isn't on the border and the that position isn't empty
                    if (grid[by][bx + 2].owner == grid[by][bx].owner && grid[by][bx+2] instanceof Pawn
                            && grid[by][bx+1] instanceof Pawn) { //if the piece one the right to the enemy is an alli Pawn
                        grid[by][bx + 1] = null; // KILLLLLLLLLLL!!!!!!
                    }
                }
                if (bx>8 && grid[by][bx+1] instanceof Pawn){grid[by][bx + 1] = null;}  //if the enemy Pawn is on the right border- KILLLLL!!!
                if (((by == 10 && bx == 8) || (by == 0 && bx == 8)) && grid[by][bx+1] instanceof Pawn){grid[by][bx+1] = null;} //exception for the two right corners (?)
            }
        }

        //kill down
        if(by < 10 && grid[by+1][bx] != null && grid[by][bx] instanceof Pawn) { //if the current Piece isn't a king and it's not on the bottom border
            if (grid[by][bx].owner != grid[by+1][bx].owner) { //if the Pawn from below is an enemy
                if(by+2 <= 10 && grid[by+2][bx] != null) { //if the enemy from below isn't on the border and the that position isn't empty
                    if (grid[by+2][bx].owner == grid[by][bx].owner && grid[by+2][bx] instanceof Pawn
                            && grid[by+1][bx] instanceof Pawn) { //if the piece one below to the enemy is an alli Pawn
                        grid[by+1][bx] = null; // KILLLLLLLLLLL!!!!!!
                    }
                }
                if (by>8 && grid[by+1][bx] instanceof Pawn){grid[by+1][bx] = null;} //if the enemy Pawn is on the bottom border- KILLLLL!!!
                if (((by == 8 && bx == 10) || (by == 8 && bx == 0)) && grid[by+1][bx] instanceof Pawn){grid[by+1][bx] = null;} //exception for the two bottom corners (?)
            }
        }

        //kill an upper Pawn
        if(by > 0 && grid[by-1][bx] != null && grid[by][bx] instanceof Pawn) { //if the current Piece isn't a king and it's not on the top border
            if (grid[by][bx].owner != grid[by-1][bx].owner) { //if the Pawn from above is an enemy
                if(by-2 >= 0 && grid[by-2][bx] != null) { //if the enemy from above isn't on the border and the that position isn't empty
                    if (grid[by-2][bx].owner == grid[by][bx].owner && grid[by-2][bx] instanceof Pawn
                            && grid[by-1][bx] instanceof Pawn) { //if the piece one above to the enemy is an alli Pawn
                        grid[by-1][bx] = null; // KILLLLLLLLLLL!!!!!!
                    }
                }
                if (by<2 && grid[by-1][bx] instanceof Pawn){grid[by-1][bx] = null;} //if the enemy Pawn is on the top border- KILLLLL!!!
                if (((by == 2 && bx == 10) || (by == 2 && bx == 0)) && grid[by-1][bx] instanceof Pawn){grid[by-1][bx] = null;} //exception for the two top corners (?)
            }
        }



 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

        //if the king is surrounded by 4 pawns


        //if the king is at the edge with 3 pawns



        // if the king is at the corner
        if(grid[0][0] instanceof King || grid[0][10] instanceof King ||grid[10][0] instanceof King || grid[10][10] instanceof King ){
            return true;
        }
        // if one of the players has no other pieces

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

}
