package cscie99.team2.lingolearn.shared;

import java.io.Serializable;

/**
 * This class represents a lesson. It extends the Session class.
 */
public class Lesson extends Session implements Serializable {

	private static final long serialVersionUID = -4494986159570600954L;	
	
	public Lesson() {
		super();
	}

	public Lesson(Long sessId, Deck deck, Long courseId) {
		super(sessId, deck, courseId);
	}
	
	public Lesson(Long sessId, Deck deck, Long courseId, 
																SessionTypes sessionType) {
		super(sessId, deck, courseId, sessionType );
	}
}
