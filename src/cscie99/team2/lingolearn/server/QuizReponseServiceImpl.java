package cscie99.team2.lingolearn.server;

import java.util.ArrayList;
import java.util.List;

import cscie99.team2.lingolearn.client.QuizResponseService;
import cscie99.team2.lingolearn.server.datastore.QuizResponseDAO;
import cscie99.team2.lingolearn.shared.QuizResponse;

/**
 * This class handles storing and pulling QuizResponse data.
 * @author JNichols
 *
 */
public class QuizReponseServiceImpl implements QuizResponseService {
	
	QuizResponseDAO qRespAccessor;
	
	/**
	 * The constructor assigns the DAO object.
	 */
	public QuizReponseServiceImpl() {
		qRespAccessor = QuizResponseDAO.getInstance();
	}

	/**
	 * Deletes a QuizResponse by session id.
	 */
	public void deleteQuizResponseById(Long sessionId) {
		qRespAccessor.deleteQuizResponseById(sessionId);

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
		return null;
	}

	/**
	 * Stores a QuizResponse object in the datastore.
	 */
	public QuizResponse storeQuizResponse(QuizResponse qResp) {
		qRespAccessor.storeQuizResponse(qResp);
		return qResp;
	}

}
