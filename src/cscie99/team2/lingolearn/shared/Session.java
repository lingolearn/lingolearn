/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.shared;

import java.io.Serializable;
import java.util.Date;


/**
 * @author YPolyanskyy
 * 
 * This abstract class represents Session of the system. It defines common elements of all sessions.
 * Required for implementation of Lesson, Quiz, Test.
 */
public abstract class Session implements Serializable {

	private String sessId;		// Session Id
	private Date   sessStart,	// Timestamp of the session's start
				   sessEnd;		// Timestamp of the session's end
	
	private Deck   deck;		// Deck associated with this session
	private Course courseId;	// Course Id that this session belongs to
	private String gplusId;	 	//id of the user in the session
	
	/**
	 * This method interacts with DeckMng to obtain the deck for this session
	 * 
	 * @return	Deck of the cards for this session
	 */
	public Deck getDeck() {
		return deck;
	}
	
	public void setDeck(Deck deck) {
		this.deck = deck;
	}
	
	public String getSessionId() {
		return sessId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessId = sessionId;
	}
	
}
