/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import cscie99.team2.lingolearn.shared.Assessment;
import cscie99.team2.lingolearn.shared.FlashCardResponse;


/**
 * @author YPolyanskyy
 * 
 * This class represents Proxy for FlashCardResponse
 */
@Entity(name="ObjectifyableFlashCardResponse")
@Index
public class ObjectifyableFlashCardResponse implements Serializable {
	
	private static final long serialVersionUID = -3156440500322923957L;
	
@Id private Long 	fcRespId;				// Unique FlashCardResponse id
	private Long	userSessionId;			// UserSession id
	private Long 	sessionId;				// Session id / Assignment Id
	private Long	cardId;					// Id of the card, shown to the user
	private String	gplusId,				// The user's google ID	

					confuserType;			// The type of confuser that was used
	@Unindex private int sessionCardNo;		// The nth time the user has seen that card during the session
	@Unindex private int responseNo;		// The nth time the user has guessed during that card viewing
	@Unindex private boolean isDropped;		// Holds status if the user dropped this card
	@Unindex private float	timeToAnswer; 	// Time, user spent answering this question
	@Unindex private Assessment assessment;	// The user's assessment of the flashcard.
	
	public ObjectifyableFlashCardResponse() {}
	
	/**
	 * This method creates Objectifyable Proxy from real object
	 * @param fcResp		FlashCardResponse object
	 */
	public ObjectifyableFlashCardResponse (FlashCardResponse fcResp) {
		this.fcRespId = fcResp.getFcRespId();
		this.userSessionId = fcResp.getUserSessionId();
		this.sessionId = fcResp.getSessionId();
		this.gplusId = fcResp.getGplusId();
		this.cardId = fcResp.getCardId();
		this.confuserType = fcResp.getConfuserType();
		this.sessionCardNo = fcResp.getSessionCardNo();
		this.responseNo = fcResp.getResponseNo();
		this.isDropped = fcResp.isDropped();
		this.timeToAnswer = fcResp.getTimeToAnswer();
		this.assessment = fcResp.getAssessment();
	}
	
	/**
	 * This method reconstructs real object from Objectifyable Proxy
	 * @return FlashCardResponse object
	 */
	public FlashCardResponse getFlashCardResponse() {
		FlashCardResponse fcResp = new FlashCardResponse();
		fcResp.setFcRespId(this.fcRespId);
		fcResp.setUserSessionId(this.userSessionId);
		fcResp.setSessionId(this.sessionId);
		fcResp.setGplusId(this.gplusId);
		fcResp.setCardId(this.cardId);
		fcResp.setConfuserType(this.confuserType);
		fcResp.setSessionCardNo(this.sessionCardNo);
		fcResp.setResponseNo(this.responseNo);
		fcResp.setDropped(this.isDropped);
		fcResp.setTimeToAnswer(this.timeToAnswer);
		fcResp.setAssessment(this.assessment);
		return fcResp;
	}
}
