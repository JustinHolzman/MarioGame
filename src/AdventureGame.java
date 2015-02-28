
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * Class: COP 3330 OBJECT ORIENTED PROGRAMMING (Java) 12:30PM - 1:20PM
 * Homework FOUR-E : AdventureGame Extra credit
 * Adventure Game: This class holds the core of the program as well as the GUI. Did not have enough time to clan up the code but it's all there.
 * Included a sound method that plays each sound you hear in the game.
 * 
 *  @author Justin Holzman
 *  @author Stephen Fulwider
 */
public class AdventureGame implements KeyListener, MouseListener {

	static String PLAYER_NAME = null;
	int FINAL_SCORE = 2000;
	Cave CHOMP_LOCATION;
	
	public static final int DEFAULT_ROWS = 10;
	public static final int DEFAULT_COLS = 10;

	private static JFrame frame;
	private ImageLabel[][] grid;
	private JLabel statusBar;

	private Board gameBoard; // Underlying board game.
	private List<Character> characters; // Characters on the board.
	private int selected; // Which character is currently selected.

	private boolean powerUpFound = true;
	private boolean peachSaved;

	JMenuBar menuBar;
	JMenu menu;
	JMenu help;
	JMenu currentScore;
	JMenuItem menuItemHowToPlay;
	JMenuItem menuItemReturnToMainMenu;
	JMenuItem menuItemNewGame;
	JMenuItem menuItemHighScores;
	JMenuItem menuItemExit;

	/** Start the first game. 
	 * 
	 * @throws LineUnavailableException 
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * */
	public AdventureGame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		playSoundClip("sounds/entername.wav");
	
