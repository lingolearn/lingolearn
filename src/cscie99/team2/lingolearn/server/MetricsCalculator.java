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
		return fcRespAccessor.getAllFlashCardResponses();
	}

	/**
	 * Returns flashcard response data for a particular user
	 */
	public List<FlashCardResponse> getAllFlashCardResponsesByUser(String gplusId) {
		return fcRespAccessor.getAllFlashCardResponsesByUser(gplusId);
	}

	/**
	 * Returns flashcard response data for a sessions
	 */
	public FlashCardResponse getFlashCardResponseById(Long sessionId) {
		return fcRespAccessor.getFlashCardResponseById(sessionId);
	}

	/**
	 * Returns all QuizResponse objects.
	 */
	public List<QuizResponse> getAllQuizResponses() {
		return qRespAccessor.getAllQuizResponses();
	}

	/**
	 * Returns all QuizResponse objects for a particular user.
	 */
	public List<QuizResponse> getAllQuizResponsesByUser(String gplusId) {
		return qRespAccessor.getAllQuizResponsesByUser(gplusId);
	}

	/**
	 * Returns all QuizResponse objects for a particular session.
	 */
	public QuizResponse getQuizResponseById(Long sessionId) {
		return qRespAccessor.getQuizResponseById(sessionId);
	}
		
	/**
	 * Returns all UserSession objects for a particular user.
	 * @param gplusId
	 * @return
	 */
	public List<UserSession> getAllUserSessionsByUser(String gplusId) {
		return usAccessor.getAllUserSessionsByUser(gplusId);
	}
	
	public User getUserById (String gplusId) {
		return uAccessor.getUserByGplusId(gplusId);
	}
	
	public float calculatePercentNoClue(String gplusId) {		
		int noClues = 0;
		int flashCardsSeen = 0;
		
		List<FlashCardResponse> fcResps = this.getAllFlashCardResponsesByUser(gplusId);
		if (fcResps == null) {
			return 0.0f;
		}
		for (FlashCardResponse fcr: fcResps) {
			flashCardsSeen++;
			noClues += (fcr.getAssessment() == Assessment.NOCLUE) ? 1 : 0;
		}
		return ((float)noClues/(float)flashCardsSeen);
	}
	
	public float calculatePercentSortaKnewIt(String gplusId) {		
		int sortaKnewIt = 0;
		int flashCardsSeen = 0;
		
		List<FlashCardResponse> fcResps = this.getAllFlashCardResponsesByUser(gplusId);
		if (fcResps == null) {
			return 0.0f;
		}
		for (FlashCardResponse fcr: fcResps) {
			flashCardsSeen++;
			sortaKnewIt += (fcr.getAssessment() == Assessment.SORTAKNEWIT) ? 1 : 0;
		}		
		return ((float)sortaKnewIt/(float)flashCardsSeen);
	}
	
	public float calculatePercentDefinitelyKnewIt(String gplusId) {		
		int definitelyKnewIt = 0;
		int flashCardsSeen = 0;
		
		List<FlashCardResponse> fcResps = this.getAllFlashCardResponsesByUser(gplusId);
		
		if (fcResps == null) {
			return 0.0f;
		}
		for (FlashCardResponse fcr: fcResps) {
			flashCardsSeen++;
			definitelyKnewIt += (fcr.getAssessment() == Assessment.DEFINITELYKNEWIT) ? 1 : 0;
		}		
		return ((float)definitelyKnewIt/(float)flashCardsSeen);
	}

	public float calculateRecallRate(String gplusId) {
		int correctQuizAnswers = 0;
		int questionsSeen = 0;
		
		List<QuizResponse> qResps = this.getAllQuizResponsesByUser(gplusId);
		
		if (qResps == null) {
			return 0.0f;
		}
		for (QuizResponse qr: qResps) {
			questionsSeen++;
			correctQuizAnswers += (qr.isCorrect()) ? 1 : 0;
		}
		return ((float)correctQuizAnswers/(float)questionsSeen);
	}
	
	public float calculateAvgQuizReactionTime(String gplusId) {
		float totalQuizTimeToAnswer = 0.0f;
		int questionsSeen = 0;
		
		List<QuizResponse> qResps = this.getAllQuizResponsesByUser(gplusId);
		
		if (qResps == null) {
			return 0.0f;
		}
		if (!qResps.isEmpty()) {
			for (QuizResponse qr: qResps) {
				questionsSeen++;
				totalQuizTimeToAnswer = totalQuizTimeToAnswer + qr.getTimeToAnswer();
			}
		}
		return (totalQuizTimeToAnswer/(float)questionsSeen);
	}
	
	public float calculateAvgFlashCardReactionTime(String gplusId) {
		float totalFlashCardTimeToAnswer = 0.0f;
		int cardsSeen = 0;
		
		List<FlashCardResponse> fcResps = this.getAllFlashCardResponsesByUser(gplusId);
		
		if (fcResps == null) {
			return 0.0f;
		}
		for (FlashCardResponse fcr: fcResps) {
			cardsSeen++;
			totalFlashCardTimeToAnswer = totalFlashCardTimeToAnswer + fcr.getTimeToAnswer();
		}
		return (totalFlashCardTimeToAnswer/(float)cardsSeen);
	}
	
	public float calculateIndecisionRate(String gplusId) {
		int changedAnswers = 0;
		int questionsSeen = 0;
		
		List<QuizResponse> qResps = this.getAllQuizResponsesByUser(gplusId);
		
		if (qResps == null) {
			return 0.0f;
		}
		for (QuizResponse qr: qResps) {
			questionsSeen++;
			changedAnswers += (qr.isChanged()) ? 1 : 0;
		}
		return ((float)changedAnswers/(float)questionsSeen);
	}
	
	public float calculateDropRate(String gplusId) {
		int droppedCards = 0;
		int cardsSeen = 0;
		
		List<FlashCardResponse> fcResps = this.getAllFlashCardResponsesByUser(gplusId);
		
		if (fcResps == null) {
			return 0.0f;
		}
		for (FlashCardResponse fcr: fcResps) {
			cardsSeen++;
			droppedCards += (fcr.isDropped()) ? 1 : 0;
		}
		return ((float)droppedCards/(float)cardsSeen);
	}
	
	public float calculateAverageSessionTime(String gplusId) {
		float totalSessionTime = 0.0f;
		int noSessions = 0;
		
		List<UserSession> usResps = this.getAllUserSessionsByUser(gplusId);
		
		if (usResps == null) {
			return 0.0f;
		}
		for (UserSession us: usResps) {
			noSessions++;
			float length = (float)(us.getSessEnd().getTime() - us.getSessStart().getTime());
			totalSessionTime = totalSessionTime + length;
		}
		return (totalSessionTime/(float)noSessions);		
	}
	
	public float calculateRepetitionsPerWeek(String gplusId) {
		User u = this.getUserById(gplusId);
		List<UserSession> usResps = this.getAllUserSessionsByUser(gplusId);
		
		if (usResps == null) {
			return 0.0f;
		}
		int noSessions = usResps.size();
		Date today = new Date();
		long timeSinceRegistration = (today.getTime() - u.getUserRegistrationTime().getTime());
		return ((float)noSessions/ (timeSinceRegistration/604800000));
	}
}
