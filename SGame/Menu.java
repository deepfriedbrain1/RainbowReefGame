package SGame;

import java.awt.Dimension;
import javax.swing.JLabel;

public final class Menu extends JLabel {

    private final Dimension minSize;

    public Menu() {
        minSize = new Dimension(500, 34);
    }//end default constructor

    @Override
    public Dimension getMinimumSize() {
        return minSize;
    }//end getMinimumSize

    @Override
    public Dimension getPreferredSize() {
        return minSize;
    }//end getPreferredSize
}//end Menu
