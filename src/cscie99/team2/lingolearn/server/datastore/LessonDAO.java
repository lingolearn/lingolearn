package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cscie99.team2.lingolearn.server.datastore.ObjectifyableLesson;
import cscie99.team2.lingolearn.shared.Lesson;

public class LessonDAO {

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class LessonDAOHolder { 
		public static final LessonDAO INSTANCE = new LessonDAO();
	}

	public static LessonDAO getInstance() {
		return LessonDAOHolder.INSTANCE;
	}   
	
	/**
	 * This method stores the Lesson in the datastore
	 * @param course Lesson to be stored in the datastore
	 * @return stored Lesson for diagnostic purpose
	 */
	public Lesson storeLesson(Lesson lesson) {
		ObjectifyableLesson oLesson = new ObjectifyableLesson(lesson); 
		ofy().save().entity(oLesson).now();
		ObjectifyableLesson fetched = ofy().load().entity(oLesson).now();
		Lesson rlesson = fetched.getLesson();
		return rlesson;
	}
	
	/**
	 * Obtains Lesson by LessonId
	 * @param courseId courseId
	 * @return Lesson stored with the courseId or null if not found
	 */
	public Lesson getLessonById(Long sessId) {
		ObjectifyableLesson oLesson = ofy().load().type(ObjectifyableLesson.class).id(sessId).now();
		if (oLesson != null) {
			Lesson lesson = oLesson.getLesson();
			return lesson;
		}
		return null;
	}
	
	public List<Lesson> getAllLessonsByCourseId(Long courseId) {
		List<ObjectifyableLesson> oLessons = ofy().load().type(ObjectifyableLesson.class).filter("courseId", courseId).list();
		Iterator<ObjectifyableLesson> it = oLessons.iterator();
		List<Lesson> lessons = new ArrayList<>();
		while (it.hasNext()) {
			lessons.add(it.next().getLesson());
		}
		if (lessons.size() == 0) {
			return null;
		} else {
			return lessons;
		}
	}
	
	
	public List<Lesson> getAllLessons() {
		List<ObjectifyableLesson> oLessons = ofy().load().type(ObjectifyableLesson.class).list();

		Iterator<ObjectifyableLesson> it = oLessons.iterator();
		List<Lesson> lessons = new ArrayList<>();
		while (it.hasNext()) {
			lessons.add(it.next().getLesson());
		}
		if (lessons.size() == 0) {
			return null;
		} else {
			return lessons;
		}
	}
	
	
	/**
	 * Deletes the Lesson with the specified LessonId from the datastore
	 * @param sessId
	 */
	public void deleteLessonById(Long sessId) {
		ofy().delete().type(ObjectifyableLesson.class).id(sessId).now();
	}
}
