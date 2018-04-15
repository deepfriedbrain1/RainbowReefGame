package SGame;

import SGame.Blocks.*;
import static SGame.GameAction.addLife;
import static SGame.SRRGame.addToScore;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public final class Missile extends GObject {

    private Direction direction;
    private SRRGame field;
    private GameAction launcher;
    private static BufferedImage img;
    public int w;
    public int h; 
    private GSound bigleg;
    private GSound block;
    private GSound katch;
    private GSound lost;
    private GSound wall;
    private boolean left, right, top, bottom, bleft, bright, bcenter;
    Random rand;
     
    private float counter=0;
    private int speed=0;

    static {
        img = Sprite.loadSprite("Pop");
    }//end static

    public Missile(SRRGame field, GameAction launcher, Direction direction, int x, int y) {
        super(img, x, y);
        rand = new Random();
        this.speed=6;
        this.left = false;
        this.bleft=false;
        this.bright=false;
        this.right = false;
        this.top = false;
        this.right = false;
        this.bcenter=false;
        this.field = field;
        this.launcher = launcher;
        this.direction = direction;
        this.w = img.getWidth();
        this.h = img.getHeight();
        launcher.getW(w, h);
    }//end constructor
    


    public BufferedImage getBufferedImage(BufferedImage img2) {
        return img2 = Missile.img;
    }//end getBufferedImage

    public void setLocation(int x, int y) {
        this.x = super.getX();
        this.y = super.getY();
    }//end setLocation
    
    //set everything to false;
    public void setFalse(){
        this.left = false;
        this.bleft=false;
        this.bright=false;
        this.right = false;
        this.top = false;
        this.right = false;
        this.bcenter=false;
    }//end setFalse

    
    //bounce for breakable walls 
    public void Bounce() {
        int value = rand.nextInt(3);
        if (top == true) {
            //if bottom of Katch is hit generate a random direction
            switch (value) {
                case 0:
                    setDirection(Direction.DOWN);
                    break;
                case 1:
                    setDirection(Direction.LEFT_DOWN);
                    break;
                case 2:
                    setDirection(Direction.RIGHT_DOWN);
                    break;
                default:
                    break;
            }//end switch
        }else if (left == true && direction == Direction.RIGHT_UP) { 
                setDirection(Direction.LEFT_UP); 
        } else if (left == true && direction == Direction.RIGHT_DOWN) { 
                setDirection(Direction.LEFT_DOWN);
        }else if (right == true && direction == Direction.LEFT_UP) { 
               setDirection(Direction.RIGHT_UP); 
        }else if (right == true && direction == Direction.LEFT_DOWN) { 
                setDirection(Direction.RIGHT_DOWN);
        }else if (bottom == true && direction==Direction.RIGHT_DOWN) {
                setDirection(Direction.LEFT_DOWN);
        }else if (bottom == true && direction==Direction.LEFT_DOWN) {
                 setDirection(Direction.RIGHT_DOWN);
        } 

    }//end Bounce
    
    //bounce for brown walls 
    public void bWallBounce(){
        int value = rand.nextInt(2);
        if (left == true && direction == Direction.RIGHT_UP) { 
            setDirection(Direction.LEFT_UP);  
        } else if (left == true && direction == Direction.RIGHT_DOWN) { 
            setDirection(Direction.LEFT_DOWN);
        }else if (right == true && direction == Direction.LEFT_UP) { 
            setDirection(Direction.RIGHT_UP); 
        }else if (right == true && direction == Direction.LEFT_DOWN) { 
            setDirection(Direction.RIGHT_DOWN);
        }else if (bottom == true && direction==Direction.RIGHT_DOWN) {
            setDirection(Direction.LEFT_DOWN);
        } else if (bottom == true && direction==Direction.LEFT_DOWN) {
            setDirection(Direction.RIGHT_DOWN);
        } else if (top == true && direction==Direction.RIGHT_UP) {
            switch (value) {
                case 0:
                    setDirection(Direction.LEFT_UP);
                    break;
                case 1:
                    setDirection(Direction.LEFT_DOWN);
                    break;
                default:
                    break;
            }//end switch
        } else if (top == true && direction==Direction.LEFT_UP) {
            setDirection(Direction.RIGHT_UP);
             switch (value) {
                case 0:
                    setDirection(Direction.RIGHT_UP);
                    break;
                case 1:
                    setDirection(Direction.RIGHT_DOWN);
                    break;
                default:
                    break;
            }//end switch
        } else if(top==true && direction == Direction.UP){
            switch (value) {
                case 0:
                    setDirection(Direction.LEFT_DOWN);
                    break;
                case 1:
                    setDirection(Direction.RIGHT_DOWN);
                    break;
                default:
                    break;
            }//end switch
        }
    }

   //bounce Katch
    public void KatchBounce() {
        if (bcenter == true) {   
           setDirection(Direction.UP); 
            katch = new GSound(2, "resource/Sound_katch.wav");
            katch.play();
        }else if (bleft == true) {
            setDirection(Direction.LEFT_UP);
            katch = new GSound(2, "resource/Sound_katch.wav");
            katch.play();    
        }else if (bright == true) {
            setDirection(Direction.RIGHT_UP);
            katch = new GSound(2, "resource/Sound_katch.wav");
            katch.play();
        }
    }//end KatchBounce

    //bounce outside wall(boundaries)
    public void BounceW() {
        int value = rand.nextInt(3);
        if (x<=10 && direction == Direction.LEFT_DOWN) {
            setDirection(Direction.RIGHT_DOWN);
            wall = new GSound(2, "resource/Sound_wall.wav");
            wall.play();
        } else if (x>=610 && direction == Direction.RIGHT_DOWN) {
            setDirection(Direction.LEFT_DOWN);
            wall = new GSound(2, "resource/Sound_wall.wav");
            wall.play();
        } else if (x<=10 && direction == Direction.LEFT_UP) {
            setDirection(Direction.RIGHT_UP);
            wall = new GSound(2, "resource/Sound_wall.wav");
            wall.play();
        } else if (x>=610 && direction == Direction.RIGHT_UP) {
            setDirection(Direction.LEFT_UP);
            wall = new GSound(2, "resource/Sound_wall.wav");
            wall.play();
        }else if (y<=10) {
            //if bottom of Katch is hit generate a random direction
            switch (value) {
                case 0:
                    setDirection(Direction.DOWN);
                    break;
                case 1:
                    setDirection(Direction.LEFT_DOWN);
                    break;
                case 2:
                    setDirection(Direction.RIGHT_DOWN);
                    break;
                default:
                    break;
            }//end switch
            wall = new GSound(2, "resource/Sound_wall.wav");
            wall.play();
        }

    }//end BounceW

    //check collision side 
    public void intersectRectangles(Rectangle a, Rectangle b) {
        if (a.getY() <= b.getY() - (b.getHeight() / 2)) {
            bottom = true;
        }else if (a.getY() >= b.getY() + (b.getHeight() / 2)) {
            top = true;
        } else if (a.getX() < b.getX()) {
            left = true;
        } else if (a.getX() > b.getX()) {
            right = true;
        }
    }//end intersectRectangles

    //set pop direction
    public void setDirection(Direction direction) {
        this.direction = direction;
    }//end setDirection

    //check for pop collision with Katch
    public boolean hitKatch() { 
        Rectangle mine = getRectangle();
        GameAction player = field.getPlayerTank();
        if (player == launcher) {
            if (mine.intersects(player.getRect())) {
                bleft=true;
                counter++;
                if(counter==3){
                    speedUp(1);
                   counter=0;
                }
                return true;
            }else if(mine.intersects(player.getRec())){
                bcenter=true;
                counter++;
                if(counter==3){
                    speedUp(1);
                    counter=0;
                }
                return true;
            }else if(mine.intersects(player.getR())){
               bright=true;
               counter++;
               if(counter==3){
                    speedUp(1);
                    counter=0;
                }
                return true;
            }
           
        }
        return false;
    }//end hitKatcH
    
    //increment speed every 4 times Katch is hitted
    public void speedUp(int value){
        if(value==1 && speed !=18){
            speed++;   
        }else if(value== -1 && speed >= 10){
            speed=speed-4;
        }
    }
    
    
    //remove start if out of bounds
      public boolean remov(){
       if (y>=500){ 
           lost = new GSound(2, "resource/Sound_lost.wav");
           lost.play();
           return true;
        }
        return false;
    }
    
    //check pop collision with brown wall
    public boolean brownW(){
     Rectangle mine = getRectangle();
      
        // If hit walls.
        LinkedList<Brown> bw = field.getBrW();
        if (bw != null) {
            Iterator<Brown> itrWall = bw.iterator();
            while (itrWall.hasNext()) {
                Brown brwalls = itrWall.next();
                if (mine.intersects(brwalls.getRectangle())) {
                    intersectRectangles(mine, brwalls.getRectangle());
                    block = new GSound(2, "resource/Sound_block.wav");
                    block.play();
                    return true;
                }//end if
            }//end while
        }//end if
        return false;
    }
      
   //check pop collision with breakeable walls and biglegs
    public boolean hitBlocks(){
        Rectangle mine = getRectangle();

        // Detect collision from breakable walls.
        LinkedList<Blue> b = field.getBW();
        if (b != null) {
            Iterator<Blue> itrWalls = b.iterator();
            while (itrWalls.hasNext()) {
                Blue bwall = itrWalls.next();
                if (mine.intersects(bwall.getRectangle())) {
                    intersectRectangles(mine, bwall.getRectangle());
                        itrWalls.remove();
                        addToScore(10); // add 10 points to score
                        block = new GSound(2, "resource/Sound_block.wav");
                        block.play();
                    return true;
                }//end if
            }//end while
        }//end if

        // Detect collision from breakable walls.
        LinkedList<Red> r = field.getRW();
        if (r != null) {
            Iterator<Red> itrWalls = r.iterator();
            while (itrWalls.hasNext()) {
                Red rwall = itrWalls.next();
                if (mine.intersects(rwall.getRectangle())) {
                    intersectRectangles(mine, rwall.getRectangle());
                        itrWalls.remove();
                        addToScore(10); // add 10 points to score
                        block = new GSound(2, "resource/Sound_block.wav");
                        block.play();
                    return true;
                }//end if
            }//end while
        }//end if

        // Detect collision from breakable walls.
        LinkedList<Green> g = field.getGW();
        if (g != null) {
            Iterator<Green> itrWalls = g.iterator();
            while (itrWalls.hasNext()) {
                Green gwall = itrWalls.next();
                if (mine.intersects(gwall.getRectangle())) {
                    intersectRectangles(mine, gwall.getRectangle());
                        itrWalls.remove();
                        addToScore(10); // add 10 points to score
                        block = new GSound(2, "resource/Sound_block.wav");
                        block.play();
                    return true;
                }//end if
            }//end while
        }//end if

        // Detect collision from breakable walls.
        LinkedList<White> wh = field.getWW();
        if (wh != null) {
            Iterator<White> itrWalls = wh.iterator();
            while (itrWalls.hasNext()) {
                White wwall = itrWalls.next();
                if (mine.intersects(wwall.getRectangle())) {
                    intersectRectangles(mine, wwall.getRectangle());
                        itrWalls.remove();
                        addToScore(10); // add 10 points to score
                        block = new GSound(2, "resource/Sound_block.wav");
                        block.play();
                    return true;
                }//end if
            }//end while
        }//end if

        // Detect collision from breakable walls.
        LinkedList<Rouse> ro = field.getROW();
        if (ro != null) {
            Iterator<Rouse> itrWalls = ro.iterator();
            while (itrWalls.hasNext()) {
                Rouse rowall = itrWalls.next();
                if (mine.intersects(rowall.getRectangle())) {
                    intersectRectangles(mine, rowall.getRectangle());
                        itrWalls.remove();
                        addToScore(10); // add 10 points to score
                        block = new GSound(2, "resource/Sound_block.wav");
                        block.play();
                    return true;
                    
                }//end if
            }//end while
        }//end if

        // Detect collision from breakable walls.
        LinkedList<Yellow> ye = field.getYW();
        if (ye != null) {
            Iterator<Yellow> itrWalls = ye.iterator();
            while (itrWalls.hasNext()) {
                Yellow ywall = itrWalls.next();
                if (mine.intersects(ywall.getRectangle())) {
                    intersectRectangles(mine, ywall.getRectangle());
                        itrWalls.remove();
                        addToScore(10); // add 10 points to score
                        block = new GSound(2, "resource/Sound_block.wav");
                        block.play();
                    return true;
                }//end if
            }//end while
        }//end if

        // Detect collision from breakable walls.
        LinkedList<HeartW> he = field.getHeW();
        if (he != null) {
            Iterator<HeartW> itrWalls = he.iterator();
            while (itrWalls.hasNext()) {
                HeartW hwall = itrWalls.next();
                if (mine.intersects(hwall.getRectangle())) {
                    intersectRectangles(mine, hwall.getRectangle());
                        itrWalls.remove();
                        addToScore(15); // add 15 points to score
                        addLife(1);
                        block = new GSound(2, "resource/Sound_block.wav");
                        block.play();
                    return true;
                }//end if
            }//end while
        }//end if

        // Detect collision from breakable walls.
        LinkedList<StarW> st = field.getStW();
        if (st != null) {
            Iterator<StarW> itrWalls = st.iterator();
            while (itrWalls.hasNext()) {
                StarW swall = itrWalls.next();
                if (mine.intersects(swall.getRectangle())) {
                    intersectRectangles(mine, swall.getRectangle());
                        itrWalls.remove();
                        addToScore(25); // add 10 points to score
                        speedUp(-1);
                        block = new GSound(2, "resource/Sound_block.wav");
                        block.play();
                    return true;
                }//end if
            }//end while
        }//end if

        // Detect collision from breakable walls.
        LinkedList<Bigleg> bi = field.getBiW();
        if (bi != null) {
            Iterator<Bigleg> itrWalls = bi.iterator();
            while (itrWalls.hasNext()) {
                Bigleg bwall = itrWalls.next();
                if (mine.intersects(bwall.getRectangle())) {
                    intersectRectangles(mine, bwall.getRectangle());    
                        itrWalls.remove();
                        launcher.bigLeg(1);
                        addToScore(50); // add 50 points to score
                        bigleg = new GSound(2, "resource/Sound_bigleg.wav");
                        bigleg.play();
                    return true;
                }//end if
            }//end while
        }//end if      

        return false;
    }//end hitObjects

    public void paint(Graphics g) {

        switch (direction) {
            case DOWN:
                y += speed;

                break;
            case LEFT_DOWN:
                x -= (int) (speed / Math.sqrt(2));;
                y += (int) (speed / Math.sqrt(2));;

                break;
            case LEFT_UP:
                x -= (int) (speed / Math.sqrt(2));;
                y -= (int) (speed / Math.sqrt(2));;

                break;
            case RIGHT_DOWN:
                x += (int) (speed / Math.sqrt(2));;
                y += (int) (speed / Math.sqrt(2));;

                break;
            case RIGHT_UP:
                x += (int) (speed / Math.sqrt(2));;
                y -= (int) (speed / Math.sqrt(2));;

                break;
            case UP:
                y -= speed;

                break;
            default:
                break;
        }//end switch
        g.drawImage(img, x, y, this.getWidth(), this.getHeight(), null);
        setFalse();
        
    }//end paint
}//end Missle