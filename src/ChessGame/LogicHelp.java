//Coded by Brent Butkow

package ChessGame;

import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author brentbutkow
 */
public class LogicHelp {

	public static ChessGame startGame;			//used to make a chessgame JFrame

	public static DataBase DB = new DataBase();	//to interact with the database

	/**
	 * check to see if the entered username and password exists in the database,
	 * and return an error message if not
	 *
	 * @param username1 the username entered by the user
	 * @param password1 the password entered by the user
	 * @return if there is an error, and if so, the error message
	 */
	public static String signIn(String username1, String password1) {

		String[] storedUsernames = DB.selectQuery("SELECT Username FROM Users"); //all the usernames stored in the program's database
		String[] storedPasswords = DB.selectQuery("SELECT Password FROM Users"); //all the passwords stored in the program's database

		boolean usernameUsed = false;	//stores whether the username entered exists in the database
		boolean correct = false;		//stores whether the password entered corresponds to the username entered

		//iterate through all the usernames and passwords in the database
		for (int a = 0; a < storedPasswords.length; a++) {

			if (signIn.inGame) {
				signIn.encryptUser2 = storedUsernames[a];
			} else {
				signIn.encryptUser1 = storedUsernames[a];
			}

			//unencrypting the usernames and passwords that are stored in the database
			storedUsernames[a] = encrypt.decryptString(storedUsernames[a]);
			storedPasswords[a] = encrypt.decryptString(storedPasswords[a]);

			//if the username and password match elements in the database
			if (storedUsernames[a].equals(username1)) {

				usernameUsed = true;

				if (storedPasswords[a].equals(password1)) {

					correct = true;

					break;
				}

			}

		}

		/**
		 * if the values entered are correct
		 */
		if (correct) {

			//if the user whose values were entered is not already logged in
			//(needed for when 2 players are playing multiplayer)
			if (!signIn.user.equals(username1)) {

				if (!signIn.inGame) {//if this sign in is for the first user signing into the program

					signIn.user = username1;

					//call the main menu JFrame
					MainMenu help = new MainMenu();
					help.setResizable(false);
					help.setVisible(true);
					signIn.inGame = true;

				} else {//if this sign in is for the second user in a multiplayer game

					LogicCenter.curTurnW = true;

					//call the chessboard with the current user as black or white
					//based on what was selected earlier in the code
					if (MainMenu.playingwhite) {

						LogicHelp.startGame(signIn.user, username1);

					} else {

						LogicHelp.startGame(username1, signIn.user);

					}

				}

				return "close";

			} else {//if the username entered is already logged in

				return "That account is already playing";
			}

		} else if (!usernameUsed) {//if the username eneterd is not in the database

			return "That username does not exist";

		} else {//if the password entered does not correspond with the username entered

			return "The password is incorrect";
		}
	}

