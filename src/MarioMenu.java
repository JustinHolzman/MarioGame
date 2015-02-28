import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Class: COP 3330 OBJECT ORIENTED PROGRAMMING (Java) 12:30PM - 1:20PM
 * Homework FOUR-E : AdventureGame Extra credit
 * MarioMenu class: This class displays the main menu to the user.
 * 
 * @author Justin Holzman
 */
public class MarioMenu extends JFrame implements ActionListener {

	static Clip SOUND_CLIP;
	static Clip MARIO_THEME;
	JButton startButton = new JButton("Start");
	JButton highScoresButton = new JButton("High Scores");
	JButton howToPlayButton = new JButton("How To Play");
	JButton exitButton = new JButton("Exit");
	private static JFrame frame;
	public static String menuOption;
	public static boolean optionStart;

	/** GUI build for the main menu
	 * 
	 * @throws IOException
	 */
	MarioMenu() throws IOException {
		try {
			playMarioTheme("sounds/mariotheme.wav");
			MARIO_THEME.loop(Clip.LOOP_CONTINUOUSLY);	// Loop through the theme song while they are on the main screen
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Add's a fade effect to the main theme song on the menu. Will lower the decibel lvl by 30 each time an item is clicked.
		FloatControl volumeCTL = (FloatControl) MARIO_THEME.getControl(FloatControl.Type.MASTER_GAIN);
		volumeCTL.setValue(-10.0f);
		
		frame = new JFrame("Mario Adventure Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// create a new dimension object
		Dimension size = new Dimension(200, 40);

		// set the four kind of size, the button CANNOT be different than the dimension I choose
		startButton.setSize(size);
		startButton.setMinimumSize(size);
		startButton.setMaximumSize(size);
		startButton.setPreferredSize(size);
		startButton.setBounds(450, 250, 220, 30);
		startButton.addActionListener(this);

		// set the four kind of size, the button CANNOT be different than the dimension I choose
		highScoresButton.setSize(size);
		highScoresButton.setMinimumSize(size);
		highScoresButton.setMaximumSize(size);
		highScoresButton.setPreferredSize(size);
		highScoresButton.setBounds(450, 300, 220, 30);
		highScoresButton.addActionListener(this);
		
		// set the four kind of size, the button CANNOT be different than the dimension I choose
		howToPlayButton.setSize(size);
		howToPlayButton.setMinimumSize(size);
		howToPlayButton.setMaximumSize(size);
		howToPlayButton.setPreferredSize(size);
		howToPlayButton.setBounds(450, 350, 220, 30);
		howToPlayButton.addActionListener(this);
		
		// set the four kind of size, the button CANNOT be different than the dimension I choose
		exitButton.setSize(size);
		exitButton.setMinimumSize(size);
		exitButton.setMaximumSize(size);
		exitButton.setPreferredSize(size);
		exitButton.setBounds(450, 400, 220, 30);
		exitButton.addActionListener(this);

		// Now that the button are ready we put them in the panel.
		frame.add(startButton);
		frame.add(highScoresButton);
		frame.add(howToPlayButton);
		frame.add(exitButton);

		frame.getContentPane().add(new Background("menu.png"));
		frame.addMouseListener(null);
		frame.setFocusTraversalKeysEnabled(false);
		frame.setVisible(true); // Make it visible.
		frame.pack();
		frame.setSize(700, 750); // Had problems with open size so added this.
		frame.setLocation(451, 60); // Set open location for window
		frame.setResizable(false); // Lock frame size so user cannot adjust size of window
	}

	@Override
	public void actionPerformed(ActionEvent even) {
		Object option = even.getSource();

		if (option == startButton) {
			frame.setVisible(false);	// Close frame
			MARIO_THEME.stop();	// Stop loop of clip2 (Theme music for menu)
			
			try {
				optionStart();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (option == highScoresButton) {
			optionHighScores();
		}
		else if (option == howToPlayButton) {
			optionHowToPlay();
		}
		else if (option == exitButton) {
			System.exit(0);
		}
	}

	/** Option start
	 * 
	 * @return <code>true</code>
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	static boolean optionStart() throws UnsupportedAudioFileException,
			IOException, LineUnavailableException {
		// TODO Auto-generated method stub
		new AdventureGame();
		return true;
	}
	
	/** display the rules to the user */
	static void optionHowToPlay(){
		try {
			playSound("sounds/howtoplay.wav");
		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "The object of the game is to save princess peach from Bowser! " +
				"\n************************************************************************" +
				"\n                                     ********Mario********\n1) Ability to move around on open spaces.\n2) Only one allowed to save Princess Peach." +
				"\n3) Reveals the loaction of hidden pipes." +
				"\n\n                                     ********Luigi********\n1) Ability to move on open spaces.\n2) Only one able to defuse a bomb omb" +
				"\n\n                                     ********Toad********\n1) Ability to move on open spaces.\n2)Only one able to unblock blocked spaces" +
				"\n\n                           ********Other Information********" +
				"\n1) The roulette block will add 300 to your score.\n2) Each move is -25 points so save peach as fast as you can!\n3) Hidden Pipes will warp you to a random location!" +
				"\n3) You start at a score of 2,000 and can go up or down from there." +
				"\n\n                              ********Chomp Chomp********\nMoves around at random and will kill any character that is moving.");
		try {
			playSound("sounds/howtoplayclose.wav");
		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/** Display the high scores to the user */
	static void optionHighScores(){
		try {
			playSound("sounds/highscore.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HighScores highScoreM = new HighScores();			// Create a new high score. If they made top five do accordingly
		//highScoreM.addScore("Justin", 3300); 				// Use for debug to add score to the High Score list
		JOptionPane.showMessageDialog(null, highScoreM.getHighscoreString());
		try {
			playSound("sounds/howtoplayclose.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/** Method to play clip. Method will be called throughout program. Throws needed exceptions.
	 * 
	 * @param fileName
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public static void playSound(final String fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File file = new File(fileName);		// Pased in file name is now converted to type file
		AudioInputStream stream = AudioSystem.getAudioInputStream(file);
		AudioFormat format = stream.getFormat();
		
		// specify what kind of line we want to create/Create
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		// Create the line/clip
		SOUND_CLIP = (Clip)AudioSystem.getLine(info);
		// Load the sample clip from the stream/open
		SOUND_CLIP.open(stream);
		// begin playback of the sound clip/play
		SOUND_CLIP.start();
	}
	
	/** Method to play theme song. Method will be called throughout program. Throws needed exceptions. 
	 * 
	 * @param fileName
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public static void playMarioTheme(final String fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File file = new File(fileName);		// Pased in file name is now converted to type file
		AudioInputStream stream = AudioSystem.getAudioInputStream(file);
		AudioFormat format = stream.getFormat();
		
		// specify what kind of line we want to create/Create
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		// Create the line/clip
		MARIO_THEME = (Clip)AudioSystem.getLine(info);
		// Load the sample clip from the stream/open
		MARIO_THEME.open(stream);
		// begin playback of the sound clip/play
		MARIO_THEME.start();
	}
}
