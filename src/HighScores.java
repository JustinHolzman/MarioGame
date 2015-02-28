import java.util.*;
import java.io.*;

/**
 * Class: COP 3330 OBJECT ORIENTED PROGRAMMING (Java) 12:30PM - 1:20PM
 * Homework FOUR-E : AdventureGame Extra credit
 * HighScore Class: Calculates the high score for the game
 * 
 * @author Justin Holzman
 */
public class HighScores {
	// Size of top scores...Made global for easy switch to top 10 if desired.
	 int sizeOfTopScores = 5;
	
    // An arraylist of the type "score" with scores inside
    private ArrayList<Score> scores;

    // Define where the highscores will be saved
    private static final String HIGHSCORE_FILE_NAME = "HighScores.txt";

    //Initializing an in and outputStream for working with the file
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    /** HighScores method */
    public HighScores() {
        //Initializing the scores-arraylist
        scores = new ArrayList<Score>();
    }
    
    /** Get scores from file, load. 
     * 
     * @return scores
     */
    public ArrayList<Score> getScores() {
        loadScoreFile();
        sort();
        return scores;
    }
    
    /** Sort scores. */
    private void sort() {
        ScoreCompare compare = new ScoreCompare();
        Collections.sort(scores, compare);
    }
    
    /** Add a score to the file. 
     * 
     * @param name Name of Player
     * @param score Score they got
     */
    public void addScore(String name, int score) {
        loadScoreFile();
        scores.add(new Score(name, score));
        updateHighScoreFile();
    }
    
    /** load the score file */
    @SuppressWarnings("unchecked")
	public void loadScoreFile() {
       // Load file but multi try catch for FileNotFound, IO, and class not found Exception.
    	try {
            inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE_NAME));
            scores = (ArrayList<Score>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Error file not found" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
		} finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
        }
    }
    
    /** Update high score file. */
    public void updateHighScoreFile() {
        // Try to update file, catch fileNotFound, and IO exception.
    	try {
            outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE_NAME));
            outputStream.writeObject(scores);
        } catch (FileNotFoundException e) {
            System.out.println("Error File not Found" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();	// Flush: Flushes the stream. This will write any buffered output bytes and flush through to the underlying stream.
                    outputStream.close();	// Close stream
                }
            } catch (Exception e) {	// IO exception for flush and close
                System.out.println("Error " + e.getMessage());
            }
        }
    }
    
    /** Get the High Score string to write to screen on prompt. @return highscoreString This is a string of the high scores. 
     * 
     * @return highscoreString
     */
    public String getHighscoreString() {
        String highscoreString = "";

        // Convert from array list to scores
        ArrayList<Score> scores;
        scores = getScores();

        int i = 0;
        int j = scores.size();
      
        if (j > sizeOfTopScores) {
            j = sizeOfTopScores;
        }
        
        while (i < j) {
            highscoreString += (i + 1) + ".\t           " + scores.get(i).getName() + "\t\t           " + scores.get(i).getScore() + "\n";
            i++;
        }
        
        return highscoreString;
    }
}