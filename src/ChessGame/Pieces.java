//Coded by Brent Butkow

package ChessGame;

/**
 *
 * @author brentbutkow
 */
public class Pieces {

	private int Ypos;

	private int Xpos;

	private char colour;

	private char piece;

	private boolean hasMoved;

	/**
	 * The constructor for the piece object
	 *
	 * @param Ypos the Y position of the piece
	 * @param Xpos the X position of the piece
	 * @param colour the colour of the piece (white or black)
	 * @param piece	the type of piece
	 * @param hasMoved if the piece has moved before in this game
	 */
	public Pieces(int Ypos, int Xpos, char colour, char piece, boolean hasMoved) {
		this.Ypos = Ypos;
		this.Xpos = Xpos;
		this.colour = colour;
		this.piece = piece;
		this.hasMoved = hasMoved;
	}

	/**
	 * accessor method for the Y position of the piece
	 *
	 * @return
	 */
	public int getYpos() {
		return Ypos;
	}

	/**
	 * accessor method for the X position of the piece
	 *
	 * @return
	 */
	public int getXpos() {
		return Xpos;
	}

	/**
	 * accessor method for the colour of the piece
	 *
	 * @return
	 */
	public char getColour() {
		return colour;
	}

	/**
	 * accessor method for the piece type
	 *
	 * @return
	 */
	public char getPiece() {
		return piece;
	}

	/**
	 * accessor method for if the piece has moved
	 *
	 * @return
	 */
	public boolean HasMoved() {
		return hasMoved;
	}

	/**
	 * mutator method for the Y position of the piece
	 *
	 * @param Ypos
	 */
	public void setYpos(int Ypos) {
		this.Ypos = Ypos;
	}

	/**
	 * mutator method for the X position of the piece
	 *
	 * @param Xpos
	 */
	public void setXpos(int Xpos) {
		this.Xpos = Xpos;
	}

	/**
	 * mutator method for the piece type
	 *
	 * @param piece
	 */
	public void setPiece(char piece) {
		this.piece = piece;
	}

	/**
	 * mutator method for if the piece has moved
	 *
	 * @param hasMoved
	 */
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	/**
	 * the SQL statement to add this piece object into the database
	 *
	 * @return
	 */
	@Override
	public String toString() {

		return "INSERT INTO SavedGame (GameNumber, PieceType, PieceColour, Xposition, Yposition, HasMoved)"
				+ "VALUES (\"" + MainMenu.LoadedGameNum + "\",\"" + piece + "\",\"" + colour + "\"," + Xpos + "," + Ypos + "," + hasMoved + ")";
	}

}
//Coded by Brent Butkow
