
// This is a data entry code snippet for inserting 1 million test records
// This will add question information entry to the database

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;;

class TestDataEntry {
	public static void main(String args[]) {
		try {

			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			/*
			 * This statement creates a connection with the oracle driver in
			 * order to access the database
			 */
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "root");

			PreparedStatement statement = con.prepareStatement(
					"insert into QuestionInfo(QUESTIONID, QUESTIONTYPE, QUESTIONASSIGNEDDIFFICULTY, TIMETAKEN, ANSWERCHANGEDCOUNT, "
							+ "ANSWERCOMPILEDCOUNT, HINTSCOUNT, PROGRAMMINGLANGUAGEUSED, FEEDBACKRATING, NOOFSTUDENTSANSWEREDCORRECT, "
							+ "NOOFSTUDENTSANSWEREDWRONG, NOOFSTUDENTSANSWEREDPARTIAL, MAXMARKS, QUESTIONRATING, DIFFICULTY)"
							+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			long index = 1;

			while (index <= 1000000) {

				// query execution is done for inserting million rows to a
				// database table
				statement.setLong(1, index);
				statement.setString(2, "M");
				statement.setString(3, "H");
				statement.setLong(4, 1000);
				statement.setLong(5, 50);
				statement.setLong(6, 0);
				statement.setLong(7, 90);
				statement.setString(8, "C");
				statement.setLong(9, 120);
				statement.setLong(10, 100000);
				statement.setLong(11, 400000);
				statement.setLong(12, 500000);
				statement.setInt(13, 100);
				statement.setInt(14, 0);
				statement.setString(15, "N");

				// Batch Processing to increase performance
				statement.addBatch();

				if (index % 1000 == 0) {
					statement.executeBatch();
					System.out.println(index);
				}

				index++;
			}

			statement.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
