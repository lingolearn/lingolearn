/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.io.Serializable;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.CourseTest;


/**
 * @author YPolyanskyy
 *
 * This class represents Proxy for CourseTest
 */
@Entity (name="ObjectifyableCourseTest")
@Index
public class ObjectifyableCourseTest extends ObjectifyableSession implements Serializable {

	private static final long serialVersionUID = 6856444795214987148L;

	private int 	timeLimit;		// Time limit (minutes) allowed to take this test
	private Float	grade;			// Resulting grade (percent value) for obtained during this session

	public ObjectifyableCourseTest() {};
	
	/**
	 * This method creates Objectifyable Proxy from real object
	 * @param t		Test object
	 */
	public ObjectifyableCourseTest (CourseTest t) {
		this.sessId = t.getSessionId();
		this.courseId = t.getCourseId();
		this.timeLimit = t.getTimeLimit();
		this.grade = t.getGrade();
		Deck deck = t.getDeck();
		if (deck != null) { 
			ObjectifyableDeck oDeck = new ObjectifyableDeck(deck);
			ofy().save().entity(oDeck).now();
			this.deck = Ref.create(oDeck);
		} else {
			this.deck = null;
		}
	}

	public CourseTest getTest() {
		CourseTest t = new CourseTest();
		t.setSessionId(this.sessId);
		t.setCourseId(this.courseId);
		t.setTimeLimit(this.timeLimit);
		t.setGrade(this.grade);
		if (this.deck != null) {
			t.setDeck(this.deck.get().getDeck());
		} else {
			t.setDeck(null);
		}
		return t;
	}
}
