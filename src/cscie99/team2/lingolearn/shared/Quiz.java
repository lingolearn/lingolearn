/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.shared;

import java.io.Serializable;

/**
 * @author YPolyanskyy
 * 
 * This class represents a Quiz. It extends the Session class.
 */
public class Quiz extends Session implements Serializable {
	
	private static final long serialVersionUID = -8062629930985217357L;
	
	private String mode;		// Defines if the quiz should use confuser algorithm
	
	public Quiz() {
		super();
	}

	public Quiz(Long sessId, Deck deck, Long courseId) {
		super(sessId, deck, courseId);
	}

	public Quiz(Long sessId, Deck deck, Long courseId, String mode) {
		super(sessId, deck, courseId);
		this.mode = mode;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
