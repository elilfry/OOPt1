import java.util.*;

public  class GameLogic implements PlayableLogic {

    /**
     * moves the piece from one point to another.
     *
     * @param a- the current 
     * @param b- the destination position
     * @return True- if the move legal and succeeded,
     */

    //data
   protected int boardSize = 11;
   private int xKing;
   private int yKing;
   private int numOfReds;
   private static int winner;

    private boolean isPlayer2Turn;

    private ConcretePiece[][] grid = new ConcretePiece[11][11];

   // private ArrayList<ConcretePiece> deadPawns = new ArrayList<ConcretePiece>();

    private ArrayList<ConcretePiece> allPieces = new ArrayList<>();
    private ArrayList<Pawn> onlyPawns = new ArrayList<>();

    private Position[][] steppedOn = new Position[11][11];

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




        //do not alow moving from null position to null position

        //////the actual moving

        grid[by][bx] = grid[ay][ax];
        grid[ay][ax]=null;

        //////////////// calc the distance ///////

        if(ay == by){ // moving in the x grid
            grid[by][bx].setDistance( Math.abs(bx -ax) ); // add to the total distance

        }
        if(ax == bx){ // moving in the x grid
            grid[by][bx].setDistance(Math.abs(by -ay));  ; // add to the total distance

        }
// ///////////////////////////////



        isPlayer2Turn = !isPlayer2Turn;     //pass the turn

        //////////////////////////////////     Pawn Kill     ////////////////////////////////////////////////////////

        //kill a pawn on the left
        if(bx > 0 && grid[by][bx-1] != null && grid[by][bx] instanceof Pawn) {  //if the current Piece isn't a king and it's not on the left border
            if (grid[by][bx].owner != grid[by][bx - 1].owner) {     //if the Pawn on the left is an enemy
                if(bx-2 >= 0 && grid[by][bx - 2] != null) {     //if the enemy on the left isn't on the border and the that position isn't empty
                    if (grid[by][bx - 2].owner == grid[by][bx].owner && grid[by][bx-2] instanceof Pawn
                            && grid[by][bx-1] instanceof Pawn) {        //if the piece one the left to the enemy is an alli Pawn
                       // deadPawns.add(grid[by][bx-1]); //add the Pawn that is about to be killed to the ArrayList of the pieces that not on the board
                        grid[by][bx - 1] = null;       // KILLLLLLLLLLL!!!!!!
                        ((Pawn) grid[by][bx]).setKills();  //set the number of kills ++
                    }
                }
               if (bx<2 && grid[by][bx-1] instanceof Pawn) {
                   //deadPawns.add(grid[by][bx-1]); //add the Pawn that is about to be killed to the ArrayList of the pieces that not on the board
                   grid[by][bx - 1] = null;      //if the enemy Pawn is on the left border- KILLLLL!!!
                   ((Pawn) grid[by][bx]).setKills();  //set the number of kills ++
               }
                if (((by == 0 && bx == 2) || (by == 10 && bx == 2)) && grid[by][bx-1] instanceof Pawn) {
                   // deadPawns.add(grid[by][bx-1]); //add the Pawn that is about to be killed to the ArrayList of the pieces that not on the board
                    grid[by][bx - 1] = null;  //exception for the two left corners
                    ((Pawn) grid[by][bx]).setKills();  //set the number of kills ++
                }
            }
        }

