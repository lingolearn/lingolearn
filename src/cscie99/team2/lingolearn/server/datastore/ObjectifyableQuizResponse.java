/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import cscie99.team2.lingolearn.shared.QuizResponse;


/**
 * @author YPolyanskyy
 * 
 * This class represents Proxy for QuizResponse
 */
@Entity(name="ObjectifyableQuizResponse")
@Index
public class ObjectifyableQuizResponse implements Serializable {

	private static final long serialVersionUID = -8496591932881375832L;

@Id private Long 	qRespId;				// Unique QuizResponse id
	private Long	userSessionId;			// UserSession id
	private Long 	sessionId;				// Session id
	private Long cardId;					// Id of the card, shown to the user
	private String	gplusId,				// The user's google ID	
					confuserType;			// The type of confuser that was used
	@Unindex	private boolean isCorrect;	// Holds status if the user's provided answer was correct
	@Unindex	private boolean	isChanged;	// Holds status if the user was not sure and changed answer one or more times 
	@Unindex private float	timeToAnswer; 	// Time, user spent answering this question
	private Date	answerTimeRec;			// Date when the answer was obtained
	
	
	public ObjectifyableQuizResponse() {}
	
	/**
	 * This method creates Objectifyable Proxy from real object
	 * @param qResp		QuizResponse object
	 */
	public ObjectifyableQuizResponse (QuizResponse qResp) {
		this.qRespId = qResp.getqRespId();
		this.userSessionId = qResp.getUserSessionId();
		this.sessionId = qResp.getSessionId();
		this.gplusId = qResp.getGplusId();
		this.cardId = qResp.getCardId();
		this.confuserType = qResp.getConfuserType();
		this.isCorrect = qResp.isCorrect();
		this.isChanged = qResp.isChanged();
		this.timeToAnswer = qResp.getTimeToAnswer();
		this.answerTimeRec = qResp.getAnswerTimeRec();
	}
	
	/**
	 * This method reconstructs real object from Objectifyable Proxy
	 * @return QuizResponse object
	 */
	public QuizResponse getQuizResponse() {
		QuizResponse qResp = new QuizResponse();
		qResp.setqRespId(this.qRespId);
		qResp.setUserSessionId(this.userSessionId);
		qResp.setSessionId(this.sessionId);
		qResp.setGplusId(this.gplusId);
		qResp.setCardId(this.cardId);
		qResp.setConfuserType(this.confuserType);
		qResp.setCorrect(this.isCorrect);
		qResp.setChanged(this.isChanged);
		qResp.setTimeToAnswer(this.timeToAnswer);
		qResp.setAnswerTimeRec(this.answerTimeRec);
		return qResp;
	}
}
