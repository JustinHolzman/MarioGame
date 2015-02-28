/**
 * Class: COP 3330 OBJECT ORIENTED PROGRAMMING (Java) 12:30PM - 1:20PM
 * Homework FOUR-E : AdventureGame Extra credit
 * Mario Class: Mario, Luigi, and Toad all have similar code 
 * Except for the result of what happens when they move into "said" space.
 * Mario can move over/mark pipes and save Princess Peach.
 * 
 * @author Justin Holzman
 */
public class Mario extends Character {
			
	/** InitLoc is at the top left corner of the grid.  
	 * @param <code>initLoc</code>*/
	public Mario(Cave initLoc) {
		super(initLoc);
	}
	
	/** 
	 * Check for Pipe. This is only for Mario to mark!
	 * 
	 * @return	<code>true</code> if the cave was modified (i.e. it is a teleport pipe); <code>false</code> otherwise.
	 * @param <code>loc</code> Location of character */
	public boolean modifyCave(Cave loc) {
		if(loc.isPipe()){
			loc.setMarked(true);
			return true;
		}
		
		else {
			return false;
		}
	}

	/** Notify the user that a pipe was marked */
	public String describeModification() {
		String modification = new String("the pipe was marked!");
		return modification;
	}
	
	/** @return	<code>false</code> if the cave is blocked; <code>false</code> if Occupied; <code>true</code> if the cave is a pipe; <code>true</code> otherwise; */
	public boolean move(Cave to) {
		// Blocked, so user cannot move
		if(to.isBlocked()) {
			return false;			
		}
		
		// Teleport, so set to mark location. Mario can move over pipes.
		else if(to.isPipe()) {
			this.modifyCave(to);
			super.move(to);
			return true;
		}
		
		// Another player is already in set location.
		else if(to.isOccupied()) {
			return false;
		}
		
		// Nothing dare stand in the path of Mario. Set move to true. He SHALL pass.
		else {	
			super.move(to);
			return true;
		}
	}
	
	/** Returns the name of Mario 
	 * @return <code>name</code> Name of Mario*/
	public String getName() {
		String name = new String("Mario");
		return name;
	}
}