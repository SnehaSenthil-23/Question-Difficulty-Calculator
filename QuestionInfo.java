package QuestionDetails;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

// This code initializes the database table, calculates the question difficulty and updates the question rating to the database table
public class QuestionInfo {

	static Connection con;
	static PreparedStatement statement;

	static int questionId;
	static long count;
	static List<QuestionEntity> questionInfoList;
	boolean checkForUpdation = false;
	long remainingQuestions = 0;

	/*
	 * This method initializes the database questionInfo by creating a
	 * connection with the oracle driver.
	 */
	public void initDB() {
		try {

			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "root");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/*
	 * This method returns the total count of questions.
	 */
	public long readQuestionCountFromDB() {

		count = 0;

		try {

			java.sql.Statement statement = con.createStatement();

			ResultSet rs = ((java.sql.Statement) statement).executeQuery("select count(*) as count from QuestionInfo");

			while (rs.next()) {
				count = rs.getLong("count");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return count;
	}

	/*
	 * This method reads the question parameters from the database for
	 * calculating question difficulty and stores it in a list
	 */
	public void readQuestionInfoFromDB(long startIndex, long endIndex) {

		try {

			java.sql.Statement statement = con.createStatement();

			/*
			 * This query retrieves the question information from the database
			 * stored in the range start index and end index.
			 */
			ResultSet rs = ((java.sql.Statement) statement).executeQuery(
					"select * from QuestionInfo where questionId between " + startIndex + " and " + (endIndex + 1));

			questionInfoList = new ArrayList<QuestionEntity>();

			while (rs.next()) {

				QuestionEntity questionInfo = new QuestionEntity();

				questionInfo.questionId = rs.getLong("QuestionId");
				questionInfo.questionType = rs.getString("QuestionType");
				questionInfo.questionAssignedDifficulty = rs.getString("QuestionAssignedDifficulty");

				questionInfo.studentsAttendedInfo = new StudentsInfoEntity();

				// Read question parameters from the database
				questionInfo.studentsAttendedInfo.timeTaken = rs.getLong("TimeTaken");
				questionInfo.studentsAttendedInfo.answerChangedCount = rs.getLong("AnswerChangedCount");
				questionInfo.studentsAttendedInfo.answerCompiledCount = rs.getLong("AnswerCompiledCount");
				questionInfo.studentsAttendedInfo.hintsCount = rs.getLong("HintsCount");
				questionInfo.studentsAttendedInfo.programmingLanguageUsed = rs.getString("ProgrammingLanguageUsed");
				questionInfo.feedbackRating = rs.getLong("FeedbackRating");
				questionInfo.noOfStudentsAnsweredCorrect = rs.getLong("NoOfStudentsAnsweredCorrect");
				questionInfo.noOfStudentsAnsweredWrong = rs.getLong("NoOfStudentsAnsweredWrong");
				questionInfo.noOfStudentsAnsweredPartial = rs.getLong("NoOfStudentsAnsweredPartial");
				questionInfo.maxMarks = rs.getInt("MaxMarks");

				// This statement adds the questionInformation to the list
				questionInfoList.add(questionInfo);
			}
		} catch (Exception e) {

			System.out.println(e);
		}
	}

	/*
	 * This method calculates question difficulty of a question with the
	 * retrieved parameters from the database and updates the database with the
	 * calculated difficulty rating (0-100) and the final result -
	 * Easy/Medium/Hard.
	 */
	public void calculateQuestionDifficulty() {

		try {

			/*
			 * This query is used to update the question rating and question
			 * difficulty for the given question id. Used a prepare statement
			 * for performance optimization
			 */
			statement = con.prepareStatement(
					"update QuestionInfo set QUESTIONRATING = ?, DIFFICULTY = ? where QUESTIONID = ?");

			questionId = 0;

			while (questionId < questionInfoList.size()) {

				QuestionDifficultyCalculator calculateQuestionDetails = new QuestionDifficultyCalculator();

				/*
				 * This statement calculates the question difficulty based on
				 * the parameters and adds up every parameter's value to give a
				 * result in range (1 - 100)
				 */
				int questionRating = (int) calculateQuestionDetails.calculateQuestionDifficulty();

				// This updates the calculated result to database for the given
				// Question id.
				updateQuestionRatingOnDB(QuestionInfo.questionInfoList.get(questionId).questionId + 1, questionRating);

				questionId++;
			}

			statement.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/*
	 * This method updates the question rating to the database for the given
	 * question id. The update is done in batches to increase the performance.
	 */
	public void updateQuestionRatingOnDB(long questionId, int questionRating) {

		try {
			String result = "M"; // Medium

			if (questionRating < 30) {

				result = "E"; // Easy
			} else {

				result = "H"; // Hard
			}

			statement.setInt(1, questionRating);
			statement.setString(2, result);
			statement.setLong(3, questionId);

			// Batch Processing to increase performance
			if (count - questionId <= 1000) {

				statement.executeUpdate();
			}

			if (questionId % 10000 == 0) {

				statement.executeBatch();
			}

			else if (count - questionId > 1000) {

				statement.addBatch();
			}

		} catch (Exception e) {

			System.out.println(e);

		}
	}
}
