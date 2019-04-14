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
        int[][] fullResult = SU21807744.insertTeleporter(grid, 0, 1);

        assertTrue(fullExpected[0][0] == fullResult[0][0]);


        // is Empty
        int[][] emptyExpected = { {1,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,0,2,2} };
        int[][] emtpyResult = SU21807744.insertTeleporter(emptyExpected, 1, 1);

        assertTrue(1 == fullResult[3][1]);

        // wrap around
        int[][] wrapAroundExpected = { {2,0,2,2}, {2,0,2,2}, {1,0,2,2} ,{2,1,2,2} };
        int[][] wrapAroundResult = SU21807744.insertTeleporter(grid, 1, 1);

        assertTrue(wrapAroundExpected[2][0] == wrapAroundResult[2][0]);

        // standard teleport
        int[][] teleportExpected = { {0,0,2,2}, {0,0,2,2}, {0,0,2,2} ,{2,2,2,2}  };
        int[][] teleportResult = SU21807744.insertTeleporter(teleportExpected, 0, 1);

        assertTrue(1 == teleportResult[2][3]);
    }

    @Test
    public void insertBomb() {
        int[][] grid = { {2,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,1,2,2} };
        int[][] expectedGrid = { {0,0,0,2}, {0,0,0,2}, {0,0,0,2}, {2,1,2,2}};
        int columnNum = 1;
        int player = 1;


        int [][] result = SU21807744.insertBomb(grid, columnNum, player);

        printMatrix(result);
        System.out.println();
        printMatrix(expectedGrid);

        assertEquals(result, expectedGrid);
    }

    @Test
    public void getColumn(){
        int[][] grid = { {2,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,1,2,2} };
        int[] expected = {0,0,0,1};

        int[] result = SU21807744.getColumn(grid, 1);

       assertTrue(Arrays.equals(result, expected));
    }

    @Test
    public void gravity(){
        int[] column  = { 2, 0, 0, 0 };
        int[] expected = { 0, 0, 0, 2 };
        int[] result = SU21807744.gravity(column);

        printArray(result);
        System.out.println();
        printArray(expected);

        assertTrue(Arrays.equals(result, expected));
    }

    @Test
    public void updateColumn() {
        int[][] grid = { {2,1,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,0,2,2} };
        int [] column = {0 ,0, 0, 1};

        SU21807744.updateColumn(grid, column, 1);
        printMatrix(grid);
    }

    @Test
    public void isInYRange(){
        assertFalse(SU21807744.isInYRange(0, 2));
        assertTrue(SU21807744.isInYRange(1, 2));
    }

    @Test
    public void isInXRange(){
        assertFalse(SU21807744.isInXRange(0, 2));
        assertTrue(SU21807744.isInXRange(1, 2));
    }

    @Test
    public void insertColorChanger(){
        int[][] grid = { {2,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,1,2,2} };
        int[][] result = SU21807744.insertColorChanger(grid, 1, 2);

        assertTrue(result[3][1] == 2);

        int[][] emptyCol = { {2,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,0,2,2} };
        int[][] emptyRes = SU21807744.insertColorChanger(emptyCol, 1, 2);

        assertTrue(emptyRes[3][1] == 2);


    }

    @Test
    public void insert(){
        int[][] grid = { {2,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,1,2,2} };
        int [][] result = SU21807744.insert(grid, 1, 2);

        assertTrue(result[2][1] == 2);
    }

    @Test
    public void display() {
        int[][] grid = { {2,0,2,2}, {2,0,2,2}, {2,0,2,2} ,{2,1,2,2} };

        SU21807744.Display(grid);
    }

    @Test
    public void checkH(){
        int[][] grid = {
            //Y  0 1 2 3 4 5 6     X
                {0,0,0,0,0,0,0},// 0
                {0,2,0,0,0,0,0},// 1
                {0,0,2,0,0,0,0},// 2
                {0,0,0,2,0,0,0},// 3
                {0,0,0,0,2,0,0},// 4
                {0,0,0,0,0,0,0} // 5
        };

        System.out.println("Winner "+ SU21807744.descDiagonal(grid, 2));
    }

    @Test
    public void checkDraw(){

        int[][] fullGrid = {
                //Y  0 1 2 3 4 5 6     X
                {1,2,1,1,2,1,2},// 0
                {1,2,1,2,2,2,1},// 1
                {1,1,2,1,1,1,2},// 2
                {1,2,2,2,1,2,2},// 3
                {1,1,1,1,2,1,1},// 4
                {1,2,1,1,1,2,2} // 5
        };

        int[][] emptyGrid = {
                //Y  0 1 2 3 4 5 6     X
                {1,2,0,0,0,0,2},// 0
                {1,2,1,2,2,2,1},// 1
                {1,1,2,1,1,1,2},// 2
                {1,2,2,2,1,2,2},// 3
                {1,1,1,1,2,1,1},// 4
                {1,2,1,1,1,2,2} // 5
        };

        SU21807744.checkDraw(emptyGrid, 2);
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
