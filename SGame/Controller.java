package SGame;

import static SGame.SRRGame.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static SGame.SRRGame.isGameOver;
import static SGame.SRRGame.missiles;
import static SGame.SRRGame.player1;
import static SGame.SRRGame.reLoadElements;

public final class Controller extends KeyAdapter {
    private boolean left;
    private boolean right;

    private void setMyTankDirection() {

        if (left && !right) {
            player1.setDirection(Direction.LEFT);
            player1.setMoving(true);
        } else if (!left && right) {
            player1.setDirection(Direction.RIGHT);
            player1.setMoving(true);
        } else {
            player1.setMoving(false);
        }
    }//end setMyTankDirection  

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_SPACE:
                if(gameOver)
                    break;
                if (player1.Live() && missiles.size()<1) {
                    missiles.add(player1.fireMissile());
                }
                break;
            case KeyEvent.VK_ENTER: 
                if (isGameOver()) {
                    player1.setLive(true);
                    player1.setNumberOfLives(3);
                    player1.setLocation(500, 500);

                    reLoadElements();
                }
                break;
            case KeyEvent.VK_Q:
                if (isGameOver()) {
                    System.exit(0);
                }
                break;
            case KeyEvent.VK_1:
                player1.setLive(true);
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_ESCAPE:
                counter = 500;
                enableTitleScreen();
                break;
        }//end switch
        setMyTankDirection();
    }//end keyPressed

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }//end switch
        setMyTankDirection();
    }//end keyReleased
}//end Controller
