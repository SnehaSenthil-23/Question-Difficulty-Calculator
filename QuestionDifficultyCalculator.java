package QuestionDetails;

// This code is used to calculate question difficulty 

public class QuestionDifficultyCalculator {

	// Assigned maximum weightage for question parameters which totals to 100
	final int RATIO_QUESTIONTYPE = 10;
	final int RATIO_ASSIGNEDdifficulty = 10;
	final int RATIO_TOTALNUMBEROFSTUDENTS = 5;
	final int RATIO_TIMETAKEN = 5;
	final int RATIO_ANSWERCHANGEDCOUNT = 5;
	final int RATIO_ANSWERCOMPILEDCOUNT = 5;
	final int RATIO_HINTSCOUNT = 5;
	final int RATIO_PROGRAMMINGLANGUAGEUSED = 5;
	final int RATIO_FEEDBACKRATING = 10;
	final int RATIO_NOOFSTUDENTSANSWEREDCORRECT = 10;
	final int RATIO_NOOFSTUDENTSANSWEREDWRONG = 10;
	final int RATIO_NOOFSTUDENTSANSWEREDPARTIAL = 10;
	final int RATIO_MAXMARKS = 10;

	// Constants for Question Types
	final String QUESTIONTYPE_MCQ = "M";
	final String QUESTIONTYPE_FILLUP = "F";
	final String QUESTIONTYPE_PROGRAMMING = "P";
	final String QUESTIONTYPE_MATCH_THE_FOLLOWING = "O";

	// Constants for Questions Assigned difficulty
	final String QUESTIONASSIGNEDdifficulty_EASY = "E";
	final String QUESTIONASSIGNEDdifficulty_MEDIUM = "M";
	final String QUESTIONASSIGNEDdifficulty_HARD = "H";

	// Constants for the Programming Languages
	final String PROGRAMMINGLANGUAGEUSED_C = "C";
	final String PROGRAMMINGLANGUAGEUSED_CPP = "C++";
	final String PROGRAMMINGLANGUAGEUSED_JAVA = "JAVA";

	// Get the total number of students
	long totalNumberOfStudents = QuestionInfo.questionInfoList.get(QuestionInfo.questionId).noOfStudentsAnsweredWrong
			+ QuestionInfo.questionInfoList.get(QuestionInfo.questionId).noOfStudentsAnsweredCorrect
			+ QuestionInfo.questionInfoList.get(QuestionInfo.questionId).noOfStudentsAnsweredPartial;

	/*
	 * This method contains the algorithm to calculate question difficulty
	 * rating based on question type, manually assigned difficulty, answer
	 * changed count (if it is an mcq question), answer compiled count (if it is
	 * a programming question), programming language used (if it is a
	 * programming question), feedback rating given by each student to a
	 * question, total number of students who answered the question right, total
	 * number of students who answered the question wrong, total number of
	 * students who answered the question partial, maximum marks allocated to a
	 * question. Returns a difficulty rating(Range: 1-100)
	 */

	double calculateQuestionDifficulty() {

		double questionRating = 0;

		questionRating += calculateQuestionType();
		questionRating += calculateAssignedDifficulty();
		questionRating += calculateTimeTaken();
		questionRating += calculateTotalNumberOfStudents();

		/*
		 * If it is an mcq question, the question difficulty is calculated based
		 * on the answer changed count
		 */
		if (QuestionInfo.questionInfoList.get(QuestionInfo.questionId).questionType.equals(QUESTIONTYPE_MCQ)) {

			questionRating += calculateAnswerChangedCount();
		}

		/*
		 * If it is a programming question, the question difficulty is
		 * calculated based on the total compilation count and the programming
		 * language that is used
		 */
		else if (QuestionInfo.questionInfoList.get(QuestionInfo.questionId).questionType
				.equals(QUESTIONTYPE_PROGRAMMING)) {

			questionRating += calculateAnswerCompiledCount();
			questionRating += calculateProgrammingLanguageUsed();
		}

		questionRating += calculateTotalNumberOfStudents();
		questionRating += calculateFeedbackRating();
		questionRating += calculateNoOfStudentsAnsweredCorrect();
		questionRating += calculateNoOfStudentsAnsweredWrong();
		questionRating += calculateNoOfStudentsAnsweredPartial();
		questionRating += calculateMaxMarks();

		return questionRating;
	}

