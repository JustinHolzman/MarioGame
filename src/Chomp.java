/**
 * Class: COP 3330 OBJECT ORIENTED PROGRAMMING (Java) 12:30PM - 1:20PM
 * Homework FOUR-E : AdventureGame Extra credit
 * Chomp Chomp Class: Chomp moves ANYWHERE on the board and will kill anyone that is moving.
 * He will not kill anyone who is still because we all know he only gets mad if you wake him by moving.
 *
 * @author Justin Holzman
 */
public class Chomp extends Character {
			
	/** InitLoc is at the top left corner of the grid. 
	 * @param <code>initLoc</code> is the inital location*/
	public Chomp(Cave initLoc) {
		super(initLoc);
	}
	

	 /** @return <code>true</code> if spot on board. Chomp moves anywhere; <code>false</code> otherwise.
	 * @param <code>loc</code> Location of character */
	public boolean modifyCave(Cave loc) {
		if(loc.isPipe()){
			return true;
		}
		
		if (loc.isBomb()) { // Pit found
			return true;
		}
		
		else {
			return false;
		}
	}

	/** Notify the user that a cave was marked 
	 * @return <code>modification</code> */
	public String describeModification() {
		String modification = new String("the Cave was marked!");
		return modification;
	}
	
	/** @return	<code>true</code> if anywhere. Chomp can move to any location within the board. */
	public boolean move(Cave to) {
		// Blocked, so user cannot move
		if (to.isBlocked()) {
			this.modifyCave(to);
			super.move(to);
			return true;
		}
		
		else if (to.isBomb()) {
			this.modifyCave(to);
			super.move(to);
			return true;
		}
		
		// Teleport, so set to mark location. Adventurer can move over portals.
		else if(to.isPipe()) {
			this.modifyCave(to);
			super.move(to);
			return true;
		}
		
		// Another player is already in set location.
		else if(to.isOccupied()) {
			this.modifyCave(to);
			super.move(to);
			return true;
		}
		
		// Nothing dare stand in the path of adventurer. Set move to true. He SHALL pass.
		else {	
			super.move(to);
			return true;
		}
	}
	
	/** Returns the name of Chomp Chomp 
	 * @return <code>Name</code> Name of Chomp Chomp*/
	public String getName() {
		String name = new String("Chomp Chomp");
		return name;
	}
}