	/**
	 * check to see if the entered details fit the criteria, and that the
	 * username is unique, and return an error message if not
	 *
	 * @param name the user's name
	 * @param surname the user's surname
	 * @param username the user's username
	 * @param password the user's password
	 * @param confirmSPassword to check the user's password was inputted correct
	 * @param email the user's email
	 * @return if there is an error, and if so, the error message
	 */
	public static String signUp(String name, String surname, String username, String password, String confirmSPassword, String email) {

		int score = 1000;	//the score of a new user

		String[] storedUsernames = DB.selectQuery("SELECT Username FROM Users"); //all the usernames stored in the program's database

		boolean numCheck = false;		//stores whether the password has a number in it
		boolean specialCheck = false;	//stores whether the password has a special character in it

		/**
		 * find if the password contains at least one number and special
		 * character
		 */
		//iterate through the whole password
		for (int a = 0; a < password.length(); a++) {

			if (Character.isDigit(password.charAt(a))) {
				numCheck = true;
			} else if (!Character.isLetterOrDigit(password.charAt(a))) {
				specialCheck = true;
			}

		}

		/**
		 * find if the name entered has a number in it
		 */
		boolean numCheckName = false; //if there is a number in the entered name

		//iterate through the whole name
		for (int a = 0; a < name.length(); a++) {
			if (Character.isDigit(name.charAt(a))) {
				numCheckName = true;
			}
		}

		/**
		 * find if the surname entered has a number in it
		 */
		boolean numCheckSurname = false; //if there is a number in the entered surname

		//iterate through the whole surname
		for (int a = 0; a < surname.length(); a++) {
			if (Character.isDigit(surname.charAt(a))) {
				numCheckSurname = true;
			}
		}

		/**
		 * check if the entered values are suitable
		 */
		//BASIC NAME AND SURNAME CHECK
		if (name.length() < 2) {//if the name entered is too short

			return "  Make sure that to enter your name";

		} else if (numCheckName) {//if the name entered has a number in it

			return "  Names don't have numbers in them";

		} else if (surname.length() < 2) {//if the surname entered is too short

			return "  Make sure to enter your surname";

		} else if (numCheckSurname) {//if the surname entered has a number in it

			return "  Surnames don't have numbers in them";

		} // USERNAME CHECK
		else if (username.length() < 5) {//if the username entered is too short

			return "  Make sure that your username has 5 or more digits";

		} else if (username.length() > 20) {//if the username entered is too long

			return "  Make sure that your username has 20 or less digits";

		} else if (username.contains(" ")) {//if the username contains a whitespace in it

			return "  Make sure that your username does not have a space";

		} // PASSWORD CHECK
		else if (password.length() < 8) {//if the password entered is too short and thus unsecure

			return "  Make sure that your password has eight or more characters";

		} else if (!numCheck) {//if the password entered has no numbers and is thus unsecure

			return "  Make sure that your password at least one number";

		} else if (!specialCheck) {//if the password entered has no special characters and is thus unsecure

			return "  Make sure that your password has at least one special character";

		} else if (!(password.equals(confirmSPassword))) {//if the two passwords entered are not the same

			return "  Make sure that both passwords entered are the same";

		} //Email check
		else if (!email.contains("@")) {//if the email entered does not contain an @ symbol and is thus not valid

			return "  Make sure to input your correct email, all emails contain '@'";

		} else if (!email.contains(".")) {//if the email entered does not contain a period and is thus not valid

			return "  Make sure to input your correct email, all emails contain a full stop";

		} else {//if there were no values that had errors

			username = encrypt.encrypString(username);//encrypt the username to compare with the values stores in the database

			/**
			 * iterate through the used usernames and see if the username
			 * entered is unique
			 */
			boolean usernameUsed = false;

			//iterate through all the usernames in the database
			for (String storedUsername : storedUsernames) {
				if (storedUsername.equalsIgnoreCase(username)) {
					usernameUsed = true;
				}
			}

			if (!usernameUsed) {//if the username entered is new and thus valid

				//encrypt and then write the new user into the database
				name = encrypt.encrypString(name);
				surname = encrypt.encrypString(surname);
				password = encrypt.encrypString(password);
				email = encrypt.encrypString(email);
				String Query = "INSERT INTO Users (Username, FirstName, Surname, Password, Email, Score)"
						+ "VALUES (\"" + username + "\",\"" + name + "\",\"" + surname + "\",\"" + password + "\",\"" + email + "\"," + score + ")";

				DB.changeQuery(Query);

				//call the sign in JFrame and close this JFrame
				return "close";

			} else {//if the username has already been used

				return "  I'm sorry, that username is already in use";
			}
		}

	}

