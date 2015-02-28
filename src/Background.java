import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Class: COP 3330 OBJECT ORIENTED PROGRAMMING (Java) 12:30PM - 1:20PM
 * Homework FOUR-E : AdventureGame Extra credit
 * Background class: Class to add wallpaper to the main menu frame (GUI)
 * 
 * @author Justin Holzman
 */
public class Background extends JPanel {
  private Image menuBackgroundImage;

  /** Initialize the background image.
   * 
   * @param fileName
   * @throws IOException
   */
  public Background(String fileName) throws IOException {
    menuBackgroundImage = ImageIO.read(new File(fileName));
  }

  /** Paint to frame */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Draw the background image.
    g.drawImage(menuBackgroundImage, 0, 0, this);
  }
}