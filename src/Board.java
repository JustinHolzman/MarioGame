/**
 * Class: COP 3330 OBJECT ORIENTED PROGRAMMING (Java) 12:30PM - 1:20PM
 * Homework FOUR-E : AdventureGame Extra credit
 * Board class: 2D array of Caves which represents the game board.
 * Generates a random cave system.
 * 
 * @author Justin Holzman
 */
public class Board extends Object {

	private Cave[][] board;	// Of type cave(2d array)
	private int row;
	private int column;

	/** Generate a rand grid for the board to be displayed. 
	 * @param <code>rows</code> Rows 
	 * @param <code>cols</code> Columns*/
	public Board(int rows, int cols) {
		row = rows;
		column = cols;
		board = new Cave[rows][cols];

		for (int i = 0; i < rows; i++) {	//Loop till end of rows
			for (int j = 0; j < cols; j++) { // Loop till end of cols
				if ((rows == 0 && cols == 0) || (i == (rows - 1) && j == (cols - 1))) {	// Spot for Adventurer, make open.
					board[i][j] = new Cave(i, j);
					board[i][j].makeOpen();
					continue;
				}

				// Set board to Cave coordinates(grid)
				board[i][j] = new Cave(i, j);

				// Generate a number between 0 and 100 (Size of board which is 10 x 10.
				double baseRandom = Math.random(); // Between 0 and 1
				double scaledRandom = 100 * baseRandom;
				int number = (int) scaledRandom; 
				
				// Use number to determine if the spot should be open, blocked, bomb omb, powerup or pipe
				if ((number <= 100) && (number > 50)) {
					(board[i][j]).makeOpen();
				}

				else if ((number <= 50) && (number > 25)) {
					(board[i][j]).makeBlocked();
				}

				else if ((number <= 25) && (number > 11)) {
					(board[i][j]).makeBomb();
				}
				
				else if ((number <= 11) && (number > 10)) {
					(board[i][j]).makePowerup();
				}
				
				else {
					(board[i][j]).makePipe();
				}
			}
		}
	}

	/** Get the cave location for (r,c). 
	 * @param r Rows; @param c Columns*/
	public Cave getCave(int r, int c) {
		// The Cave stored at this location, or null if this spot is not on the board.
		if (this.ok(r, c)) {
			return this.board[r][c];
		}
		return null;
	}

	/** Check if this location is inside the bounds of the board. 
	 * @return <code>true</code> if within bounds; <code>false</code> otherwise*/
	public boolean ok(int r, int c) {
		if ((r < this.row) && (c < this.column) && (r >= 0) && (c >= 0)) {
			return true;
		}

		else {
			return false;
		}
	}

	/** Get a random location that is open. 
	 * @return this.board[numberRow][numberCol] An open location on the board.*/
	public Cave getUnoccupiedOpenLocation() {
		while (true) {
			// Generate a number between 0 and 100 (Size of board which is 10 x 10. Row.
			double baseRowRandom = Math.random();
			double scaledRowRandom = (this.row - 1) * baseRowRandom;
			int numberRow = (int) scaledRowRandom;

			// Generate a number between 0 and 100 (Size of board which is 10 x 10. Col.
			double baseColRandom = Math.random();
			double scaledColRandom = (this.column - 1) * baseColRandom;
			int numberCol = (int) scaledColRandom;

			// Check location to see if it is NOT Occupied or Open
			if (!(this.board[numberRow][numberCol].isOccupied()) && (this.board[numberRow][numberCol].isOpen())) {
				return this.board[numberRow][numberCol];
			}
		}
	}
}