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
import cscie99.team2.lingolearn.shared.Quiz;


/**
 * @author YPolyanskyy
 *
 * This class represents Proxy for Quiz
 */
@Entity (name="ObjectifyableQuiz")
@Index
public class ObjectifyableQuiz extends ObjectifyableSession implements Serializable {

	private static final long serialVersionUID = -2460916898532300742L;
	
	private String mode;		// Defines if the quiz should use confuser algorithm

	public ObjectifyableQuiz() {};
	
	/**
	 * This method creates Objectifyable Proxy from real object
	 * @param q		Lesson object
	 */
	public ObjectifyableQuiz (Quiz q) {
		this.sessId = q.getSessionId();
		this.courseId = q.getCourseId();
		this.mode = q.getMode();
		Deck deck = q.getDeck();
		if (deck != null) { 
			ObjectifyableDeck oDeck = new ObjectifyableDeck(deck);
			ofy().save().entity(oDeck).now();
			this.deck = Ref.create(oDeck);
		} else {
			this.deck = null;
		}
	}

	public Quiz getQuiz() {
		Quiz q = new Quiz();
		q.setSessionId(this.sessId);
		q.setCourseId(this.courseId);
		q.setMode(this.mode);
		if (this.deck != null) {
			q.setDeck(this.deck.get().getDeck());
		} else {
			q.setDeck(null);
		}
		return q;
	}
}
