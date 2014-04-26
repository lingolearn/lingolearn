package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cscie99.team2.lingolearn.shared.QuizResponse;

public class QuizResponseDAO {
	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class QuizResponseDAOHolder { 
		public static final QuizResponseDAO INSTANCE = new QuizResponseDAO();
	}

	public static QuizResponseDAO getInstance() {
		return QuizResponseDAOHolder.INSTANCE;
	}   
	
	/**
	 * This method stores the QuizResponse in the datastore
	 * @param qResp QuizResponse object to be stored in datastore
	 * @return stored QuizResponse for diagnostic
	 */
	public QuizResponse storeQuizResponse(QuizResponse qResp) {
		ObjectifyableQuizResponse oQResp = new ObjectifyableQuizResponse(qResp);
		ofy().save().entity(oQResp).now();
		ObjectifyableQuizResponse fetched = ofy().load().entity(oQResp).now();
		return fetched.getQuizResponse();	
	}
	
	/**
	 * Obtains QuizResponse by QuizResponseId
	 * @param uId qRespId
	 * @return QuizResponse stored with the qRespId or null if not found
	 */
	public QuizResponse getQuizResponseById(Long qRespId) {
		if (qRespId == null) {
			return null;
		}
		ObjectifyableQuizResponse oQuizResponse = ofy().load().type(ObjectifyableQuizResponse.class).id(qRespId).now();
		return (oQuizResponse != null) ? oQuizResponse.getQuizResponse() : null;
	}
	
	/**
	 * Obtains a list of all available QuizResponses made by the same user specified by gplusId in the datastore
	 * @param gplusId of the User
	 * @return List of all QuizResponses in the datastore created by the same User with gplusIdlanguage or null if nothing found
	 */
	public List<QuizResponse> getAllQuizResponsesByUser(String gplusId) {
		List<ObjectifyableQuizResponse> oQuizResponses = ofy().load().type(ObjectifyableQuizResponse.class).filter("gplusId", gplusId).list();
		Iterator<ObjectifyableQuizResponse> it = oQuizResponses.iterator();
		List<QuizResponse> qResps = new ArrayList<>();
		while (it.hasNext()) {
			qResps.add(it.next().getQuizResponse());
		}
		return (qResps.size() == 0) ? null : qResps;
	}
		
	
	/**
	 * Obtains a list of all available QuizResponses in the datastore
	 * @return list of all QuizResponses or Null if no QuizResponses in the datastore
	 */
	public List<QuizResponse> getAllQuizResponses() {
		List<ObjectifyableQuizResponse> oQuizResponses = ofy().load().type(ObjectifyableQuizResponse.class).list();
		Iterator<ObjectifyableQuizResponse> it = oQuizResponses.iterator();
		List<QuizResponse> qResps = new ArrayList<>();
		while (it.hasNext()) {
			qResps.add(it.next().getQuizResponse());
		}
		return (qResps.size() == 0) ? null : qResps;
	}
	
	public List<QuizResponse> getAllQuizResponsesBefore(Date date) {
		List<ObjectifyableQuizResponse> oQuizResponses = ofy().load().type(ObjectifyableQuizResponse.class).filter("answerTimeRec <", date).list();
		Iterator<ObjectifyableQuizResponse> it = oQuizResponses.iterator();
		List<QuizResponse> qResps = new ArrayList<>();
		while (it.hasNext()) {
			qResps.add(it.next().getQuizResponse());
		}
		return (qResps.size() == 0) ? null : qResps;
	}
	
	public List<QuizResponse> getAllQuizResponsesOn(Date date) {
		List<ObjectifyableQuizResponse> oQuizResponses = ofy().load().type(ObjectifyableQuizResponse.class).filter("answerTimeRec =", date).list();
		Iterator<ObjectifyableQuizResponse> it = oQuizResponses.iterator();
		List<QuizResponse> qResps = new ArrayList<>();
		while (it.hasNext()) {
			qResps.add(it.next().getQuizResponse());
		}
		return (qResps.size() == 0) ? null : qResps;
	}
	
	public List<QuizResponse> getAllQuizResponsesAfter(Date date) {
		List<ObjectifyableQuizResponse> oQuizResponses = ofy().load().type(ObjectifyableQuizResponse.class).filter("answerTimeRec >", date).list();
		Iterator<ObjectifyableQuizResponse> it = oQuizResponses.iterator();
		List<QuizResponse> qResps = new ArrayList<>();
		while (it.hasNext()) {
			qResps.add(it.next().getQuizResponse());
		}
		return (qResps.size() == 0) ? null : qResps;
	}
	
	/**
	 * Deletes the QuizResponse with the specified sessionId from the datastore
	 * @param cardId
	 */
	public void deleteQuizResponseById(Long qRespId) {
		if (qRespId != null) {
			ofy().delete().type(ObjectifyableQuizResponse.class).id(qRespId).now();
		}
	}
}
