
import java.util.Comparator;

/** 
 * Class: COP 3330 OBJECT ORIENTED PROGRAMMING (Java) 12:30PM - 1:20PM
 * Homework FOUR-E : AdventureGame Extra credit
 * ScoreComparator Class: Compares the scores for ordering in top 5
 * Comparator to compare the scores on file
 * 
 * @author Justin Holzman
 */
public class ScoreCompare implements Comparator<Score> {
	/** Compare the scores. Sort based on score. @return -1 if score 1 is larger.; @return 1 if score2 is larger.; @return else otherwise; @param score1 This is the first score; @param score2 This is the second score.; */
	public int compare(Score score1, Score score2) {

		int sc1 = score1.getScore();
		int sc2 = score2.getScore();

		// Simple logic sort to order the top scores
		if (sc1 > sc2) {
			return -1;
		} else if (sc1 < sc2) {
			return 1;
		} else {
			return 0;
		}
	}
}