/**
 * Class: COP 3330 OBJECT ORIENTED PROGRAMMING (Java) 12:30PM - 1:20PM
 * Homework FOUR-E : AdventureGame Extra credit
 * Toad Class: Mario, Luigi, and Toad all have similar code 
 * Except for the result of what happens when they move into "said" space.
 * Toad can unblock a cave.
 * 
 * @author Justin Holzman
 */
public class Toad extends Character {

	/** Inital location of Toad */
	public Toad(Cave initLoc) {
		super(initLoc);
	}
	
	/** 
	 * Check for blocked cave. This is only for Toad to unblock!
	 * 
	 * @return	<code>true</code> if the cave is blocked; <code>false</code> otherwise.
	 * @param Loc Location of character */
	public boolean modifyCave(Cave loc) {
		if (loc.isBlocked()) {
			loc.makeOpen(); // Blocked, so set to open.
			return true;
		}

		else {
			return false;
		}
	}

	/** Notify user the cave was unblocked. 
	 * @return modification*/
	public String describeModification() {
		String modification = new String("the cave was unblocked!");
		return modification;
	}

	/** @return	<code>true</code> if the cave is blocked; <code>false</code> if Occupied; <code>true</code> if the cave is a pipe; <code>true</code> otherwise; */
	public boolean move(Cave to) {
		// Toad can move here. Return true and open spot.
		if (to.isBlocked()) {
			super.move(to);
			return true;
		}

		// Filled by another person
		else if (to.isOccupied()) {
			return false;
		}

		// Pipe
		else if (to.isPipe()) {
			super.move(to);
			return true;
		}

		else {
			super.move(to);
			return true;
		}
	}

	/** Returns the name of Toad 
	 * @return <code>Name</code> Name of Toad*/
	public String getName() {
		String name = new String("Toad");
		return name;
	}
}