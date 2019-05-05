import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class SU21807744 {
    //Board size
    static int X = 6;
    static int Y = 7;
    //This variable can be used to turn your debugging output on and off. Preferably use
    static boolean DEBUG = true;

    public static boolean isFull(int[] column) {
        boolean full = true;

        for(int i = 0; i < column.length; i ++) {
            if(column[i] == 0) {
                return false;
            }
        }
        return full;
    }

    public static boolean isEmpty(int[] column) { return column[column.length - 1] == 0; }

    public static int[][] insert(int[][] grid, int columnNum, int player) {

        boolean isFirstOpen = true;
        int [] column = getColumn(grid, columnNum);

        for(int i = column.length - 1 ; i >= 0 ; i --) {
            if(column[i] == 0 && isFirstOpen) {
                column[i] = player;
                isFirstOpen = false;
            }
        }

        updateColumn(grid, column, columnNum);
        return grid;
    }

    //TODO add to insert functions

    /**
     * takes index of column and returns it as a array
     *
     * @param grid int[][]
     * @param column int
     * @return int[]
     */
    public static int[] getColumn(int[][] grid, int column) {
        int[] col = new int [grid.length];
        int c = 0;

        for (int i = 0; i < grid.length ; i++) {

            for (int j = 0; j < grid[i].length; j++) {
                if(j == column) {
                    col[c] = grid[i][j];
                    c++;
                }
            }
        }

        return col;
    }

    public static int[][] insertColorChanger(int[][] grid, int columnNum, int player) {

        boolean isFirstOpen = true;
        int [] column = getColumn(grid, columnNum);

        for(int i = column.length - 1; i > 0; i --) {
            if(column[i] == 0 && isFirstOpen) {
                if(i != column.length - 1) column[i + 1] = player;
                else {
                    column[i] = player; //if empty insert player disk
                }
                isFirstOpen = false;
            }
        }

        updateColumn(grid, column, columnNum);

        return grid;
    }

    public static boolean isInYRange(int row, int yPosition) {
        return row == yPosition + 1 || row == yPosition - 1 || row == yPosition;
    }

    public static boolean isInXRange(int column, int xPosition) {
        return column == xPosition + 1 || column == xPosition - 1 || column == xPosition;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }

    public static int[] gravity(int [] column) {
        boolean isFirst = true;
        int offset = 0;
        int top = 0;

        //calc top position
        for (int i = 0; i < column.length; i++) {
            if((column[i] == 1 || column[i] == 2) && isFirst) {
                top = i;
                isFirst = false;
            }
        }

        // calc offset
        for (int j = column.length - 1; j > 0; j--) {
            if(column[j] == 0 && j > top) offset++;
        }

        // shift array values
        for (int k = top; k < column.length; k++) {
            if((k + offset) < (column.length)) {
                column[k+offset] = column[k];
                column[k] = 0;
            }
        }

        return column;
    }

    public static int[][] insertTeleporter(int[][] grid, int columnNum, int player) {

        boolean isFirstOpen = true;
        int [] column = getColumn(grid, columnNum);
        int destinationColNum = 0;

        if(isFull(column)) {
            column[0] = player;
            updateColumn(grid, column, columnNum);
        } else if(isEmpty(column)) {
            column[column.length - 1] = player;
            updateColumn(grid, column, columnNum);
        } else {

            for(int i = column.length - 1; i > 0; i --) {
                if(column[i] == 0 && isFirstOpen) {
                    if(columnNum+3 > column.length) {
                        System.out.println(column.length);
                        System.out.println(i);
                        int remainder = 3 - (column.length - columnNum);
                        grid[i][remainder - 1] = player;
                        destinationColNum = remainder - 1;
                    } else {
                        grid[i][columnNum+3] = player;
                        destinationColNum = columnNum+3;
                    }
                    isFirstOpen = false;
                }
            }

            System.out.println("Dest "+destinationColNum);
           int[] destCol = gravity(getColumn(grid, destinationColNum)); // retrieve and apply gravity to column

            updateColumn(grid, destCol, destinationColNum); // update column in grid
        }

        return grid;
    }

    public static void printMatrix(int[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void invalidInsert(int[][] grid, boolean player1){
        System.out.println("Illegal move, please input legal move:");
        if(StdIn.hasNextChar()){

            String i = StdIn.readString();

            if(i.matches("[0-6]")){
                int column = Integer.parseInt(i);

                if(column <= 6) {

                    if(!isFull(getColumn(grid, column))) {
                        if(player1) {
                            grid = insert(grid, column, 1);
                        } else {
                            //is player 2's turn
                            grid = insert(grid, column, 2);
                        }
                    } else {
                        StdOut.print("Column is full.\n");
                    }
                }
            } else {
                invalidInsert(grid, player1);
            }
        }
    }

    public static void invalidInsertBomb(int [][] grid, boolean player1, int [] curppowers) {
        StdOut.println("Illegal move, please input legal move:");

        if(StdIn.hasNextChar()) {
            String in = StdIn.readString();

            if(in.matches("^([0-6])$")){
                int column = Integer.parseInt(in);

                if(!isFull(getColumn(grid, column))) {
                    if(curppowers[0] > 0){
                        if(player1) {
                            grid = insertBomb(grid, column, 1);
                        } else {
                            //is player 2's turn
                            grid = insertBomb(grid, column, 2);
                        }
                        curppowers[0]--;
                    } else StdOut.println("Error: Out of bombs");
                } else {
                    StdOut.println("Column is full.\n");
                }

            } else {
                invalidInsertBomb(grid, player1, curppowers);
            }

        } else {
            invalidInsertBomb(grid, player1, curppowers);
        }
    }

    public static int[][] insertBomb(int [][] grid, int columnNum, int player) {
        boolean isFirstOpen = true;

        //position of bomb
        int yPosition = 0;
        int xPosition = columnNum;

        int [] column = getColumn(grid, columnNum);

        //insert bomb

        for(int i = column.length - 1; i > 0; i--) {
            if(column[i] == 0 && isFirstOpen) {
                column[i] = player;
                isFirstOpen = false;
                yPosition = i;
            }
        }

        // update columns into grid
        for (int j = 0; j < column.length; j++) {
            grid[j][columnNum] = column[j];
        }

        //explode !!!
        for(int r = 0; r < grid.length; r++) {

            for(int c = 0; c < grid[r].length; c++) {

                if(isInYRange(r, yPosition) && isInXRange(c, xPosition)) {
                    grid[r][c] = 0;
                }
            }
        }

        //Insert bomb token
        grid[yPosition][xPosition] = player;

        //Shift effected columns
        int[][] effectedColumns = {getColumn(grid,xPosition - 1), getColumn(grid, xPosition), getColumn(grid,xPosition + 1)};

        int c = -1;
        for (int[] col: effectedColumns) {
            updateColumn(grid, gravity(col), xPosition + c);
            c++;
        }

        return grid;
    }

    public static void updateColumn(int [][] grid, int[] column, int pos) {
        if(pos >= 0) {
            for (int i = 0; i < grid.length; i++) {
                grid[i][pos] = column[i];
            }
        }
    }

    public static boolean player1 = true;
    public static boolean gui = true;

    public static void main (String[] args) {
        //TODO: Read in a comamnd line argument that states whether the program will be in either terminal
        //      mode (T) or in GUI mode (G) [Hint: use args and the variable below]

        //Sets whether the game is in terminal mode or GUI mode

        int input = 0;
        int pos = -1;
        //The 6-by-7 grid that represents the gameboard, it is initially empty
        int [][] grid = new int [X][Y];
        //Shows which player's turn it is, true for player 1 and false for player 2
//        boolean player1 = true;
        //Shows the number of special tokens a player has left
        int [] p1powers = {2, 2, 2};
        int [] p2powers = {2, 2, 2};

        if (!gui) {
            //Terminal mode

            //TODO: Display 10 line title
            StdOut.println("****************************************************************************");
            StdOut.println("*  _______  _______  __    _  __    _  _______  _______  _______  _   ___  *"+
						"\n* |       ||       ||  |  | ||  |  | ||       ||       ||       || | |   | *"+
						"\n* |       ||   _   ||   |_| ||   |_| ||    ___||       ||_     _|| |_|   | *"+
						"\n* |       ||  | |  ||       ||       ||   |___ |       |  |   |  |       | *"+
						"\n* |      _||  |_|  ||  _    ||  _    ||    ___||      _|  |   |  |___    | *"+
						"\n* |     |_ |       || | |   || | |   ||   |___ |     |_   |   |      |   | *"+
						"\n* |_______||_______||_|  |__||_|  |__||_______||_______|  |___|      |___| *");
            StdOut.println("*                                                                          *");
            StdOut.println("****************************************************************************");
            //Array for current player's number of power storage
            int [] curppowers = new int[3];
            while (true) {
                if (player1) {
                    StdOut.print("Player 1's turn (You are Red):");
                    curppowers = p1powers;
                } else {
                    StdOut.print("Player 2's turn (You are Yellow):");
                    curppowers = p2powers;
                }
                StdOut.print("\nChoose your move: \n 1. Play Normal \n 2. Play Bomb ("+curppowers[0]+" left) \n 3. Play Teleportation ("+curppowers[1]+" left) \n 4. Play Colour Changer ("+curppowers[2]+" left)\n 5. Display Gameboard \n 6. Load Test File \n 0. Exit \n");
                if(StdIn.hasNextChar()) {

                    String i = StdIn.readString();

                    if(i.matches("-?\\d+")){
                        input = Integer.parseInt(i);
                    } else {
                        input = 8; //default invalid value
                    }

                } else {
                    input = 8;
                }

                switch(input) {
                    case 0: Exit();
                            break;

                    case 1: {
                        StdOut.print("Choose a column to play in:\n");

                        if(StdIn.hasNextChar()){

                            String i = StdIn.readString();

                            if(i.matches("^([0-6])$")){
                                int column = Integer.parseInt(i);

                                if(column <= 6) {

                                    if(!isFull(getColumn(grid, column))) {
                                        if(player1) {
                                            grid = insert(grid, column, 1);
                                        } else {
                                            //is player 2's turn;
                                            grid = insert(grid, column, 2);
                                        }
                                    } else {
                                        StdOut.print("Column is full.\n");
                                    }
                                }
                            } else {
                                invalidInsert(grid, player1);
                            }
                        }
                        break;
                    }

                    case 2: {
                        StdOut.print("Choose a column to play in:");

                        if(StdIn.hasNextChar()) {
                            String in = StdIn.readString();

                            if(in.matches("^([0-6])$")){
                                int column = Integer.parseInt(in);

                                if(!isFull(getColumn(grid, column))) {
                                    if(curppowers[0] > 0){
                                        if(player1) {
                                            grid = insertBomb(grid, column, 1);
                                        } else {
                                            //is player 2's turn
                                            grid = insertBomb(grid, column, 2);
                                        }
                                        curppowers[0]--;
                                    } else StdOut.println("Error: Out of bombs");
                                } else {
                                    StdOut.println("Column is full.\n");
                                }

                            } else {
                                StdOut.println("Illegal move, please input legal move:");
                            }

                        } else {
                            StdOut.println("Illegal move, please input legal move:");
                        }

                        break;
                    }

                    case 3: {
                        StdOut.print("Choose a column to play in:");
                        int column = StdIn.readInt();

                        if(curppowers[1] > 0){
                            if(player1) {
                                grid = insertTeleporter(grid, column, 1);
                            } else {
                                //is player 2's turn
                                grid = insertTeleporter(grid, column, 2);
                            }
                            curppowers[1]--;
                        } else StdOut.println("Error: Out of teleporters");

                        break;
                    }

                    case 4: {
                        StdOut.print("Choose a column to play in:");
                        int column = StdIn.readInt();

                        if(!isFull(getColumn(grid, column))) {
                            if(curppowers[2] > 0){
                                if(player1) {
                                    grid = insertColorChanger(grid, column, 1);
                                } else {
                                    //is player 2's turn
                                    grid = insertColorChanger(grid, column, 2);
                                }
                                curppowers[2]--;
                            } else StdOut.println("Error: Out of Color changers");
                        } else {
                            StdOut.println("Error: Column is already full");
                        }

                        break;
                    }

					//This part will be used during testing, please DO NOT change it. This will result in loss of marks
                    case 5: Display(grid);
                    		//Displays the current gameboard again, doesn't count as a move, so the player stays the same
                            player1 = !player1;
                            break;

					//This part will be used during testing, please DO NOT change it. This will result in loss of marks
                    case 6: grid = Test(StdIn.readString());
                    		//Reads in a test file and loads the gameboard from it.
                            player1 = !player1;
                            break;
                    //This part will be used during testing, please DO NOT change it. This will result in loss of marks
                    case 7: Save(StdIn.readString(), grid);
				            player1 = !player1;
				            break;

					default: StdOut.println("Please choose a valid option");
                            break;
                }
				//Displays the grid after a new move was played
                Display(grid);
                int win = Check_Win(grid, player1);

                if(win != 0) {
                    StdOut.println("Player "+ win +" wins!");
                    System.out.println("Do you want to play again?");
                    System.out.println(" 1. Yes");
                    System.out.println(" 2. No");

                    int play = StdIn.readInt();
                    if(play == 2){
                        Exit();
                    } else {
                        clearBoard(grid);
                        player1 = true;
                    }
                }

                if(input != 8) { //if input not invalid
                    player1 = !player1;
                }

            }
        } else {
            //Graphics mode

            try {
                int [] curppowers = new int[3];
                GUI.main(new String [1]);

                while(true) {

                    if (player1) {
                        GUI.currentPlayer = "player1";
                        GUI.curpowers = p1powers;
                    } else {
                        GUI.currentPlayer = "player2";
                        GUI.curpowers = p2powers;
                    }

                    int win = Check_Win(GUI.grid, player1);

                    if(win != 0) {
                        clearBoard(GUI.grid);
                        GUI.clearGUI();
                        GUI.DisplayWinner(win);
                        p1powers = new int []{2,2,2};
                        p2powers = new int []{2,2,2};
                    }
                }

            } catch (Exception e) {
                System.out.println("Something terribly wrong happened");
                e.printStackTrace();
            }


        }

    }
    
    public static void clearBoard(int [][] grid){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = 0;
            }
        }
    }

    /**
     * Displays the grid, empty spots as *, player 1 as R and player 2 as Y. Shows column and row numbers at the top.
     * @param grid  The current board state
     */
    public static void Display (int [][] grid) {
        int c = 0;

        System.out.println();
        StdOut.print("  0");
        for (int r = 0; r < Y - 1; r++) {
            StdOut.print(" "+ (r+1));
        }

        for (int i = 0; i < grid.length ; i++) {
            StdOut.println();
            StdOut.print(c);
            for (int j = 0; j < grid[i].length ; j++) {
                if(grid[i][j] == 0) StdOut.print(" *");
                if(grid[i][j] == 1) StdOut.print(" R");
                if(grid[i][j] == 2) StdOut.print(" Y");
            }
            c++;
        }
        System.out.println("\n");
    }

    /**
     * Exits the current game state
     */
    public static void Exit() {
        System.exit(0);
        //TODO: Exit the game
    }


    /**
     * Plays a normal disc in the specified position (i) in the colour of the player given. Returns the modified grid or
     * prints out an error message if the column is full.
     * @param i         Column that the disc is going to be played in
     * @param grid      The current board state
     * @param player1   The current player
     * @return grid     The modified board state
     */
    public static int [][] Play (int i, int [][] grid, boolean player1) {
        //TODO: Play a normal disc of the given colour, if the column is full, print out the message: "Column is full."
        return grid;
    }

    /**
     *Checks whether a player has 4 tokens in a row or if the game is a draw.
     * @param grid The current board state in a 2D array of integers
     */
    public static int Check_Win (int [][] grid, boolean player1) {
        int player = player1 ? 1 : 2;
        int winner = 0;


        if(vertical(grid, player) || horizontal(grid, player)  || ascDiagonal(grid, player) || descDiagonal(grid, player)){
            return player;
        } else {
            checkDraw(grid, winner);
        }
        return winner;
    }

    public static void checkDraw(int [][] grid, int winner) {
        int full = 0;
        for (int i = 0; i < grid[0].length; i++) {
            if(grid[0][i] == 1 || grid[0][i] == 2) {
                full++;
            }
        }
        if (grid[0].length == full) {
            winner = 0; //Draw

            if(!gui) {
                System.out.println("Draw!");
                System.out.println("Do you want to play again?");
                System.out.println(" 1. Yes");
                System.out.println(" 2. No");

                int play = StdIn.readInt();
                if(play == 2){
                    Exit();
                } else {
                    clearBoard(grid);
                }
            } else {
                clearBoard(grid);
                GUI.clearGUI();
                GUI.DisplayDraw();
            }
        }
    }

    public static boolean ascDiagonal(int [][] grid, int player) {
        for (int i=3; i<X; i++){
            for (int j=0; j<Y-3; j++){
                boolean check = has4ConsecutiveDiagAsc(grid, i, j, 0, player);
                if (check) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean descDiagonal(int [][] grid, int player) {

        for (int i=X-1; i > 0; i--){
            for (int j=Y-1; j > 0; j--){
                boolean check = has4ConsecutiveDiagDesc(grid, i, j, 0, player);
                if (check) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean vertical(int [][] grid, int player) {

        for (int i = 0; i < X; i++) {
            boolean check = has4Consecutive(grid[i], 0, 0, player);
            if(check){
                return true;
            }
        }

        return false;
    }

    public static boolean horizontal(int[][] grid, int player) {

        for (int i = 0; i < Y; i++){
            if(has4Consecutive(SU21807744.getColumn(grid, i), 0, 0, player)){
                return true;
            }
        }

        return false;
    }

    public static boolean has4Consecutive(int[] array, int offset, int count, int player){
        if(offset == array.length) { // exits loop
            return count == 4;
        } else { // main logic
            if(count == 4){
                return true;
            }
            int c = array[offset] == player ? count + 1 : 0; // boolean ? if true : else this
            return has4Consecutive(array, offset + 1, c , player);
        }
    }

    public static boolean has4ConsecutiveDiagDesc(int [][] grid, int xOffset, int yOffset, int count, int player) {
        if(xOffset == X || yOffset == Y || xOffset < 0 || yOffset < 0) { // exits loop yOffset == 0
            return count == 4;
        } else {
            if(count == 4) {
                return true;
            }
            int c = grid[xOffset][yOffset] == player ? count + 1 : 0;

            return has4ConsecutiveDiagDesc(grid, xOffset - 1, yOffset - 1, c, player);
        }
    }

    public static boolean has4ConsecutiveDiagAsc(int[][] grid, int xOffset, int yOffset, int count, int player) {
        if(xOffset == X || yOffset == Y || xOffset < 0 || yOffset < 0) { // exits loop yOffset == 0
            return count == 4;
        } else {
            if(count == 4) {
                return true;
            }
            int c = grid[xOffset][yOffset] == player ? count + 1 : 0;

            return has4ConsecutiveDiagAsc(grid, xOffset - 1, yOffset + 1, c, player);
        }
    }

    /**
     * Plays a bomb disc that blows up the surrounding tokens and drops down tokens above it. Should not affect the
     * board state if there's no space in the column. In that case, print the error message: "Column is full."
     * @param i         Column that the disc is going to be played in
     * @param grid      The current board state
     * @param player1   The current player
     * @return grid     The modified board state
     */
    public static int [][] Bomb (int i, int [][] grid, boolean player1) {
        //TODO: Play a bomb in the given column and make an explosion take place. Discs should drop down afterwards. Should not affect the
        //      board state if there's no space in the column. In that case, print the error message: "Column is full."
        //      Leaves behind a normal disc of the player's colour
        return grid;
    }

    /**
     * Plays a teleporter disc that moves the targeted disc 3 columns to the right. If this is outside of the board
     * boundaries, it should wrap back around to the left side. If the column where the targeted disc lands is full,
     * destroy that disc. If the column where the teleporter disc falls is full, play as normal, with the teleporter
     * disc replacing the top disc.
     * @param i         Column that the disc is going to be played in
     * @param grid      The current board state
     * @param player1   The current player
     * @return grid     The modified board state
     */
    public static int [][] Teleport (int i, int [][] grid, boolean player1) {
        //TODO: Play a teleporter disc that moves the targeted disc 3 columns to the right. If this is outside of the board
        //      boundaries, it should wrap back around to the left side. If the column where the targeted disc lands is full,
        //      destroy that disc. If the column where the teleporter disc falls is full, play as normal, with the teleporter
        //      disc replacing the top disc.
        //      No error message is required.
        //      If the colour change disc lands on the bottom row, it must leave a disc of the current player’s colour.
        return grid;
    }

    /**
     * Plays the colour changer disc that changes the affected disc's colour to the opposite colour
     * @param i         Column that the disc is going to be played in
     * @param grid      The current board state
     * @param player   The current player
     * @return grid     The modified board state
     */
    public static int [][] Colour_Changer (int i, int [][] grid, boolean player) {
        //TODO: Colour Change: If the colour change disc lands on top of another disc, it changes the colour of that
        //      disc to the opposite colour. The power disc does not remain.
        //      If the colour change disc lands on the bottom row, it must leave a disc of the current player’s colour.
        return grid;
    }

    /**
     * Reads in a board from a text file.
     * @param name  The name of the given file
     * @return
     */
    //Reads in a game state from a text file, assumes the file is a txt file
    public static int [][] Test(String name) {
        int [][] grid = new int[6][7];
        try{
            File file = new File(name+".txt");
            Scanner sc = new Scanner(file);

            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++) {
                    grid[i][j] = sc.nextInt();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return grid;
    }

    /**
     * Saves the current game board to a text file.
     * @param name  The name of the given file
     * @param grid  The current game board
     * @return
     */
    // Used for testing
    public static int [][] Save(String name, int [][] grid) {
    	try{
    		FileWriter fileWriter = new FileWriter(name+".txt");
	    	for (int i = 0; i < X; i++) {
	    		for (int j = 0; j < Y; j++) {
		    		fileWriter.write(Integer.toString(grid[i][j]) + " ");
		    	}
		    	fileWriter.write("\n");
		    }
		    fileWriter.close();
	    } catch (Exception ex) {
            ex.printStackTrace();
        }
    	return grid;
    }

    /**
     * Debugging tool, preferably use this since we can then turn off your debugging output if you forgot to remove it.
     * Only prints out if the DEBUG variable is set to true.
     * @param line  The String you would like to print out.
     */
    public static void Debug(String line) {
        if (DEBUG)
            System.out.println(line);
    }
}