	/*
	 * This method contains the algorithm to calculate question difficulty
	 * rating based on the question type. 
	 * Returns the difficulty rating (Range:0-1)
	 * Assuming no other type of question the database contains.
	 */
	double calculateQuestionType() {

		double difficulty = 0;

		if (QuestionInfo.questionInfoList.get(QuestionInfo.questionId).questionType.equals(QUESTIONTYPE_MCQ)) {

			difficulty = 0.5;

		} else if (QuestionInfo.questionInfoList.get(QuestionInfo.questionId).questionType
				.equals(QUESTIONTYPE_FILLUP)) {

			difficulty = 0.75;

		} else if (QuestionInfo.questionInfoList.get(QuestionInfo.questionId).questionType
				.equals(QUESTIONTYPE_PROGRAMMING)) {

			difficulty = 1.0;

		} else if (QuestionInfo.questionInfoList.get(QuestionInfo.questionId).questionType
				.equals(QUESTIONTYPE_MATCH_THE_FOLLOWING)) {

			difficulty = 0.3;

		}

		return difficulty * RATIO_QUESTIONTYPE;
	}

	/*
	 * This method contains the algorithm to calculate question difficulty
	 * rating based on the manual assigned difficulty to a question. 
	 * Returns the difficulty rating (Range: 0-1)
	 */
	double calculateAssignedDifficulty() {

		double difficulty = 0;

		if (QuestionInfo.questionInfoList.get(QuestionInfo.questionId).questionAssignedDifficulty
				.equals(QUESTIONASSIGNEDdifficulty_EASY)) {

			difficulty = 0.15;

		} else if (QuestionInfo.questionInfoList.get(QuestionInfo.questionId).questionAssignedDifficulty
				.equals(QUESTIONASSIGNEDdifficulty_MEDIUM)) {

			difficulty = 0.55;

		} else if (QuestionInfo.questionInfoList.get(QuestionInfo.questionId).questionAssignedDifficulty
				.equals(QUESTIONASSIGNEDdifficulty_HARD)) {

			difficulty = 0.85;
		}

		return difficulty * RATIO_ASSIGNEDdifficulty;
	}

	/*
	 * This method contains the algorithm to calculate question difficulty
	 * rating based on total time taken 
	 * Returns the difficulty rating (Range:0-1)
	 */
	double calculateTimeTaken() {

		double difficulty = 0.2;

		// timeTaken is calculated for minutes
		double timeTakenRatio = QuestionInfo.questionInfoList
				.get(QuestionInfo.questionId).studentsAttendedInfo.timeTaken / totalNumberOfStudents;

		if (timeTakenRatio > 8 && timeTakenRatio <= 10
				&& (!QuestionInfo.questionInfoList.get(QuestionInfo.questionId).questionType
						.equals(QUESTIONTYPE_PROGRAMMING))) {

			difficulty = 1.0;

		} else if (timeTakenRatio > 6 && timeTakenRatio <= 8
				&& (!QuestionInfo.questionInfoList.get(QuestionInfo.questionId).questionType
						.equals(QUESTIONTYPE_PROGRAMMING))) {

			difficulty = 0.8;

		} else if (timeTakenRatio > 4 && timeTakenRatio <= 2
				&& (!QuestionInfo.questionInfoList.get(QuestionInfo.questionId).questionType
						.equals(QUESTIONTYPE_PROGRAMMING))) {

			difficulty = 0.6;

		} else if (timeTakenRatio > 2 && timeTakenRatio <= 1
				&& (!QuestionInfo.questionInfoList.get(QuestionInfo.questionId).questionType
						.equals(QUESTIONTYPE_PROGRAMMING))) {

			difficulty = 0.4;
		}

		return difficulty * RATIO_TIMETAKEN;
	}

	/*
	 * This method contains the algorithm to calculate question difficulty
	 * rating based on the answer changed count 
	 * Returns the difficulty rating (Range: 0-1) 
	 * Assuming that the answer change count is allowed for only 10
	 * times by a person.
	 */
	double calculateAnswerChangedCount() {

		double difficulty = 0.2;

		double AnswerChangedRatio = QuestionInfo.questionInfoList
				.get(QuestionInfo.questionId).studentsAttendedInfo.answerChangedCount / totalNumberOfStudents;

		if (AnswerChangedRatio >= 8 && AnswerChangedRatio < 10) {

			difficulty = 1.0;

		} else if (AnswerChangedRatio >= 6 && AnswerChangedRatio < 8) {

			difficulty = 0.8;

		} else if (AnswerChangedRatio >= 4 && AnswerChangedRatio < 2) {

			difficulty = 0.6;

		} else if (AnswerChangedRatio >= 2 && AnswerChangedRatio < 1) {

			difficulty = 0.4;
		}

		return difficulty * RATIO_ANSWERCHANGEDCOUNT;
	}

