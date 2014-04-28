package cscie99.team2.lingolearn.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * This class contains statistics regarding user's interaction with the flashcard
 */
public class QuizResponse implements Serializable {

	private static final long serialVersionUID = 3269082972556822708L;

	private Long 	qRespId,			// Unique QuizResponse id
					userSessionId,		// UserSession id
					sessionId,			// Session id/ Assignment Id
					cardId;				// Id of the card, shown to the user
	private String	gplusId,			// The user's google ID	
					confuserType;		// The type of confuser that was used
	private boolean isCorrect,			// Holds status if the user's provided answer was correct
					isChanged;			// Holds status if the user was not sure and changed answer one or more times 
	private float	timeToAnswer; 		// Time, user spent answering this question
	private Date	answerTimeRec;		// Date when the answer was obtained
	private int 	numConfusersUsed;	// Number of confuser's used [0,1,2,3]
	private String	wrongAnswers;		// CSV list of wrong answers that were presented, for example [dog,bird,cow]
	private String	seq;				// Sequence
	
	public QuizResponse () {
		this.answerTimeRec = new Date();
	};
	
	public QuizResponse(Long sessionId, Long cardId, String gplusId, 
			String confuserType, boolean isCorrect, boolean isChanged,
			float timeToAnswer) {
		this.sessionId = sessionId;
		this.gplusId = gplusId;
		this.cardId = cardId;
		this.confuserType = confuserType;
		this.isCorrect = isCorrect;
		this.isChanged = isChanged;
		this.timeToAnswer = timeToAnswer;
		seq = "Japanese-->English";
	}
	
	public QuizResponse(Long qRespId, Long userSessionId, Long sessionId, Long cardId, String gplusId, 
			String confuserType, boolean isCorrect, boolean isChanged,
			float timeToAnswer) {
		this.qRespId = qRespId;
		this.userSessionId = userSessionId;
		this.sessionId = sessionId;
		this.gplusId = gplusId;
		this.cardId = cardId;
		this.confuserType = confuserType;
		this.isCorrect = isCorrect;
		this.isChanged = isChanged;
		this.timeToAnswer = timeToAnswer;
		seq = "Japanese-->English";
	}
	
	public QuizResponse(Long qRespId, Long userSessionId, Long sessionId,
			Long cardId, String gplusId, String confuserType,
			boolean isCorrect, boolean isChanged, float timeToAnswer,
			Date answerTimeRec) {
		super();
		this.qRespId = qRespId;
		this.userSessionId = userSessionId;
		this.sessionId = sessionId;
		this.cardId = cardId;
		this.gplusId = gplusId;
		this.confuserType = confuserType;
		this.isCorrect = isCorrect;
		this.isChanged = isChanged;
		this.timeToAnswer = timeToAnswer;
		this.answerTimeRec = answerTimeRec;
		seq = "Japanese-->English";
	}

	public QuizResponse(Long qRespId, Long userSessionId, Long sessionId,
			Long cardId, String gplusId, String confuserType,
			boolean isCorrect, boolean isChanged, float timeToAnswer,
			Date answerTimeRec, int numConfusersUsed, String wrongAnswers) {
		this.qRespId = qRespId;
		this.userSessionId = userSessionId;
		this.sessionId = sessionId;
		this.cardId = cardId;
		this.gplusId = gplusId;
		this.confuserType = confuserType;
		this.isCorrect = isCorrect;
		this.isChanged = isChanged;
		this.timeToAnswer = timeToAnswer;
		this.answerTimeRec = answerTimeRec;
		this.numConfusersUsed = numConfusersUsed;
		this.wrongAnswers = wrongAnswers;
		seq = "Japanese-->English";
	}
	
	public QuizResponse(Long qRespId, Long userSessionId, Long sessionId,
			Long cardId, String gplusId, String confuserType,
			boolean isCorrect, boolean isChanged, float timeToAnswer,
			Date answerTimeRec, int numConfusersUsed, String wrongAnswers,
			String seq) {
		this.qRespId = qRespId;
		this.userSessionId = userSessionId;
		this.sessionId = sessionId;
		this.cardId = cardId;
		this.gplusId = gplusId;
		this.confuserType = confuserType;
		this.isCorrect = isCorrect;
		this.isChanged = isChanged;
		this.timeToAnswer = timeToAnswer;
		this.answerTimeRec = answerTimeRec;
		this.numConfusersUsed = numConfusersUsed;
		this.wrongAnswers = wrongAnswers;
		this.seq = seq;
		seq = "Japanese-->English";
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

	public Long getqRespId() {
		return qRespId;
	}

	public void setqRespId(Long qRespId) {
		this.qRespId = qRespId;
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

	public int getNumConfusersUsed() {
		return numConfusersUsed;
	}

	public void setNumConfusersUsed(int numConfusersUsed) {
		this.numConfusersUsed = numConfusersUsed;
	}

	public String getWrongAnswers() {
		return wrongAnswers;
	}

	public void setWrongAnswers(String wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
}
