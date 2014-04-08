/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.shared;

import java.io.Serializable;


/**
 * @author YPolyanskyy
 * 
 * This class represents Session of the system. It defines common elements of all sessions.
 * Required for implementation of Lesson, Quiz, Test.
 */
public abstract class Session implements Serializable {
	private static final long serialVersionUID = -8324973450477098118L;

	private Long   sessId;	// Unique Session / Assignment Id
	private Deck    deck;	// Deck associated with this session
	private Long courseId;	// Course Id that this session belongs to
	
	public Session() {};
	
	public Session(Long sessId, Deck deck, Long courseId) {
		this.sessId = sessId;
		this.deck = deck;
		this.courseId = courseId;
	}
	
	
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
	
	public Long getSessionId() {
		return sessId;
	}
	
	public void setSessionId(Long sessionId) {
		this.sessId = sessionId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
}
