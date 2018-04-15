package SGame;

import SGame.Blocks.Wall;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public final class GameAction extends GObject {

    private Direction direction;
    private boolean live = true;
    protected static int numberOfLives = 3;
    private int count = 0;
    private boolean collision = false;
    private SRRGame field;
    private int level=0;
    private int w, h;
    private boolean victory=false;
    private boolean moving;
    private static BufferedImage img; 

    static {
        img = Sprite.loadSprite("Katch");
        
    }//end static

    public GameAction(SRRGame field, int x, int y, int speed) {
        super(img, x, y, speed);
        this.field = field;
    }//end constructor

    public void getW(int w, int h) {
        this.w = w;
        this.h = h;
    }//end getW

    public boolean hasLives() {
        return (numberOfLives > 0);
    }//end hasLives

    //missile getting shot from the center of the tank
    public Missile fireMissile(Direction direction) {
        Missile missile = new Missile(field, this, direction, x + this.getWidth() / 4 + w / 4, y + this.getHeight() / -3 + h / -3);
        return missile;
    }//end fireMissle

    public Missile fireMissile() {
        return fireMissile(Direction.UP);
    }//end fireMissle

    public LinkedList<Missile> fireMissiles() {
        LinkedList<Missile> missiles = new LinkedList<>();
        Direction[] directions = Direction.values();
        for (int i = 0; i < directions.length - 1; ++i) {
            missiles.add(fireMissile(directions[i]));
        }
        return missiles;
    }//end fireMissles 

    //detect collision from walls on Katch
    public void Collision(int oldX, int oldY) {
        Rectangle mine = getRectangle();

        // Detect collision from walls.
        LinkedList<Wall> walls = field.getW();
        if (walls != null) {
            Iterator<Wall> itrWall = walls.iterator();
            while (itrWall.hasNext()) {
                Wall wall = itrWall.next();
                if (mine.intersects(wall.getRectangle())) {
                    setLocation(oldX, oldY);
                    return;
                }//end if
            }//end while
        }//end if

    }//end Collision
    
    //increment by one every time Bigleg is hit
    public void bigLeg(int count){
        this.count+=count;
    }
  
    //change map when count=3
    public boolean map() throws IOException{
        if(count==3){
            level++;
            if(level<=2){
                field.loadMap(level);
                return true;
            }else if(level>2){
               victory=true;
            }
           
        }
        return false; 
    }
    
    
    //set to true when all levels have been bitten 
    public boolean winner(){
        if(victory==true){
            return true;
        }
        return false;
    }
    //set count to 0 
    public void setmapcount(int count){
        this.count = count;
    }//end setNumberOfLives
    
    //subtract one live when pop out of bounds
    public void damage() {
        
            if (numberOfLives > 0) {
                numberOfLives--;
            }
    }//end damage

    public int getNumberOfLives() {
        return numberOfLives;
    }//end getNumberOfLives

    
    public static void addLife(int numberOfLives){
        GameAction.numberOfLives += numberOfLives;
    }
    
    public void setNumberOfLives(int numOfLives){
        this.numberOfLives = numOfLives;
    }//end setNumberOfLives

    public boolean Live() {
        return live;
    }//end Live

    public void setLive(boolean live) {
        this.live = live;
    }//end setLive

    public void setMoving(boolean moving) {
        this.moving = moving;
    }//end setMoving

    public void setDirection(Direction direction) {
        this.direction = direction;
    }//end setDirection

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }//end setLocation

    public void move(Graphics g) {
        if (moving) {

            int oldX = x;
            int oldY = y;

            switch (direction) {

                case LEFT:
                    x -= speed;
                    break;
                case RIGHT:
                    x += speed;
                    break;
            }//end switch
            
            Collision(oldX, oldY);
        }//end 
        g.drawImage(img, x, y, this.getWidth(), this.getHeight(), null);
    }//end move

}//end GameAction