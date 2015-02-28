/**
 * Class: COP 3330 OBJECT ORIENTED PROGRAMMING (Java) 12:30PM - 1:20PM
 * Homework FOUR-E : AdventureGame Extra credit
 * Cave class: This represents a single cave on the board.
 * Instance var. contain information on it's position.
 *
 * @author Justin Holzman
 */
public class Cave extends Object {

	private CaveType cave;
	private int row;
	private int column;
	private boolean marked;
	private boolean occupied;

	/** Generate an open cave. Cave is set to CaveType.OPEN. Set marked to false and occupation to false.
	 * 
	 * @param r
	 * @param c
	 */
	public Cave(int r, int c) {
		cave = CaveType.OPEN;
		row = r;
		column = c;
		marked = false;
		occupied = false;
	}
	
	/** Get the column of this cave. 
	 * 
	 * @return column
	 */
	public int getCol() {
		return this.column;
	}	

	/** Get the row of this cave. 
	 * 
	 * @return row
	 */
	public int getRow() {
		return this.row;
	}	
	
	/** Set cave as marked. 
	 * 
	 * @param set
	 */
	public void setMarked(boolean set) {
		this.marked = set;
	}

	/** See if cave is marked. 
	 * 
	 * @return marked
	 */
	public boolean isMarked() {
		return this.marked;
	}

	/** Set cave as occupied. 
	 * 
	 * @param set
	 */
	public void setOccupied(boolean set) {
		this.occupied = set;
	}

	/** See if cave is occupied. 
	 * 
	 * @return occupied
	 */
	public boolean isOccupied() {
		return occupied;
	}

	/** Set cave as open. */
	public void makeOpen() {
		this.cave = CaveType.OPEN;
	}

	/** See if cave is open. 
	 * 
	 * @return true if open
	 * @return false otherwise
	 */
	public boolean isOpen() {
		switch (this.cave) {
		case OPEN:
			return true;
		default:
			return false;
		}
	}

	/** Set cave as as blocked. */
	public void makeBlocked() {
		this.cave = CaveType.BLOCKED;
	}

	/** See if cave is blocked. 
	 * 
	 * @return true if blocked else false
	 */
	public boolean isBlocked() {
		switch (this.cave) {
		case BLOCKED:
			return true;
		default:
			return false;
		}
	}
	
	/** Set cave as as PowerUP. */
	public void makePowerup() {
		this.cave = CaveType.POWER_UP;
	}

	/** See if cave is PowerUp. */
	public boolean isPowerup() {
		switch (this.cave) {
		case POWER_UP:
			return true;
		default:
			return false;
		}
	}

	/** Set cave as a bomb omb. */
	public void makeBomb() {
		this.cave = CaveType.PIT;
	}

	/** See if cave is a bomb omb. */
	public boolean isBomb() {
		switch (this.cave) {
		case PIT:
			return true;
		default:
			return false;
		}
	}

	/** Set cave as a pipe. */
	public void makePipe() {
		this.cave = CaveType.TELEPORT;
	}

	/** See if cave is a pipe. */
	public boolean isPipe() {
		switch (this.cave) {
		case TELEPORT:
			return true;
		default:
			return false;
		}
	}

	/** Types of caves. */
	public static enum CaveType {
		BLOCKED, OPEN, PIT, TELEPORT, POWER_UP;
	}
}