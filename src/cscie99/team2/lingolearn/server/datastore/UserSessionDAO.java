package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cscie99.team2.lingolearn.shared.UserSession;

public class UserSessionDAO {

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class UserSessionDAOHolder { 
		public static final UserSessionDAO INSTANCE = new UserSessionDAO();
	}

	public static UserSessionDAO getInstance() {
		return UserSessionDAOHolder.INSTANCE;
	}   
	
	/**
	 * This method stores the UserSession in the datastore
	 * @param uSess UserSession object to be stored in datastore
	 * @return stored UserSession for diagnostic
	 */
	public UserSession storeUserSession(UserSession uSess) {
		ObjectifyableUserSession oUSess = new ObjectifyableUserSession(uSess);
		ofy().save().entity(oUSess).now();
		ObjectifyableUserSession fetched = ofy().load().entity(oUSess).now();
		return fetched.getUserSession();	
	}
	
	/**
	 * Obtains UserSession by UserSessionId
	 * @param userSessionId userSessionId
	 * @return UserSession stored with the userSessionId or null if not found
	 */
	public UserSession getUserSessionById(Long userSessionId) {
		if (userSessionId == null) {
			return null;
		}
		ObjectifyableUserSession oUserSession = ofy().load().type(ObjectifyableUserSession.class).id(userSessionId).now();
		return (oUserSession != null) ? oUserSession.getUserSession() : null;
	}
	
	/**
	 * Obtains a list of all available UserSessions made by the same user specified by gplusId in the datastore
	 * @param gplusId of the User
	 * @return List of all UserSessions in the datastore created by the same User with gplusIdlanguage or null if nothing found
	 */
	public List<UserSession> getAllUserSessionsByUser(String gplusId) {
		List<ObjectifyableUserSession> oUserSessions = ofy().load().type(ObjectifyableUserSession.class).filter("gplusId", gplusId).list();
		Iterator<ObjectifyableUserSession> it = oUserSessions.iterator();
		List<UserSession> uSesss = new ArrayList<>();
		while (it.hasNext()) {
			uSesss.add(it.next().getUserSession());
		}
		return (uSesss.size() == 0) ? null : uSesss;
	}
	
	/**
	 * Obtains a list of all available UserSessions related to the same Session/Assignment Id in the datastore
	 * @param sessionId of the Session/Assignemnt
	 * @return List of all UserSessions in the datastore created for the same Session/Assignment or null if nothing found
	 */
	public List<UserSession> getAllUserSessionsBySession(Long sessionId) {
		List<ObjectifyableUserSession> oUserSessions = ofy().load().type(ObjectifyableUserSession.class).filter("sessionId", sessionId).list();
		Iterator<ObjectifyableUserSession> it = oUserSessions.iterator();
		List<UserSession> uSesss = new ArrayList<>();
		while (it.hasNext()) {
			uSesss.add(it.next().getUserSession());
		}
		return (uSesss.size() == 0) ? null : uSesss;
	}
	
	/**
	 * Obtains a list of all available UserSessions in the datastore
	 * @return list of all UserSessions or Null if no UserSessions in the datastore
	 */
	public List<UserSession> getAllUserSessions() {
		List<ObjectifyableUserSession> oUserSessions = ofy().load().type(ObjectifyableUserSession.class).list();
		Iterator<ObjectifyableUserSession> it = oUserSessions.iterator();
		List<UserSession> uSesss = new ArrayList<>();
		while (it.hasNext()) {
			uSesss.add(it.next().getUserSession());
		}
		return (uSesss.size() == 0) ? null : uSesss;
	}
	
	/**
	 * Deletes the UserSession with the specified sessionId from the datastore
	 * @param sessionId
	 */
	public void deleteUserSessionById(Long sessionId) {
		if (sessionId != null) {
			ofy().delete().type(ObjectifyableUserSession.class).id(sessionId).now();
		}
	}
}
