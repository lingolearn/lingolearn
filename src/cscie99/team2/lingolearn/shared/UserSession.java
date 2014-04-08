/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.shared;

import java.io.Serializable;
import java.util.Date;


/**
 * A user's Session with an assignment
 */
public class UserSession implements Serializable {

	private static final long serialVersionUID = 2027043321258471702L;

	private Long    userSessionId;	// Unique UserSession id
	private Long    sessionId;		// Session(Assignement) id
	private String  gplusId;       	// User id
	private Date    sessStart,		// Timestamp of the session's start
				    sessEnd;    	// Timestamp of the session's end

	public UserSession() {};
	
	public UserSession(Long userSessionId, Long sessionId, String gplusId,
			Date sessStart, Date sessEnd) {
		this.userSessionId = userSessionId;
		this.sessionId = sessionId;
		this.gplusId = gplusId;
		this.sessStart = sessStart;
		this.sessEnd = sessEnd;
	}
	
	public Date getSessStart() {
		return sessStart;
	}
	public void setSessStart(Date sessStart) {
		this.sessStart = sessStart;
	}
	public Date getSessEnd() {
		return sessEnd;
	}
	public void setSessEnd(Date sessEnd) {
		this.sessEnd = sessEnd;
	}
	public Long getUserSessionId() {
		return userSessionId;
	}
	public void setUserSessionId(Long userSessionId) {
		this.userSessionId = userSessionId;
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
	
	@Override
	public String toString() {
		String result = null;
		result  = "UserSession Id : " + this.userSessionId + "\n";
		result += "Session Id     : " + this.getSessionId() + "\n";
		result += "GplusId        : " + this.getGplusId() + "\n";
		result += "Start Time     : " + this.getSessStart() + "\n";
		result += "Stop Time      : " + this.getSessEnd() + "\n";
		return result;
	}
}