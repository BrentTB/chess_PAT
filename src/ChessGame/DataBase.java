//Coded by Brent Butkow

package ChessGame;

import java.sql.*;

/**
 *
 * @author brentbutkow
 */
public class DataBase {

	//These are needed to connect to the database from my program
	public static Connection conn;

	public static Statement state;

	/**
	 * connect to the database
	 */
	public DataBase() {
		try {

			String database = "PAT 2020 CHESS.accdb";//the name of the database
			conn = DriverManager.getConnection("jdbc:ucanaccess://" + database);

			state = conn.createStatement();
		} catch (SQLException ex) {

			System.out.println("Error: " + ex);//error message

		}

	}

	/**
	 * runs a query for queries that have an output
	 *
	 * @param quer the select query to be executed in the database
	 * @return the result of said query
	 */
	public String[] selectQuery(String quer) {

		try {
			//run the query twice
			ResultSet rs = state.executeQuery(quer);	//hold the results of the query to return them
			ResultSet help = state.executeQuery(quer);	//hold the results of the query to count them
			int counter = 0;

			//find how many results to the query there were
			while (help.next()) {
				counter++;
			}

			String[] results = new String[counter];//to get the results of the query
			counter = 0;//the current element in the array

			//iterate through every results of the query
			while (rs.next()) {
				results[counter] = rs.getString(1);
				counter++;
			}
			return results;
		} catch (SQLException ex) {

			System.out.println("Error: " + ex);//error message
		}
		return null;
	}

	/**
	 * runs a query for queries that change the database
	 *
	 * @param quer the update, delete or insert query to be executed in the
	 * database
	 */
	public void changeQuery(String quer) {

		try {
			//run the query
			state.executeUpdate(quer);
		} catch (SQLException ex) {

			System.out.println("Error: " + ex);//error message

		}
	}

}
//Coded by Brent Butkow
