package cscie99.team2.lingolearn.server;

import java.util.Date;
import java.util.List;

import cscie99.team2.lingolearn.server.datastore.FlashCardResponseDAO;
import cscie99.team2.lingolearn.server.datastore.QuizResponseDAO;
import cscie99.team2.lingolearn.server.datastore.UserDAO;
import cscie99.team2.lingolearn.server.datastore.UserSessionDAO;
import cscie99.team2.lingolearn.shared.Assessment;
import cscie99.team2.lingolearn.shared.FlashCardResponse;
import cscie99.team2.lingolearn.shared.QuizResponse;
import cscie99.team2.lingolearn.shared.User;
import cscie99.team2.lingolearn.shared.UserSession;

public class MetricsCalculator {
	
	QuizResponseDAO qRespAccessor;
	FlashCardResponseDAO fcRespAccessor;
	UserSessionDAO usAccessor;
	UserDAO uAccessor;
	
	public MetricsCalculator() {
		qRespAccessor = QuizResponseDAO.getInstance();
		fcRespAccessor = FlashCardResponseDAO.getInstance();
		usAccessor = UserSessionDAO.getInstance();
		uAccessor = UserDAO.getInstance();
	}
	

	/**
	 * Returns all flashcard response data
	 */
	public List<FlashCardResponse> getAllFlashCardResponses() {
		if (fcRespAccessor.getAllFlashCardResponses() != null) {
			return fcRespAccessor.getAllFlashCardResponses();
		}
		else {
			return null;
		}
	}