	/**
	 * read in the help menu and the saved games the user is playing
	 *
	 * @return all the games that the user has saved and that the user can
	 * continue playing
	 */
	public static String[] MainMenu() {

		try {

			//get the details of the current user
			MainMenu.FirstName = DB.selectQuery("SELECT FirstName FROM Users WHERE Username = \"" + signIn.encryptUser1 + "\"")[0];
			MainMenu.Surname = DB.selectQuery("SELECT Surname FROM Users WHERE Username = \"" + signIn.encryptUser1 + "\"")[0];
			MainMenu.Email = DB.selectQuery("SELECT Email FROM Users WHERE Username = \"" + signIn.encryptUser1 + "\"")[0];

			//the txt file comtaining the help menu and instructions
			Scanner temp = new Scanner(new File("help.txt"));

			boolean onInstructions = false;	//to seporate the help from the instructions in the txt file

			MainMenu.instructionsMenu = "";
			MainMenu.helpMenu = "";

			//iterate through every line in the txt file
			while (temp.hasNextLine()) {

				String help = temp.nextLine();

				//when the iterations get to the chess rules, start savings the text in the instructions, not helpMenu
				if (onInstructions || help.equals("CHESS RULES:")) {

					MainMenu.instructionsMenu += help + "\n";
					onInstructions = true;

				} else {

					MainMenu.helpMenu += help + "\n";
				}

			}

			//get the data of every saved game from the database
			MainMenu.gameNums = DB.selectQuery("SELECT GameNumber FROM GamePlayers");
			MainMenu.userName1 = DB.selectQuery("SELECT Username1 FROM GamePlayers");
			MainMenu.userName2 = DB.selectQuery("SELECT Username2 FROM GamePlayers");
			MainMenu.whoTurn = DB.selectQuery("SELECT WhoTurn FROM GamePlayers");

			//decrypt all the usernames from the database
			for (int i = 0; i < MainMenu.userName1.length; i++) {
				MainMenu.userName1[i] = encrypt.decryptString(MainMenu.userName1[i]);
				MainMenu.userName2[i] = encrypt.decryptString(MainMenu.userName2[i]);

			}

			MainMenu.validNums = new int[MainMenu.gameNums.length];
			MainMenu.place = new int[MainMenu.gameNums.length];
			MainMenu.counter = 0;

			//iterate through every saved game
			for (int i = 0; i < MainMenu.gameNums.length; i++) {

				//if the current user is playing in the saved game, add that game to the array for the current user
				if (MainMenu.userName1[i].equals(signIn.user) || MainMenu.userName2[i].equals(signIn.user)) {

					MainMenu.validNums[MainMenu.counter] = Integer.parseInt(MainMenu.gameNums[i]);
					MainMenu.place[MainMenu.counter] = i;
					MainMenu.counter++;
				}
			}

			String[] gameShower = new String[MainMenu.counter];

			//add each game that the user is currently playing, to a string
			for (int i = 0; i < MainMenu.counter; i++) {

				if (MainMenu.userName1[MainMenu.place[i]].equals(signIn.user)) { //if the user is  playingwhite
					switch (MainMenu.userName2[MainMenu.place[i]]) {
						case "E":
							//if the user is playing against an easy bot

							gameShower[i] = (i + 1) + ") White vs easy bot";
							break;
						case "H":
							//if the user is playing against a hard bot

							gameShower[i] = (i + 1) + ") White vs hard bot";
							break;
						default:
							//if the the user is playing against another user

							gameShower[i] = (i + 1) + ") White vs " + MainMenu.userName2[MainMenu.place[i]];
							break;
					}

				} else { //if the user is playing black
					switch (MainMenu.userName1[MainMenu.place[i]]) {
						case "E":
							//if the user is playing against an easy bot

							gameShower[i] = (i + 1) + ") Black vs easy bot";
							break;
						case "H":
							//if the user is playing against a hard bot

							gameShower[i] = (i + 1) + ") Black vs hard bot";
							break;
						default:
							//if the the user is playing against another user

							gameShower[i] = (i + 1) + ") Black vs " + MainMenu.userName1[MainMenu.place[i]];
							break;
					}

				}

			}
			return gameShower;

		} catch (FileNotFoundException ex) {
			System.out.println("Error: " + ex);//error message
		}
		return null;
	}

	/**
	 * go through the database to get every users score, sort the score from
	 * biggest to smallest and save the top 50 user's and their scores
	 *
	 * @return the String to be displayed as the leaderboard
	 */
	public static String showLeaderboard() {

		String leaderboard = "";	//the String to be displayed as the leaderboard

		String[] usernames = DB.selectQuery("SELECT Username FROM Users");	//get the usernames of every user from the database
		String[] scores = DB.selectQuery("SELECT Score FROM Users");		//get the score of every user from the database

		String helpUser, helpScore;	//temporary variables to help swap elements in the arrays declared above

		//iterate through every user's score - bubble sort
		for (int i = 0; i < scores.length; i++) {
			for (int j = 0; j < scores.length; j++) {

				if (Integer.parseInt(scores[i]) > Integer.parseInt(scores[j])) {//if the scores' order is wrong, change the order

					//swap the usernames in the array
					helpUser = usernames[i];
					usernames[i] = usernames[j];
					usernames[j] = helpUser;

					//swap the corresponding scores in the array
					helpScore = scores[i];
					scores[i] = scores[j];
					scores[j] = helpScore;

				}

			}

		}

		int length = scores.length <= 50 ? scores.length : 50;//only show the top 50 scores, if there are more than fifty

		//iterate 'length' times through the users' scores
		for (int i = 0; i < length; i++) {
			usernames[i] = encrypt.decryptString(usernames[i]);

			//display the user, their score, and their position relative to the other users
			leaderboard += (i + 1) + ") - " + scores[i] + " points\t              " + usernames[i] + "\n";
		}

		return leaderboard;

	}

