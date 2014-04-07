package cscie99.team2.lingolearn.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cscie99.team2.lingolearn.client.FlashCardResponseService;
import cscie99.team2.lingolearn.server.datastore.FlashCardResponseDAO;
import cscie99.team2.lingolearn.shared.FlashCardResponse;

public class FlashCardResponseServiceImpl extends RemoteServiceServlet implements FlashCardResponseService {
	
	FlashCardResponseDAO fcRespAccessor;
	
	public FlashCardResponseServiceImpl() {
		fcRespAccessor = FlashCardResponseDAO.getInstance();
	}

	/**
	 * Deletes flashcard response data by session id
	 */
	public void deleteFlashCardResponseById(Long sessionId) {
		if (fcRespAccessor.getFlashCardResponseById(sessionId) != null) {
			fcRespAccessor.deleteFlashCardResponseById(sessionId);
		}
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
	 * Stores flashcard response in the datastore
	 */
	public FlashCardResponse storeFlashCardResponse(FlashCardResponse fcResp) {
		fcRespAccessor.storeFlashCardResponse(fcResp);
		return fcResp;
	}

}
