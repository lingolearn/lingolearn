package cscie99.team2.lingolearn.server.datastore;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * This method guarantees that the entities are registered before use of Objectify,
 * but doesn't necessarily impact application startup for requests which do not access the datastore.
 */
public class OfyService {
	static {
		factory().register(ObjectifyableCard.class);
		factory().register(ObjectifyableImage.class);
		factory().register(ObjectifyableSound.class);
		factory().register(ObjectifyableUser.class);
		factory().register(ObjectifyableDeck.class);
		factory().register(ObjectifyableQuizResponse.class);
		factory().register(ObjectifyableFlashCardResponse.class);
		factory().register(ObjectifyableCourse.class);
		factory().register(ObjectifyableCourseRegistration.class);
		factory().register(ObjectifyableUserSession.class);
		factory().register(ObjectifyableQuiz.class);
		factory().register(ObjectifyableCourseTest.class);
		factory().register(ObjectifyableLesson.class);
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}
}
