import java.util.TimerTask;

public class Scheduler extends TimerTask {

    @Override
    public void run() {

        if(StdDraw.isKeyPressed(32)) { // spacebar pressed
            try {

                SU21807744.player1 = true;
                GUI.clearGUI();

            } catch (Exception e) {
                System.out.println("Failed to reset game");
                e.printStackTrace();
            }
        }

        if(StdDraw.isKeyPressed(67) && StdDraw.isMousePressed()) // C + click
        {
            System.out.println("Color Changer");

            if(GUI.curpowers[2] > 0) {
                int columnNum = (int) (StdDraw.mouseX()/0.2);

                GUI.curpowers[2]--;

                SU21807744.insertColorChanger(GUI.grid, columnNum, GUI.currentPlayer.equals("player1") ? 1 : 2);
                SU21807744.player1 = !SU21807744.player1;
                GUI.render(GUI.grid);
            }

        }
        else if (StdDraw.isKeyPressed(66) && StdDraw.isMousePressed()) { // B + Click

            System.out.println("Bomb");

            if(GUI.curpowers[0] > 0) {

                int columnNum = (int) (StdDraw.mouseX()/0.2);

                GUI.curpowers[0]--;

                SU21807744.insertBomb(GUI.grid, columnNum, GUI.currentPlayer.equals("player1") ? 1 : 2);
                SU21807744.player1 = !SU21807744.player1;
                GUI.render(GUI.grid);

            }
        }
        else if(StdDraw.isKeyPressed(84) && StdDraw.isMousePressed()) { // T + Click

            System.out.println("teleporter");

            if(GUI.curpowers[1] > 0) {

                int columnNum = (int) (StdDraw.mouseX()/0.2);

                GUI.curpowers[1]--;

                SU21807744.insertTeleporter(GUI.grid, columnNum, GUI.currentPlayer.equals("player1") ? 1 : 2);
                SU21807744.player1 = !SU21807744.player1;
                GUI.render(GUI.grid);

            }

        }
        else if(StdDraw.isMousePressed()) {
            System.out.println("Pressed");

            int columnNum = (int) (StdDraw.mouseX()/0.2);

            SU21807744.insert(GUI.grid ,columnNum, GUI.currentPlayer.equals("player1") ? 1 : 2);
            SU21807744.player1 = !SU21807744.player1;
            GUI.render(GUI.grid);
        }
    }
}
