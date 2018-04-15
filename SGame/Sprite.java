package SGame;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class Sprite {

    public Sprite() {
    }//end default constructor

    public static BufferedImage loadSprite(String name) {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(Sprite.class.getResource("resource/" + name + ".gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }//end try-catch

        return sprite;
    }//end loadSprite
    
}//end Sprite
