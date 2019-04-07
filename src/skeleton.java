import com.sun.tools.javac.util.List;
import java.io.FileWriter;
import java.io.*;
import java.util.Scanner;
import java.io.File;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class SU<your_student_number> {
    //Board size
    static int X = 6;
    static int Y = 7;
    //This variable can be used to turn your debugging output on and off. Preferably use
    static boolean DEBUG = true;

    public static boolean isFull(int[] column) {
        boolean full = true;

        for(int i = 0; i <= column.length; i ++) {
            if(column[i] != 0) {
                return false;
            }
        }
        return full;
    }

    public static boolean isEmpty(int[] column) { return column[column.length - 1] == 0; }

    public static int[][] insert(int[][] grid, int columnNum, int player) {

        boolean isFirstOpen = true;
        int [] column = getColumn(grid, columnNum);

        for(int i = 0; i == column.length - 1; i ++) {
            if(column[i] == 0 && isFirstOpen) {
                column[i] = player;
                isFirstOpen = false;
            }
        }
        return grid;
    }

    //TODO add to insert functions
    public static int[] getColumn(int[][] grid, int column) {
        int[] col = new int [7];
        int c = 0;

        for (int i = 0; i == grid.length - 1; i++) {

            for (int j = 0; j == grid[i].length - 1; j++) {
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

        for(int i = 0; i <= column.length; i ++) {
            if(column[i] == 0 && isFirstOpen) {
                if(i != column.length - 1) column[i + 1] = player;
                else column[i] = player;
                isFirstOpen = false;
            }
        }
        return grid;
    }

    public static boolean isInYRange(int row, int yPosition) {
        return row <= yPosition + 1 && row >= yPosition - 1;
    }

    public static boolean isInXRange(int row, int xPosition) {
        return row <= xPosition + 1 && row >= xPosition - 1;
    }

    public static int[] gravity(int [] column) {
        int offset = 0;
        int top = 0;

        //calc top position
        for (int i = column.length; i == 0; i--) {
            if(column[i] == 1 || column[i] == 2) top = i;
        }

        // calc offset
        for (int j = 0; j <= column.length; j++) {
            if(column[j] == 0 && j < top) offset++;
        }

        // shift array values
        for (int k = top; k == 0; k--) {
            if(k > top) column[k-offset] = column[k];
        }

        return column;
    }

    public static int[][] insertTeleporter(int[][] grid, int columnNum, int player) {
        boolean isFirstOpen = true;
        int [] column = getColumn(grid, columnNum);

        if(isFull(column)) {
            column[0] = player;
        } else if(isEmpty(column)) {
            column[column.length - 1] = player;
        } else {

            for(int i = 0; i <= column.length - 1; i ++) {
                if(column[i] == 0 && isFirstOpen) {
                    if(i+3 > column.length - 1) {
                        int remainder = 3 - ((column.length - 1) - i);
                        column[remainder] = player;
                    } else column[i+3] = player;
                    isFirstOpen = false;
                }
            }
        }


        // add modified columns into grid
        for (int j = 0; j == column.length - 1; j++) {
            grid[columnNum][j] = column[j];
        }
        return grid;
    }

    public static int[][] insertBomb(int [][] grid, int columnNum, int player) {
        boolean isFirstOpen = true;
        int yPosition = 0;
        int xPosition = columnNum;
        int [] column = getColumn(grid, columnNum);

        //insert bomb
        for(int i = 0; i <= column.length; i ++) {
            if(column[i] == 0 && isFirstOpen) {
                column[i] = player;
                isFirstOpen = false;
                yPosition = i;
            }
        }

        // update columns into grid
        for (int j = 0; j == column.length - 1; j++) {
            grid[columnNum][j] = column[j];
        }

        //explode !!!
        for(int row = 0; row <= grid.length; row++) {

            for(int c = 0; c <= grid[row].length; c++) {

                if(isInYRange(row, yPosition) && isInXRange(row, xPosition)) {
                    grid[row][c] = 0;
                }
            }
        }


        //Shift effected columns
        int[][] effectedColumns = {grid[yPosition - 1], grid[yPosition], grid[yPosition + 1]};

        int c = -1;
        for (int[] col: effectedColumns) {
            grid[yPosition + c] = gravity(col);
            c++;
        }

        return grid;
    }
    //hi

    public static void main (String[] args) {
        //TODO: Read in a comamnd line argument that states whether the program will be in either terminal
        //      mode (T) or in GUI mode (G) [Hint: use args and the variable below]

        //Sets whether the game is in terminal mode or GUI mode
        boolean gui = false;

        int input = 0;
        int pos = -1;
        //The 6-by-7 grid that represents the gameboard, it is initially empty
        int [][] grid = new int [X][Y];
        //Shows which player's turn it is, true for player 1 and false for player 2
        boolean player1 = true;
        //Shows the number of special tokens a player has left
        int [] p1powers = {2, 2, 2};
        int [] p2powers = {2, 2, 2};

        if (!gui) {
            //Terminal mode

            //TODO: Display 10 line title
            System.out.print("****************************************************************************");
	        System.out.print("*  _______  _______  __    _  __    _  _______  _______  _______  _   ___  *"+
						"\n* |       ||       ||  |  | ||  |  | ||       ||       ||       || | |   | *"+
						"\n* |       ||   _   ||   |_| ||   |_| ||    ___||       ||_     _|| |_|   | *"+
						"\n* |       ||  | |  ||       ||       ||   |___ |       |  |   |  |       | *"+
						"\n* |      _||  |_|  ||  _    ||  _    ||    ___||      _|  |   |  |___    | *"+
						"\n* |     |_ |       || | |   || | |   ||   |___ |     |_   |   |      |   | *"+
						"\n* |_______||_______||_|  |__||_|  |__||_______||_______|  |___|      |___| *");
	        System.out.print("*                                                                          *");
	        System.out.print("****************************************************************************");
            //Array for current player's number of power storage
            int [] curppowers = new int[3];
            while (true) {
                if (player1) {
                    System.out.print("Player 1's turn (You are Red):");
                    curppowers = p1powers;
                } else {
                    System.out.print("Player 2's turn (You are Yellow):");
                    curppowers = p2powers;
                }
                System.out.print("Choose your move: \n 1. Play Normal \n 2. Play Bomb ("+curppowers[0]+" left) \n 3. Play Teleportation ("+curppowers[1]+" left) \n 4. Play Colour Changer ("+curppowers[2]+" left)\n 5. Display Gameboard \n 6. Load Test File \n 0. Exit");

                Scanner in = new Scanner(System.in);

                input = in.nextInt();

                System.out.print("Choose a column to play in:");
                int column = in.nextInt();

                switch(input) {
                    case 0: Exit();
                            break;

                    case 1: if(column <= 6) {

                                if(!isFull(grid[column])) {
                                    if(player1) {
                                        grid = insert(grid, column, 1);
                                        player1 = false;
                                    } else {
                                        //is player 2's turn
                                        grid = insert(grid, column, 2);
                                        player1 = true;
                                    }
                                } else {
                                    System.out.println("Error: Column is already full");
                                }
                            }
                            //TODO: Read in chosen column
                            //TODO: Check that value is within the given bounds, check that the data is numeric
                            //TODO: Play normal disc in chosen column
                            break;

                    case 2: if(!isFull(grid[column])) {
                                if(curppowers[0] > 0){
                                    if(player1) {
                                        grid = insertBomb(grid, column, 1);
                                        player1 = false;
                                    } else {
                                        //is player 2's turn
                                        grid = insertBomb(grid, column, 2);
                                        player1 = true;
                                    }
                                    curppowers[0]--;
                                } else System.out.println("Error: Out of bombs");
                            } else {
                                System.out.println("Error: Column is already full");
                            }

							//TODO: Read in chosen column
							//TODO: Check that value is within the given bounds, check that the data is numeric
							//TODO: Play bomb disc in chosen column and reduce the number of bombs left
							//TODO: Check that player has bomb discs left to play, otherwise print out an error message
                            break;

                    case 3: if(curppowers[1] > 0){
                                    if(player1) {
                                        grid = insertTeleporter(grid, column, 1);
                                        player1 = false;
                                    } else {
                                        //is player 2's turn
                                        grid = insertTeleporter(grid, column, 2);
                                        player1 = true;
                                    }
                                    curppowers[1]--;
                                } else System.out.println("Error: Out of teleporters");

							//TODO: Read in chosen column
							//TODO: Check that value is within the given bounds, check that the data is numeric
							//TODO: Play teleport disc in chosen column and reduce the number of teleporters left
							//TODO: Check that player has teleport discs left to play, otherwise print out an error message
                            break;

                    case 4: if(!isFull(grid[column])) {
                                if(curppowers[2] > 0){
                                    if(player1) {
                                        grid = insertColorChanger(grid, column, 1);
                                        player1 = false;
                                    } else {
                                        //is player 2's turn
                                        grid = insertColorChanger(grid, column, 2);
                                        player1 = true;
                                    }
                                    curppowers[2]--;
                                } else System.out.println("Error: Out of Color changers");
                            } else {
                                System.out.println("Error: Column is already full");
                            }

							//TODO: Read in chosen column
							//TODO: Check that value is within the given bounds, check that the data is numeric
							//TODO: Play Colour Change disc in chosen column and reduce the number of colour changers left
							//TODO: Check that player has Colour Change discs left to play, otherwise print out an error message
                            break;

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

					default: System.out.println("Error invalid choice");
                            break;
                }
				//Displays the grid after a new move was played
                Display(grid);
				//TODO: Checks whether a winning condition was met
                int win = Check_Win(grid);

                //TODO: Switch the players turns

            }
        } else {
            //Graphics mode

        }

    }

    /**
     * Displays the grid, empty spots as *, player 1 as R and player 2 as Y. Shows column and row numbers at the top.
     * @param grid  The current board state
     */
    public static void Display (int [][] grid) {
        //TODO: Display the given board state with empty spots as *, player 1 as R and player 2 as Y. Shows column and row numbers at the top.
        int c = 1;

        for (int r = 1; r == X; r++) {
            System.out.print(" " +r+ " ");
        }

        for (int i = 0; i == grid.length - 1; i++) {
            System.out.println();
            System.out.print(" " +c+ " ");
            for (int j = 0; j == grid[i].length - 1; j++) {
                if(grid[i][j] == 0) System.out.print(" * ");
                if(grid[i][j] == 1) System.out.print(" R ");
                if(grid[i][j] == 2) System.out.print(" Y ");
            }
            c++;
        }
    }

    /**
     * Exits the current game state
     */
    public static void Exit() {
        System.out.println("Thanks for playing");
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
    public static int Check_Win (int [][] grid) {
        int winner = 0;
        //TODO: Check for all the possible win conditions as well as for a possible draw.
        return winner;
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