		PLAYER_NAME = null;
		UIManager.put("OptionPane.okButtonText", "Start"); // Change default text to start
		PLAYER_NAME = JOptionPane.showInputDialog(null, "Enter your name:", "Press Start To Play", JOptionPane.PLAIN_MESSAGE);			// Get the name of the player
		UIManager.put("OptionPane.okButtonText", "Ok"); // Change back to default
		if(PLAYER_NAME != null){	// If user clicks start
			playSound("sounds/enter.wav");
			playSound("sounds/herewego.wav");

			buildGui();
			newGame();
			updateGameBoard();
		}
		else{			// User clicked on Exit or Close
			playSoundClip("sounds/howtoplayclose.wav");
			new MarioMenu();
			//System.exit(0);
		}
	}

	/** Build the initial GUI of the game board. */
	private void buildGui() {
		// Make the frame.
		frame = new JFrame("Mario Adventure Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// Build the grid.
		grid = new ImageLabel[DEFAULT_ROWS][DEFAULT_COLS];
		JPanel gridPanel = new JPanel(
				new GridLayout(DEFAULT_ROWS, DEFAULT_COLS));
		for (int i = 0; i < grid.length; ++i)
			for (int j = 0; j < grid[i].length; ++j) {
				grid[i][j] = new ImageLabel("icons64/pit.png", i, j);
				grid[i][j].addMouseListener(this);
				gridPanel.add(grid[i][j]);
			}
		frame.add(gridPanel, BorderLayout.CENTER);

		// Add the status bar.
		statusBar = new JLabel();
		statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
		frame.add(statusBar, BorderLayout.SOUTH);

		// Add the listener for key strokes.
		frame.addKeyListener(this);
		frame.setFocusTraversalKeysEnabled(false);
		frame.setVisible(true);			// Make it visible.
		frame.pack();
		frame.setSize(700,750);		// Had problems with open size so added this.
		frame.setLocation(451,60);	// Set open location for window
		frame.setResizable(false);		// Lock frame size so user cannot adjust size of window

		menuBar = new JMenuBar();	// Create the menu bar. With several operations.

		menu = new JMenu("Menu");		// Build the first menu.
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("File menu");
		menuBar.add(menu);
		help = new JMenu("Help");
		menuBar.add(help);
		currentScore = new JMenu("Current Score: " + FINAL_SCORE);
		menuBar.add(currentScore);

		// JMenuItems show the menu items
		menuItemNewGame = new JMenuItem("New Game", new ImageIcon("images/new.gif"));
		menuItemNewGame.setMnemonic(KeyEvent.VK_N);
		menu.add(menuItemNewGame);

		// Add a separation in the menu
		menu.addSeparator();
		
		// HowToPlay
		menuItemHowToPlay = new JMenuItem("How To Play", new ImageIcon("images/howtoplay.gif"));
		menuItemHowToPlay.setMnemonic(KeyEvent.VK_H);
		help.add(menuItemHowToPlay);

		// High Scores
		menuItemHighScores = new JMenuItem("High Scores", new ImageIcon("images/pause.gif"));
		menuItemHighScores.setMnemonic(KeyEvent.VK_P);
		menu.add(menuItemHighScores);
		
		//menuItemReturnToMainMenu
		menuItemReturnToMainMenu = new JMenuItem("Return To Menu", new ImageIcon("images/pause.gif"));
		menuItemReturnToMainMenu.setMnemonic(KeyEvent.VK_R);
		menu.add(menuItemReturnToMainMenu);

		// Exit game
		menuItemExit = new JMenuItem("Exit", new ImageIcon("images/exit.gif"));
		menuItemExit.setMnemonic(KeyEvent.VK_E);
		menu.add(menuItemExit);

		// add menu bar to frame(Gui created already)
		frame.setJMenuBar(menuBar);

		menuItemHighScores.addActionListener(new ActionListener() {	// ActionListener for a click on High Scores
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					highScores();
				} catch (UnsupportedAudioFileException | IOException
						| LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				playSoundClip("sounds/howtoplayclose.wav");
			}
		});

		menuItemExit.addActionListener(new ActionListener() {	// ActionListener for a click on Exit Game
			@Override
			public void actionPerformed(ActionEvent e) {
				playSoundClip("sounds/thanksforplaying.wav");

				JOptionPane.showMessageDialog(null, "Thanks you so much for playing my game.");
				System.exit(0);
			}
		});
		
		menuItemReturnToMainMenu.addActionListener(new ActionListener() {	// ActionListener for a click on Exit Game
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				playSoundClip("sounds/thanksforplaying.wav");

				try {
					new MarioMenu();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		menuItemNewGame.addActionListener(new ActionListener() {	// ActionListener for a click on New Game
			@Override
			public void actionPerformed(ActionEvent e) {
				playSoundClip("sounds/enter.wav");
				playSoundClip("sounds/herewego.wav");

				newGame();
				updateGameBoard();
			}
		});
		
		menuItemHowToPlay.addActionListener(new ActionListener() {	// ActionListener for a click on How to play
			@Override
			public void actionPerformed(ActionEvent e) {
				playSoundClip("sounds/howtoplay.wav");

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
				playSoundClip("sounds/howtoplayclose.wav");
			}
		});
	}

	/** Presents the high scores to the user. 
	 * 
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	protected void highScores() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		playSound("sounds/highscore.wav");
		HighScores highScoreM = new HighScores();			// Create a new high score. If they made top five do accordingly
		//highScoreM.addScore("Justin", 3300); 				// Use for debug to add score to the High Score list
		JOptionPane.showMessageDialog(null, highScoreM.getHighscoreString());
	}

	/** Initialize a new underlying game. */
	private void newGame() {
		powerUpFound = true;
		
		// Reset Score to 2,000 for when when they start a new game.
		FINAL_SCORE = 2000;
		
		// Set up the game board.
		gameBoard = new Board(DEFAULT_ROWS, DEFAULT_COLS);

		// Set up the 3 characters.
		characters = new ArrayList<Character>();

		// Add Mario (always in the top left).
		characters.add(new Mario(gameBoard.getCave(0, 0)));
		selected = 0; // Initially select Mario.

		// Get a location for Toad and add him.
		Cave toadLoc = gameBoard.getUnoccupiedOpenLocation();
		characters.add(new Toad(gameBoard.getCave(toadLoc.getRow(),toadLoc.getCol())));

		// Get a location for Luigi and add him.
		Cave luigiLoc = gameBoard.getUnoccupiedOpenLocation();
		characters.add(new Luigi(gameBoard.getCave(luigiLoc.getRow(), luigiLoc.getCol())));

		// Get a location for Chomp Chomp and add him.
		Cave chompLoc = gameBoard.getUnoccupiedOpenLocation();
		characters.add(new Chomp(gameBoard.getCave(chompLoc.getRow(), chompLoc.getCol())));

		// We seek Peach!
		peachSaved = false;

		updateStatus("Welcome! Select characters with the mouse (or just use TAB) " + "and use the arrow keys to move.");
	}

	/** Update the visible state of the game board based on the internal state of the game. */
	private void updateGameBoard() {
		//Update the menu bar CurrentScore. To seem live, remove the score then just add the new one with updated score.
		menuBar.remove(currentScore);
		currentScore = new JMenu("Current Score: " + FINAL_SCORE);
		menuBar.add(currentScore);
		
		// Put icons on the board.
		for (int i = 0; i < grid.length; ++i)
			for (int j = 0; j < grid[i].length; ++j) {
				Cave c = gameBoard.getCave(i, j);
				String img = "icons64/";
				if (c.isBlocked())
					img += "cave.png";
				else if (c.isBomb())
					img += "bomb.gif";	// Pit
				else if (c.isPowerup())
					img += "rouletteblock.gif";
				else if (c.isPipe() && c.isMarked())
					img += "pipe2.png";
				else
					// open OR teleport and not marked
					img += "ground.png";
				grid[i][j].setIcon(img);
				grid[i][j].setBorder(BorderFactory.createBevelBorder(1));
			}

		// Show the characters. Highlight the selected one.
		int idx = 0;
		for (Character ch : characters) {
			Cave c = ch.getLocation();
			if (ch instanceof Mario)
				grid[c.getRow()][c.getCol()].setIcon("icons64/mario.gif");
			else if (ch instanceof Toad)
				grid[c.getRow()][c.getCol()].setIcon("icons64/toad.gif");
			else if (ch instanceof Luigi)
				grid[c.getRow()][c.getCol()].setIcon("icons64/luigi.gif");
			else if (ch instanceof Chomp)
				grid[c.getRow()][c.getCol()].setIcon("icons64/monster.gif");
			if (idx++ == selected) {
				grid[c.getRow()][c.getCol()].setBorder(BorderFactory.createBevelBorder(1, Color.red, Color.red));
			}
		}

		// Show peach if Mario has not saved her yet.
		if (!peachSaved)
			grid[grid.length - 1][grid[0].length - 1].setIcon("icons64/peach.gif");
	}

	/** Show the given message on the status bar. 
	 * 
	 * @param msg
	 */
	private void updateStatus(String msg) {
		statusBar.setText(msg);
	}

	/** Handle user input from keyboard. */
	public void keyPressed(KeyEvent e) {
		// Check if this is a tab event to move characters.
		if (e.getKeyCode() == KeyEvent.VK_TAB) {
			selected = (selected + 1) % characters.size();
			updateStatus("You have selected "+ characters.get(selected).getName());
			try {
				playerCall();
			} catch (UnsupportedAudioFileException | IOException
					| LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			updateGameBoard();
			return;
		}

		// Check for F2 (new game)
		if (e.getKeyCode() == KeyEvent.VK_F2) {
			newGame();
			updateGameBoard();
			return;
		}
		
		// Only move if the selected character is valid.
		if (selected < 0 || selected >= characters.size()-1) {
			// Selected monster Cannot move!
			if(selected >= characters.size()-1){
				updateStatus("You are not allowed to move Chomp Chomp!");
			}
			return;
		}
		
		// Get the direction of movement.
		int dr = 0, dc = 0;
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			dr = -1;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			dr = 1;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			dc = -1;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			dc = 1;
		} else { // non-recognized key event
			return;
		}
		
		playSoundClip("sounds/move.wav");

		//****************
		AutoMoveChomp();	// Calls autoMove for Chomp on each iteration of a keyStroke. The method will move the Chomp as though he is an AI
		//****************
		
		// Make the move
		Character ch = characters.get(selected);
		Cave c = ch.getLocation();
		if (gameBoard.ok(c.getRow() + dr, c.getCol() + dc)) {
			Cave newC = gameBoard.getCave(c.getRow() + dr, c.getCol() + dc);

			// Make sure only Mario can save Peach.
			if (newC.getRow() == DEFAULT_ROWS - 1
					&& newC.getCol() == DEFAULT_COLS - 1
					&& !(ch instanceof Mario)) {
				updateStatus("Only Mario can save Peach!");
			}

			// Try and make the move.
			else if (ch.move(newC)) {

				// Try and modify this cave is possible.
				CaveWorker cw = ch;
				if (cw.modifyCave(newC)) {
					// Need this or else mario could boost the score by going back and fourth across the pipe location.
					if(!newC.isPipe())
						FINAL_SCORE += 50;
					updateGameBoard();

					// If it is a pipe, present no score update else the character is not mario so present Score +50
					if(newC.isPipe())
						updateStatus(ch.getName() + " successfully moved and " + cw.describeModification()+ " Score doesnt change");
					else
						updateStatus(ch.getName() + " successfully moved and " + cw.describeModification() + " Score +50");
				} else if (newC.isBomb()) { // This character dies.
					playSoundClip("sounds/die.wav");

					updateStatus(ch.getName()+ " was attacked by a bomb omb and died! Score -100");
					FINAL_SCORE -= 100;
					
					//newC.setOccupied(false); *********************
					
					characters.remove(selected);
					selected %= characters.size();
					updateGameBoard();
					if (ch instanceof Mario) {
						if (!peachSaved) {
							JOptionPane.showMessageDialog(frame,ch.getName()+ " is now dead :( " + "No way to save Peach now. Better luck next life!");
						} else {
							JOptionPane.showMessageDialog(frame,ch.getName()+ " is now dead :( " + "Mario was attack by a troopa and they took Peach back to Bowser!");
						}
					}
				} else if (newC.isPipe()) { // Transport this character to a random location.
					playSoundClip("sounds/pipe.wav");

					Cave randomLoc = gameBoard.getUnoccupiedOpenLocation();
					ch.move(randomLoc); // Guaranteed to return true.
					updateGameBoard();
					updateStatus(ch.getName()+ " was teleported through a pipe!");
					
				} else if (newC.isPowerup()) { // Powerup
					newC.makeOpen();
					playSoundClip("sounds/powerupscore.wav");
					FINAL_SCORE += 300;
					if(powerUpFound){
						JOptionPane.showMessageDialog(frame,"Power-up found! Score +300");					
					}
					
					powerUpFound = false;
		
					// This was going to be for the powerup. Was going to change the icon for set turns.
					for (Character ch2 : characters) {
						Cave c2 = ch2.getLocation();
						if (ch2 instanceof Mario){
							grid[c.getRow()][c.getCol()].setIcon("icons64/marioP.gif");
							updateGameBoard();
						}
					}
					if (cw.modifyCave(newC)) {
						updateGameBoard();
					}
				} else {
					updateStatus(ch.getName() + " successfully moved! Score-25");
					FINAL_SCORE -= 25;
					updateGameBoard();
				}

				if (!peachSaved && newC.getRow() == DEFAULT_ROWS - 1 && newC.getCol() == DEFAULT_COLS - 1 && ch instanceof Mario) {
					playSoundClip("sounds/savedpeach.wav");

					peachSaved = true;
					updateGameBoard();
					JOptionPane.showMessageDialog(frame, ch.getName() + " has saved Peach!" + " Fame in the kingdom is now yours! Score +500");
					FINAL_SCORE += 500;
					HighScores newHighScore = new HighScores();
					newHighScore.addScore(PLAYER_NAME, FINAL_SCORE);
					updateStatus("Keep exploring if you desire!");
				}
			} else if (newC.isBlocked()) { // No move can be made.
				updateStatus(ch.getName() + " isn't toad so can't open up blocked caves!");
			} else {
				updateStatus("Only one character can occupy a cave");
			}
			
			if(CHOMP_LOCATION ==  c){		// If the location is the same, treat as though the Chomp Chomp is a moving 'pit'...Remove character.
				//System.out.print("SAME");
				playSoundClip("sounds/die.wav");

				updateStatus(ch.getName()+ "CHOMP CHOMP ATTACK! Score -100");
				FINAL_SCORE -= 100;
				newC.setOccupied(true);
				characters.remove(selected);
				selected %= characters.size();
				updateGameBoard();
				
				if(characters.size() == 1){
					JOptionPane.showMessageDialog(frame, " Everyone is dead, the Chomp Chomp wins! Starting new game.");
					playSoundClip("sounds/everyonedead.wav");

					newGame();
					updateGameBoard();
				}
			}
		} else { // This location is off the board
			updateStatus("No character can leave the caves! Get back to work!");
		}
	}

	/** Call for when the user clicks on a character. 
	 * 
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	private void playerCall() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		//System.out.println(characters.get(selected).getName());
		if (characters.get(selected).getName().equals("Mario")){
			playSound("sounds/clickmario.wav");
		}
		else if(characters.get(selected).getName().equals("Toad")){
			playSound("sounds/clicktoad.wav");
		}
		else if(characters.get(selected).getName().equals("Luigi")){
			playSound("sounds/clickluigi.wav");
		}
		else{
			playSound("sounds/clickchomp.wav");
		}
	}
	
	/** Auto movement for the Chomp Chomp AI. */
	private void AutoMoveChomp() {
		// Get the direction of movement.
				int dr = 0, dc = 0;
				int randNum = (int) (Math.random() * ( 5 - 1 ));	// Generate rand number to decide movement
				if(randNum == 0)
					dr = -1;
				else if(randNum == 1)
					dr = 1;
				else if(randNum == 2)
					dc = -1;
				else
					dc = 1;
		
		int index = characters.size();
		Character ch = characters.get(index -1);
		Cave c = ch.getLocation();
		CHOMP_LOCATION = c;
		
		if (gameBoard.ok(c.getRow() + dr, c.getCol() + dc)) {
			Cave newC = gameBoard.getCave(c.getRow() + dr, c.getCol() + dc);

			// Make sure only the adventurer moves to the treasure.
			if (newC.getRow() == DEFAULT_ROWS - 1 && newC.getCol() == DEFAULT_COLS - 1 && !(ch instanceof Mario)) {
				updateStatus("Only Mario can save Peach!");
			}

			// Try and make the move.
			else if (ch.move(newC)) {
				// Try and modify this cave is possible.
				CaveWorker cw = ch;
				if (cw.modifyCave(newC)) {
					updateGameBoard();
				} else {
					updateStatus(ch.getName() + " successfully moved!");
					updateGameBoard();
				}
			} else if (newC.isBlocked()) { // No move can be made.
				updateStatus(ch.getName());
			} else {
				updateStatus("Chomp Chomp attack!");
				characters.remove(selected);
				updateGameBoard();
			}
		} else { // This location is off the board
			updateStatus("No character can leave the caves! Get back to work!");
		}
	}

	/** Handle input from user with a mouse. */
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent() instanceof ImageLabel) {
			ImageLabel il = (ImageLabel) e.getComponent();		// Get which ImageLabel was clicked.
			int row = il.r, col = il.c;		// Get its row & column.

			// See if any characters on the grid are at this spot.
			int idx = 0;
			for (Character ch : characters) {
				Cave c = ch.getLocation();
				if (c.getRow() == row && c.getCol() == col) {
					selected = idx;
					updateGameBoard();
					try {
						playerCall(); 		// Player sound when clicked
					} catch (UnsupportedAudioFileException | IOException
							| LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					updateStatus("You have selected " + ch.getName());
					return;
				}
				++idx;
			}
		}
	}

	// Don't need to do anything with these, but must implement per the
	// KeyListener interface.
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}

	// Don't need to do anything with these, but must implement per the
	// MouseListener interface.
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}

	/** Main method. Plays sound to start the game. Gets name from user.
	 * 
	 * @param args
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new MarioMenu();
		playSound("sounds/start.wav");
	}

	/** Helper class for an Image label that displays an icon instead of text.*/
	private static class ImageLabel extends JLabel {

		static TreeMap<String, ImageIcon> M = new TreeMap<String, ImageIcon>();
		private int r, c;

		public ImageLabel(String img, int r, int c) {
			this(new ImageIcon(img), r, c);
		}

		public ImageLabel(ImageIcon icon, int r, int c) {
			setIcon(icon);
			// setSize(icon.getImage().getWidth(null),
			// icon.getImage().getHeight(null));
			this.r = r;
			this.c = c;
		}

		public void setIcon(String img) {
			if (!M.containsKey(img))
				M.put(img, new ImageIcon(img));
			setIcon(M.get(img));
		}
	}
	
	/** Method to throw any exceptions for sound clip file
	 * 
	 * @param FileName
	 */
	public static void playSoundClip(String FileName){
		try {
			playSound(FileName);
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
		Clip clip = (Clip)AudioSystem.getLine(info);
		// Load the sample clip from the stream/open
		clip.open(stream);
		// begin playback of the sound clip/play
		clip.start();
	}
}
