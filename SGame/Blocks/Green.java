package SGame.Blocks;

import SGame.GObject;
import SGame.Sprite;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;




public class Green extends GObject {
	  private int x;
  	private int y;
	  private int width;
	  private int height;
    private int hit = 100;
    private static BufferedImage img;
  
	
static {
    img = Sprite.loadSprite("Block4");
		
	}
  
	public Green(int x, int y, int width, int height, int speed) throws IOException {
        super(img, x, y, speed);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

  
	public void draw(Graphics g) {
            g.drawImage(img, x, y, width, height, null);
         
	} 

}    
	
