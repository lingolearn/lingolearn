package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cscie99.team2.lingolearn.shared.FlashCardResponse;

public class FlashCardResponseDAO {

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class FlashCardResponseDAOHolder { 
		public static final FlashCardResponseDAO INSTANCE = new FlashCardResponseDAO();
	}

	public static FlashCardResponseDAO getInstance() {
		return FlashCardResponseDAOHolder.INSTANCE;
	}   
	
	/**
	 * This method stores the FlashCardResponse in the datastore
	 * @param qResp FlashCardResponse object to be stored in datastore
	 * @return stored FlashCardResponse for diagnostic
	 */
	public FlashCardResponse storeFlashCardResponse(FlashCardResponse qResp) {
		ObjectifyableFlashCardResponse oResp = new ObjectifyableFlashCardResponse(qResp);
		ofy().save().entity(oResp).now();
		ObjectifyableFlashCardResponse fetched = ofy().load().entity(oResp).now();
		return fetched.getFlashCardResponse();	
	}
	
	/**
	 * Obtains FlashCardResponse by FlashCardResponseId
	 * @param uId fcRespId
	 * @return FlashCardResponse stored with the qRespId or null if not found
	 */
	public FlashCardResponse getFlashCardResponseById(Long fcRespId) {
		if (fcRespId == null) {
			return null;
		}
		ObjectifyableFlashCardResponse oFlashCardResponse = ofy().load().type(ObjectifyableFlashCardResponse.class).id(fcRespId).now();
		return (oFlashCardResponse != null) ? oFlashCardResponse.getFlashCardResponse() : null;
	}
	
	/**
	 * Obtains a list of all available FlashCardResponses made by the same user specified by gplusId in the datastore
	 * @param gplusId of the User
	 * @return List of all FlashCardResponses in the datastore created by the same User with gplusIdlanguage or null if nothing found
	 */
	public List<FlashCardResponse> getAllFlashCardResponsesByUser(String gplusId) {
		List<ObjectifyableFlashCardResponse> oFlashCardResponses = ofy().load().type(ObjectifyableFlashCardResponse.class).filter("gplusId", gplusId).list();
		Iterator<ObjectifyableFlashCardResponse> it = oFlashCardResponses.iterator();
		List<FlashCardResponse> qResps = new ArrayList<>();
		while (it.hasNext()) {
			qResps.add(it.next().getFlashCardResponse());
		}
		return (qResps.size() == 0) ? null : qResps;
	}
	
	/**
	 * Return all Flash Card Responses that match the specified userSession Id and
	 * sessionId.
	 * @param userSessionId
	 * @param sessionId
	 * @return a List of matching flash card responses
	 */
	public List<FlashCardResponse> getAllFlashCardResponsesByUserSessionId(
																Long userSessionId, Long sessionId){
		List<ObjectifyableFlashCardResponse> oFlashCardResponses = 
							ofy().load().type(ObjectifyableFlashCardResponse.class).filter("sessionId", sessionId)
										.filter("userSessionId", userSessionId).list();
		
		Iterator<ObjectifyableFlashCardResponse> it = oFlashCardResponses.iterator();
		List<FlashCardResponse> matchedResponses = new ArrayList<>();
		while (it.hasNext()) {
			matchedResponses.add(it.next().getFlashCardResponse());
		}
		return matchedResponses;
	
	}
	
	/**
	 * Get all Flash Card Responses associated with the specified session id.
	 * @param sessionId
	 * @return a list of all matching flash card responses.
	 */
	public List<FlashCardResponse> getAllFlashCardResponsesBySessionId( 
																											Long sessionId ){
		List<ObjectifyableFlashCardResponse> oFlashCardResponses = 
				ofy().load().type(ObjectifyableFlashCardResponse.class)
																	.filter("sessionId", sessionId).list();

		Iterator<ObjectifyableFlashCardResponse> it = oFlashCardResponses.iterator();
		List<FlashCardResponse> matchedResponses = new ArrayList<>();
		while (it.hasNext()) {
			matchedResponses.add(it.next().getFlashCardResponse());
		}
		return matchedResponses;
	}
	
	
	/**
	 * Obtains a list of all available FlashCardResponses in the datastore
	 * @return list of all FlashCardResponses or Null if no FlashCardResponses in the datastore
	 */
	public List<FlashCardResponse> getAllFlashCardResponses() {
		List<ObjectifyableFlashCardResponse> oFlashCardResponses = ofy().load().type(ObjectifyableFlashCardResponse.class).list();
		Iterator<ObjectifyableFlashCardResponse> it = oFlashCardResponses.iterator();
		List<FlashCardResponse> qResps = new ArrayList<>();
		while (it.hasNext()) {
			qResps.add(it.next().getFlashCardResponse());
		}
		return (qResps.size() == 0) ? null : qResps;
	}
	
	public List<FlashCardResponse> getAllFlashCardResponsesBefore(Date date) {
		if (date == null || !(date instanceof Date)) {
			return null;
		}
		List<ObjectifyableFlashCardResponse> oFlashCardResponses = ofy().load().type(ObjectifyableFlashCardResponse.class).filter("answerTimeRec <", date).list();
		Iterator<ObjectifyableFlashCardResponse> it = oFlashCardResponses.iterator();
		List<FlashCardResponse> qResps = new ArrayList<>();
		while (it.hasNext()) {
			qResps.add(it.next().getFlashCardResponse());
		}
		return (qResps.size() == 0) ? null : qResps;
	}
	
	public List<FlashCardResponse> getAllFlashCardResponsesOn(Date date) {
		if (date == null || !(date instanceof Date)) {
			return null;
		}
		List<ObjectifyableFlashCardResponse> oFlashCardResponses = ofy().load().type(ObjectifyableFlashCardResponse.class).filter("answerTimeRec =", date).list();
		Iterator<ObjectifyableFlashCardResponse> it = oFlashCardResponses.iterator();
		List<FlashCardResponse> qResps = new ArrayList<>();
		while (it.hasNext()) {
			qResps.add(it.next().getFlashCardResponse());
		}
		return (qResps.size() == 0) ? null : qResps;
	}
	
	public List<FlashCardResponse> getAllFlashCardResponsesAfter(Date date) {
		if (date == null || !(date instanceof Date)) {
			return null;
		}
		List<ObjectifyableFlashCardResponse> oFlashCardResponses = ofy().load().type(ObjectifyableFlashCardResponse.class).filter("answerTimeRec >", date).list();
		Iterator<ObjectifyableFlashCardResponse> it = oFlashCardResponses.iterator();
		List<FlashCardResponse> qResps = new ArrayList<>();
		while (it.hasNext()) {
			qResps.add(it.next().getFlashCardResponse());
		}
		return (qResps.size() == 0) ? null : qResps;
	}
	
	/**
	 * Deletes the FlashCardResponse with the specified sessionId from the datastore
	 * @param cardId
	 */
	public void deleteFlashCardResponseById(Long fcRespId) {
		if (fcRespId != null) {
			ofy().delete().type(ObjectifyableFlashCardResponse.class).id(fcRespId).now();
		}
	}
}
