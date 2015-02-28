import java.io.Serializable;

/**
 * Class: COP 3330 OBJECT ORIENTED PROGRAMMING (Java) 12:30PM - 1:20PM
 * Homework FOUR-E : AdventureGame Extra credit
 * Score Class: returns the name, and score for the user playing.
 * 
 * @author Justin Holzman
 */
@SuppressWarnings("serial")
public class Score implements Serializable {
	private int score;
	private String name;

	/** @return score*/
	public int getScore() {
		return score;
	}

	/** @return name*/
	public String getName() {
		return name;
	}

	/** 
	 * Sets score and name. 
	 * 
	 * @param Name This is the name of the user. 
	 * @param score This is the score(High Score). 
	 */
	public Score(String name, int score) {
		this.score = score;
		this.name = name;
	}
}