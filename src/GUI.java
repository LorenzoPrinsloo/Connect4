import java.awt.*;
import java.util.Timer;


public class GUI {

    public static int [][] grid = new int[6][7];
    public static int [] curpowers = {2,2,2};
    public static String currentPlayer = "player1";
    public static boolean isFinished = false;

    public static void main(String [] args) {

        Timer t = new Timer();
        t.scheduleAtFixedRate(new Scheduler(), 0,500);

        StdDraw.setXscale(0, 2.0);
        StdDraw.setYscale(0, 2.0);
        StdAudio.loop("resources/pokemon.wav");
        StdDraw.picture(0.7, 0.5, "resources/label.jpg", 1.0, 0.6);
        isFinished = false;

        render(grid);
    }

    public static void clearGUI() { //cmd + shift shows images
        StdDraw.clear();

        StdDraw.picture(0.7, 0.5, "resources/label.jpg", 1.0, 0.6);
        isFinished = false;

        render(grid);
    }

    public static void drawCoord(int x, int y, String image) {
        double xCoord = 0.1 + ((x) * 0.2);
        double yCoord = 0.9 + ((y) * 0.2);

        StdDraw.picture(xCoord, yCoord, image, 0.45, 0.35);
    }

    public static void drawCoord(int x, int y, Color color) {
        double xCoord = 0.1 + ((x) * 0.2);
        double yCoord = 0.9 + ((y) * 0.2);

        StdDraw.setPenColor(color);
        StdDraw.filledCircle(xCoord, yCoord, 0.1);
    }

    public static void DisplayWinner(int player) {
        StdDraw.picture(0.7, 1.45, "resources/player"+player+"winner.jpg", 1.0, 0.5);
        StdDraw.picture(0.7, 1.25, "resources/gameprompt.jpg", 1.0, 0.25);
    }

    public static void DisplayDraw() {
        StdDraw.picture(0.7, 1.45, "resources/playerdraw.jpg", 1.0, 0.5);
        StdDraw.picture(0.7, 1.25, "resources/gameprompt.jpg", 1.0, 0.25);

    }

    public static void render(int [][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if(grid[y][x] == 1) {
                    drawCoord(x, Math.abs((y - grid.length) + 1), "resources/normal.png");
                } else if(grid[y][x] == 2) {
                    drawCoord(x, Math.abs((y - grid.length) + 1), "resources/greatball.png");
                } else drawCoord(x, Math.abs((y - grid.length) + 1), StdDraw.LIGHT_GRAY);
            }
        }

        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        StdDraw.filledRectangle(1.7, 1.6, 0.25, 0.15);

        StdDraw.setPenColor(StdDraw.YELLOW);

        StdDraw.setFont(new Font("Georgia Pro Semibold", 1, 15));
        StdDraw.text(1.555, 1.7, "Bombs");
        StdDraw.text(1.625, 1.6, "Teleporters");
        StdDraw.text(1.625, 1.5, "Color Swap");

        StdDraw.text(1.9, 1.7, ""+ curpowers[0]);
        StdDraw.text(1.9, 1.6, ""+ curpowers[1]);
        StdDraw.text(1.9, 1.5, ""+ curpowers[2]);

        StdDraw.picture(1.7, 1.85, "resources/"+currentPlayer+".jpg", 0.4, 0.2);
    }
}
