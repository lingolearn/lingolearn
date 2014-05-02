package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import cscie99.team2.lingolearn.shared.UserSession;

/**
 * This class represents Proxy for User's Session
 */
@Entity(name="ObjectifyableUserSession")
public class ObjectifyableUserSession implements Serializable {

	private static final long serialVersionUID = -2526827390464962399L;

	@Id private Long    userSessionId;	// Unique UserSession id
	@Index private Long    sessionId;	// Session(Assignement) id
	@Index private String  gplusId;      	// User id
	@Index private String sessionType;		// type of session, e.g. kanji >> translation
	private Date    sessStart,			// Timestamp of the session's start
				    sessEnd;    		// Timestamp of the session's end

	public ObjectifyableUserSession() {};
	
	/**
	 * This method creates Objectifyable Proxy from real object
	 * @param uSession		UserSession object
	 */
	public ObjectifyableUserSession(UserSession uSession) {
		this.userSessionId = uSession.getUserSessionId();
		this.sessionId = uSession.getSessionId();
		this.gplusId = uSession.getGplusId();
		this.sessionType = 
				uSession.getSessionType() != null ? 
						uSession.getSessionType().toString() : "";
		this.sessStart = uSession.getSessStart();
		this.sessEnd = uSession.getSessEnd();
	}
	
	/**
	 * This method reconstructs real object from Objectifyable Proxy
	 * @return UserSession object
	 */
	public UserSession getUserSession() {
		UserSession uSession = new UserSession();
		uSession.setUserSessionId(this.userSessionId);
		uSession.setSessionId(this.sessionId);
		uSession.setGplusId(this.gplusId);
		if( this.sessionType == null || this.sessionType.isEmpty()){
			uSession.setSessionType("");
		}else{
			uSession.setSessionType(this.sessionType);
		}
		uSession.setSessStart(this.sessStart);
		uSession.setSessEnd(this.sessEnd);
		return uSession;
	}
}