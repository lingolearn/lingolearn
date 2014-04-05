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

	private Session session;	// Session
	private Date    sessStart,	// Timestamp of the session's start
				    sessEnd;    // Timestamp of the session's end
	private User    user;       // User 
	
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	
	
	
}