	/**
	 * check to see if the user's details entered fit the criteria, and if so,
	 * change the details in the database
	 *
	 * @param name //the user's new name
	 * @param surname //the user's new name
	 * @param password //the user's new name
	 * @param confirmPassword1 to check the user's password was inputted correct
	 * @param email //the user's new name
	 * @return if there is an error, the error message; otherwise return the
	 * current details of the user
	 */
	public static String changeValues(String name, String surname, String password, String confirmPassword1, String email) {

		boolean numCheck = false;       //if the password has a number in it
		boolean specialCheck = false;   //if the password has a special character in it

		//if nothing has been entered
		if ("".equals(name) && "".equals(surname) && "".equals(password) && "".equals(confirmPassword1) && "".equals(email)) {

			return "  You have not selected to change anything";

		} else {

			//Checking the Password field
			if (!password.equals("") || !confirmPassword1.equals("")) {//if the user is trying to change their password

				//iterate through evevry char of the entered password
				for (int a = 0; a < password.length(); a++) {//password

					if (Character.isDigit(password.charAt(a))) {//checking if the char is a number

						numCheck = true;

					} else if (!Character.isLetterOrDigit(password.charAt(a))) {//checking if the char is a special character

						specialCheck = true;

					}

				}

				if (password.length() < 8) {//if the password entered is too short

					return "  Make sure that your password has eight or more characters";

				} else if (!numCheck) {//if the password entered has no numbers

					return "  Make sure that your password at least one number";

				} else if (!specialCheck) {//if the password entered has no special characters

					return "  Make sure that your password has at least one special character";

				} else if (!(password.equals(confirmPassword1))) {//if the password entered does not equal the confirm password field

					return "  Make sure that both passwords entered are the same";
				}

			}

			//Checking the Name field
			if (!name.equals("")) {//if the user is trying to change their saved name

				boolean numCheckName = false;//if the name entered has a number in it

				//iterate through evevry char of the entered name
				for (int a = 0; a < name.length(); a++) {

					if (Character.isDigit(name.charAt(a))) {

						numCheckName = true;

					}

				}

				if (name.length() < 2) {//if the name entered is too short

					return "  Make sure that to enter your name";

				} else if (numCheckName) {//if the name entered has a number in it

					return "  Names don't have numbers in them";
				}

			}

			//Checking the Surname field
			if (!surname.equals("")) {//if the user is trying to change their saved surname

				boolean numCheckSurname = false;//if the surname entered has a number in it

				//iterate through evevry char of the entered surname
				for (int a = 0; a < surname.length(); a++) {

					if (Character.isDigit(surname.charAt(a))) {

						numCheckSurname = true;

					}

				}

				if (surname.length() < 2) {//if the surname entered is too short

					return "  Make sure to enter your surname";

				} else if (numCheckSurname) {//if the surname entered has a number in it

					return "  Surnames don't have numbers in them";
				}

			}

			//Checking the Email field
			if (!email.equals("")) {//if the user is trying to change their saved email

				if (!email.contains("@")) {//if the email entered does not contain an @ symbol and is thus not valid

					return "  Make sure to input your correct email, all emails contain '@'";

				} else if (!email.contains(".")) {//if the email entered does not contain a period and is thus not valid

					return "  Make sure to input your correct email, all emails contain a full stop";
				}
			}

			//If there were no errors in the user-entered data
			if (!name.equals("")) {//if the name field was changed, change the users name in the database

				name = encrypt.encrypString(name);
				MainMenu.FirstName = name;
				DB.changeQuery("UPDATE Users SET [FirstName] = '" + name + "' WHERE [Username] = '" + signIn.encryptUser1 + "'");
			}

			if (!password.equals("")) {//if the password field was changed, change the password name in the database
				password = encrypt.encrypString(password);
				DB.changeQuery("UPDATE Users SET [Password] = '" + password + "' WHERE [Username] = '" + signIn.encryptUser1 + "'");

			}

			if (!surname.equals("")) {//if the surname field was changed, change the users surname in the database

				surname = encrypt.encrypString(surname);
				MainMenu.Surname = surname;
				DB.changeQuery("UPDATE Users SET [Surname] = '" + surname + "' WHERE [Username] = '" + signIn.encryptUser1 + "'");

			}

			if (!email.equals("")) {//if the email field was changed, change the users email in the database

				email = encrypt.encrypString(email);
				MainMenu.Email = email;
				DB.changeQuery("UPDATE Users SET [Email] = '" + email + "' WHERE [Username] = '" + signIn.encryptUser1 + "'");

			}

			return "correctHere are your current details:\n"
					+ "\nName:      \t" + encrypt.decryptString(MainMenu.FirstName)
					+ "\nSurname:   \t" + encrypt.decryptString(MainMenu.Surname)
					+ "\nEmail:     \t" + encrypt.decryptString(MainMenu.Email);

		}
	}

