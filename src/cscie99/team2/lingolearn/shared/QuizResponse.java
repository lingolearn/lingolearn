/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.shared;

import java.io.Serializable;

/**
 * @author YPolyanskyy
 * 
 * This class contains statistics regarding user's interaction with the flashcard
 */
public class QuizResponse implements Serializable {


	private static final long serialVersionUID = 3269082972556822708L;

	private Long 	sessionId;		// Session id
	private String	gplusId,		// The user's google ID	
					cardId,			// Id of the card, shown to the user
					confuserType;	// The type of confuser that was used
	private boolean isCorrect,		// Holds status if the user's provided answer was correct
					isChanged;		// Holds status if the user was not sure and changed answer one or more times 
	private float	timeToAnswer; 	// Time, user spent answering this question
	
	public QuizResponse () {};
	
	public QuizResponse(Long sessionId, String gplusId, String cardId,
			String confuserType, boolean isCorrect, boolean isChanged,
			float timeToAnswer) {
		this.sessionId = sessionId;
		this.gplusId = gplusId;
		this.cardId = cardId;
		this.confuserType = confuserType;
		this.isCorrect = isCorrect;
		this.isChanged = isChanged;
		this.timeToAnswer = timeToAnswer;
	}
	
	public Long getSessionId() {
		return sessionId;
	}
	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}
	public String getGplusId() {
		return gplusId;
	}
	public void setGplusId(String gplusId) {
		this.gplusId = gplusId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getConfuserType() {
		return confuserType;
	}
	public void setConfuserType(String confuserType) {
		this.confuserType = confuserType;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	public boolean isChanged() {
		return isChanged;
	}
	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}
	public float getTimeToAnswer() {
		return timeToAnswer;
	}
	public void setTimeToAnswer(float timeToAnswer) {
		this.timeToAnswer = timeToAnswer;
	}
	

}