        //kill a pawn on the right
        if(bx < 10 && grid[by][bx+1] != null && grid[by][bx] instanceof Pawn) { //if the current Piece isn't a king and it's not on the right border
            if (grid[by][bx].owner != grid[by][bx + 1].owner) {  //if the Pawn on the right is an enemy
                if(bx+2 <= 10 && grid[by][bx + 2] != null) { //if the enemy on the right isn't on the border and the that position isn't empty
                    if (grid[by][bx + 2].owner == grid[by][bx].owner && grid[by][bx+2] instanceof Pawn
                            && grid[by][bx+1] instanceof Pawn) { //if the piece one the right to the enemy is an alli Pawn
                       // deadPawns.add(grid[by][bx + 1]); //add the Pawn that is about to be killed to the ArrayList of the pieces that not on the board
                        grid[by][bx + 1] = null; // KILLLLLLLLLLL!!!!!!
                        ((Pawn) grid[by][bx]).setKills();  //set the number of kills ++
                    }
                }
                if (bx>8 && grid[by][bx+1] instanceof Pawn) {
                   // deadPawns.add(grid[by][bx + 1]); //add the Pawn that is about to be killed to the ArrayList of the pieces that not on the board
                    grid[by][bx + 1] = null; //if the enemy Pawn is on the right border- KILLLLL!!!
                    ((Pawn) grid[by][bx]).setKills();  //set the number of kills ++
                }
                if (((by == 10 && bx == 8) || (by == 0 && bx == 8)) && grid[by][bx+1] instanceof Pawn){
                  //  deadPawns.add(grid[by][bx + 1]); //add the Pawn that is about to be killed to the ArrayList of the pieces that not on the board
                    grid[by][bx+1] = null;//exception for the two right corners (?)
                ((Pawn) grid[by][bx]).setKills(); //set the number of kills ++
                }

            }
        }

        //kill down
        if(by < 10 && grid[by+1][bx] != null && grid[by][bx] instanceof Pawn) { //if the current Piece isn't a king and it's not on the bottom border
            if (grid[by][bx].owner != grid[by+1][bx].owner) { //if the Pawn from below is an enemy
                if(by+2 <= 10 && grid[by+2][bx] != null) { //if the enemy from below isn't on the border and the that position isn't empty
                    if (grid[by+2][bx].owner == grid[by][bx].owner && grid[by+2][bx] instanceof Pawn
                            && grid[by+1][bx] instanceof Pawn) { //if the piece one below to the enemy is an alli Pawn
                      //  deadPawns.add(grid[by + 1][bx]); //add the Pawn that is about to be killed to the ArrayList of the pieces that not on the board
                        grid[by+1][bx] = null; // KILLLLLLLLLLL!!!!!!
                        ((Pawn) grid[by][bx]).setKills();  //set the number of kills ++
                    }
                }
                if (by>8 && grid[by+1][bx] instanceof Pawn){
                  //  deadPawns.add(grid[by + 1][bx]); //add the Pawn that is about to be killed to the ArrayList of the pieces that not on the board
                    grid[by+1][bx] = null;//if the enemy Pawn is on the bottom border- KILLLLL!!!
                ((Pawn) grid[by][bx]).setKills();   //set the number of kills ++
                }
                if (((by == 8 && bx == 10) || (by == 8 && bx == 0)) && grid[by+1][bx] instanceof Pawn){
                   // deadPawns.add(grid[by + 1][bx]); //add the Pawn that is about to be killed to the ArrayList of the pieces that not on the board
                    grid[by+1][bx] = null;//exception for the two bottom corners (?)
                ((Pawn) grid[by][bx]).setKills();   //set the number of kills ++
                }
            }
        }

        //kill an upper Pawn
        if(by > 0 && grid[by-1][bx] != null && grid[by][bx] instanceof Pawn) { //if the current Piece isn't a king and it's not on the top border
            if (grid[by][bx].owner != grid[by-1][bx].owner) { //if the Pawn from above is an enemy
                if(by-2 >= 0 && grid[by-2][bx] != null) { //if the enemy from above isn't on the border and the that position isn't empty
                    if (grid[by-2][bx].owner == grid[by][bx].owner && grid[by-2][bx] instanceof Pawn
                            && grid[by-1][bx] instanceof Pawn) { //if the piece one above to the enemy is an alli Pawn
                      //  deadPawns.add(grid[by - 1][bx]); //add the Pawn that is about to be killed to the ArrayList of the pieces that not on the board
                        grid[by-1][bx] = null; // KILLLLLLLLLLL!!!!!!
                        ((Pawn) grid[by][bx]).setKills();  //set the number of kills ++
                    }
                }
                if (by<2 && grid[by-1][bx] instanceof Pawn){
                  //  deadPawns.add(grid[by - 1][bx]); //add the Pawn that is about to be killed to the ArrayList of the pieces that not on the board
                    grid[by-1][bx] = null;  //if the enemy Pawn is on the top border- KILLLLL!!!
                ((Pawn) grid[by][bx]).setKills();   //set the number of kills ++
                }
                if (((by == 2 && bx == 10) || (by == 2 && bx == 0)) && grid[by-1][bx] instanceof Pawn){
                  //  deadPawns.add(grid[by - 1][bx]); //add the Pawn that is about to be killed to the ArrayList of the pieces that not on the board
                    grid[by-1][bx] = null; //exception for the two top corners (?)
                ((Pawn) grid[by][bx]).setKills(); //set the number of kills ++
                }
            }
        }



 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        grid[by][bx].setPos(new Position(bx,by));   //add the new position to the current piece moving list