	/**
	 * loads a game that the user is currently playing, and if the game is
	 * multiplayer, asks for the second users password
	 *
	 * @param temp the index of the selected element in the JComboBox
	 * @param place an array to convert the index of the game selector combo box
	 * into the array element
	 * @return if there is an error, and if so, the error message
	 */
	public static String startLoadedGame(int temp, int[] place) {

		//if there are no games that the user is currently playing
		if (MainMenu.counter == 0) {

			return "There are no games to open";

			//if the JComboBox is still on the first element which does not display a game
		} else if (temp == -1) {

			return "No Game has been selected";

		} else {

			//if both players are users
			if (MainMenu.userName1[place[temp]].length() != 1 && MainMenu.userName2[place[temp]].length() != 1) {

				//the username of the player not currently logged in
				String guest = MainMenu.userName2[place[temp]].equals(signIn.user) ? MainMenu.userName1[place[temp]] : MainMenu.userName2[place[temp]];

				String[] password = DB.selectQuery("SELECT Password FROM Users");	//the password of every user in the database
				String[] users = DB.selectQuery("SELECT Username FROM Users");		//the username of every user in the database

				int guestNum = 0;	//the element in the array of the second user

				//find the password of the guest user by comparing usernames
				for (int i = 0; i < password.length; i++) {

					signIn.encryptUser2 = users[i];

					//decrypt the usernames and passwords from the database
					users[i] = encrypt.decryptString(users[i]);
					password[i] = encrypt.decryptString(password[i]);

					if (users[i].equals(guest)) {
						guestNum = i;
						break;
					}
				}

				String enterPassword = JOptionPane.showInputDialog("Please enter the password for " + guest + "\n\nLeave this line blank to go back");

				//while the correct password has not been entered and the user does not want to go back
				while ((enterPassword != null && !enterPassword.equals("")) && !enterPassword.equals(password[guestNum])) {

					enterPassword = JOptionPane.showInputDialog("Please enter the password for " + guest + "\n\nLeave this line blank to go back");
				}

				//if the correct password was entered, the game can start
				if (enterPassword != null && !enterPassword.equals("")) {
					return "correct";
				}
				return "";

			}//if one player is a bot, start the game
			return "correct";
		}

	}

	/**
	 * call the chessboard UI, given the username as the difficulty of the bot
	 * if a bot is playing
	 *
	 * @param user1temp	the username of the first player
	 * @param user2temp	the username of the second player
	 */
	public static void startGame(String user1temp, String user2temp) {

		ChessGame.user1 = user1temp;
		ChessGame.user2 = user2temp;
		startGame = new ChessGame(user1temp, user2temp);
		startGame.setVisible(true);
		startGame.setResizable(false);

	}

