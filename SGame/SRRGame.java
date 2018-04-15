package SGame;

import SGame.Blocks.*;
import static SGame.PlayerNameEntry.createPlayerEntry;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SRRGame extends JPanel implements Runnable {

    private Thread thread;
    public static final int SPEED = 25;
    private BufferedImage background, title, life, gameOverImage, victoryImage;
    private BufferedImage start, load, help, quit, scores;
    public static Graphics g2;
    private LinkedList<Wall> wall;
    private LinkedList<Blue> blue;
    private LinkedList<Brown> brown;
    private LinkedList<Yellow> yellow;
    private LinkedList<Red> red;
    private LinkedList<Green> green;
    private LinkedList<Rouse> rouse;
    private LinkedList<White> white;
    private LinkedList<Bigleg> bigleg;
    private LinkedList<HeartW> heart;
    private LinkedList<StarW> star;
    private LinkedList<Missile> pop;
    private String name;
    protected static LinkedList<Missile> missiles;
    private GSound backgroundS;
    protected static GameAction player1, player2;
    protected static BufferedReader map;
    private static int w, h;
    private int sizeX, sizeY;
    private static int W, H;
    protected static int counter = 500; // approx. 25 seconds
    protected static boolean enableTitleScreen = true;
    protected static int loadLevel;
    protected static boolean reLoadLevel = false;
    private static int score = 0;
    private boolean isGameOver = false;
    private boolean display = true;
    protected static boolean gameOver = false;

    public SRRGame() throws IOException {
        //new ScoreBoard();
        this.setFocusable(true);
        //String line;
        this.name = name;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        W = 660;
        H = 600;

        try {
            // Load images for title screen and background
            background = ImageIO.read(SRRGame.class.getResource("resource/Background1.bmp"));
            title = ImageIO.read(SRRGame.class.getResource("resource/titleScreen.gif"));
            gameOverImage = ImageIO.read(SRRGame.class.getResource("resource/Gameover.bmp"));
            victoryImage = ImageIO.read(SRRGame.class.getResource("resource/congrats.gif"));
            start = ImageIO.read(SRRGame.class.getResource("resource/Button_start.gif"));
            load = ImageIO.read(SRRGame.class.getResource("resource/Button_load.gif"));
            quit = ImageIO.read(SRRGame.class.getResource("resource/Button_quit.gif"));
            help = ImageIO.read(SRRGame.class.getResource("resource/Button_help.gif"));
            scores = ImageIO.read(SRRGame.class.getResource("resource/Button_scores.gif"));
            life = Sprite.loadSprite("Heart");

        } catch (IOException ioe) {
            System.out.println("*** IOException: " + ioe.getMessage());
        }//end try-catch

        // Set background music
        backgroundS = new GSound(1, "resource/Back_sound.mid");
        setBackground(Color.white);
        player1 = new GameAction(this, 500, 500, SPEED);

        // Create missiles.
        missiles = new LinkedList<>();

        // Create walls.
        wall = new LinkedList<>();
        blue = new LinkedList<>();
        brown = new LinkedList<>();
        yellow = new LinkedList<>();
        red = new LinkedList<>();
        green = new LinkedList<>();
        rouse = new LinkedList<>();
        white = new LinkedList<>();
        bigleg = new LinkedList<>();
        heart = new LinkedList<>();
        star = new LinkedList<>();

        addKeyListener(new Controller());
        addMouseListener(new MouseMotionEvent());
        loadMap(0); // initialize level 1 at start of game 

    }//end constructor

    public void loadMap(int level) throws IOException{

        if (level >= 0 || level <= 2) {
            map = new BufferedReader(new InputStreamReader(SRRGame.class.getResource("resource/Map" + level + ".txt").openStream()));
        }
        
        bigleg.clear();
        wall.clear();
        blue.clear();
        brown.clear();
        yellow.clear();
        red.clear();
        green.clear();
        rouse.clear();
        white.clear();
        heart.clear();
        star.clear();

            String line = map.readLine();
            w = line.length();
            h = 0;
            while (line != null) {
                for (int i = 0, n = line.length(); i < n; i++) {
                    char c = line.charAt(i);

                    if (c == '0') {
                        Bigleg g = new Bigleg(40 * i, 20 * h, 80, 60, 0);
                        bigleg.add(g);
                    }

                    if (c == 's') {
                        Bigleg g = new Bigleg(35 * i, 20 * h, 120, 60, 0);
                        bigleg.add(g);
                    }

                    if (c == '1') {
                        Wall w1 = new Wall(20 * i, 20 * h, 20, 20, 0);
                        wall.add(w1);
                    }

                    if (c == 'b') {
                        Blue b = new Blue(20 * i, 20 * h, 60, 20, 0);
                        blue.add(b);
                    }

                    if (c == '2') {
                        Blue b = new Blue(40 * i, 20 * h, 40, 20, 0);
                        blue.add(b);
                    }

                    if (c == '3') {
                        Red r = new Red(40 * i, 20 * h, 40, 20, 0);
                        red.add(r);
                    }

                    if (c == '4') {
                        Yellow y = new Yellow(40 * i, 20 * h, 40, 20, 0);
                        yellow.add(y);
                    }

                    if (c == '5') {
                        Green g = new Green(40 * i, 20 * h, 40, 20, 0);
                        green.add(g);
                    }

                    if (c == '6') {
                        Rouse ro = new Rouse(40 * i, 20 * h, 40, 20, 0);
                        rouse.add(ro);
                    }

                    if (c == '7') {
                        White wh = new White(40 * i, 20 * h, 40, 20, 0);
                        white.add(wh);
                    }

                    if (c == '8') {
                        HeartW he = new HeartW(40 * i, 20 * h, 40, 20, 0);
                        heart.add(he);
                    }

                    if (c == 'd') {
                        HeartW he = new HeartW(20 * i, 20 * h, 60, 20, 0);
                        heart.add(he);
                    }

                    if (c == '9') {
                        StarW st = new StarW(40 * i, 20 * h, 40, 20, 0);
                        star.add(st);
                    }

                    if (c == 'a') {
                        Brown br = new Brown(40 * i, 20 * h, 40, 20, 0);
                        brown.add(br);
                    }

                }//end for
                h++;
                line = map.readLine();
            }//end while
            map.close();
    }//end loadMap

    public LinkedList<Wall> getW() {
        return wall;
    }//end getWalls

    public LinkedList<Blue> getBW() {
        return blue;
    }//end getWalls

    public LinkedList<Brown> getBrW() {
        return brown;
    }//end getWalls

    public LinkedList<Yellow> getYW() {
        return yellow;
    }//end getWalls

    public LinkedList<Red> getRW() {
        return red;
    }//end getWalls

    public LinkedList<Green> getGW() {
        return green;
    }//end getWalls

    public LinkedList<Rouse> getROW() {
        return rouse;
    }//end getWalls

    public LinkedList<White> getWW() {
        return white;
    }//end getWalls

    public LinkedList<Bigleg> getBiW() {
        return bigleg;
    }//end getWalls

    public LinkedList<HeartW> getHeW() {
        return heart;
    }//end getWalls

    public LinkedList<StarW> getStW() {
        return star;
    }//end getWalls

    public static void enableTitleScreen() {
        enableTitleScreen = true;
    }//ed enableTitleScreen

    public static void addToScore(int add) {
        score += add;
    }//end addToScore

    public static void reLoadElements() {
        gameOver = false;
    }//end reLoadElements 

    public static boolean isGameOver() {
        return gameOver;
    }//end isGameOver

    @Override
    public void paint(Graphics g) {

        try {
            if (reLoadLevel == true) {
                reLoadLevel = false;
                player1.setNumberOfLives(3);
                player1.setLive(true);
                loadMap(loadLevel);
                gameOver = false;
                display = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(SRRGame.class.getName()).log(Level.SEVERE, null, ex);
        }

        g.drawImage(background, 0, 0, W, H, null);

        if (player1.Live()) {
            player1.move(g);
        }

        //if player1 is not alive, but still has lives
        if ((!player1.Live()) && (player1.hasLives())) {
            int rX = 500, rY = 500;
            if (player1.getNumberOfLives() == 2) {
                rX = 500;
                rY = 500;
            } else if (player1.getNumberOfLives() == 1) {
                rX = 500;
                rY = 500;
            }
   
            player1.setLive(true);
            player1.setLocation(rX, rY);
            player1.move(g);
           
        }

        // Paint walls
        Iterator<Wall> itrWall = wall.iterator();
        while (itrWall.hasNext()) {
            Wall walls = itrWall.next();
            walls.draw(g);
        }//end while

        //paint blue blocks
        Iterator<Blue> itrWalls = blue.iterator();
        while (itrWalls.hasNext()) {
            Blue b = itrWalls.next();
            b.draw(g);
        }//end while

        //paint brown blocks
        Iterator<Brown> itrbrW = brown.iterator();
        while (itrbrW.hasNext()) {
            Brown br = itrbrW.next();
            br.draw(g);
        }//end while

        //paint green blocks
        Iterator<Green> itrGW = green.iterator();
        while (itrGW.hasNext()) {
            Green br = itrGW.next();
            br.draw(g);
        }//end while

        //paint yellow blocks
        Iterator<Yellow> itrYW = yellow.iterator();
        while (itrYW.hasNext()) {
            Yellow y = itrYW.next();
            y.draw(g);
        }//end while

        //paint red blocks
        Iterator<Red> itrRW = red.iterator();
        while (itrRW.hasNext()) {
            Red r = itrRW.next();
            r.draw(g);
        }//end while

        //paint rouse blocks
        Iterator<Rouse> itrRoW = rouse.iterator();
        while (itrRoW.hasNext()) {
            Rouse ro = itrRoW.next();
            ro.draw(g);
        }//end while

        //paint white blocks
        Iterator<White> itrWW = white.iterator();
        while (itrWW.hasNext()) {
            White wh = itrWW.next();
            wh.draw(g);
        }//end while

        //paint Bigleg enemies
        Iterator<Bigleg> itrBiW = bigleg.iterator();
        while (itrBiW.hasNext()) {
            Bigleg bi = itrBiW.next();
            bi.draw(g);
        }//end while

        //paint star blocks
        Iterator<StarW> itrStW = star.iterator();
        while (itrStW.hasNext()) {
            StarW st = itrStW.next();
            st.draw(g);
        }//end while

        //paint heart blocks
        Iterator<HeartW> itrHeW = heart.iterator();
        while (itrHeW.hasNext()) {
            HeartW he = itrHeW.next();
            he.draw(g);
        }//end while

        // Paint missiles.
        Iterator<Missile> itrMissile = missiles.iterator();
        while (itrMissile.hasNext()) {
            Missile missile = itrMissile.next();
            missile.paint(g);
                if (missile.hitBlocks()) {
                    missile.Bounce();
                } else if (missile.hitKatch()) {
                    missile.KatchBounce();

                } else if (missile.remov()) {
                    itrMissile.remove();
                    player1.damage();
                } else if (missile.brownW()) {
                    missile.bWallBounce();

                } else try {
                    if (player1.map()) {
                        itrMissile.remove();
                        player1.setmapcount(0);
                    } else {
                        missile.BounceW();
                    }
            } catch (IOException ex) {
                Logger.getLogger(SRRGame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }//end while

        //Draw the number of lives on the bottom left
        for (int i = 20, n = 0; n < player1.getNumberOfLives(); i += 20, n++) {
            g.drawImage(life, i, 540, life.getWidth(), life.getHeight(), null);
        }

        //Draw score board on bottom right
        g.setFont(new Font("Calibri", Font.PLAIN, 20));
        g.setColor(Color.BLACK);
        g.drawString("SCORE " + score, 525, 555);

        // gameover display
        if (player1.getNumberOfLives() == 0) {
            gameOver = true;
            g.drawImage(gameOverImage, 20, 20, W - 41, H - 49, null);
            // scores limits for testing 
            if (gameOver && display) {
                display = false;
                createPlayerEntry(score);
                score = 0;
            }
        }

            // display victory display
            if(player1.winner()==true){
                gameOver = true;
                g.drawImage(victoryImage, 19, 19, W - 41, H - 49, null);
                // scores limits for testing 
                if (gameOver && display) {
                   display = false;
                   createPlayerEntry(score);
                   score = 0;
                }
                
            }
       

        //Draw main title screen and options buttons      
        if (counter > 0 && enableTitleScreen) { // runs for approx. 25 seconds
            g.drawImage(title, 20, 20, W - 41, H - 49, null);
            g.drawImage(start, 36, 516, 100, 34, null);
            g.drawImage(load, 160, 516, 100, 34, null);
            g.drawImage(help, 285, 516, 100, 34, null);
            g.drawImage(quit, 527, 516, 100, 34, null);
            g.drawImage(scores, 409, 516, 100, 34, null);

            counter--;
        }
        if (counter <= 0) {
            enableTitleScreen = false;

        }

        g.dispose();

    }//end paint

    public GameAction getPlayerTank() {
        return player1;
    }//end getPlayerTank

    @Override
    public void run() {
        Thread me = Thread.currentThread();
        setFocusable(true);
        while (thread == me) {
            repaint();
            try {
                Thread.sleep(40);
            } catch (InterruptedException ie) {
                break;
            }
        }
    }//end run

    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }//end start

    public static void main(String[] args) {

        try {
            SRRGame game = new SRRGame();

            JFrame f = new JFrame("Super Rainbow Reef");
            f.addWindowListener(new WindowAdapter() {

                @Override
                public void windowGainedFocus(WindowEvent e) {
                    game.requestFocusInWindow();
                }//end windowGainedFocus

            });//end WindowAdapter

            f.getContentPane().add("Center", game);
            f.pack();
            f.setSize(new Dimension(W, H));
            game.start();
            f.setVisible(true);
            f.setResizable(false);
            f.setLocation(300, 20);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        } catch (IOException ioe) {
            Logger.getLogger(SRRGame.class.getName()).log(Level.SEVERE, null, ioe);
        }//end try-catch

    }//end main 
}//end TankGame

