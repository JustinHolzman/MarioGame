/**
 * Class: COP 3330 OBJECT ORIENTED PROGRAMMING (Java) 12:30PM - 1:20PM
 * Homework FOUR-E : AdventureGame Extra credit
 * Character class: This generates a new character with attributes that 
 * Mario, Toad, Luigi, and Chomp Chomp all take from.
 *
 * @author Justin Holzman
 */
public abstract class Character extends Object implements CaveWorker {
	
	/** This is the location the character. */
	protected Cave location;
	
	/** Construct a new character at the initial location. */
	public Character(Cave initLoc) {
		location = initLoc;
		location.setOccupied(true);
	}
	
	/** The Cave that this character is occupying. @return this.location*/
	public Cave getLocation() {
		return this.location;
	}
	
	/** Movement from current location. Set the old location to false and the new location to true.*/
	public boolean move(Cave to) {
		this.location.setOccupied(false);
		this.location = to;
		this.location.setOccupied(true);
		
		return true;
	}
	
	/** Abstract, to get the name of "this" character. */
	public abstract String getName();
}