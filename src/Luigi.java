/**
 * Class: COP 3330 OBJECT ORIENTED PROGRAMMING (Java) 12:30PM - 1:20PM
 * Homework FOUR-E : AdventureGame Extra credit
 * Luigi Class: Mario, Luigi, and Toad all have similar code 
 * Except for the result of what happens when they move into "said" space.
 * Luigi can defuse a bomb omb.
 * 
 * @author Justin Holzman
 */
public class Luigi extends Character {

	/** Inital location of Luigi. @param initLoc */
	public Luigi(Cave initLoc) {
		super(initLoc);
	}

	 /** Check for Bomb omb. This is only for Luigi to defuse!
	 * 
	 * @return	<code>true</code> if the location is a bomb omb; <code>false</code> otherwise.
	 * @param <code>loc</code> Location of character */
	public boolean modifyCave(Cave loc) {
		if (loc.isBomb()) { // Bomb found
			loc.makeOpen();
			return true;
		}

		else {
			return false;
		}
	}

	/** Notify user the bomb omb was diffused 
	 * @return modification */
	public String describeModification() {
		String modification = new String("the bomb omb was diffused!");
		return modification;
	}

	/** @return	<code>false</code> if the cave is blocked; <code>false</code> if Occupied; <code>true</code> if bomb; <code>true</code> if the cave is a pipe; <code>true</code> otherwise; */
	public boolean move(Cave to) {
		// Blocked, can't move. Only Toad can move here
		if (to.isBlocked()) {
			return false;
		}

		// Bomb omb, can move. Only the Luigi can move here.
		else if (to.isBomb()) {
			super.move(to);
			return true;
		}

		// Filled by another character
		else if (to.isOccupied()) {
			return false;
		}

		// Teleport
		else if (to.isPipe()) {
			super.move(to);
			return true;
		}

		else {
			super.move(to);
			return true;
		}
	}

	/** Returns the name of Luigi 
	 * @return <code>name</code> Name of Luigi*/
	public String getName() {
		String name = new String("Luigi");
		return name;
	}
}