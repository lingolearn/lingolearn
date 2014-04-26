package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

/**
 *  This class represents Proxy for Session
 */
public class ObjectifyableSession implements Serializable {
	private static final long serialVersionUID = -2955237026660166613L;
	
	@Id protected Long   sessId;			// Unique Session / Assignment Id
	@Index protected Long courseId;		// Course Id that this session belongs to
	@Load Ref<ObjectifyableDeck> deck;	// Deck associated with this session
	
	public ObjectifyableSession() {};
}
