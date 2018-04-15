package SGame;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static SGame.SRRGame.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.logging.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public final class MouseMotionEvent extends JPanel implements MouseListener {

    private BufferedImage background;
    private Menu optionsMenu;
    private boolean dispose = false;
    private JFrame frame;

    public MouseMotionEvent() {
        super(new GridLayout(36, 516));
        optionsMenu = new Menu();
        add(optionsMenu);
        optionsMenu.addMouseListener(this);
        addMouseListener(this);

        try {
            // Load images for score screen and background
            background = ImageIO.read(SRRGame.class.getResource("resource/Background1.bmp"));

        } catch (IOException ioe) {
            System.out.println("*** IOException: MouseMotionEvent: unable to load background:  " + ioe.getMessage());
        }//end try-catch
    }//end constructor

    //Class for level selection 
    private class ListSelection extends JPanel implements ActionListener {

        JTextArea output;
        JList list;
        JTable table;
        ListSelectionModel list_selection_model;
        JSplitPane splitPane;

        public ListSelection() {
            super(new BorderLayout());

            String[] listData = {"Level 1", "Level 2", "Level 3"};
            list = new JList(listData);

            list_selection_model = list.getSelectionModel();
            list_selection_model.addListSelectionListener(
                    new SharedListSelectionHandler());
            JScrollPane listPane = new JScrollPane(list);

            //Build output area
            output = new JTextArea(1, 10);
            output.setEditable(false);
            JScrollPane outputPane = new JScrollPane(output,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            //Do the layout
            splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            add(splitPane, BorderLayout.CENTER);

            // top half of splitPane to select levels
            JPanel topHalf = new JPanel();
            topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.LINE_AXIS));
            JPanel listContainer = new JPanel(new GridLayout(1, 1));
            listContainer.setBorder(BorderFactory.createTitledBorder("Levels"));
            listContainer.add(listPane);
            topHalf.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
            topHalf.add(listContainer);
            topHalf.setMinimumSize(new Dimension(230, 100));
            topHalf.setPreferredSize(new Dimension(250, 100));
            splitPane.add(topHalf);

            //BottomHalf
            JPanel bottomHalf = new JPanel(new BorderLayout());
            JPanel contentPane = new JPanel();
            JButton selectionButton = new JButton("Select");
            selectionButton.addActionListener(this);
            contentPane.add(selectionButton);
            bottomHalf.add(contentPane, BorderLayout.PAGE_START);
            bottomHalf.setPreferredSize(new Dimension(20, 40));
            splitPane.add(bottomHalf);
        }//end constructor

        @Override
        public void actionPerformed(ActionEvent e) {
            // Used for Load menu option selection of levels: 
            // Level 1 = 0, Level 2 = 1, Level 3 = 2
            if (list_selection_model.getAnchorSelectionIndex() >= 0) {
                loadLevel = list_selection_model.getAnchorSelectionIndex();
                
                if (loadLevel == 0) {
                   reLoadLevel = true;
                   gameOver = false;
                   
                   //System.out.println("loadLevel: " + loadLevel);
                }
                if (loadLevel == 1) {
                    reLoadLevel = true;
                    gameOver = false;
                    //System.out.println("loadLevel: " + loadLevel);
                }
                if (loadLevel == 2) {
                    reLoadLevel = true;
                    gameOver = false;
                    //System.out.println("loadLevel: " + loadLevel);
                }
                frame.dispose();
            }//end if

        }//end actionPerformed
    }//end ListSelection

    private class SharedListSelectionHandler implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        }//end valueChanged
    }//end SharedListSelectionHandler

    private class HelpTextField extends JFrame implements DocumentListener {

        private JTextField entry;
        private JLabel label;
        private JScrollPane scrollPane;
        private JLabel status;
        private JTextArea textArea;

        private final Color HIGHLIGHT_COLOR = Color.LIGHT_GRAY;
        private final Color ERROR_COLOR = Color.PINK;
        private final String CANCEL_ACTION = "cancel-search";

        private final Color entryBackground;
        private final Highlighter highlighter;
        private final Highlighter.HighlightPainter painter;

        public HelpTextField() {
            initComponents();

            InputStream in = getClass().getResourceAsStream("resource/help.txt");
            try {
                textArea.read(new InputStreamReader(in), null);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }//try-catch

            highlighter = new DefaultHighlighter();
            painter = new DefaultHighlighter.DefaultHighlightPainter(HIGHLIGHT_COLOR);
            textArea.setHighlighter(highlighter);

            entryBackground = entry.getBackground();
            entry.getDocument().addDocumentListener(this);

            InputMap inputMap = entry.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = entry.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), CANCEL_ACTION);
            actionMap.put(CANCEL_ACTION, new CancelAction());
        }//end HelpTextField

        private void initComponents() {
            entry = new JTextField();
            textArea = new JTextArea();
            status = new JLabel();
            label = new JLabel();

            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setTitle("Help");

            textArea.setColumns(22);
            textArea.setLineWrap(true);
            textArea.setRows(6);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            scrollPane = new JScrollPane(textArea);

            label.setText("Enter text to search:");

            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);

            //Create a parallel group for the horizontal axis
            GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);

            //Create a sequential and a parallel groups
            GroupLayout.SequentialGroup h1 = layout.createSequentialGroup();
            GroupLayout.ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);

            //Add a container gap to the sequential group h1
            h1.addContainerGap();

            //Add a scroll pane and a label to the parallel group h2
            h2.addComponent(scrollPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);
            h2.addComponent(status, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);

            //Create a sequential group h3
            GroupLayout.SequentialGroup h3 = layout.createSequentialGroup();
            h3.addComponent(label);
            h3.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
            h3.addComponent(entry, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE);

            //Add the group h3 to the group h2
            h2.addGroup(h3);
            //Add the group h2 to the group h1
            h1.addGroup(h2);

            h1.addContainerGap();

            //Add the group h1 to the hGroup
            hGroup.addGroup(GroupLayout.Alignment.TRAILING, h1);
            //Create the horizontal group
            layout.setHorizontalGroup(hGroup);

            //Create a parallel group for the vertical axis
            GroupLayout.ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
            //Create a sequential group v1
            GroupLayout.SequentialGroup v1 = layout.createSequentialGroup();
            //Add a container gap to the sequential group v1
            v1.addContainerGap();
            //Create a parallel group v2
            GroupLayout.ParallelGroup v2 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
            v2.addComponent(label);
            v2.addComponent(entry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
            //Add the group v2 tp the group v1
            v1.addGroup(v2);
            v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            v1.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE);
            v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            v1.addComponent(status);
            v1.addContainerGap();

            //Add the group v1 to the group vGroup
            vGroup.addGroup(v1);
            //Create the vertical group
            layout.setVerticalGroup(vGroup);
            pack();
        }//end initComponets

        public void search() {
            highlighter.removeAllHighlights();

            String str = entry.getText();
            if (str.length() <= 0) {
                message("Nothing to search");
                return;
            }

            String text_content = textArea.getText();
            int i = text_content.indexOf(str, 0);
            if (i >= 0) {
                try {
                    int end = i + str.length();
                    highlighter.addHighlight(i, end, painter);
                    textArea.setCaretPosition(end);
                    entry.setBackground(entryBackground);
                    message("'" + str + "' found. Press ESC to end search");
                } catch (BadLocationException ble) {
                    ble.printStackTrace();
                }
            } else {
                entry.setBackground(ERROR_COLOR);
                message("'" + str + "' not found. Press ESC to start a new search");
            }
        }//end search

        public void message(String message) {
            status.setText(message);
        }//end message

        @Override
        public void insertUpdate(DocumentEvent e) {
            search();
        }//end insertUpdate

        @Override
        public void removeUpdate(DocumentEvent e) {
            search();
        }//end removeUpdate

        @Override
        public void changedUpdate(DocumentEvent e) {
        }//end changedUpdate

        class CancelAction extends AbstractAction {

            @Override
            public void actionPerformed(ActionEvent e) {
                highlighter.removeAllHighlights();
                entry.setText("");
                entry.setBackground(entryBackground);
            }//end actionPerformed
        }//end CancelAction
    }//end HelpTextField

    private class Scores extends JFrame {

        private JLabel label;
        private JLabel label_table;

        public Scores() {
            setTitle("Scores");
            setSize(230, 300);
            setLocation(490, 150);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setResizable(false);
            setVisible(true);
            setLayout(new BorderLayout());
            JLabel b_image = new JLabel(new ImageIcon(background));
            add(b_image);
            b_image.setLayout(new FlowLayout());
            label = new JLabel("", JLabel.CENTER);
            label.setText("<html>" + "<b>HIGH SCORES</b><br>" + "</html>");
            label.setFont(new Font("<b>Courier New </b>", Font.PLAIN, 22));

            b_image.add(label);
            String str = "";
            
            // Use HTML tags to format text in JLabel 
            try {
                new ScoreBoard("<<EMPTY>> ", 0);
                Path path = Paths.get("score_table.txt");
                BufferedReader br = Files.newBufferedReader(path);
                String line = br.readLine();
                label_table = new JLabel("", JLabel.CENTER);
                while (line != null) {
                    if(line.contains("<<EMPTY>>  0")){
                        str += "&lt EMPTY " + "&gt  0" + "<br>";
                    }else{
                        str += line + "<br>";
                    }
                    line = br.readLine();
                    if(line == null){
                        label_table.setText("<html>" + str + "</html>");
                        label_table.setFont(new Font("Courier New", Font.PLAIN, 14));
                        b_image.add(label_table);    
                    }                   
                }
            } catch (IOException ex) {
                Logger.getLogger(SRRGame.class.getName()).log(Level.SEVERE, null, ex);
            }//end try-catch

        }//end constructor
    }//end Scores

    @Override
    public void mouseClicked(MouseEvent e) {
        // Start button position and action
        if ((e.getX() >= 36 && e.getX() <= 136) && (e.getY() >= 516 && e.getY() <= 550)) {
            counter = 0; // releases title screen 
        }
        // Load button
        if ((e.getX() >= 160 && e.getX() <= 260) && (e.getY() >= 516 && e.getY() <= 550)) {
            counter += 2000;
            //Create and set up the window
            frame = new JFrame("Level Selection");
            frame.setLocation(500, 270);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            //Create and set up the text_content pane
            ListSelection list_selection = new ListSelection();
            list_selection.setOpaque(true);
            frame.getContentPane().add(list_selection);

            //Display the window
            frame.pack();
            frame.setVisible(true);
        }
        //  Help button
        if ((e.getX() >= 285 && e.getX() <= 385) && (e.getY() >= 516 && e.getY() <= 550)) {
            counter += 2000;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    UIManager.put("swing.boldMetal", Boolean.FALSE);
                    HelpTextField htf = new HelpTextField();
                    htf.setVisible(true);
                    htf.setLocation(400, 150);
                    htf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            });
        }
        // Scores button
        if ((e.getX() >= 409 && e.getX() <= 509) && (e.getY() >= 516 && e.getY() <= 550)) {
            counter += 2000;
            new Scores();

        }
        // Quit button
        if ((e.getX() >= 527 && e.getX() <= 627) && (e.getY() >= 516 && e.getY() <= 550)) {
            System.exit(0);
        }
    }//end mouseClicked

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}//end MouseMotionEvent
