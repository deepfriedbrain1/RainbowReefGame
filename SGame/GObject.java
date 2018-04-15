package SGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GObject {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int diagSpeed;
    protected float speed;
    protected BufferedImage img;

    public GObject() {
    }//end default constructor

    public GObject(BufferedImage img, int x, int y) {
        this.img=img;
        this.x=x;
        this.y=y;
        if (img != null) {
            this.width = img.getWidth();
            this.height = img.getHeight();
        }//end if
    }//end constructor

    public GObject(BufferedImage img, int x, int y, float speed) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.diagSpeed = (int) (speed / Math.sqrt(2));
        if (img != null) {
            this.width = img.getWidth();
            this.height = img.getHeight();
        }//end if
    }//end constructor

    public BufferedImage getImage() {
        return img;
    }//end getImage
    
    public void setSpeed(float sp){
        this.speed=sp;
    }//end setSpeed

    public int getX() {
        return this.x;
    }//end getX

    public int getY() {
        return this.y;
    }//end getY

    public int getWidth() {
        return this.width;
    }//end getWidth

    public int getDiagSp() {
        return this.diagSpeed;
    }//end getDiagSp

    public int getHeight() {
        return this.height;
    }//end getHeight

    public void setX(int a) {
        this.x = a;
    }//end setX

    public void setY(int b) {
        this.y = b;
    }//end setY

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }//end getRectangle
    
    //left side
    public Rectangle getRect() {
        return new Rectangle(x,y,30,30);
    }//end getRectangle
    
    //center side 
    public Rectangle getRec() {
        return new Rectangle(x+30,y,20, 30);
    }//end getRectangle
    
    //right side 
    public Rectangle getR() {
        return new Rectangle(x+60,y,40, 30);
    }//end getRectangle

}//end GObject
