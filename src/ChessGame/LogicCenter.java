//Coded by Brent Butkow

package ChessGame;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author brentbutkow
 */
public class LogicCenter {

	static DataBase DB = new DataBase();			//to interact with the database

	static int[][][] scoreCheck = new int[32][8][8];//stores the score of every piece moving to every position for the bots turn

	static boolean[][] board = new boolean[8][8];	//is true if there is a piece on that position

	static Pieces[] game = new Pieces[32];			//the array of all piece objects

	static HashMap values = new HashMap();			//returns the numeric value of any piece type

	static int WScore = 0, BScore = 0;				//is the combined value of all the white pieces; and black pieces to get a score

	static int selectedY, selectedX;				//the X and Y position of the piece that the user has selected when trying to move

	static int curY, curX;							//the current X and Y position of th users in-game 'curser'

	static int counter = 0;							//the number of piece objects in use (pieces that have not been taken)

	static int totalScore = 0;						//a temporary count of the score of every specific possible bot move

	static int movedPiece = 0;						//stores the last piece moved in the game

	static int lastMoved = -1;						//stores the last piece moved by the bot if the bot is playing

	static boolean canCastle;						//if the current player is trying to castle

	static boolean selected;						//if the user is currently selecting a piece to move

	static boolean curTurnW;						//is the representation of which colours turn it is

	static boolean canCheckMate;					//is the representation of whether the bot can move to checkmate its opponent

	static boolean bot;								//whether the game is singleplayer or multiplayer

	static boolean moveDone;						//whether the bot's move is finished

	static boolean firstMove;						//if there has been a single move in the game so far - used to fix UI issues

	static boolean checkScore;						//during the bot's move, this shows if the bot is checking the scores of possible moves or moving

	static boolean checkBotTakes;					//during the bot's move, this shows if the bot is actually taking a piece, needed to solve errors

	static boolean canStale;						//stores whether the program can call stalemate or not

	static char botColour;							//if the game is singleplayer, this shows the colour of the bot