	/*
	 * This method contains the algorithm to calculate question difficulty
	 * rating based on the answer compiled count Returns the difficulty rating
	 * (Range: 0-1) Assuming that the compilation count is allowed for only 10
	 * times at maximum for a student
	 */
	double calculateAnswerCompiledCount() {

		double difficulty = 0.2;

		double AnswerCompiledRatio = QuestionInfo.questionInfoList
				.get(QuestionInfo.questionId).studentsAttendedInfo.answerCompiledCount / totalNumberOfStudents;

		if (AnswerCompiledRatio >= 8 && AnswerCompiledRatio < 10) {

			difficulty = 1.0;

		} else if (AnswerCompiledRatio >= 6 && AnswerCompiledRatio < 8) {

			difficulty = 0.8;

		} else if (AnswerCompiledRatio >= 4 && AnswerCompiledRatio < 6) {

			difficulty = 0.6;

		} else if (AnswerCompiledRatio >= 2 && AnswerCompiledRatio < 1) {

			difficulty = 0.4;
		}

		return difficulty * RATIO_ANSWERCOMPILEDCOUNT;
	}

	/*
	 * This method contains the algorithm to calculate question difficulty
	 * rating based on the count of hints usage 
	 * Returns the difficulty rating (Range: 0-1) 
	 * With one is the maximum number of hint for a question
	 */
	double calculateNumberOfHintsUsed() {

		double difficulty = 0.9;

		double hintsUsedRatio = QuestionInfo.questionInfoList
				.get(QuestionInfo.questionId).studentsAttendedInfo.hintsCount / totalNumberOfStudents;

		if (hintsUsedRatio == 0 && QuestionInfo.questionInfoList
				.get(QuestionInfo.questionId).noOfStudentsAnsweredCorrect == totalNumberOfStudents) {

			difficulty = 0;
		}

		else if (hintsUsedRatio == 1 && QuestionInfo.questionInfoList
				.get(QuestionInfo.questionId).noOfStudentsAnsweredCorrect == totalNumberOfStudents) {
			
			difficulty = 0.5;
		}
		
		else if (hintsUsedRatio >= 0.2 && hintsUsedRatio <= 0.4) {
			
			difficulty = 0.3;
			
		}
		
		else if (hintsUsedRatio >= 0.5 && hintsUsedRatio <= 0.7) {
			
			difficulty = 0.6;
			
		}

		return difficulty * RATIO_HINTSCOUNT;
	}

	/*
	 * This method contains the algorithm to calculate question difficulty
	 * rating based on the programming language used if it is a programming
	 * question Returns the difficulty rating (Range: 0-1)
	 */
	double calculateProgrammingLanguageUsed() {

		double difficulty = 0;

		if (QuestionInfo.questionInfoList.get(QuestionInfo.questionId).studentsAttendedInfo.programmingLanguageUsed
				.equals(PROGRAMMINGLANGUAGEUSED_C)) {

			difficulty = 0.4;

		} else if (QuestionInfo.questionInfoList
				.get(QuestionInfo.questionId).studentsAttendedInfo.programmingLanguageUsed
						.equals(PROGRAMMINGLANGUAGEUSED_CPP)) {

			difficulty = 0.8;

		} else if (QuestionInfo.questionInfoList
				.get(QuestionInfo.questionId).studentsAttendedInfo.programmingLanguageUsed
						.equals(PROGRAMMINGLANGUAGEUSED_JAVA)) {

			difficulty = 1.0;
		}

		return difficulty * RATIO_PROGRAMMINGLANGUAGEUSED;
	}

	/*
	 * This method calculates question difficulty rating based on the total
	 * count of students. The difficulty rating will be 1 when no one have
	 * attended the question. 
	 * Returns the difficulty rating.
	 */
	double calculateTotalNumberOfStudents() {

		double difficulty = 0.5;

		if (totalNumberOfStudents == 0)
			difficulty = 1.0;

		return difficulty * RATIO_TOTALNUMBEROFSTUDENTS;
	}

	/*
	 * This method contains the algorithm to calculate question difficulty
	 * rating based on the feedback given by students for a question Returns the
	 * difficulty rating (Range: 0-1)
	 * Where for feedback (1-5) is required.
	 * 1 represents easy and the difficulty increases with order.
	 */
	double calculateFeedbackRating() {

		double difficulty = 0;

		double feedbackRatingRatio = QuestionInfo.questionInfoList.get(QuestionInfo.questionId).feedbackRating
				/ totalNumberOfStudents;

		if (feedbackRatingRatio >= 1 && feedbackRatingRatio < 2) {

			difficulty = 0.2;

		} else if (feedbackRatingRatio >= 2 && feedbackRatingRatio < 3) {

			difficulty = 0.4;

		} else if (feedbackRatingRatio >= 3 && feedbackRatingRatio < 4) {

			difficulty = 0.6;

		} else if (feedbackRatingRatio >= 4 && feedbackRatingRatio < 5) {

			difficulty = 0.8;
		}

		return difficulty * RATIO_FEEDBACKRATING;
	}

