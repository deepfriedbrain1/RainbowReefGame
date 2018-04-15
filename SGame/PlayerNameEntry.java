package SGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class PlayerNameEntry extends JPanel implements PropertyChangeListener, ActionListener {

    private JLabel playerName;
    private static String name = "Player's first name: ";
    private JFormattedTextField nameField;
    private JButton selectionButton;
    private String nameOfPlayer;
    private static JFrame frame;
    private int score = 0;

    public PlayerNameEntry(int score) {
        super(new BorderLayout());
        this.score = score;
        selectionButton = new JButton("Enter");
        selectionButton.addActionListener(this);
        playerName = new JLabel(name);

        nameField = new JFormattedTextField();
        nameField.setValue("Name");
        nameField.setColumns(10);
        nameField.addPropertyChangeListener("value", this);

        playerName.setLabelFor(nameField);

        JPanel labelPane = new JPanel(new GridLayout(0, 1));
        labelPane.add(playerName);
        JPanel fieldPane = new JPanel(new GridLayout(0, 1));
        fieldPane.add(nameField);
        JPanel buttonPane = new JPanel(new GridLayout(0,1));
        buttonPane.add(selectionButton);

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
        add(buttonPane, BorderLayout.SOUTH);
        
    }//end constructor 

    public static void scoreDispose(){
        frame.dispose();
    }//end scoreDispose 
    
    public static void createPlayerEntry(int score) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                frame = new JFrame("Player Entry");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLocation(475, 250);

                frame.add(new PlayerNameEntry(score));

                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();
        if(nameField == source)
            nameOfPlayer = (String)nameField.getValue();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        new ScoreBoard(nameOfPlayer, score);
        scoreDispose();
        
    }

}
