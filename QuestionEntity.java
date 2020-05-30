package QuestionDetails;

// This sub entity contains student information specific parameters
class StudentsInfoEntity {

	long timeTaken;
	long answerChangedCount;
	long answerCompiledCount;
	long hintsCount;
	String programmingLanguageUsed;

}

//This is the main entity which contains all the question parameters
public class QuestionEntity {

	long questionId;
	String questionType;
	String questionAssignedDifficulty;

	StudentsInfoEntity studentsAttendedInfo;

	long feedbackRating;
	long noOfStudentsAnsweredCorrect;
	long noOfStudentsAnsweredWrong;
	long noOfStudentsAnsweredPartial;
	int maxMarks;
}
