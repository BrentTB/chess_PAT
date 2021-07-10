//Coded by Brent Butkow

package ChessGame;

import java.awt.*;

/**
 *
 * @author brentbutkow
 */
public class ChessGame extends javax.swing.JFrame {

	public static String user1;				//the username of the first user

	public static String user2;				//the username of the second user, if there are two users

	//instantiations of all the buttons, pictures and text areas
	public static javax.swing.JButton saveGame;			//button that allows you to save your current game

	public static javax.swing.JLabel placeShower;		//to show the current position of the users in-game 'curser'

	public static javax.swing.JTextField colourShower;	//to show whose turn it is

	public static javax.swing.JTextField errorShower;	//to display why the user cannot make any specific move

	public static javax.swing.JLabel boardShower;		//to display the chess board as a background for the game

	public static javax.swing.JTextField Bshower;		//to diplay black's score

	public static javax.swing.JTextField Wshower;		//to display white's score

	public static javax.swing.JTextField userShower;	//to display the username of whose turn it is

	public static javax.swing.JLabel pieces[] = new javax.swing.JLabel[32]; //all the chess pieces

	/**
	 * load the chess game's graphics and call the logic class. If one of the
	 * users is a bot, take in the bots difficulty as its username
	 *
	 * @param user1temp	the username of the first player
	 * @param user2temp	the username of the second player
	 */
	public ChessGame(String user1temp, String user2temp) {

		//get ready for the chess game to start
		LogicHelp.ChessGame(user1temp, user2temp);

		//make all the GUI components
		saveGame = new javax.swing.JButton();
		placeShower = new javax.swing.JLabel();
		boardShower = new javax.swing.JLabel();
		Bshower = new javax.swing.JTextField();
		Wshower = new javax.swing.JTextField();
		userShower = new javax.swing.JTextField();
		colourShower = new javax.swing.JTextField();
		errorShower = new javax.swing.JTextField();
		setIconImage(null);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				LogicCenter.formKeyPressed(evt);
			}

		});
		getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		placeShower.setBackground(new java.awt.Color(51, 51, 255));
		placeShower.setOpaque(true);
		getContentPane().add(placeShower, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 870, 50, 50));

		colourShower.setText(LogicCenter.curTurnW ? "    Whites Turn" : "    Blacks Turn");
		colourShower.setEditable(false);
		colourShower.setFont(new Font("Courier", Font.BOLD, 35));
		colourShower.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				LogicCenter.formKeyPressed(evt);
			}

		});
		getContentPane().add(colourShower, new org.netbeans.lib.awtextra.AbsoluteConstraints(975, 50, 300, 60));

		errorShower.setText("");
		errorShower.setEditable(false);
		errorShower.setFont(new Font("Courier", Font.PLAIN, 20));
		errorShower.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				LogicCenter.formKeyPressed(evt);
			}

		});
		getContentPane().add(errorShower, new org.netbeans.lib.awtextra.AbsoluteConstraints(975, 700, 300, 150));

		Wshower.setText("  White: " + LogicCenter.WScore);
		Wshower.setEditable(false);
		Wshower.setFont(new Font("Courier", Font.BOLD, 35));
		Wshower.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				LogicCenter.formKeyPressed(evt);
			}

		});
		getContentPane().add(Wshower, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 250, 200, 60));

		saveGame.setText("Save this game and exit");
		saveGame.setFont(new Font("Courier", Font.PLAIN, 25));
		saveGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveGameActionPerformed(evt);

			}

		});
		saveGame.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				LogicCenter.formKeyPressed(evt);
			}

		});
		getContentPane().add(saveGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 580, 330, 60));

		Bshower.setText("  Black: " + LogicCenter.BScore);
		Bshower.setEditable(false);
		Bshower.setFont(new Font("Courier", Font.BOLD, 35));
		Bshower.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				LogicCenter.formKeyPressed(evt);
			}

		});
		getContentPane().add(Bshower, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 350, 200, 60));

		userShower.setText(LogicCenter.curTurnW ? user1 : user2);
		userShower.setEditable(false);
		userShower.setFont(new Font("Courier", Font.BOLD, 25));
		userShower.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				LogicCenter.formKeyPressed(evt);
			}

		});
		getContentPane().add(userShower, new org.netbeans.lib.awtextra.AbsoluteConstraints(955, 150, 340, 60));

		//load the image of every piece that is in the game
		for (int i = 0; i < LogicCenter.counter; i++) {

			LogicCenter.board[LogicCenter.game[i].getYpos()][LogicCenter.game[i].getXpos()] = true;
			pieces[i] = new javax.swing.JLabel();
			pieces[i].setBackground(Color.GRAY);
			javax.swing.ImageIcon pic = new javax.swing.ImageIcon(LogicCenter.game[i].getColour() + "" + LogicCenter.game[i].getPiece() + ".png");
			javax.swing.ImageIcon scaledpic = new javax.swing.ImageIcon(pic.getImage().getScaledInstance(70, 70, 70));

			pieces[i].setIcon(scaledpic);

			getContentPane().add(pieces[i], new org.netbeans.lib.awtextra.AbsoluteConstraints(20 + LogicCenter.game[i].getXpos() * 120,
					860 - (LogicCenter.game[i].getYpos()) * 120, 70, 70));
		}

		javax.swing.ImageIcon pic = new javax.swing.ImageIcon("Chess Board.jpg");
		javax.swing.ImageIcon scaledpic = new javax.swing.ImageIcon(pic.getImage().getScaledInstance(950, 950, 950));
		boardShower.setIcon(scaledpic);
		boardShower.setMaximumSize(new java.awt.Dimension(500, 500));
		boardShower.setMinimumSize(new java.awt.Dimension(400, 400));
		boardShower.setPreferredSize(new java.awt.Dimension(400, 400));
		getContentPane().add(boardShower, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 950));

		pack();
		setLocationRelativeTo(null);

		//if the bot is making the next move, call the bot
		if (LogicCenter.bot && ((LogicCenter.botColour == 'w' && LogicCenter.curTurnW) || (LogicCenter.botColour == 'b' && !LogicCenter.curTurnW))) {
			LogicCenter.botplays();
		}

	}

	/**
	 * save the current state of the board and its pieces to the database
	 */
	public void saveGameActionPerformed(java.awt.event.ActionEvent evt) {

		//if the user wants to save the game, call the main menu JFrame and close this JFrame
		if (LogicHelp.saveGame()) {
			MainMenu swap = new MainMenu();
			swap.setVisible(true);
			swap.setResizable(false);
			dispose();

		}
	}

}
//Coded by Brent Butkow
