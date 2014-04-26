package cscie99.team2.lingolearn.shared;

import java.io.Serializable;

/**
 * This class represents a Test. It extends the Session class.
 */
public class CourseTest extends Session implements Serializable {

	private static final long serialVersionUID = 8855393807202007560L;
	
	private int 	timeLimit;		// Time limit (minutes) allowed to take this test
	private Float	grade;			// Resulting grade (percent value) for obtained during this session
	
	public CourseTest() {
		super();
	}

	public CourseTest(Long sessId, Deck deck, Long courseId) {
		super(sessId, deck, courseId);
	}
	
	public CourseTest(Long sessId, Deck deck, Long courseId, int timeLimit, Float grade) {
		super(sessId, deck, courseId);
		this.timeLimit = timeLimit;
		this.grade = grade;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Float getGrade() {
		return grade;
	}

	public void setGrade(Float grade) {
		this.grade = grade;
	}
}
