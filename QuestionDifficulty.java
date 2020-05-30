import java.time.Instant;
import QuestionDetails.QuestionInfo;

public class QuestionDifficulty {

	public static void main(String[] args) throws Exception {
		
		// Set the batch size
		final long batchSize = 10000;

		QuestionInfo questionInfo = new QuestionInfo();

		// Initializes the oracle database connection to get the question details
		questionInfo.initDB();

		// Get the total question count
		long questionCount = questionInfo.readQuestionCountFromDB();

		System.out.println("Total Question Count = " + questionCount);

		long startIndex = 1;
		long endIndex = batchSize;
		
		// Handle if total number of questions is less than batch size
		if(batchSize > questionCount) {
			endIndex = questionCount;
		}
		
		long finalTimeStart = Instant.now().getEpochSecond();
		
		/*
		 * This process 10000 records in a batch mode and uses various algorithm
		 * to find the question difficulty rating
		 */
		while ((endIndex <= questionCount) && (startIndex <= questionCount)) {

			long timeStart = Instant.now().getEpochSecond();

			/*
			 * This fetches the question information from the database for the
			 * given range of question ids
			 */
			questionInfo.readQuestionInfoFromDB(startIndex, endIndex);
			
			/*
			 * This is the core function which calculates the question
			 * difficulty
			 */
			questionInfo.calculateQuestionDifficulty();

			long timeEnd = Instant.now().getEpochSecond();

			// Print the performance metrics for each batch
			System.out.println("Record Start Index : " + startIndex);
			System.out.println("Record End Index : " + endIndex);

			System.out.println("Time Start (s) : " + timeStart);
			System.out.println("Time End (s) : " + timeEnd);
			System.out.println("Time Elapsed (s) : " + (timeEnd - timeStart));

			startIndex = endIndex + 1;
			
			endIndex += batchSize;
			
			// Handle if the batch exceeds the total questions count
			if (endIndex > questionCount) {
				endIndex = questionCount;
			}

		}

		long finalTimeEnd = Instant.now().getEpochSecond();

		// Print the overall performance metrics
		System.out.println("Final Time Start (s) : " + finalTimeStart);
		System.out.println("Final Time End (s) : " + finalTimeEnd);
		System.out.println("Final Time Elapsed (s) : " + (finalTimeEnd - finalTimeStart));
	}
}