	/**
	 * load all pieces into an array, work out the score of black and white, and
	 * get the game ready for playing
	 */
	public static void LogicStart() {

		//set all variables to the starting value
		firstMove = true;
		movedPiece = 0;
		checkScore = false;
		checkBotTakes = false;
		moveDone = false;
		curY = 0;
		curX = 0;
		canCastle = false;
		lastMoved = 0;
		canStale = true;
		WScore = 0;
		BScore = 0;

		//reset the board array to be false on every square
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = false;
			}
		}

		//add the value of every piece to the hashmap
		values.put('p', 1);
		values.put('r', 5);
		values.put('n', 3);
		values.put('b', 3);
		values.put('q', 9);
		values.put('k', 0);

		//add the score of every piece to their respective colour to get white and black's score
		for (int i = 0; i < counter; i++) {

			if (game[i].getColour() == 'w') {//if the piece is white

				WScore += Integer.parseInt("" + values.get(game[i].getPiece()));

			} else {//if the piece is black

				BScore += Integer.parseInt("" + values.get(game[i].getPiece()));

			}

		}

	}

	/**
	 * reads in a key press and converts it into a move if the key press is
	 * valid
	 *
	 * @param evt the key pressed
	 */
	public static void formKeyPressed(java.awt.event.KeyEvent evt) {

		//in order to get around java thread ordering issues, this sets the UI
		//to thecorrect state after the first key press
		if (firstMove) {
			firstMove = false;
			setAllPics();
		}

		int key = evt.getKeyCode();
		switch (key) {
			case KeyEvent.VK_ENTER://if the enter key is pressed, to represent a piece selection or a move

				//if a piece is already selected or there is a piece at the current position of the place shower
				if (selected || (curTurnW && isPiece(curY, curX) > 0) || ((!curTurnW && isPiece(curY, curX) < 0))) {

					if (!selected && board[curY][curX]) {//if there is currently no piece selected

						selected = true;

						//add a background to the now selected piece to show that it is selected
						//This code is purposefully redundant as it makes the UI update properly
						ChessGame.pieces[Math.abs(isPiece(curY, curX)) - 1].setBackground(Color.BLUE);
						ChessGame.pieces[Math.abs(isPiece(curY, curX)) - 1].setBackground(Color.GRAY);
						ChessGame.pieces[Math.abs(isPiece(curY, curX)) - 1].setOpaque(true);

						//change the value of the selected position
						selectedX = curX;
						selectedY = curY;

					} else {//if there is a piece selected

						selected = false;

						//take away the background of the selected piece
						//This code is purposefully redundant as it makes the UI update properly
						ChessGame.pieces[Math.abs(isPiece(selectedY, selectedX)) - 1].setBackground(Color.BLUE);
						ChessGame.pieces[Math.abs(isPiece(selectedY, selectedX)) - 1].setBackground(Color.GRAY);
						ChessGame.pieces[Math.abs(isPiece(selectedY, selectedX)) - 1].setOpaque(false);

						//if the new position is not equal to the selected position
						if (selectedX != curX || selectedY != curY) {
							//the error message is shown immediatly, and is removed if there are no errors
							ChessGame.errorShower.setText("              Illegal Move");

							//try move the piece from its position to the new position
							move(curY, curX, selectedY, selectedX);
						}
					}

				} else {//if a position without the users piece is selected

					ChessGame.errorShower.setText((isPiece(curY, curX) == 0 ? "      There is no piece there" : "        That is not your piece"));
				}

				break;

			//if the user wants to move the curser one block up
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:

				//if the curser can move higher
				if (curY < 7) {
					curY++;
					//move the position of the curser to reflect the change in position
					ChessGame.placeShower.setLocation(ChessGame.placeShower.getLocation().x, ChessGame.placeShower.getLocation().y - 120);
				}

				break;

			//if the user wants to move the curser one block down
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:

				//if the curser can move lower
				if (curY > 0) {
					curY--;
					//move the position of the curser to reflect the change in position
					ChessGame.placeShower.setLocation(ChessGame.placeShower.getLocation().x, ChessGame.placeShower.getLocation().y + 120);

				}
				break;

			//if the user wants to move the curser one block left
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:

				//if the curser can move more to the left
				if (curX > 0) {
					curX--;
					//move the position of the curser to reflect the change in position
					ChessGame.placeShower.setLocation(ChessGame.placeShower.getLocation().x - 120, ChessGame.placeShower.getLocation().y);
				}
				break;

			//if the user wants to move the curser one block right
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:

				//if the curser can move more to the right
				if (curX < 7) {
					curX++;
					//move the position of the curser to reflect the change in position
					ChessGame.placeShower.setLocation(ChessGame.placeShower.getLocation().x + 120, ChessGame.placeShower.getLocation().y);
				}
				break;

			//if the user wants to display the help menu
			case KeyEvent.VK_F1:
				JOptionPane.showMessageDialog(null, MainMenu.helpMenu);
				JOptionPane.showMessageDialog(null, MainMenu.instructionsMenu);
				break;

		}
	}

	/**
	 * takes in a piece, a starting and ending position and says if the move is
	 * valid
	 *
	 * @param num the number of the piece that is being moved
	 * @param CY the new Y position of the pawn
	 * @param CX the new X position of the pawn
	 * @param SY the current Y position of the pawn
	 * @param SX the current X position of the pawn
	 * @return whether the pawn can move to the position given
	 */
	public static boolean pawn(int num, int CY, int CX, int SY, int SX) {

		int changeY = CY - SY;//Total change in the Y value of the pawn
		int changeX = CX - SX;//Total change in the X value of the pawn

		if (game[num].getColour() == 'w') {//if the pawn is white

			//if the bot is checking to see if it can take a piece
			if (checkBotTakes) {
				return Math.abs(changeX) == 1 && changeY == 1;

			}

			if (CX != SX || changeY > 2 || changeY < 1) {//if the pawn did not move 1 or 2 spaces up

				if (Math.abs(changeX) == 1 && changeY == 1) {//if the pawn is trying to take a piece

					return isPiece(CY, CX) < 0;//if there is a piece where the bot is trying to take, the move is valid

				} else {
					return false;
				}
			} else {//the pawn is trying to move exactly 1 or 2 spaces up

				if (!game[num].HasMoved()) {//if the pawn has not moved before, as a pawn can move two spaces only when it first moves

					ChessGame.errorShower.setText("   There are pieces in the way");

					if (changeY == 1) {//if the pawn is trying to move 1 block up

						//if the board is empty where the pawn wants to move, the move is valid
						return !board[CY][CX];

					} else {//if the pawn is trying to move 2 bloacks up

						//if the board is empty where the pawn wants to move and the previous sqaure, the move is valid
						return !board[CY][CX] && !board[CY - 1][CX];

					}

				} else {//if the pawn has moved before

					if (changeY == 1) {//if the pawn is trying to move 1 block up

						//if the board is empty where the pawn wants to move, the move is valid
						return !board[CY][CX];

					} else {
						return false;
					}

				}
			}
		} else {//if the pawn is black

			//if the bot is checking to see if it can take a piece
			if (checkBotTakes) {
				return Math.abs(changeX) == 1 && changeY == -1;

			}

			if (CX != SX || changeY < -2 || changeY > -1) {//if the pawn did not move 1 or 2 spaces down

				if (Math.abs(changeX) == 1 && changeY == - 1) {//if the pawn is trying to take a piece

					return isPiece(CY, CX) > 0;//if there is a piece where the bot is trying to take, the move is valid

				} else {
					return false;
				}
			} else {//the pawn is trying to move exactly 1 or 2 spaces up

				if (!game[num].HasMoved()) {//if the pawn has not moved before, as a pawn can only move two spaces when it first moves

					ChessGame.errorShower.setText("   There are pieces in the way");

					if (changeY == -1) {//if the pawn is trying to move 1 block down

						//if the board is empty where the pawn wants to move, the move is valid
						return !board[CY][CX];

					} else {

						//if the board is empty where the pawn wants to move and the previous sqaure, the move is valid
						return !board[CY][CX] && !board[CY + 1][CX];

					}

				} else {//if the pawn has moved before

					if (changeY == -1) {//if the pawn is trying to move 1 block down

						//if the board is empty where the pawn wants to move, the move is valid
						return !board[CY][CX];

					} else {
						return false;
					}

				}
			}

		}
	}

	/**
	 * takes in a piece, a starting and ending position and says if the move is
	 * valid
	 *
	 * @param CY the new Y position of the rook
	 * @param CX the new X position of the rook
	 * @param SY the current Y position of the rook
	 * @param SX the current X position of the rook
	 * @return whether the rook can move to the position given
	 */
	public static boolean rook(int CY, int CX, int SY, int SX) {

		int changeY = Math.abs(CY - SY);//Total change in Y value of the rook
		int changeX = Math.abs(CX - SX);//Total change in X value of the rook

		if ((changeX == 0 && changeY != 0) || (changeX != 0 && changeY == 0)) {//if the rook is moving straight up and down, or across

			ChessGame.errorShower.setText("   There are pieces in the way");

			if (changeY != 0) {//if the rook is moving vertically

				if (CY > SY) {//if the rook is moving up

					//iterate through every square between the current and new position of
					//the rook to make sure there are no pieces in the way
					for (int i = SY + 1; i <= CY - 1; i++) {

						if (board[i][CX]) {//if there is a piece in the rooks way, the move is not valid
							return false;
						}
					}

				} else {//if the rook is moving down

					//iterate through every square between the current and new position of
					//the rook to make sure there are no pieces in the way
					for (int i = SY - 1; i >= CY + 1; i--) {

						if (board[i][CX]) {//if there is a piece in the rooks way, the move is not valid
							return false;
						}
					}

				}
				return true;//if there are no pieces on the way, the move is valid

			} else {//if the rook is moving horizontally

				if (CX > SX) {//if the rook is moving to the right

					//iterate through every square between the current and new position of
					//the rook to make sure there are no pieces in the way
					for (int i = SX + 1; i <= CX - 1; i++) {

						if (board[CY][i]) {//if there is a piece in the rooks way, the move is not valid
							return false;
						}
					}

				} else {//if the rook is moving to the left

					//iterate through every square between the current and new position of
					//the rook to make sure there are no pieces in the way
					for (int i = SX - 1; i >= CX + 1; i--) {

						if (board[CY][i]) {//if there is a piece in the rooks way, the move is not valid
							return false;
						}
					}

				}

				return true;//if there are no pieces in the way, the move is valid

			}

		} else {//if the rook is not moving only across, or up and down, the move is not valid
			return false;
		}

	}

	/**
	 * takes in a piece, a starting and ending position and says if the move is
	 * valid
	 *
	 * @param CY the new Y position of the knight
	 * @param CX the new X position of the knight
	 * @param SY the current Y position of the knight
	 * @param SX the current X position of the knight
	 * @return whether the knight can move to the position given
	 */
	public static boolean knight(int CY, int CX, int SY, int SX) {

		int changeY = Math.abs(CY - SY);//Total change in Y value of the knight
		int changeX = Math.abs(CX - SX);//Total change in X value of the knight

		//if the knight is moving in an L shape, the move is valid
		return (changeX == 1 && changeY == 2) || changeX == 2 && changeY == 1;
	}

	/**
	 * takes in a piece, a starting and ending position and says if the move is
	 * valid
	 *
	 * @param CY the new Y position of the bishop
	 * @param CX the new X position of the bishop
	 * @param SY the current Y position of the bishop
	 * @param SX the current X position of the bishop
	 * @return whether the bishop can move to the position given
	 */
	public static boolean bishop(int CY, int CX, int SY, int SX) {

		int changeY = Math.abs(CY - SY);//Total change in Y value of the bishop
		int changeX = Math.abs(CX - SX);//Total change in X value of the bishop

		//to check the squares between the start and end points
		int Xcheck = SX;
		int Ycheck = SY;

		if (changeX == changeY && changeX != 0) {//if the bishop is moving diagonally

			ChessGame.errorShower.setText("   There are pieces in the way");

			if (CY > SY) {//if the bishop is moving up

				if (CX > SX) {//if the bishop is moving diagonally up right

					//iterate through every square between the current and new position of
					//the bishop to make sure there are no pieces in the way
					for (int i = SY + 1; i <= CY - 1; i++) {

						//change the position of the square being checked
						Ycheck++;
						Xcheck++;

						if (board[Ycheck][Xcheck]) {//if there is a piece in the bishops way, the move is not valid

							return false;
						}
					}

				} else {//if the bishop is moving diagonally up left

					//iterate through every square between the current and new position of
					//the bishop to make sure there are no pieces in the way
					for (int i = SY + 1; i <= CY - 1; i++) {

						//change the position of the square being checked
						Ycheck++;
						Xcheck--;

						if (board[Ycheck][Xcheck]) {//if there is a piece in the bishops way, the move is not valid

							return false;
						}
					}

				}

				return true;//if there are no pieces in the way, the move is valid

			} else {//if the bishop is moving down

				if (CX > SX) {//if the bishop is moving diagonally down right

					//iterate through every square between the current and new position of
					//the bishop to make sure there are no pieces in the way
					for (int i = SY - 1; i >= CY + 1; i--) {

						//change the position of the square being checked
						Ycheck--;
						Xcheck++;

						if (board[Ycheck][Xcheck]) {//if there is a piece in the bishops way, the move is not valid

							return false;
						}
					}

				} else {//if the bishop is moving diagonally down left

					//iterate through every square between the current and new position of
					//the bishop to make sure there are no pieces in the way
					for (int i = SY - 1; i >= CY + 1; i--) {

						//change the position of the square being checked
						Ycheck--;
						Xcheck--;

						if (board[Ycheck][Xcheck]) {//if there is a piece in the bishops way, the move is not valid

							return false;
						}
					}

				}
				return true;//if there are no pieces in the way, the move is valid

			}

		} else {//if the bishop is not moving diagonally, the move is invalid
			return false;
		}

	}

	/**
	 * takes in a piece, a starting and ending position and says if the move is
	 * valid
	 *
	 * @param num the number of the piece that is being moved
	 * @param CY the new Y position of the king
	 * @param CX the new X position of the king
	 * @param SY the current Y position of the king
	 * @param SX the current X position of the king
	 * @return whether the king can move to the position given
	 */
	public static boolean king(int num, int CY, int CX, int SY, int SX) {

		int changeY = Math.abs(CY - SY);//Total change in Y value of the king
		int changeX = Math.abs(CX - SX);//Total change in X value of the king

		if (!(changeY > 1 || changeX > 1)) {//if the king is only moving one sqaure, then the move is valid

			return true;

			//seeing if the king can castle
		} else if (!game[num].HasMoved() && changeX == 2 && changeY == 0) {

			if (CX > SX) {//if it is King side castling

				//if the piece in the right corner is a rook that hasn't yet moved, then the castling could be valid
				int findRook = Math.abs(isPiece(CY, CX + 1)) - 1;
				if (findRook != -1 && game[findRook].getPiece() == 'r' && !game[findRook].HasMoved()) {

					//if there are no pieces in the way of the castling, then the move is valid
					if (!board[CY][CX] && !board[CY][CX - 1]) {

						totalScore += MainMenu.difficulty == 'E' ? 6 : 12;//the bot gets 6 or 12 points for castling king side depending on its difficulty
						canCastle = true;//castling is a valid move
						return true;
					}

				}

			} else {//if it is Queen side castling

				//if the piece in the left corner is a rook that hasn't yet moved, then the castling could be valid
				int findRook = Math.abs(isPiece(CY, CX - 2)) - 1;
				if (findRook != -1 && game[findRook].getPiece() == 'r' && !game[findRook].HasMoved()) {

					//if there are no pieces in the way of the castling, then the move is valid
					if (!board[CY][CX] && !board[CY][CX + 1] && !board[CY][CX - 1]) {

						totalScore += MainMenu.difficulty == 'E' ? 6 : 11;//the bot gets 6 or 11 points for castling queen side depending on its difficulty
						canCastle = true;//castling is a valid move
						return true;

					}

				}

			}

		}
		return false;//if the king is not castling or can not castle, then the move is not valid
	}

	/**
	 * takes in a piece, a starting and ending position and says if the move is
	 * valid
	 *
	 * @param CY the new Y position of the queen
	 * @param CX the new X position of the queen
	 * @param SY the current Y position of the queen
	 * @param SX the current X position of the queen
	 * @return whether the queen can move to the position given
	 */
	public static boolean queen(int CY, int CX, int SY, int SX) {

		int changeY = Math.abs(CY - SY);//Total change in Y value of the queen
		int changeX = Math.abs(CX - SX);//Total change in Y value of the queen

		//to check the squares between the start and end points
		int Xcheck = SX;
		int Ycheck = SY;

		//if the queen is moving diagonally, horizontally, vertically
		if (changeX == changeY || (changeX == 0 && changeY != 0) || (changeX != 0 && changeY == 0)) {
			ChessGame.errorShower.setText("   There are pieces in the way");

			//if the queen is moving vertically
			if (changeX == 0) {

				if (CY > SY) {//if the queen is moving up

					//iterate through every square between the current and new position of
					//the queen to make sure there are no pieces in the way
					for (int i = SY + 1; i <= CY - 1; i++) {

						if (board[i][CX]) {//if there is a piece in the queens way, the move is not valid

							return false;
						}
					}

					return true;

				} else {//if the queen is moving down

					//iterate through every square between the current and new position of
					//the queen to make sure there are no pieces in the way
					for (int i = SY - 1; i >= CY + 1; i--) {//making sure there are no pieces in between the starting and end point

						if (board[i][CX]) {//if there is a piece in the queens way, the move is not valid

							return false;
						}
					}

				}

			} else if (changeY == 0) {//if the queen is mooving horizontally

				if (CX > SX) {//if the queen is moving right

					//iterate through every square between the current and new position of
					//the queen to make sure there are no pieces in the way
					for (int i = SX + 1; i <= CX - 1; i++) {//making sure there are no pieces in between the starting and end point

						if (board[CY][i]) {//if there is a piece in the queens way, the move is not valid

							return false;
						}
					}

					return true;

				} else {//if the queen is moving left

					//iterate through every square between the current and new position of
					//the queen to make sure there are no pieces in the way
					for (int i = SX - 1; i >= CX + 1; i--) {//making sure there are no pieces in between the starting and end point

						if (board[CY][i]) {//if there is a piece in the queens way, the move is not valid

							return false;
						}
					}

				}

			} else if (CY > SY) {//if the queen is moving diagonally up

				if (CX > SX) {//if the queen is moving diagonally up right

					//iterate through every square between the current and new position of
					//the queen to make sure there are no pieces in the way
					for (int i = SY + 1; i <= CY - 1; i++) {//making sure there are no pieces in between the starting and end point

						//change the position of the square being checked
						Ycheck++;
						Xcheck++;

						if (board[Ycheck][Xcheck]) {//if there is a piece in the queens way, the move is not valid

							return false;
						}
					}

				} else {//if the queen is moving diagonally up left

					//iterate through every square between the current and new position of
					//the queen to make sure there are no pieces in the way
					for (int i = SY + 1; i <= CY - 1; i++) {//making sure there are no pieces in between the starting and end point

						//change the position of the square being checked
						Ycheck++;
						Xcheck--;

						if (board[Ycheck][Xcheck]) {//if there is a piece in the queens way, the move is not valid

							return false;
						}
					}

				}

			} else {//if the queen is moving diagonally down

				if (CX > SX) {//if the queen is moving diagonally down right

					//iterate through every square between the current and new position of
					//the queen to make sure there are no pieces in the way
					for (int i = SY - 1; i >= CY + 1; i--) {//making sure there are no pieces in between the starting and end point

						//change the position of the square being checked
						Ycheck--;
						Xcheck++;

						if (board[Ycheck][Xcheck]) {//if there is a piece in the queens way, the move is not valid

							return false;
						}
					}

				} else {//if the queen is moving diagonally down left

					//iterate through every square between the current and new position of
					//the queen to make sure there are no pieces in the way
					for (int i = SY - 1; i >= CY + 1; i--) {//making sure there are no pieces in between the starting and end point

						//change the position of the square being checked
						Ycheck--;
						Xcheck--;

						if (board[Ycheck][Xcheck]) {//if there is a piece in the queens way, the move is not valid

							return false;
						}
					}

				}

			}
			return true;//if there are no pieces in the way, the move is valid

		} else {
			return false;//if the queen is not moving diagonally, horizontally or vertically, then the move is not valid

		}

	}

	/**
	 * takes in a piece, a starting and ending position and says if the move is
	 * valid, by calling a specific method based on what type of piece is parsed
	 * in
	 *
	 * @param num the number of the piece that is being moved
	 * @param CY the new Y position of the piece
	 * @param CX the new X position of the piece
	 * @param SY the current Y position of the piece
	 * @param SX the current X position of the piece
	 * @return whether the piece can move to the position given
	 */
	public static boolean check(int num, int CY, int CX, int SY, int SX) {

		//call the respective method based on what type of piece the variable num is connected with
		switch (game[num].getPiece()) {

			case 'p'://the piece is a pawn
				return pawn(num, CY, CX, SY, SX);

			case 'r'://the piece is a rook
				return rook(CY, CX, SY, SX);

			case 'n'://the piece is a knight
				return knight(CY, CX, SY, SX);

			case 'b'://the piece is a bishop
				return bishop(CY, CX, SY, SX);

			case 'k'://the piece is a king
				return king(num, CY, CX, SY, SX);

			case 'q'://the piece is a queen
				return queen(CY, CX, SY, SX);

			default:
				return false;
		}
	}

	/**
	 * takes in a starting and ending position of the king and castles the rook
	 * and king
	 *
	 * @param CY the new Y position of the king
	 * @param CX the new X position of the king
	 * @param SY the current Y position of the king
	 * @param SX the current X position of the king
	 */
	public static void castle(int CY, int CX, int SY, int SX) {

		if (CX > SX) {//if it is king side castling

			int rookNum = Math.abs(isPiece(CY, CX + 1)) - 1;//finding the number of the correct rook

			//changing the board array to reflect the movement of the king and rook
			board[CY][CX] = true;
			board[CY][CX - 1] = true;
			board[CY][CX + 1] = false;
			board[SY][SX] = false;

			//moving the rook and saving its new position
			game[rookNum].setHasMoved(true);
			game[rookNum].setXpos(CX - 1);
			ChessGame.pieces[rookNum].setLocation(ChessGame.pieces[rookNum].getLocation().x - 2 * 120, ChessGame.pieces[rookNum].getLocation().y);

		} else {//if it is queen side castling

			int rookNum = Math.abs(isPiece(CY, CX - 2)) - 1;//finding the number of the correct rook

			//changing the board array to reflect the movement of the king and rook
			board[CY][CX] = true;
			board[CY][CX + 1] = true;
			board[CY][CX - 2] = false;
			board[SY][SX] = false;

			//moving the rook and saving its new position
			game[rookNum].setHasMoved(true);
			game[rookNum].setXpos(CX + 1);
			ChessGame.pieces[rookNum].setLocation(ChessGame.pieces[rookNum].getLocation().x + 3 * 120, ChessGame.pieces[rookNum].getLocation().y);
		}

		canCastle = false;//you can no longer castle after you have already castled

	}

	/**
	 * takes in a position on the board, and returns if there is a piece there,
	 * and what piece and colour it is
	 *
	 * @param y the Y position on the board
	 * @param x the X position on the board
	 * @return 0 if there is no piece; the piece's number +1 if the piece is
	 * white; -(the piece's number +1) if the piece is black
	 */
	public static int isPiece(int y, int x) {

		//iterate through every piece
		for (int i = 0; i < counter; i++) {

			//if the piece's X and Y position match those given, return the pieces number and a way to tell the pieces colour
			if (game[i].getYpos() == y && game[i].getXpos() == x) {

				if (game[i].getColour() == 'w') {

					return i + 1;
				} else {
					return -(i + 1);
				}
			}
		}

		return 0;//if there is no piece at the position given, return 0
	}

	/**
	 * updates the picture of every piece JLabel to fix UI issues
	 */
	public static void setAllPics() {

		//iterate through every piece
		for (int i = 0; i < counter; i++) {

			//set every piece to its correct position
			ChessGame.pieces[i].setLocation(20 + game[i].getXpos() * 120, 860 - (game[i].getYpos()) * 120);
		}
	}

	/**
	 * take in two positions on the board, see if the piece at the one position
	 * can move to the other position, and if it can, move the piece
	 *
	 * @param CY the Y position of the square that the piece is moving to
	 * @param CX the X position of the square that the piece is moving to
	 * @param SY the Y position of the square that the piece is currently on
	 * @param SX the X position of the square that the piece is currently on
	 */
	public static void move(int CY, int CX, int SY, int SX) {

		String newPiece = "";		//if the pawn promotes, this stores what type of piece the pawn becomes
		char OldPiece;				//the type of piece that is trying to be moved
		int curPiece = 0;			//the number of the piece trying to be moved
		boolean friendPiece = false;//if there is a piece of the same colour on the square the piece is trying to move to
		int enemyPiece = -1;		//the game number of the piece on the new square, if that piece is the opposite colour of the moving piece
		boolean white;				//whether the piece being moved is white or black

		//iterate through every piece
		for (int i = 0; i < counter; i++) {

			//if a piece has the same position as the given position, then that is the piece trying to be moved
			if (game[i].getYpos() == SY && game[i].getXpos() == SX) {
				curPiece = i;
				break;
			}
		}

		white = game[curPiece].getColour() == 'w';

		if (board[CY][CX]) {//if there is a piece at the new position

			int ispiece = isPiece(CY, CX);//the type of piece at the new position

			if ((ispiece > 0 && white) || (ispiece < 0 && !white)) {//if the piece is the same colour as the moving piece

				friendPiece = true;

			} else {//if the piece is the opposite colour to the moving piece

				enemyPiece = Math.abs(ispiece) - 1;

			}

		}

		int kingsPlace = 0;//the gamenumber of the current person's king

		//iterate through every piece
		for (int i = 0; i < counter; i++) {

			//if the piece is a king and it is the same colour as the piece being moved
			if (game[i].getPiece() == 'k' && ((white && game[i].getColour() == 'w') || (!white && game[i].getColour() == 'b'))) {

				kingsPlace = i;
				i = counter;

			}

		}

		//change the enemy piece's place when taking them
		if (!friendPiece && check(curPiece, CY, CX, SY, SX)) {//if the piece can move to the new square and there is not a piece of the same colour

			int enemyY = 0, enemyX = 0;//the X and Y position of the enemy piece

			if (enemyPiece != -1) {//if there is a piece being taken

				//store the position of the piece and then move it off the board
				//this is incase the move is invalid and the board must be set back to before the move
				enemyY = game[enemyPiece].getYpos();
				enemyX = game[enemyPiece].getXpos();
				game[enemyPiece].setXpos(-2);
				game[enemyPiece].setYpos(-2);
			}

			//move the current piece to its new position
			game[curPiece].setYpos(CY);
			game[curPiece].setXpos(CX);
			board[CY][CX] = true;
			board[SY][SX] = false;

			OldPiece = game[curPiece].getPiece();//the piece type of the piece being moved

			//if the piece is a pawn and promoting at the end rank
			if (game[curPiece].getPiece() == 'p' && (game[curPiece].getYpos() == 7 || game[curPiece].getYpos() == 0)) {

				//keep asking the user for what type of piece the pawn is becomming, until a valid answer is given
				while (newPiece == null || (newPiece.length() != 1 || !Character.isDigit(newPiece.charAt(0))
						|| Integer.parseInt(newPiece) < 1 || Integer.parseInt(newPiece) > 4)) {

					//if the bot is promoting its pawn, it must always choose a queen
					if (bot && ((botColour == 'w' && curTurnW) || (botColour == 'b' && !curTurnW))) {
						newPiece = "1";
					} else {

						//ask the user what to promote their pawn into
						newPiece = JOptionPane.showInputDialog("What is your pawn becoming? please type only the number \n1)Queen\n2)Rook\n3)Knight\n4)Bishop");

					}
				}
				//change the piece type of the pawn to whatever was selected
				switch (Integer.parseInt(newPiece)) {

					case 1://set the pawn to a queen
						game[curPiece].setPiece('q');
						break;

					case 2://set the pawn to a rook
						game[curPiece].setPiece('r');
						break;

					case 3://set the pawn to a knight
						game[curPiece].setPiece('n');
						break;

					case 4://set the pawn to a bishop
						game[curPiece].setPiece('b');
						break;
				}
			}

			//whether the king of the user whose turn it is in check after their move
			canStale = false;
			boolean storecheck = inCheck(kingsPlace, CX, SX);
			canStale = true;

			int EnemyKingsPlace = 0;//the game number of the king of the enemy to the user whose turn it is

			//iterate through all the pieces
			for (int i = 0; i < counter; i++) {

				//if the piece is a king and it is the opposite colour as the piece being moved
				if (game[i].getPiece() == 'k' && ((white && game[i].getColour() == 'b') || (!white && game[i].getColour() == 'w'))) {
					EnemyKingsPlace = i;
					i = counter;
				}
			}

			if (checkScore) {//if the bot is checking moves but not actually making a move yet

				canStale = false;

				totalScore += opponentInCheck(EnemyKingsPlace) ? (MainMenu.difficulty == 'E' ? 5 : 14) : 0;
				//the bot gets 5 or 14 points if the move puts the enemy king in check depending on its difficulty

				canCheckMate = true;//temporarily change if the bot can check mate the user

				opponentInCheck(EnemyKingsPlace);//checks if the bots move would cause a checkmate and gives points accordingly

				canCheckMate = false;//change back if the bot can check mate the user

				canStale = true;

				//the bot gets points if it takes an enemy piece, based on the piece's value
				if (enemyPiece != -1) {
					totalScore += Integer.parseInt("" + values.get(game[enemyPiece].getPiece())) * (MainMenu.difficulty == 'E' ? 10 : 5);

				}

			}

			//revert board back to how it was before the move
			board[CY][CX] = false;
			board[SY][SX] = true;

			game[curPiece].setYpos(SY);
			game[curPiece].setXpos(SX);
			game[curPiece].setPiece(OldPiece);

			if (enemyPiece != -1) {
				board[CY][CX] = true;
				game[enemyPiece].setXpos(enemyX);
				game[enemyPiece].setYpos(enemyY);
			}

			//if the move is valid
			if (!storecheck) {

				moveDone = true;

				//make the piece that just moved have a background so that the user can see what move was just made
				ChessGame.pieces[movedPiece].setBackground(Color.BLUE);
				ChessGame.pieces[movedPiece].setBackground(Color.GRAY);
				ChessGame.pieces[movedPiece].setOpaque(false);

				//take away the background of the piece that used to be the last move but now is not
				ChessGame.pieces[curPiece].setBackground(Color.DARK_GRAY);
				ChessGame.pieces[curPiece].setOpaque(true);

				movedPiece = curPiece;

				//if the move was not performed by a bot just checking the moves - the move can go ahead
				if (!checkScore) {

					//if you are taking a piece
					if (enemyPiece != -1) {

						//move the taken piece off the board
						ChessGame.pieces[enemyPiece].setLocation(-120, -120);
						game[enemyPiece].setXpos(-2);
						game[enemyPiece].setYpos(-2);

						//edit the score based on whatever piece was taken
						if (game[enemyPiece].getColour() == 'w') {

							WScore -= Integer.parseInt("" + values.get(game[enemyPiece].getPiece()));
						} else {

							BScore -= Integer.parseInt("" + values.get(game[enemyPiece].getPiece()));
						}

						//reduce the amount of pieces currently in the game
						counter--;
						game[enemyPiece] = game[counter];
						ChessGame.pieces[enemyPiece] = ChessGame.pieces[counter];

					}

					if (curTurnW) {//if white just moved, set it to be blacks turn, and change the name of whose turn it is

						curTurnW = false;

						ChessGame.colourShower.setText("    Blacks Turn");

						ChessGame.userShower.setText(ChessGame.user2);

					} else {//if black just moved, set it to be whites turn, and change the name of whose turn it is

						curTurnW = true;

						ChessGame.colourShower.setText("    Whites Turn");

						ChessGame.userShower.setText(ChessGame.user1);

					}

					//if the move was castling, castle
					if (canCastle) {

						castle(CY, CX, SY, SX);
					}

					//move the piece to its new position and update the board array
					game[curPiece].setYpos(CY);
					game[curPiece].setXpos(CX);
					board[CY][CX] = true;
					board[SY][SX] = false;
					game[curPiece].setHasMoved(true);
					ChessGame.pieces[curPiece].setLocation(20 + game[curPiece].getXpos() * 120, 860 - (game[curPiece].getYpos()) * 120);

					//if the pawn is promoting, change its piece and value
					if (game[curPiece].getPiece() == 'p' && (game[curPiece].getYpos() == 7 || game[curPiece].getYpos() == 0)) {

						switch (Integer.parseInt(newPiece)) {

							case 1:
								//add the value of a queen but subtract the value of a pawn
								game[curPiece].setPiece('q');
								if (curTurnW) {
									BScore += 8;
								} else {
									WScore += 8;
								}
								break;

							case 2:
								//add the value of a rook but subtract the value of a pawn
								game[curPiece].setPiece('r');
								if (curTurnW) {
									BScore += 4;
								} else {
									WScore += 4;
								}
								break;

							case 3:
								//add the value of a knight but subtract the value of a pawn
								game[curPiece].setPiece('n');
								if (curTurnW) {
									BScore += 2;
								} else {
									WScore += 2;
								}
								break;

							case 4:
								//add the value of a bishop but subtract the value of a pawn
								game[curPiece].setPiece('b');
								if (curTurnW) {
									BScore += 2;
								} else {
									WScore += 2;
								}
								break;
						}

						//change the image of the pawn when it promotes
						javax.swing.ImageIcon pic = new javax.swing.ImageIcon(game[curPiece].getColour() + "" + game[curPiece].getPiece() + ".png");
						javax.swing.ImageIcon scaledpic = new javax.swing.ImageIcon(pic.getImage().getScaledInstance(70, 70, 70));
						ChessGame.pieces[curPiece].setIcon(scaledpic);

					}

					//show the new score of black and white
					ChessGame.Wshower.setText("  White: " + WScore);
					ChessGame.Bshower.setText("  Black: " + BScore);

					//save all the piece types left on the board in 2 arrays
					char[] wpieces = new char[counter];
					int wcounter = 0;
					char[] bpieces = new char[counter];
					int bcounter = 0;

					//iterate through every piece
					for (int i = 0; i < counter; i++) {

						if (game[i].getColour() == 'w') {//if a piece is white, store it in the white pieces array

							wpieces[wcounter] = game[i].getPiece();
							wcounter++;

						} else {//if a piece is black, store it in the black pieces array

							bpieces[bcounter] = game[i].getPiece();
							bcounter++;

						}

					}
					boolean forcedStale = true;

					//if there are more than 3 white pieces on the board, it isnt automatically stalemate
					if (wcounter >= 4) {
						forcedStale = false;
					} else {

						//iterate through every white piece
						for (int i = 0; i < wcounter; i++) {

							//it is stalemate if the remianing white and black pieces cant be used to checkmate
							if (wpieces[i] == 'q' || wpieces[i] == 'r' || wpieces[i] == 'p') {

								forcedStale = false;
								break;
							}
							if (wpieces[i] == 'b' && wcounter == 3) {

								forcedStale = false;
								break;
							}

						}
					}
					if (forcedStale) {

						//if there are more than 3 white pieces on the board, it isnt automatically stalemate
						if (bcounter >= 4) {
							forcedStale = false;
						} else {

							//iterate through every white piece
							for (int i = 0; i < bcounter; i++) {

								//it is stalemate if the remianing white and black pieces cant be used to checkmate
								if (bpieces[i] == 'q' || bpieces[i] == 'r' || bpieces[i] == 'p') {

									forcedStale = false;
									break;
								}
								if (bpieces[i] == 'b' && bcounter == 3) {

									forcedStale = false;
									break;
								}

							}
						}
					}

					//if no side can checkmate the other, it is stalemate
					if (forcedStale) {
						gameOver(false, 'n');

					} else {

						//if the bot just moved, check to see if the user is in checkmate or stalemate
						opponentInCheck(EnemyKingsPlace);

						ChessGame.errorShower.setText("");

						//if it is now bots turn, call the bots logic method
						if (bot && ((botColour == 'w' && curTurnW) || (botColour == 'b' && !curTurnW))) {
							moveDone = false;
							botplays();
						}
					}
				} else {
					canCastle = false;
				}
			}
		}

	}

	/**
	 * looks through every possible move for the bot, sees if the move is valid,
	 * and plays the best move - only in singleplayer games
	 */
	public static void botplays() {

		checkScore = true;	//tells the program that the bot is only testing moves

		//iterate through every piece
		for (int i = 0; i < counter; i++) {

			if (game[i].getColour() == botColour) {//if the pieces colour is the same as the bot's colour

				//iterate thorugh every x and y position, 0-7
				for (int j = 0; j < 8; j++) {
					for (int k = 0; k < 8; k++) {

						totalScore = 0;//reset the score of the move to be zero

						move(j, k, game[i].getYpos(), game[i].getXpos());//try move the piece to the selected position, and see if it is valid

						if (moveDone) {//if the move is valid, continue
							moveDone = false;

							//the bot gets 2 or 8 points for developing a bishop or a knight depending on the difficulty of the bot
							if (!game[i].HasMoved() && (game[i].getPiece() == 'n' || game[i].getPiece() == 'b')) {
								totalScore += MainMenu.difficulty == 'E' ? 2 : 8;
							}

							//the bot gets 2 or 6 points for developing a center pawn depending on the difficulty of the bot
							if (!game[i].HasMoved() && game[i].getPiece() == 'p' && (game[i].getXpos() == 3 || game[i].getXpos() == 4)) {
								totalScore += MainMenu.difficulty == 'E' ? 2 : 6;
							}

							//the bot loses 0 or 3 points for moving its king depending on the difficulty of the bot
							if (game[i].getPiece() == 'k') {
								totalScore -= MainMenu.difficulty == 'E' ? 0 : 3;
							}

							//the bot loses 0 or 2 points for moving a knight to the last or first column depending on the difficulty of the bot
							if (game[i].getPiece() == 'n' && (k == 0 || k == 7)) {
								totalScore -= MainMenu.difficulty == 'E' ? 0 : 2;
							}

							//the bot gets 0 or 1 points for developing a rook to a center column depending on the difficulty of the bot
							if (game[i].getPiece() == 'r' && (k == 3 || k == 4)) {
								totalScore += MainMenu.difficulty == 'E' ? 0 : 1;
							}

							//the bot gets points the further they push their pawns to encourage queening of pawns,
							//but lose points if they move in the first two rows, to avoid mass pawn pushing
							if (game[i].getPiece() == 'p') {
								if (game[i].getColour() == 'w') {
									totalScore += (j - 3);
								} else {
									totalScore += (4 - j);
								}

								//the bot gets 400 or 40 points for queening a pawn depending on the difficulty of the bot
								if ((game[i].getYpos() == 1 && game[i].getColour() == 'b') || (game[i].getYpos() == 6 && game[i].getColour() == 'w')) {
									totalScore += MainMenu.difficulty == 'E' ? 400 : 40;
								}

							}

							//the bot loses a point if it moves the same piece twice in a row,
							//to encourage different moves over simply looping the same moves
							if (i == lastMoved) {
								totalScore--;
							}

							checkBotTakes = true;

							//iterate through every piece
							for (int l = 0; l < counter; l++) {

								//if the piece is the opposite colour to the boss
								if (game[l].getColour() != botColour
										//if an opponent piece can take the piece the bot just moved, take away the value of that piece in points
										&& check(l, j, k, game[l].getYpos(), game[l].getXpos())) {

									//the bot loses points based on the value of the piece
									totalScore -= Integer.parseInt("" + values.get(game[i].getPiece())) * MainMenu.difficulty == 'E' ? 2 : 5;
									l = counter;

								}
							}

							checkBotTakes = false;
							scoreCheck[i][j][k] = totalScore;

						} else {//if the move is invalid, make it scored as -100 so that it is never selected

							scoreCheck[i][j][k] = -100;

						}

					}
				}
			}
		}

		checkScore = false;//the bot is no longer checking moves

		//these variables find the move with the highest score and record the move
		int highestNum = -99;

		String pieceMove = "";

		//iterate through every possible move and compare their scores
		for (int i = 0; i < counter; i++) {
			for (int j = 0; j < 8; j++) {
				for (int k = 0; k < 8; k++) {

					//record the highest score
					if (scoreCheck[i][j][k] >= highestNum) {
						highestNum = scoreCheck[i][j][k];
						pieceMove = i + " " + j + " " + k;
					}

					//reset all scores to -100;
					scoreCheck[i][j][k] = -100;
				}
			}
		}

		String[] temp = pieceMove.split(" ");

		//if the move is valid, do the move
		if (temp.length
				> 2) {
			lastMoved = Integer.parseInt(temp[0]);//save the last moved piece's number

			move(Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), game[Integer.parseInt(temp[0])].getYpos(), game[Integer.parseInt(temp[0])].getXpos());

			ChessGame.errorShower.setText("");
		}

	}

	/**
	 * takes in the game number of the king as well as its X positions to see if
	 * the king would be in check if it moved to any specific square - this
	 * method deals with if the king is trying to castle
	 *
	 * @param Knum the game number of the king
	 * @param CX the kings new X value
	 * @param SX the kings old X value
	 * @return whether the king would be in check at any specific board state
	 */
	public static boolean inCheck(int Knum, int CX, int SX) {

		boolean check = false;	//whether the king is in check

		if (!canCastle) {//if the king is not trying to castle, move to the more general check for if the king is in check

			check = opponentInCheck(Knum);

		} else {

			//so that the pawns act correct in this check
			boolean temp = checkBotTakes;
			checkBotTakes = true;

			if (CX > SX) {//if it is king side castling

				//iterate through every piece
				for (int i = 0; i < counter; i++) {

					//if the piece has not been taken
					if (game[i].getYpos() != -2
							//if the piece is the opposite colour to the king
							&& (game[i].getColour() != game[Knum].getColour())
							//if the piece is attacking the king at any point in the castle, then the move is not valid
							&& (check(i, game[Knum].getYpos(), game[Knum].getXpos(), game[i].getYpos(), game[i].getXpos())
							|| check(i, game[Knum].getYpos(), game[Knum].getXpos() - 1, game[i].getYpos(), game[i].getXpos())
							|| check(i, game[Knum].getYpos(), game[Knum].getXpos() - 2, game[i].getYpos(), game[i].getXpos()))) {
						ChessGame.errorShower.setText("    You cannot castle into check");
						canCastle = false;
						check = true;
						break;

					}

				}
			} else {//if it is queen side castling

				//iterate through every piece
				for (int i = 0; i < counter; i++) {

					//if the piece has not been taken
					if (game[i].getYpos() != -2
							//if the piece is the opposite colour to the king
							&& ((game[i].getColour() == 'b' && curTurnW) || (game[i].getColour() == 'w' && !curTurnW))
							//if the piece is attacking the king at any point in the castle, then the move is not valid
							&& (check(i, game[Knum].getYpos(), game[Knum].getXpos(), game[i].getYpos(), game[i].getXpos())
							|| check(i, game[Knum].getYpos(), game[Knum].getXpos() + 1, game[i].getYpos(), game[i].getXpos())
							|| check(i, game[Knum].getYpos(), game[Knum].getXpos() + 2, game[i].getYpos(), game[i].getXpos())
							|| check(i, game[Knum].getYpos(), game[Knum].getXpos() + 3, game[i].getYpos(), game[i].getXpos()))) {

						ChessGame.errorShower.setText("    You cannot castle into check");
						canCastle = false;
						check = true;
						break;
					}

				}
			}
			checkBotTakes = temp;

		}
		return check;

	}

	/**
	 * takes in the number of the king as well as its X positions to see if the
	 * king would be in check if it moved to any specific square - this method
	 * deals with if the king is not trying to castle
	 *
	 * @param Knum the game number of the king
	 * @return whether the king would be in check at any specific board state
	 */
	public static boolean opponentInCheck(int Knum) {

		boolean check = false;	//if the king is in check
		int numChecks = 0;		//records how many pieces are cehcking the king
		int attackPiece = 0;	//if only one piece is cehcking the king, this records its game number

		//so that the pawns act correct in this check
		boolean temp = checkBotTakes;
		checkBotTakes = true;

		//iterate through every piece
		for (int i = 0; i < counter; i++) {

			//if the piece has not been taken
			if (game[i].getYpos() != -2
					//if the piece is the opposite colour as the king
					&& game[i].getColour() != game[Knum].getColour()
					//if the piece can take the king at its current position
					&& check(i, game[Knum].getYpos(), game[Knum].getXpos(), game[i].getYpos(), game[i].getXpos())) {
				ChessGame.errorShower.setText("     You are in check");

				canCastle = false;	//castling becomes invalid as you can't castle into check
				check = true;		//the king is in cehck
				numChecks++;		//the number of pieces checking the king increases
				attackPiece = i;	//the attacking piece is saved
			}

		}
		checkBotTakes = temp;

		if (check) {//if the king is in check, see if it is checkmate

			checkMate(Knum, numChecks, attackPiece);

		} else if (canStale) {//if the king is not in check, see if it is stalemate

			staleMate(Knum);

		}

		return check;//return if the king is in check
	}

	/**
	 * takes in the position of the king and information about the attacking
	 * pieces, and works out if there is a way for the king to get out of check
	 *
	 * @param Knum the game number of the king
	 * @param numChecks the number of pieces attacking the king
	 * @param attackPiece the game number of the last piece attacking the king
	 */
	public static void checkMate(int Knum, int numChecks, int attackPiece) {

		boolean checkMate = true;	//if the king is in checkmate
		boolean checkMateHelp;		//a variable to help determine if the king is checkmated

		//if more than one piece is attacking the king, the king has to move to get out of check
		if (numChecks > 1) {

			//so that the pawns act correct in this check
			boolean temp = checkBotTakes;
			checkBotTakes = true;

			//iterate through every square next to the king to see if the king can move there
			for (int i = game[Knum].getXpos() - 1; i <= game[Knum].getXpos() + 1; i++) {
				for (int j = game[Knum].getYpos() - 1; j <= game[Knum].getYpos() + 1; j++) {

					int ispiece = isPiece(j, i);//finds if there is a piece on the square the king is trying to move to
					checkMateHelp = true;

					//if the position is not off the board, there is no piece there or the piece is the opposite colour to the king
					if (i >= 0 && i <= 7 && j >= 0 && j <= 7 && ((ispiece >= 0 && game[Knum].getColour() == 'b') || (ispiece <= 0 && game[Knum].getColour() == 'w'))) {

						checkMateHelp = false;

						//iterate through every piece
						for (int z = 0; z < counter; z++) {

							//if the piece has not been taken
							if (game[z].getYpos() != -2
									//exclude the piece on the square the king has just moved to, if there is a piece there
									&& ((ispiece == 0) || (z != Math.abs(ispiece) - 1))
									//if the piece is not the same colour as the king
									&& ((game[z].getColour() == 'b' && game[Knum].getColour() == 'w') || (game[z].getColour() == 'w' && game[Knum].getColour() == 'b'))
									//if the piece can take the king on this square, the sqaure is not valid and the king could still be in checkmate
									&& check(z, j, i, game[z].getYpos(), game[z].getXpos())) {

								checkMateHelp = true;
								break;

							}

						}

					}

					//if there is a square where no piece can take the king, it is not checkmate
					if (checkMateHelp == false) {
						checkMate = false;
					}

				}

			}
			checkBotTakes = temp;

		} else {//if only one piece is attacking the king, anything can be done to stop the check

			//so that the pawns act correct in this check
			boolean temp = checkBotTakes;
			checkBotTakes = true;
			board[game[Knum].getYpos()][game[Knum].getXpos()] = false;

			//Checking if the king can move out the way
			//
			//iterate through every square next to the king to see if the king can move there
			for (int i = game[Knum].getXpos() - 1; i <= game[Knum].getXpos() + 1; i++) {// if the king can move
				for (int j = game[Knum].getYpos() - 1; j <= game[Knum].getYpos() + 1; j++) {

					int ispiece = isPiece(j, i);//finds if there is a piece on the square the king is trying to move to
					checkMateHelp = true;

					//if the position is not off the board, there is no piece there or the piece is the opposite colour to the king
					if (i >= 0 && i <= 7 && j >= 0 && j <= 7 && ((ispiece >= 0 && game[Knum].getColour() == 'b') || (ispiece <= 0 && game[Knum].getColour() == 'w'))) {

						checkMateHelp = false;

						//iterate through every piece
						for (int z = 0; z < counter; z++) {

							//if the piece has not been taken
							if (game[z].getYpos() != -2
									//exclude the piece on the square the king has just moved to, if there is a piece there
									&& ((ispiece == 0) || (z != Math.abs(ispiece) - 1))
									//if the new attacking piece is the same colour as the attacking piece
									&& game[z].getColour() == game[attackPiece].getColour()
									//if the piece can take the king on this square, the sqaure is not valid and the king could still be in checkmate
									&& check(z, j, i, game[z].getYpos(), game[z].getXpos())) {

								checkMateHelp = true;
								break;

							}

						}

					}

					//if no piece is attacking the king at any position, it is not checkmate
					if (checkMateHelp == false) {
						checkMate = false;
					}

				}
			}
			checkBotTakes = temp;
			board[game[Knum].getYpos()][game[Knum].getXpos()] = true;

			//Checking if a piece can take the attacking piece
			if (checkMate) {//do this check if there is no valid way out of checkmate thus far

				//so that the pawns act correct in this check
				temp = checkBotTakes;
				checkBotTakes = true;

				//iterate through every piece
				for (int j = 0; j < counter; j++) {

					//if the colour of the piece is not the same as the colour of the attacking piece and the piece hasnt been taken
					if ((game[j].getColour() != game[attackPiece].getColour() && game[j].getYpos() != -2 && j != Knum)
							//if the piece can take the attacking piece
							&& check(j, game[attackPiece].getYpos(), game[attackPiece].getXpos(), game[j].getYpos(), game[j].getXpos())) {

						checkMateHelp = false;
						board[game[j].getYpos()][game[j].getXpos()] = false;//temporarily set the attacking pieces position to false

						//iterate through every piece
						for (int i = 0; i < counter; i++) {

							//if the piece is the same colour as the attacking piece and the piece has not been taken
							if ((game[i].getColour() == game[attackPiece].getColour() && game[i].getYpos() != -2 && i != attackPiece)
									//if there is a piece that can attack the king after a piece has moved, it is still checkmate
									&& check(i, game[Knum].getYpos(), game[Knum].getXpos(), game[i].getYpos(), game[i].getXpos())) {

								checkMateHelp = true;

							}

						}
						board[game[j].getYpos()][game[j].getXpos()] = true;//set the attacking pieces position back to true

						//if no piece is attacking the king at any position, it is not checkmate
						if (checkMateHelp == false) {
							checkMate = false;
						}

					}

				}

				checkBotTakes = temp;
			}

			//Checking if a piece can move and block the check
			//
			//if the attacking piece is not a pawn, king or knight (the attack can be blocked) and there is no way out of checkmate so far
			if (checkMate && game[attackPiece].getPiece() != 'n' && game[attackPiece].getPiece() != 'p' && game[attackPiece].getPiece() != 'k') {

				//If the attacking piece is in the same column as the king
				if (game[attackPiece].getXpos() == game[Knum].getXpos()) {

					//the smaller and bigger Y positions of the king and attacking piece
					int smallY, bigY;
					smallY = (game[attackPiece].getYpos() < game[Knum].getYpos()) ? game[attackPiece].getYpos() : game[Knum].getYpos();
					bigY = (game[attackPiece].getYpos() > game[Knum].getYpos()) ? game[attackPiece].getYpos() : game[Knum].getYpos();

					//iterate through every point between the king and the attacking piece
					for (int j = smallY + 1; j < bigY; j++) {

						//iterate for every piece
						for (int i = 0; i < counter; i++) {

							//if the piece is the same colour as the king, but not the king, and it has not been taken
							if (game[i].getColour() != game[attackPiece].getColour() && i != Knum && game[i].getYpos() != -2) {

								board[game[i].getYpos()][game[i].getXpos()] = false;
								checkMateHelp = true;

								//check if the piece can move to block the check
								if (check(i, j, game[Knum].getXpos(), game[i].getYpos(), game[i].getXpos())) {

									boolean tempHelp = board[j][game[Knum].getXpos()];
									board[j][game[Knum].getXpos()] = true;

									checkMateHelp = false;

									//iterate for every piece
									for (int k = 0; k < counter; k++) {

										//if the piece is not the same colour as the king and it has not been taken
										if (game[k].getColour() != game[Knum].getColour() && game[k].getYpos() != -2
												//if there is a piece that can attack the king after a piece has moved, it is still checkmate
												&& check(k, game[Knum].getYpos(), game[Knum].getXpos(), game[k].getYpos(), game[k].getXpos())) {

											checkMateHelp = true;
											break;

										}

									}

									board[j][game[Knum].getXpos()] = tempHelp;

								}

								//if no piece is attacking the king at any position, it is not checkmate
								if (checkMateHelp == false) {
									checkMate = false;
								}

								board[game[i].getYpos()][game[i].getXpos()] = true;
							}
						}
					}

					//if the attacking piece is in the same row as the king
				} else if (game[attackPiece].getYpos() == game[Knum].getYpos()) {

					//the smaller and bigger X positions of the king and attacking piece
					int smallX, bigX;
					smallX = (game[attackPiece].getXpos() < game[Knum].getXpos()) ? game[attackPiece].getXpos() : game[Knum].getXpos();
					bigX = (game[attackPiece].getXpos() > game[Knum].getXpos()) ? game[attackPiece].getXpos() : game[Knum].getXpos();

					//iterate through every point between the king and the attacking piece
					for (int j = smallX + 1; j < bigX; j++) {

						//iterate for every piece
						for (int i = 0; i < counter; i++) {

							//if the piece is the same colour as the king, but not the king, and it has not been taken
							if (game[i].getColour() != game[attackPiece].getColour() && i != Knum && game[i].getYpos() != -2) {

								board[game[i].getYpos()][game[i].getXpos()] = false;
								checkMateHelp = true;

								//check if the piece can move to block the check
								if (check(i, game[Knum].getYpos(), j, game[i].getYpos(), game[i].getXpos())) {

									boolean tempHelp = board[game[Knum].getYpos()][j];
									board[game[Knum].getYpos()][j] = true;

									checkMateHelp = false;

									//iterate for every piece
									for (int k = 0; k < counter; k++) {

										//if the piece is not the same colour as the king and it has not been taken
										if (game[k].getColour() != game[Knum].getColour() && game[k].getYpos() != -2
												//if there is a piece that can attack the king after a piece has moved, it is still checkmate
												&& check(k, game[Knum].getYpos(), game[Knum].getXpos(), game[k].getYpos(), game[k].getXpos())) {

											checkMateHelp = true;

										}

									}

									board[game[Knum].getYpos()][j] = tempHelp;

								}

								//if no piece is attacking the king at any position, it is not checkmate
								if (checkMateHelp == false) {
									checkMate = false;
								}

								board[game[i].getYpos()][game[i].getXpos()] = true;
							}
						}
					}

					//if the attacking piece is in the same diagonal as the king
				} else {

					//these variables store the X and Y position of the king and attacking piece
					int Y1, Y2, X1, X2;
					Y1 = game[attackPiece].getYpos();
					Y2 = game[Knum].getYpos();
					X1 = game[attackPiece].getXpos();
					X2 = game[Knum].getXpos();

					//these variables store the smaller and bigger values of the X and Y coordinate
					int smallX, bigX, smallY, bigY;

					smallX = (X1 < X2) ? X1 : X2;
					bigX = (X1 > X2) ? X1 : X2;

					smallY = (Y1 < Y2) ? Y1 : Y2;
					bigY = (Y1 > Y2) ? Y1 : Y2;

					//iterate for every piece
					for (int i = 0; i < counter; i++) {

						//if the piece is the same colour as the king, but not the king, and it has not been taken
						if (game[i].getColour() != game[attackPiece].getColour() && i != Knum && game[i].getYpos() != -2) {

							//if the check is on a right diagonal
							if ((Y1 > Y2 && X1 > X2) || (Y1 < Y2 && X1 < X2)) {

								int z = smallY + 1;

								//iterate through every point between the king and the attacking piece
								for (int j = smallX + 1; j < bigX; j++) {

									board[game[i].getYpos()][game[i].getXpos()] = false;
									checkMateHelp = true;

									//check if the piece can move to block the check
									if (check(i, z, j, game[i].getYpos(), game[i].getXpos())) {

										boolean tempHelp = board[z][j];
										board[z][j] = true;

										checkMateHelp = false;

										//iterate for every piece
										for (int k = 0; k < counter; k++) {

											//if the piece is not the same colour as the king and it has not been taken
											if (game[k].getColour() != game[Knum].getColour() && game[k].getYpos() != -2
													//if there is a piece that can attack the king after a piece has moved, it is still checkmate
													&& check(k, game[Knum].getYpos(), game[Knum].getXpos(), game[k].getYpos(), game[k].getXpos())) {

												checkMateHelp = true;

											}

										}

										board[z][j] = tempHelp;

									}

									//if no piece is attacking the king at any position, it is not checkmate
									if (checkMateHelp == false) {
										checkMate = false;

									}

									board[game[i].getYpos()][game[i].getXpos()] = true;

									z++;
								}

								//if the check is on a left diagonal
							} else {

								int z = bigY - 1;

								//iterate through every point between the king and the attacking piece
								for (int j = smallX + 1; j < bigX; j++) {

									board[game[i].getYpos()][game[i].getXpos()] = false;
									checkMateHelp = true;

									//check if the piece can move to block the check
									if (check(i, z, j, game[i].getYpos(), game[i].getXpos())) {

										boolean tempHelp = board[z][j];
										board[z][j] = true;

										checkMateHelp = false;

										//iterate for every piece
										for (int k = 0; k < counter; k++) {

											//if the piece is not the same colour as the king and it has not been taken
											if (game[k].getColour() != game[Knum].getColour() && game[k].getYpos() != -2
													//if there is a piece that can attack the king after a piece has moved, it is still checkmate
													&& check(k, game[Knum].getYpos(), game[Knum].getXpos(), game[k].getYpos(), game[k].getXpos())) {

												checkMateHelp = true;

											}

										}

										board[z][j] = tempHelp;

									}

									//if no piece is attacking the king at any position, it is not checkmate
									if (checkMateHelp == false) {
										checkMate = false;

									}

									board[game[i].getYpos()][game[i].getXpos()] = true;

									z--;
								}

							}

						}

					}

				}

			}

		}

		ChessGame.errorShower.setText("     You are in check");

		//if the king would be in checkmate but the bot is just testing moves
		if (checkMate && canCheckMate) {

			totalScore = 999; //if the bot can checkmate, it must

		} else if (checkMate && !checkScore) {//if the king is in checkmate

			gameOver(true, game[Knum].getColour());

		}
	}

	/**
	 * takes in the position of the king, and works out if the game is in
	 * stalemate
	 *
	 * @param Knum the game number of the king
	 */
	public static void staleMate(int Knum) {

		boolean staleMate = true;	//if the king is in stalemate
		boolean staleMateHelp;		//a variable to help determine if the king is stalemated

		bigLoop:

		//iterate through every piece
		for (int z = 0; z < counter; z++) {

			//if the piece is the same colour as the king and has not been taken
			if (game[z].getColour() == game[Knum].getColour() && game[z].getYpos() != -2) {

				//if the piece is the king
				if (z == Knum) {

					//so that the pawns act correct in this check
					boolean temp = checkBotTakes;
					checkBotTakes = true;

					//iterate through every square next to the king to see if the king can move there
					for (int i = game[z].getXpos() - 1; i <= game[z].getXpos() + 1; i++) {
						for (int j = game[z].getYpos() - 1; j <= game[z].getYpos() + 1; j++) {

							int ispiece = isPiece(j, i);//finds if there is a piece on the square the king is trying to move to

							//if the position is not off the board, there is no piece there or the piece is the opposite colour to the king
							if (i >= 0 && i <= 7 && j >= 0 && j <= 7 && ((ispiece >= 0 && game[Knum].getColour() == 'b') || (ispiece <= 0 && game[Knum].getColour() == 'w'))) {

								staleMateHelp = false;
								board[game[Knum].getYpos()][game[Knum].getXpos()] = false;

								//iterate through every piece
								for (int y = 0; y < counter; y++) {

									//if the piece has not been taken, is the opposite colour as the king
									if (game[y].getColour() != game[Knum].getColour() && game[y].getYpos() != -2
											//if the piece can take the king in its new position
											&& check(y, j, i, game[y].getYpos(), game[y].getXpos())) {

										staleMateHelp = true;
										break;

									}

								}
								board[game[Knum].getYpos()][game[Knum].getXpos()] = true;

								//if there is a square where the king can move, it is not stalemate
								if (staleMateHelp == false) {
									staleMate = false;
									checkBotTakes = temp;
									break bigLoop;
								}
							}
						}

					}
					checkBotTakes = temp;

				} else {//if the piece is not the king

					//iterate through every square
					for (int i = 0; i <= 7; i++) {
						for (int j = 0; j <= 7; j++) {

							//if the piece can move to that square
							if (check(z, j, i, game[z].getYpos(), game[z].getXpos())) {

								staleMateHelp = false;

								board[game[z].getYpos()][game[z].getXpos()] = false;

								//iterate for every piece
								for (int k = 0; k < counter; k++) {

									//if the piece is not the same colour as the king and it has not been taken
									if (game[z].getColour() != game[Knum].getColour() && game[k].getYpos() != -2
											//if the piece can take the king
											&& check(k, game[Knum].getYpos(), game[Knum].getXpos(), game[k].getYpos(), game[k].getXpos())) {

										staleMateHelp = true;
										break;

									}

								}

								board[game[z].getYpos()][game[z].getXpos()] = true;

								//if there is a square where a piece can move without the king being in check, it is not stalemate
								if (staleMateHelp == false) {
									staleMate = false;
									break bigLoop;
								}

							}
						}
					}

				}

			}

		}

		//if it is stalemate, end the game
		if (staleMate && canStale) {
			gameOver(false, 'n');
		}

	}

	/**
	 * ends the game, changes the score based on who won and loads the main menu
	 *
	 * @param checkMate if the game ended by checkmate or stalemate
	 * @param colour the colour of the losing side, if the game ended by
	 * checkmate
	 */
	public static void gameOver(boolean checkMate, char colour) {

		//delete the saved game if it is not the default game
		ChessGame.errorShower.setText("GAME OVER");
		if (MainMenu.LoadedGameNum != 0) {

			DB.changeQuery("DELETE * FROM GamePlayers WHERE GameNumber = " + MainMenu.LoadedGameNum);
			DB.changeQuery("DELETE * FROM SavedGame WHERE GameNumber = '" + MainMenu.LoadedGameNum + "'");
		}

		int scoreW, scoreB;//the score of the user/bot playing white and black

		String[] userScore;

		if (ChessGame.user1.length() == 1) {//if white is a bot

			if (ChessGame.user1.equals("E")) {//if the bot is on easy difficulty

				scoreW = 600;

			} else {//if the bot is on hard difficulty

				scoreW = 1100;

			}

		} else {//if white is a user

			if (ChessGame.user1.equals(encrypt.decryptString(signIn.encryptUser1))) {//if the signed up user is white

				userScore = DB.selectQuery("SELECT Score FROM Users WHERE Username = \"" + signIn.encryptUser1 + "\"");
				scoreW = Integer.parseInt(userScore[0]);

			} else {//if the second user is white

				userScore = DB.selectQuery("SELECT Score FROM Users WHERE Username = \"" + signIn.encryptUser2 + "\"");
				scoreW = Integer.parseInt(userScore[0]);

			}

		}

		if (ChessGame.user2.length() == 1) {//if black is a bot

			if (ChessGame.user2.equals("E")) {//if the bot is on easy difficulty

				scoreB = 600;

			} else {//if the bot is on hard difficulty

				scoreB = 1100;

			}

		} else {//if black is a user

			if (ChessGame.user2.equals(encrypt.decryptString(signIn.encryptUser1))) {//if the signed up user is black

				userScore = DB.selectQuery("SELECT Score FROM Users WHERE Username = \"" + signIn.encryptUser1 + "\"");
				scoreB = Integer.parseInt(userScore[0]);

			} else {//if the second user is black

				userScore = DB.selectQuery("SELECT Score FROM Users WHERE Username = \"" + signIn.encryptUser2 + "\"");
				scoreB = Integer.parseInt(userScore[0]);

			}
		}

		int newscoreB = 0, newscoreW = 0;//the changed score of white and black

		if (checkMate) {//if the game ended by checkmate

			if (scoreW > scoreB) {//if white has a higher score than black

				if (colour == 'b') {//if black lost

					//change the score of both colours
					newscoreW = scoreW + (int) Math.floor(Math.sqrt(4000 - (scoreW - scoreB) * 2));
					newscoreB = scoreB - (int) Math.floor(Math.sqrt(4000 - (scoreW - scoreB) * 2));

				} else {//if white lost

					//change the score of both colours
					newscoreW = scoreW - (int) Math.round(Math.pow((scoreW - scoreB) / 40, 2) + 100);
					newscoreB = scoreB + (int) Math.round(Math.pow((scoreW - scoreB) / 40, 2) + 100);

				}

			} else if (scoreB > scoreW) {//if white has a lower score than black

				if (colour == 'b') {//if black lost

					//change the score of both colours
					newscoreW = scoreW + (int) Math.round(Math.pow((scoreB - scoreW) / 40, 2) + 100);
					newscoreB = scoreB - (int) Math.round(Math.pow((scoreB - scoreW) / 40, 2) + 100);

				} else {//if white lost

					//change the score of both colours
					newscoreW = scoreW - (int) Math.floor(Math.sqrt(4000 - (scoreB - scoreW) * 2));
					newscoreB = scoreB + (int) Math.floor(Math.sqrt(4000 - (scoreB - scoreW) * 2));
				}

			} else {//if white has an equal score to black

				if (colour == 'b') {//if black lost

					//change the score of both colours
					newscoreW = scoreW + 100;
					newscoreB = scoreB - 100;

				} else {//if white lost

					//change the score of both colours
					newscoreW = scoreW - 100;
					newscoreB = scoreB + 100;

				}
			}

		} else {//if the game ended by stalemate

			if (scoreW > scoreB) {//if white has a higher score than black

				//change the score of both colours
				newscoreB = scoreB + 10;
				newscoreW = scoreW - 10;

			} else if (scoreB > scoreW) {//if white has a lower score than black

				//change the score of both colours
				newscoreB = scoreB - 10;
				newscoreW = scoreW + 10;

			}

		}

		if (ChessGame.user1.length() != 1) {//if white is a human

			if (ChessGame.user1.equals(encrypt.decryptString(signIn.encryptUser1))) {//if the signed up user is white

				DB.changeQuery("UPDATE Users SET Score = " + newscoreW + " WHERE [Username] = '" + signIn.encryptUser1 + "'");

			} else {

				DB.changeQuery("UPDATE Users SET Score = " + newscoreW + " WHERE [Username] = '" + signIn.encryptUser2 + "'");

			}

		}

		if (ChessGame.user2.length() != 1) {//if black is a human

			if (ChessGame.user2.equals(encrypt.decryptString(signIn.encryptUser1))) {//if the signed up user is black

				DB.changeQuery("UPDATE Users SET Score = " + newscoreB + " WHERE [Username] = '" + signIn.encryptUser1 + "'");

			} else {

				DB.changeQuery("UPDATE Users SET Score = " + newscoreB + " WHERE [Username] = '" + signIn.encryptUser2 + "'");

			}
		}

		String won = colour == 'b' ? "WHITE" : "BLACK";//the colour that won

		if (checkMate) {//if the game ended by checkmate

			JOptionPane.showMessageDialog(null, "     GAME OVER\n     CHECKMATE\n\n     " + won + " WINS");

		} else {//if the game ended by stalemate

			JOptionPane.showMessageDialog(null, "     GAME OVER\n     STALEMATE");

		}

		LogicHelp.endGame();

	}

}
//Coded by Brent Butkow