	/**
	 * loads the pieces and their position from the database based on the game
	 * number, and if either player is a bot, set the Logic center variables
	 *
	 * @param user1temp the username of the user playing white
	 * @param user2temp the username of the user playing black
	 */
	public static void ChessGame(String user1temp, String user2temp) {

		//load the position and state of every piece based on the gamenumber
		int GameNum = MainMenu.LoadedGameNum;
		String[] PieceType = DB.selectQuery("SELECT [PieceType] FROM SavedGame WHERE GameNumber = " + GameNum);
		String[] PieceColour = DB.selectQuery("SELECT [PieceColour] FROM SavedGame WHERE GameNumber = " + GameNum);
		String[] Xposition = DB.selectQuery("SELECT [Xposition] FROM SavedGame WHERE GameNumber = " + GameNum);
		String[] Yposition = DB.selectQuery("SELECT [Yposition] FROM SavedGame WHERE GameNumber = " + GameNum);
		String[] HasMoved = DB.selectQuery("SELECT [HasMoved] FROM SavedGame WHERE GameNumber = " + GameNum);

		//set the user one and two in the chessgame class
		ChessGame.user1 = user1temp;
		ChessGame.user2 = user2temp;

		//giving the logic class the data it needs
		LogicCenter.counter = PieceType.length;

		if (user1temp.length() == 1) {//if player 1 is a bot

			LogicCenter.bot = true;
			LogicCenter.botColour = 'w';

		} else if (user2temp.length() == 1) {//if player 2 is a bot

			LogicCenter.bot = true;
			LogicCenter.botColour = 'b';

		} else {//if neither player is a bot

			LogicCenter.bot = false;
			LogicCenter.botColour = 'b';
		}

		//iterate through every piece to add them to the logic class
		for (int i = 0; i < PieceType.length; i++) {

			LogicCenter.game[i] = new Pieces(Integer.parseInt(Yposition[i]), Integer.parseInt(Xposition[i]),
					PieceColour[i].charAt(0), PieceType[i].charAt(0), (HasMoved[i].equalsIgnoreCase("true")));

		}

		LogicCenter.LogicStart();//start working the logic in the game
	}

	/**
	 * call the main menu JFrame and close the chessboard UI
	 */
	public static void endGame() {

		startGame.dispose(); //close the chessGame JFrame
		MainMenu help = new MainMenu();
		help.setResizable(false);
		help.setVisible(true);

	}

	/**
	 * saves the piece data from the current game into the database if the user
	 * wants to end the game
	 *
	 * @return whether the user ended the game or not
	 */
	public static boolean saveGame() {

		String exit = JOptionPane.showInputDialog("Are you sure you would like to save your game and exit?\n\nPlease type ( Y / N)");

		//validating the users exit request
		while (exit == null || exit.equals("") || (exit.toUpperCase().charAt(0) != 'Y' && exit.toUpperCase().charAt(0) != 'N')) {
			exit = JOptionPane.showInputDialog("Are you sure you would like to save your game and exit?\n\nPlease type ( Y / N)");

		}

		//if the user does want to exit the current game
		if (exit.toUpperCase().charAt(0) == 'Y') {

			//if there is already a save of the current game, delete the old save
			if (MainMenu.LoadedGameNum != 0) {
				DB.changeQuery("DELETE * FROM GamePlayers WHERE GameNumber = " + MainMenu.LoadedGameNum);
				DB.changeQuery("DELETE * FROM SavedGame WHERE GameNumber = '" + MainMenu.LoadedGameNum + "'");

			}

			int counter = 0;//find a game number
			String[] checkGameNum;//used to see if there is a saved game with any specific game number

			//find the smallest game number that is not currently in use
			do {
				counter++;
				checkGameNum = DB.selectQuery("SELECT * FROM GamePlayers WHERE GameNumber=" + counter);
			} while (checkGameNum.length != 0);

			//set the game number to a non used number found above
			MainMenu.LoadedGameNum = counter;

			//enter the data of every piece of the current game into the database
			for (int i = 0; i < LogicCenter.counter; i++) {
				DB.changeQuery(LogicCenter.game[i].toString());
			}

			char whoTurn;
			String values;

			if (LogicCenter.bot) {//if it is a singleplayer game

				whoTurn = LogicCenter.botColour == 'w' ? 'b' : 'w';

			} else {//if it is a multiplayer game

				whoTurn = LogicCenter.curTurnW ? 'w' : 'b';//save who evers turn it is
			}

			//store the user data in the GamePlayers table in the database
			values = "VALUES (\"" + MainMenu.LoadedGameNum + "\",\"" + encrypt.encrypString(ChessGame.user1) + "\",\"" + encrypt.encrypString(ChessGame.user2) + "\",\"" + whoTurn + "\")";

			//insert the current game into the list of games
			DB.changeQuery("INSERT INTO GamePlayers (GameNumber, Username1, Username2, WhoTurn)" + values);
			return true;
		}
		return false;
	}

}
//Coded by Brent Butkow