	/*
	 * This method contains the algorithm to calculate question difficulty
	 * rating based on number of students who answered correct.
	 *  Returns the difficulty rating (Range: 0-1)
	 */
	double calculateNoOfStudentsAnsweredCorrect() {

		double difficulty = 0.2;

		double AnsweredCorrectRatio = QuestionInfo.questionInfoList
				.get(QuestionInfo.questionId).noOfStudentsAnsweredCorrect / totalNumberOfStudents;

		if (AnsweredCorrectRatio >= 0.1 && AnsweredCorrectRatio <= 0.2) {

			difficulty = 1.0;

		} else if (AnsweredCorrectRatio >= 0.21 && AnsweredCorrectRatio <= 0.4) {

			difficulty = 0.8;

		} else if (AnsweredCorrectRatio >= 0.41 && AnsweredCorrectRatio <= 0.6) {

			difficulty = 0.6;

		} else if (AnsweredCorrectRatio >= 0.61 && AnsweredCorrectRatio <= 0.8) {

			difficulty = 0.4;
		}

		return difficulty * RATIO_NOOFSTUDENTSANSWEREDCORRECT;
	}

	/*
	 * This method contains the algorithm to calculate question difficulty
	 * rating based on number of students who answered wrong Returns the
	 * difficulty rating (Range: 0-1)
	 */
	double calculateNoOfStudentsAnsweredWrong() {

		double difficulty = 1.0;

		double AnsweredWrongRatio = QuestionInfo.questionInfoList.get(QuestionInfo.questionId).noOfStudentsAnsweredWrong
				/ totalNumberOfStudents;

		if (AnsweredWrongRatio >= 0.1 && AnsweredWrongRatio <= 0.2) {

			difficulty = 0.2;

		} else if (AnsweredWrongRatio >= 0.21 && AnsweredWrongRatio <= 0.4) {

			difficulty = 0.4;

		} else if (AnsweredWrongRatio >= 0.41 && AnsweredWrongRatio <= 0.6) {

			difficulty = 0.6;

		} else if (AnsweredWrongRatio >= 0.61 && AnsweredWrongRatio <= 0.8) {

			difficulty = 0.8;
		}

		return difficulty * RATIO_NOOFSTUDENTSANSWEREDWRONG;
	}

	/*
	 * This method contains the algorithm to calculate question difficulty
	 * rating based on number of students who answered partial. Returns the
	 * difficulty rating (Range: 0-1)
	 */
	double calculateNoOfStudentsAnsweredPartial() {

		double difficulty = 0.8;

		double AnsweredPartialRatio = QuestionInfo.questionInfoList
				.get(QuestionInfo.questionId).noOfStudentsAnsweredPartial / totalNumberOfStudents;

		if (AnsweredPartialRatio >= 0.1 && AnsweredPartialRatio <= 0.2) {

			difficulty = 0.1;

		} else if (AnsweredPartialRatio >= 0.21 && AnsweredPartialRatio <= 0.4) {

			difficulty = 0.3;

		} else if (AnsweredPartialRatio >= 0.41 && AnsweredPartialRatio <= 0.6) {

			difficulty = 0.5;

		} else if (AnsweredPartialRatio >= 0.61 && AnsweredPartialRatio <= 0.8) {

			difficulty = 0.7;
		}

		return difficulty * RATIO_NOOFSTUDENTSANSWEREDPARTIAL;
	}

	/*
	 * This method contains the algorithm to calculate question difficulty
	 * rating based on the maximum marks allocated to a question. Returns the
	 * difficulty rating (Range: 0-1)
	 */
	double calculateMaxMarks() {

		double difficulty = 1.0;

		if (QuestionInfo.questionInfoList.get(QuestionInfo.questionId).maxMarks >= 1
				&& QuestionInfo.questionInfoList.get(QuestionInfo.questionId).maxMarks <= 20) {

			difficulty = 0.2;

		} else if (QuestionInfo.questionInfoList.get(QuestionInfo.questionId).maxMarks >= 21
				&& QuestionInfo.questionInfoList.get(QuestionInfo.questionId).maxMarks <= 40) {

			difficulty = 0.4;

		} else if (QuestionInfo.questionInfoList.get(QuestionInfo.questionId).maxMarks >= 41
				&& QuestionInfo.questionInfoList.get(QuestionInfo.questionId).maxMarks <= 60) {

			difficulty = 0.6;

		} else if (QuestionInfo.questionInfoList.get(QuestionInfo.questionId).maxMarks >= 61
				&& QuestionInfo.questionInfoList.get(QuestionInfo.questionId).maxMarks <= 80) {

			difficulty = 0.8;

		}

		return difficulty * RATIO_MAXMARKS;
	}
}