	/**
	 * Returns flashcard response data for a particular user
	 */
	public List<FlashCardResponse> getAllFlashCardResponsesByUser(String gplusId) {
		if (fcRespAccessor.getAllFlashCardResponsesByUser(gplusId) != null) {
			return fcRespAccessor.getAllFlashCardResponsesByUser(gplusId);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns flashcard response data for a sessions
	 */
	public FlashCardResponse getFlashCardResponseById(Long sessionId) {
		if (fcRespAccessor.getFlashCardResponseById(sessionId) != null) {
			return fcRespAccessor.getFlashCardResponseById(sessionId);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all QuizResponse objects.
	 */
	public List<QuizResponse> getAllQuizResponses() {
		if (qRespAccessor.getAllQuizResponses() != null) {
			return qRespAccessor.getAllQuizResponses();
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all QuizResponse objects for a particular user.
	 */
	public List<QuizResponse> getAllQuizResponsesByUser(String gplusId) {
		if (qRespAccessor.getAllQuizResponsesByUser(gplusId) != null) {
			return qRespAccessor.getAllQuizResponsesByUser(gplusId);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all QuizResponse objects for a particular session.
	 */
	public QuizResponse getQuizResponseById(Long sessionId) {
		if (qRespAccessor.getQuizResponseById(sessionId) != null) {
			return qRespAccessor.getQuizResponseById(sessionId);
		}
		else {
			return null;
		}
	}
		
	/**
	 * Returns all UserSession objects for a particular user.
	 * @param gplusId
	 * @return
	 */
	public List<UserSession> getAllUserSessionsByUser(String gplusId) {
		if (usAccessor.getAllUserSessionsByUser(gplusId) != null) {
			return usAccessor.getAllUserSessionsByUser(gplusId);
		}
		else {
			return null;
		}
	}
	
	public User getUserById (String gplusId) {
		if (uAccessor.getUserById(gplusId) != null) {
			return uAccessor.getUserById(gplusId);
		}
		else {
			return null;
		}
	}
	
	public float calculatePercentNoClue(String gplusId) {		
		int noClues = 0;
		int flashCardsSeen = 0;
		
		List<FlashCardResponse> fcResps = this.getAllFlashCardResponsesByUser(gplusId);
		
		for (FlashCardResponse fcr: fcResps) {
			flashCardsSeen++;
			if (fcr.getAssessment() == Assessment.NOCLUE) {
				noClues++;
			}

		}
		
		return (noClues/flashCardsSeen);
	}
	
	public float calculatePercentSortaKnewIt(String gplusId) {		
		int sortaKnewIt = 0;
		int flashCardsSeen = 0;
		
		List<FlashCardResponse> fcResps = this.getAllFlashCardResponsesByUser(gplusId);
		
		for (FlashCardResponse fcr: fcResps) {
			flashCardsSeen++;
			if (fcr.getAssessment() == Assessment.NOCLUE) {
				sortaKnewIt++;
			}

		}
		
		return (sortaKnewIt/flashCardsSeen);
	}
	
	public float calculatePercentDefinitelyKnewIt(String gplusId) {		
		int definitelyKnewIt = 0;
		int flashCardsSeen = 0;
		
		List<FlashCardResponse> fcResps = this.getAllFlashCardResponsesByUser(gplusId);
		
		for (FlashCardResponse fcr: fcResps) {
			flashCardsSeen++;
			if (fcr.getAssessment() == Assessment.NOCLUE) {
				definitelyKnewIt++;
			}

		}
		
		return (definitelyKnewIt/flashCardsSeen);
	}

	public float calculateRecallRate(String gplusId) {
		int correctQuizAnswers = 0;
		int questionsSeen = 0;
		
		List<QuizResponse> qResps = this.getAllQuizResponsesByUser(gplusId);
		
		for (QuizResponse qr: qResps) {
			questionsSeen++;
			if (qr.isCorrect()) {
				correctQuizAnswers++;
			}			
		}
		
		return (correctQuizAnswers/questionsSeen);
		
	}
	
	public float calculateAvgQuizReactionTime(String gplusId) {
		
		float totalQuizTimeToAnswer = 0.0f;
		int questionsSeen = 0;
		
		List<QuizResponse> qResps = this.getAllQuizResponsesByUser(gplusId);
		
		if (!qResps.isEmpty()) {
			for (QuizResponse qr: qResps) {
				questionsSeen++;
				totalQuizTimeToAnswer = totalQuizTimeToAnswer + qr.getTimeToAnswer();
			}
		}
		
		return (totalQuizTimeToAnswer/questionsSeen);
		
	}
	
	public float calculateAvgFlashCardReactionTime(String gplusId) {
		float totalFlashCardTimeToAnswer = 0.0f;
		int cardsSeen = 0;
		
		List<FlashCardResponse> fcResps = this.getAllFlashCardResponsesByUser(gplusId);
		
		for (FlashCardResponse fcr: fcResps) {
			cardsSeen++;
			totalFlashCardTimeToAnswer = totalFlashCardTimeToAnswer + fcr.getTimeToAnswer();
		}
		
		return (totalFlashCardTimeToAnswer/cardsSeen);
	}
	
	public float calculateIndecisionRate(String gplusId) {
		int changedAnswers = 0;
		int questionsSeen = 0;
		
		List<QuizResponse> qResps = this.getAllQuizResponsesByUser(gplusId);
		
		for (QuizResponse qr: qResps) {
			questionsSeen++;
			if (qr.isChanged()) {
				changedAnswers++;
			}			
		}
		
		return (changedAnswers/questionsSeen);
		
	}
	
	public float calculateDropRate(String gplusId) {
		int droppedCards = 0;
		int cardsSeen = 0;
		
		List<FlashCardResponse> fcResps = this.getAllFlashCardResponsesByUser(gplusId);
		
		for (FlashCardResponse fcr: fcResps) {
			cardsSeen++;
			if (fcr.isDropped()) {
				droppedCards++;
			}			
		}
		
		return (droppedCards/cardsSeen);
		
	}
	
	public float calculateAverageSessionTime(String gplusId) {
		float totalSessionTime = 0.0f;
		int noSessions = 0;
		
		List<UserSession> usResps = this.getAllUserSessionsByUser(gplusId);
		
		for (UserSession us: usResps) {
			noSessions++;
			float length = (float)(us.getSessEnd().getTime() - us.getSessStart().getTime());
			totalSessionTime = totalSessionTime + length;
		}
		
		return (totalSessionTime/noSessions);
		
	}
	
	public float calculateRepetitionsPerWeek(String gplusId) {
		User u = this.getUserById(gplusId);
		List<UserSession> usResps = this.getAllUserSessionsByUser(gplusId);
		
		int noSessions = usResps.size();
		Date today = new Date();
		long timeSinceRegistration = (today.getTime() - u.getUserRegistrationTime().getTime());
		
		
		return (noSessions/ (timeSinceRegistration/604800000));
		
	}


}
