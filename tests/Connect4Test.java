import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Connect4Test {

    static int X = 6;
    static int Y = 7;

    @Test
    public void insertTeleporter(){
        int[][] grid = { {2,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,1,2,2} };

        //is Full
        int[][] fullExpected = { {1,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,1,2,2} };
        int[][] fullResult = SU.insertTeleporter(grid, 0, 1);

        assertTrue(fullExpected[0][0] == fullResult[0][0]);


        // is Empty
        int[][] emptyExpected = { {1,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,0,2,2} };
        int[][] emtpyResult = SU.insertTeleporter(emptyExpected, 1, 1);

        assertTrue(1 == fullResult[3][1]);

        // wrap around
        int[][] wrapAroundExpected = { {2,0,2,2}, {2,0,2,2}, {1,0,2,2} ,{2,1,2,2} };
        int[][] wrapAroundResult = SU.insertTeleporter(grid, 1, 1);

        assertTrue(wrapAroundExpected[2][0] == wrapAroundResult[2][0]);

        // standard teleport
        int[][] teleportExpected = { {0,0,2,2}, {0,0,2,2}, {0,0,2,2} ,{2,2,2,2}  };
        int[][] teleportResult = SU.insertTeleporter(teleportExpected, 0, 1);

        assertTrue(1 == teleportResult[2][3]);
    }

    @Test
    public void insertBomb() {
        int[][] grid = { {2,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,1,2,2} };
        int[][] expectedGrid = { {0,0,0,2}, {0,0,0,2}, {0,0,0,2}, {2,1,2,2}};
        int columnNum = 1;
        int player = 1;


        int [][] result = SU.insertBomb(grid, columnNum, player);

        printMatrix(result);
        System.out.println();
        printMatrix(expectedGrid);

        assertEquals(result, expectedGrid);
    }

    @Test
    public void getColumn(){
        int[][] grid = { {2,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,1,2,2} };
        int[] expected = {0,0,0,1};

        int[] result = SU.getColumn(grid, 1);

       assertTrue(Arrays.equals(result, expected));
    }

    @Test
    public void gravity(){
        int[] column  = { 2, 0, 0, 0 };
        int[] expected = { 0, 0, 0, 2 };
        int[] result = SU.gravity(column);

        printArray(result);
        System.out.println();
        printArray(expected);

        assertTrue(Arrays.equals(result, expected));
    }

    @Test
    public void updateColumn() {
        int[][] grid = { {2,1,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,0,2,2} };
        int [] column = {0 ,0, 0, 1};

        SU.updateColumn(grid, column, 1);
        printMatrix(grid);
    }

    @Test
    public void isInYRange(){
        assertFalse(SU.isInYRange(0, 2));
        assertTrue(SU.isInYRange(1, 2));
    }

    @Test
    public void isInXRange(){
        assertFalse(SU.isInXRange(0, 2));
        assertTrue(SU.isInXRange(1, 2));
    }

    @Test
    public void insertColorChanger(){
        int[][] grid = { {2,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,1,2,2} };
        int[][] result = SU.insertColorChanger(grid, 1, 2);

        assertTrue(result[3][1] == 2);

        int[][] emptyCol = { {2,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,0,2,2} };
        int[][] emptyRes = SU.insertColorChanger(emptyCol, 1, 2);

        assertTrue(emptyRes[3][1] == 2);


    }

    @Test
    public void insert(){
        int[][] grid = { {2,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,1,2,2} };
        int [][] result = SU.insert(grid, 1, 2);

        assertTrue(result[2][1] == 2);
    }

    @Test
    public void display() {
        int[][] grid = { {2,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,1,2,2} };

        SU.Display(grid);
    }

    @Test
    public void checkD(){
        int[][] grid = {
                {0,2,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,2,0,2,0},
                {2,0,0,0,2,0,0},
                {0,0,0,2,0,0,0},
                {0,0,2,0,0,0,0}
        };

    }

    public boolean diagonalCheck(int [][] grid, int player){
//
//        int c = 1;
//        for (int r = 0; r < X; r++) {
//            if (grid[r][c] == player && grid[r-1][c+1] == player && grid[r-2][c+2] == player && grid[r-3][c+3] == player)
//                return true;
//            c++;
//        }

        // horizontalCheck
        for (int j = 0; j<Y-3 ; j++ ){
            for (int i = 0; i<X; i++){
                if (grid[i][j] == player && grid[i][j+1] == player && grid[i][j+2] == player && grid[i][j+3] == player){
                    return true;
                }
            }
        }
        // verticalCheck
        for (int i = 0; i<X-3 ; i++ ){
            for (int j = 0; j<Y; j++){
                if (grid[i][j] == player && grid[i+1][j] == player && grid[i+2][j] == player && grid[i+3][j] == player){
                    return true;
                }
            }
        }

        // ascendingDiagonalCheck
        for (int i=3; i<X; i++){
            for (int j=0; j<Y-3; j++){
                if (grid[i][j] == player && grid[i-1][j+1] == player && grid[i-2][j+2] == player && grid[i-3][j+3] == player)
                    return true;
            }
        }
        // descendingDiagonalCheck
        for (int i=3; i<X; i++){
            for (int j=3; j<Y; j++){
                if (grid[i][j] == player && grid[i-1][j-1] == player && grid[i-2][j-2] == player && grid[i-3][j-3] == player)
                    return true;
            }
        }
        int full = 0;
        for (int i = 0; i < grid[0].length; i++) {
            if(grid[0][i] == 1 || grid[0][i] == 2) {
                full++;
            }
        }
        if (grid[0].length == full) ;
        return false;
    }

    public static void printMatrix(int[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }
}