        steppedOn[by][bx].setSteppedOnMe(grid[by][bx]);    //add the number of steps the Piece stepped to the total

        if(isGameFinished()){
            printAllStuff();
        }

//
//        printing tests:
//        System.out.println(grid[by][bx].getName() + grid[by][bx].getPos());
//        if (grid[by][bx] instanceof Pawn) {
//            System.out.println(grid[by][bx].getName() + " kills: " + ((Pawn) grid[by][bx]).getKills());
//        }
//        System.out.println(grid[by][bx].getName() + "'s total distance = " + grid[by][bx].getDistance());
//        System.out.println("stepped on this position: " + steppedOn[by][bx]);

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

        // if one of the red player has no other pieces and find the king
        for (int i=0; i <= 10; i++) {    //check if the matrix has no red pawns
            for (int j = 0; j <= 10; j++) {
                if (grid[i][j] != null && grid[i][j].owner == player2) {
                    numOfReds++;
                }
                if (grid[i][j] instanceof King) {
                    yKing = i;
                    xKing = j;
                }
            }
            if (numOfReds == 0) {
                player1.setWins(); //set the number of wins on player 1 ++
                winner = 1;
               // printAllStuff();

                return true;
            }   //if player2 ran out of Pawns == player 1(blue) wins
        }

        //if the king is surrounded by 4 pawns
        if (xKing != 0 && xKing != 10 && yKing != 0 && yKing != 10) { //if the king is not on the border
            if (grid[yKing][xKing + 1] != null && grid[yKing][xKing - 1] != null && grid[yKing + 1][xKing] != null && grid[yKing - 1][xKing] != null) {
                if (grid[yKing][xKing + 1].owner == player2 && grid[yKing][xKing - 1].owner == player2 &&
                        grid[yKing + 1][xKing].owner == player2 && grid[yKing - 1][xKing].owner == player2) {
                    player2.setWins(); //set the number of wins on player 2 ++
                    winner = 2;
                   // printAllStuff();
                    return true; //player 2 (red) wins
                }
            }
        }
        else {  //if the king is at the border with 3 pawns
            if (yKing == 0 && xKing != 0 && xKing != 10) {  //surrounded with the border upstairs
                if (grid[yKing][xKing + 1] != null && grid[yKing][xKing - 1] != null && grid[yKing - 1][xKing] != null) {
                    if (grid[yKing][xKing + 1].owner == player2 && grid[yKing][xKing - 1].owner == player2 &&
                            grid[yKing - 1][xKing].owner == player2) {
                        player2.setWins(); //set the number of wins on player 2 ++
                        winner = 2;
                      //  printAllStuff();
                        return true; //player 2 (red) wins
                    }
                }
            }

            if (yKing == 10 && xKing != 0 && xKing != 10) { //surrounded with the border downstairs
                if (grid[yKing][xKing + 1] != null && grid[yKing][xKing - 1] != null && grid[yKing + 1][xKing] != null) {
                    if (grid[yKing][xKing + 1].owner == player2 && grid[yKing][xKing - 1].owner == player2 &&
                            grid[yKing + 1][xKing].owner == player2) {
                        player2.setWins(); //set the number of wins on player 2 ++
                        winner = 2;
                       // printAllStuff();
                        return true; //player 2 (red) wins
                    }
                }
            }
            if (xKing == 10 && yKing != 0 && yKing != 10) { //surrounded with the border from the right
                if (grid[yKing][xKing - 1] != null && grid[yKing + 1][xKing] != null && grid[yKing - 1][xKing] != null) {
                    if (grid[yKing][xKing - 1].owner == player2 && grid[yKing + 1][xKing].owner == player2 &&
                            grid[yKing - 1][xKing].owner == player2) {
                        player2.setWins(); //set the number of wins on player 2 ++
                        winner = 2;
                       // printAllStuff();
                        return true; //player 2 (red) wins
                    }
                }
            }
            if (xKing == 0 && yKing != 0 && yKing != 10) {  //surrounded with the border from the left
                if (grid[yKing][xKing + 1] != null && grid[yKing +1][xKing] != null && grid[yKing - 1][xKing] != null) {
                    if (grid[yKing][xKing + 1].owner == player2 && grid[yKing + 1][xKing].owner == player2 &&
                            grid[yKing - 1][xKing].owner == player2) {
                        player2.setWins(); //set the number of wins on player 2 ++
                        winner = 2;     //the winner is Player2 (pint his data first)
                        //printAllStuff();
                        return true; //player 2 (red) wins
                    }
                }
            }
        }


