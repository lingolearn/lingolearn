package cscie99.team2.lingolearn.shared;

import java.io.Serializable;

/**
 * This class represents Session of the system. It defines common elements of all sessions.
 * Required for implementation of Lesson, Quiz, Test.
 */
public abstract class Session implements Serializable {
	private static final long serialVersionUID = -8324973450477098118L;

	private Long   sessId;	// Unique Session / Assignment Id
	private Deck    deck;	// Deck associated with this session
	private Long courseId;	// Course Id that this session belongs to
	private String sessionType;	// type of session, e.g. kanji >> translation
	
	public Session() {};
	
	public Session(Long sessId, Deck deck, Long courseId, String sessionType) {
		this.sessId = sessId;
		this.deck = deck;
		this.courseId = courseId;
		this.sessionType = sessionType;
	}
	
	public Session(Long sessId, Deck deck, Long courseId,
																				SessionTypes sessionType ) {
		this(sessId, deck, courseId, sessionType.toString());
	}
	
	public Session(Long sessId, Deck deck, Long courseId) {
		this(sessId, deck, courseId, "");
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
	
	public SessionTypes getSessionType() {
		return SessionTypes.valueOf(sessionType);
	}

	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}

	public void setSessionType(SessionTypes type){
		this.sessionType = type.toString();
	}
	
}
