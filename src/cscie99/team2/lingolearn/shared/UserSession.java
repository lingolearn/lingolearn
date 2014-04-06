/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.shared;

import java.io.Serializable;
import java.util.Date;


/**
 * A user's 
 */
public abstract class UserSession implements Serializable {

	private Long    sessionId;	// Session id
	private Date    sessStart,	// Timestamp of the session's start
				    sessEnd;    // Timestamp of the session's end
	private String  userId;       // User id

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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getSessionId() {
		return sessionId;
	}
	public void setSession(Long sessionId) {
		this.sessionId = sessionId;
	}

}