        // if the king is at the corner
        if(grid[0][0] instanceof King || grid[0][10] instanceof King ||grid[10][0] instanceof King || grid[10][10] instanceof King ){
            player1.setWins(); //set the number of wins on player 1 ++
            winner =1;  //the winner is Player1 (Print his data first)
           // printAllStuff();
            return true; //player 1 (blue) wins
        }

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
        winner = 0;     //the winner is none of the players :)
        //grid= new ConcretePiece[11][11];
        int k=0;
        isPlayer2Turn = true;

        //initiate the board with pieces
        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                grid[j][i] = null;
            }
        }
        for (int i = 3; i <= 7; i++) {
            grid[0][i] = new Pawn(player2);
            grid[0][i].setPos(new Position(i,0));

            grid[10][i] = new Pawn(player2);
            grid[10][i].setPos(new Position(i,10));

            grid[i][0] = new Pawn(player2);
            grid[i][0].setPos(new Position(0,i));

            grid[i][10] = new Pawn(player2);
            grid[i][10].setPos(new Position(10,i));

            if (i != 5) {
                grid[5][i] = new Pawn(player1);
                grid[5][i].setPos(new Position(i, 5));
            }

            if (i > 3 && i < 7) {
                grid[4][i] = new Pawn(player1);
                grid[4][i].setPos(new Position(i,4));

                grid[6][i] = new Pawn(player1);
                grid[6][i].setPos(new Position(i,6));

                if(i==5){
                    grid[3][i] = new Pawn(player1);
                    grid[3][5].setPos(new Position(5,3));

                    grid[7][i] = new Pawn(player1);
                    grid[7][5].setPos(new Position(5,7));

                    grid[i][1] = new Pawn(player2);
                    grid[5][1].setPos(new Position(1,5));

                    grid[i][9] = new Pawn(player2);
                    grid[5][9].setPos(new Position(9,5));

                    grid[1][i] = new Pawn(player2);
                    grid[1][5].setPos(new Position(5,1));

                    grid[9][i] = new Pawn(player2);
                    grid[9][5].setPos(new Position(5,9));
                }
            }
        }

        grid[5][5] = new King(player1);
        grid[5][5].setPos(new Position(5,5));

        //set the position matrix and count the pieces that starting on each position
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                steppedOn[j][i] = new Position(i,j);
                if (grid[j][i] != null){
                    steppedOn[j][i].setSteppedOnMe(grid[j][i]);
                }
            }

        }

        //initiate the List of all the pieces
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (grid[j][i] != null){
                    allPieces.add(grid[j][i]);
                }
            }
        }

        //initiate the List of all the Pawns without the King
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (grid[j][i] != null && grid[j][i] instanceof Pawn){
                    onlyPawns.add((Pawn) grid[j][i]);
                }
            }
        }



        setNames(); //call for the function that gives each pieces its name
    }

    @Override
    public void undoLastMove() {

    }

    @Override
    public int getBoardSize() {
        return boardSize;
    }

    public void setNames(){
        //Attackers:

        grid[0][3].setName("A1");
        grid[0][4].setName("A2");
        grid[0][5].setName("A3");
        grid[0][6].setName("A4");
        grid[0][7].setName("A5");
        grid[1][5].setName("A6");
        grid[3][0].setName("A7");
        grid[3][10].setName("A8");
        grid[4][0].setName("A9");
        grid[4][10].setName("A10");
        grid[5][0].setName("A11");
        grid[5][1].setName("A12");
        grid[5][9].setName("A13");
        grid[5][10].setName("A14");
        grid[6][0].setName("A15");
        grid[6][10].setName("A16");
        grid[7][0].setName("A17");
        grid[7][10].setName("A18");
        grid[9][5].setName("A19");
        grid[10][3].setName("A20");
        grid[10][4].setName("A21");
        grid[10][5].setName("A22");
        grid[10][6].setName("A23");
        grid[10][7].setName("A24");

        //Defenders:
        grid[3][5].setName("D1");
        grid[4][4].setName("D2");
        grid[4][5].setName("D3");
        grid[4][6].setName("D4");
        grid[5][3].setName("D5");
        grid[5][4].setName("D6");
        grid[5][5].setName("K7");   //the King
        grid[5][6].setName("D8");
        grid[5][7].setName("D9");
        grid[6][4].setName("D10");
        grid[6][5].setName("D11");
        grid[6][6].setName("D12");
        grid[7][5].setName("D13");
    }

    /**
     * return the number of the player who won:
     *      0= none, 1= Player1, 2= Player2
     * @return the number of the winner
     */
    public static int getWinner(){
        return winner;
    }

    /**
     * print the history of the position the piece traveled.
     * sorted from the most position traveled to the least, the winner team shown first.
     */
    public void printSteps(){
        //sort by name and staepped history
        Comparator<ConcretePiece> NameAndHistoryComp = new NameCompare().thenComparing(new SteppedHistoryCompare()).thenComparing(new SerialNumberCompare());
        Collections.sort(allPieces,NameAndHistoryComp);
         for(ConcretePiece i : allPieces){
             if (i.getMap().size() > 1){ System.out.println(i.getName() + ": " + i.getMap());}
         }
    }

    /**
     * print the number of kills each killer Pawn, sorted from the most kills to the least. if draw - print the piece of
     * the winner team first.
     */
    public void printKilles() {

        Comparator<Pawn> killesAndNameComp = new KillesCompare().thenComparing(new SerialNumberCompare()).thenComparing(new NameCompare());
        Collections.sort(onlyPawns, killesAndNameComp);

        for (Pawn i : onlyPawns) {
            if (i.getKills() > 0) {
                System.out.println(i.getName() + ": " + i.getKills() + " kills");
            }
        }
    }

    /**
     * print the number of squares of each Piece that moved from the longest road to the least.
     * if there is similar number of squares - the piece with the bigger serial number will be first.
     * if there is similar number of squares AND same serial number - the piece of the winning team will be first.
     */
    public void printDistance(){
        Comparator<ConcretePiece> distAndSerialAndNameComp = new DistanceCompare().thenComparing(new SerialNumberCompare()).thenComparing(new NameCompare());
        Collections.sort(allPieces, distAndSerialAndNameComp);

        for (ConcretePiece i : allPieces) {
            if (i.getDistance() > 0) {
                System.out.println(i.getName() + ": " + i.getDistance() + " squares");
            }
        }
    }

    public void printPositionsThatHaveBeenStepped(){

        ArrayList<Position> positionArrayList = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                positionArrayList.add(steppedOn[j][i]);

            }

        }

        Comparator<Position> stappedXAndYComp = new PositionTakenCompare().thenComparing(new xCompare()).thenComparing(new yCompare());
        Collections.sort(positionArrayList, stappedXAndYComp);

        for (Position i : positionArrayList) {
            if (i.getSteppedOnMe() > 1) {
                System.out.println(i.toString() + i.getSteppedOnMe() + " pieces");
            }
        }

    }
    public void printAllStuff(){
        printSteps();
        System.out.println("***************************************************************************");
        printKilles();
        System.out.println("***************************************************************************");
        printDistance();
        System.out.println("***************************************************************************");
        printPositionsThatHaveBeenStepped();
        System.out.println("***************************************************************************");
    }

}


