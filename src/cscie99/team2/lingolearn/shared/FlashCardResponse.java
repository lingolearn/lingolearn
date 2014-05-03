package cscie99.team2.lingolearn.shared;

import java.io.Serializable;
import java.util.Date;

public class FlashCardResponse implements Serializable {

	private static final long serialVersionUID = -1298962988486358463L;
	public static final String DEFAULT_SEQUENCE = 
							SessionTypes.Kanji_Translation.toString();
	
	
	private Long 	fcRespId,		// Unique QuizResponse id
					userSessionId,	// UserSession id
					sessionId,		// Session id/ Assignment Id
					cardId;			// Id of the card, shown to the user
	private String	gplusId,		// The user's google ID	
					confuserType;	// The type of confuser that was used
	private int     sessionCardNo,	// The nth time the user has seen that card during the session
					responseNo;		// The nth time the user has guessed during that card viewing
	private boolean isDropped;		// Holds status if the user dropped this card
	private float	timeToAnswer; 	// Time, user spent answering this question
	private Assessment assessment;	// The user's assessment of the flashcard.
	private Date	answerTimeRec;	// Date when the answer was obtained
	private String	seq;			// Now holds the SessionType

	public FlashCardResponse() {
		this.answerTimeRec = new Date();
	};
	
	public FlashCardResponse(Long sessionId, Long cardId, String gplusId, 
			String confuserType, int sessionCardNo, int responseNo,
			boolean isDropped, float timeToAnswer, Assessment assessment) {
		this.sessionId = sessionId;
		this.gplusId = gplusId;
		this.cardId = cardId;
		this.confuserType = confuserType;
		this.sessionCardNo = sessionCardNo;
		this.responseNo = responseNo;
		this.isDropped = isDropped;
		this.timeToAnswer = timeToAnswer;
		this.assessment = assessment;
		seq = DEFAULT_SEQUENCE;
	}

	public FlashCardResponse(Long fcRespId, Long userSessionId, Long sessionId,
			Long cardId, String gplusId, String confuserType,
			int sessionCardNo, int responseNo, boolean isDropped,
			float timeToAnswer, Assessment assessment) {
		this.fcRespId = fcRespId;
		this.userSessionId = userSessionId;
		this.sessionId = sessionId;
		this.cardId = cardId;
		this.gplusId = gplusId;
		this.confuserType = confuserType;
		this.sessionCardNo = sessionCardNo;
		this.responseNo = responseNo;
		this.isDropped = isDropped;
		this.timeToAnswer = timeToAnswer;
		this.assessment = assessment;
		seq = DEFAULT_SEQUENCE;
	}
	
	public FlashCardResponse(Long fcRespId, Long userSessionId, Long sessionId,
			Long cardId, String gplusId, String confuserType,
			int sessionCardNo, int responseNo, boolean isDropped,
			float timeToAnswer, Assessment assessment, Date answerTimeRec) {
		super();
		this.fcRespId = fcRespId;
		this.userSessionId = userSessionId;
		this.sessionId = sessionId;
		this.cardId = cardId;
		this.gplusId = gplusId;
		this.confuserType = confuserType;
		this.sessionCardNo = sessionCardNo;
		this.responseNo = responseNo;
		this.isDropped = isDropped;
		this.timeToAnswer = timeToAnswer;
		this.assessment = assessment;
		this.answerTimeRec = answerTimeRec;
		seq = DEFAULT_SEQUENCE;
	}
	
	public FlashCardResponse(Long fcRespId, Long userSessionId, Long sessionId,
			Long cardId, String gplusId, String confuserType,
			int sessionCardNo, int responseNo, boolean isDropped,
			float timeToAnswer, Assessment assessment, Date answerTimeRec,
			String seq) {
		this.fcRespId = fcRespId;
		this.userSessionId = userSessionId;
		this.sessionId = sessionId;
		this.cardId = cardId;
		this.gplusId = gplusId;
		this.confuserType = confuserType;
		this.sessionCardNo = sessionCardNo;
		this.responseNo = responseNo;
		this.isDropped = isDropped;
		this.timeToAnswer = timeToAnswer;
		this.assessment = assessment;
		this.answerTimeRec = answerTimeRec;
		this.seq = seq;
		seq = DEFAULT_SEQUENCE;
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

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public String getConfuserType() {
		return confuserType;
	}

	public void setConfuserType(String confuserType) {
		this.confuserType = confuserType;
	}

	public int getSessionCardNo() {
		return sessionCardNo;
	}

	public void setSessionCardNo(int sessionCardNo) {
		this.sessionCardNo = sessionCardNo;
	}

	public int getResponseNo() {
		return responseNo;
	}

	public void setResponseNo(int responseNo) {
		this.responseNo = responseNo;
	}

	public boolean isDropped() {
		return isDropped;
	}

	public void setDropped(boolean isDropped) {
		this.isDropped = isDropped;
	}

	public float getTimeToAnswer() {
		return timeToAnswer;
	}

	public void setTimeToAnswer(float timeToAnswer) {
		this.timeToAnswer = timeToAnswer;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	public Long getFcRespId() {
		return fcRespId;
	}

	public void setFcRespId(Long fcRespId) {
		this.fcRespId = fcRespId;
	}

	public Long getUserSessionId() {
		return userSessionId;
	}

	public void setUserSessionId(Long userSessionId) {
		this.userSessionId = userSessionId;
	}

	public Date getAnswerTimeRec() {
		return answerTimeRec;
	}

	public void setAnswerTimeRec(Date answerTimeRec) {
		this.answerTimeRec = answerTimeRec;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
}
