package cscie99.team2.lingolearn.shared;

public class FlashCardResponse {
	
	private String 	sessionId,		// Session id
					gplusId,		// The user's google ID	
					cardId,			// Id of the card, shown to the user
					confuserType;	// The type of confuser that was used
	private int     sessionCardNo,	// The nth time the user has seen that card during the session
					responseNo;		// The nth time the user has guessed during that card viewing
	private boolean isDropped;		// Holds status if the user dropped this card
	private float	timeToAnswer; 	// Time, user spent answering this question
	private Assessment assessment;	// The user's assessment of the flashcard.

}
