package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.io.Serializable;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.Lesson;

/**
 * This class represents Proxy for Lesson
 */
@Entity(name="ObjectifyableLesson")
@Index
public class ObjectifyableLesson extends ObjectifyableSession implements Serializable {

	private static final long serialVersionUID = -4494986159570600954L;	
	
	public ObjectifyableLesson() {};
	
	/**
	 * This method creates Objectifyable Proxy from real object
	 * @param l		Lesson object
	 */
	public ObjectifyableLesson (Lesson l) {
		this.sessId = l.getSessionId();
		this.courseId = l.getCourseId();
		this.deck = null;
		
		Deck deck = l.getDeck();
		if (deck != null) { 
			ObjectifyableDeck oDeck = new ObjectifyableDeck(deck);
			ofy().save().entity(oDeck).now();
			this.deck = Ref.create(oDeck);
		}
	}

	public Lesson getLesson() {
		Lesson l = new Lesson();
		l.setSessionId(this.sessId);
		l.setCourseId(this.courseId);
		if (this.deck != null) {
			l.setDeck(this.deck.get().getDeck());
		} else {
			l.setDeck(null);
		}
		return l;
	}
}
