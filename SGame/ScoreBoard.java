package SGame;

import java.io.*;
import java.nio.file.*;
import java.util.StringTokenizer;
import java.util.logging.*;

public final class ScoreBoard {

    private String[] names;
    private int[] scores;
    private final int INITIAL_CAPACITY = 10; // Top 10 High Scores
    private BufferedReader br;
    private String line;
    private String str;

    static {
        try {
            //Determine if file already exists...if not throw exception 
            System.out.println("Reading file...");
            new FileReader("score_table.txt");
        } catch (FileNotFoundException ex) {
            try {
                //Exception thrown so create a new blank score_table.txt
                System.out.println("File Not Found, creating new empty text file...");
                new FileWriter("score_table.txt");
            } catch (IOException ex1) {
                Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex1);
            }//end try-catch
        }//end try-catch
    }//end static

    public ScoreBoard() {
        this("<<EMPTY>>", 0);
    }//end default constructor

    public ScoreBoard(String name, int score) {
        try {
            //names array contains empty name holder 
            names = new String[INITIAL_CAPACITY];
            for (int i = 0; i < INITIAL_CAPACITY; i++) {
                names[i] = "<<EMPTY>> ";
            }

            //scores array contains zero scores
            scores = new int[INITIAL_CAPACITY];
            for (int i = 0; i < INITIAL_CAPACITY; i++) {
                scores[i] = 0;
            }

            //Setup values for buffered reader
            str = "";
            int i = 0;
            Path path = Paths.get("score_table.txt");
            br = Files.newBufferedReader(path);
            line = br.readLine();

            while (line != null) {
                // Tokenizer w/ delimiters
                StringTokenizer st = new StringTokenizer(line, " \n\t");
                while (st.hasMoreElements()) {
                    str = st.nextToken();
                    if (isInteger(str)) { // check if str is integer
                        scores[i] = (int) Integer.parseInt(str);

                    } else {
                        if (names[i] != null) {
                            names[i] = str + " ";
                        }

                    }//end if-else
                }//end while
                if (i < INITIAL_CAPACITY) {
                    i++;
                }
                line = br.readLine();
            }//end while

            // close buffered reader
            br.close();

            // sort scores in desending order
            int index = 0;
            while (index != (INITIAL_CAPACITY)) {
                if (scores[index] < score) {
                    int temp = scores[index];
                    String tName = names[index];
                    scores[index] = score;
                    names[index] = name;
                    score = temp;
                    name = tName;
                }//end if

                index++;

            }//end while

            // save to file on hard drive
            saveToFile();

        } catch (ArrayIndexOutOfBoundsException ae) {
            try {
                // Detect an ill formed score_table and create new blank
                System.out.println("Corrupt file detected recreating blank score table...");
                new FileWriter("score_table.txt");
                System.out.println("Done recreating.");
            } catch (IOException ex1) {
                Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (IOException ex) {
            Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex);
        }//end try-catch
    }//end constructor

    public void saveToFile() {
        BufferedWriter writer = null;
        try {

            writer = new BufferedWriter(new FileWriter("score_table.txt", false));
            writer.flush();

            for (int index = 0; index < INITIAL_CAPACITY; index++) {
                writer.write(names[index] + " " + scores[index] + "\n");
            }//end for

        } catch (IOException ex) {
            Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//end finally
    }//end saveToFile

    // Returns true if string is integer, otherwise returns false
    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }//end isInteger

}//end ScoreBoard
