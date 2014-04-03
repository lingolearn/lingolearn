/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.shared;

import java.util.List;

/**
 * @author YPolyanskyy
 * 
 * This class contains statistics regarding user's interaction with the flashcard
 */
public class QuizResponse {

	private String 	sessionId,		// Session id
					gplusId,		// The user's google ID	
					cardId,			// Id of the card, shown to the user
					confuserType;	// The type of confuser that was used
	private boolean isCorrect,		// Holds status if the user's provided answer was correct
					isChanged;		// Holds status if the user was not sure and changed answer one or more times 
	private float	timeToAnswer; 	// Time, user spent answering this question
	

}
