package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cscie99.team2.lingolearn.server.datastore.ObjectifyableCourse;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.CourseRegistration;
import cscie99.team2.lingolearn.shared.User;
import cscie99.team2.lingolearn.shared.error.CardNotFoundException;

public class CourseRegistrationDAO {

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 * *****
	 */
	private static class CourseRegistrationDAOHolder { 
		public static final CourseRegistrationDAO INSTANCE = new CourseRegistrationDAO();
	}

	public static CourseRegistrationDAO getInstance() {
		return CourseRegistrationDAOHolder.INSTANCE;
	}   
	
	/**
	 * This method stores the CourseRegistration in the datastore
	 * @param course CourseRegistration to be stored in the datastore
	 * @return stored CourseRegistration for diagnostic purpose
	 */
	public CourseRegistration storeCourseRegistration( CourseRegistration courseRegistration ) {
		ObjectifyableCourseRegistration oCourseRegistration = new ObjectifyableCourseRegistration(courseRegistration); 
		ofy().save().entity(oCourseRegistration).now();
		ObjectifyableCourseRegistration fetched = ofy().load().entity(oCourseRegistration).now();
		courseRegistration = fetched.getCourseRegistration();
		return courseRegistration;
	}
	
	/**
	 * Obtains CourseRegistration by UserId
	 * @param uId gplusId
	 * @return CourseRegistration stored for the userId or null if not found
	 */
	public CourseRegistration getCourseRegistrationById(String gplusId) {
		ObjectifyableCourseRegistration oCourseReg = ofy().load().type(ObjectifyableCourseRegistration.class).id(gplusId).now();
		if (oCourseReg != null) {
			CourseRegistration creg = oCourseReg.getCourseRegistration();
			return creg;
			}
		return null;
	}
	
	/**
	 * Deletes the course with the specified CourseId from the datastore
	 * @param courseId
	 */
	public void deleteCourseRegistrationById(String gplusId) {
		ofy().delete().type(ObjectifyableCourseRegistration.class).id(gplusId).now();
	}
}
