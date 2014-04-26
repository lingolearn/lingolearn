package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cscie99.team2.lingolearn.server.datastore.ObjectifyableCourseTest;
import cscie99.team2.lingolearn.shared.CourseTest;

public class CourseTestDAO {

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class TestDAOHolder { 
		public static final CourseTestDAO INSTANCE = new CourseTestDAO();
	}

	public static CourseTestDAO getInstance() {
		return TestDAOHolder.INSTANCE;
	}   
	
	/**
	 * This method stores the Test in the datastore
	 * @param course Test to be stored in the datastore
	 * @return stored Test for diagnostic purpose
	 */
	public CourseTest storeTest(CourseTest test) {
		ObjectifyableCourseTest oTest = new ObjectifyableCourseTest(test); 
		ofy().save().entity(oTest).now();
		ObjectifyableCourseTest fetched = ofy().load().entity(oTest).now();
		return fetched.getTest();
	}
	
	/**
	 * Obtains Test by TestId
	 * @param sessId sessId
	 * @return Test stored with the sessId or null if not found
	 */
	public CourseTest getTestById(Long sessId) {
		if (sessId == null) {
			return null;
		}
		ObjectifyableCourseTest oTest = ofy().load().type(ObjectifyableCourseTest.class).id(sessId).now();
		return (oTest != null) ? oTest.getTest() : null;
	}
	
	public List<CourseTest> getAllTestsByCourseId(Long courseId) {
		List<ObjectifyableCourseTest> oTests = ofy().load().type(ObjectifyableCourseTest.class).filter("courseId", courseId).list();
		Iterator<ObjectifyableCourseTest> it = oTests.iterator();
		List<CourseTest> tests = new ArrayList<>();
		while (it.hasNext()) {
			tests.add(it.next().getTest());
		}
		return (tests.size() == 0) ? null : tests;
	}
	
	
	public List<CourseTest> getAllTests() {
		List<ObjectifyableCourseTest> oTests = ofy().load().type(ObjectifyableCourseTest.class).list();

		Iterator<ObjectifyableCourseTest> it = oTests.iterator();
		List<CourseTest> tests = new ArrayList<>();
		while (it.hasNext()) {
			tests.add(it.next().getTest());
		}
		return (tests.size() == 0) ? null : tests;
	}
	
	
	/**
	 * Deletes the Test with the specified TestId from the datastore
	 * @param sessId
	 */
	public void deleteTestById(Long sessId) {
		if (sessId != null) {
			ofy().delete().type(ObjectifyableCourseTest.class).id(sessId).now();
		}
	}
}
