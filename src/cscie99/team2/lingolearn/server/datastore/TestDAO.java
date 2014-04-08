package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cscie99.team2.lingolearn.server.datastore.ObjectifyableTest;
import cscie99.team2.lingolearn.shared.Test;

public class TestDAO {

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class TestDAOHolder { 
		public static final TestDAO INSTANCE = new TestDAO();
	}

	public static TestDAO getInstance() {
		return TestDAOHolder.INSTANCE;
	}   
	
	/**
	 * This method stores the Test in the datastore
	 * @param course Test to be stored in the datastore
	 * @return stored Test for diagnostic purpose
	 */
	public Test storeTest(Test test) {
		ObjectifyableTest oTest = new ObjectifyableTest(test); 
		ofy().save().entity(oTest).now();
		ObjectifyableTest fetched = ofy().load().entity(oTest).now();
		Test rtest = fetched.getTest();
		return rtest;
	}
	
	/**
	 * Obtains Test by TestId
	 * @param sessId sessId
	 * @return Test stored with the sessId or null if not found
	 */
	public Test getTestById(Long sessId) {
		ObjectifyableTest oTest = ofy().load().type(ObjectifyableTest.class).id(sessId).now();
		if (oTest != null) {
			Test test = oTest.getTest();
			return test;
		}
		return null;
	}
	
	public List<Test> getAllTestsByCourseId(Long courseId) {
		List<ObjectifyableTest> oTests = ofy().load().type(ObjectifyableTest.class).filter("courseId", courseId).list();
		Iterator<ObjectifyableTest> it = oTests.iterator();
		List<Test> tests = new ArrayList<>();
		while (it.hasNext()) {
			tests.add(it.next().getTest());
		}
		if (tests.size() == 0) {
			return null;
		} else {
			return tests;
		}
	}
	
	
	public List<Test> getAllTests() {
		List<ObjectifyableTest> oTests = ofy().load().type(ObjectifyableTest.class).list();

		Iterator<ObjectifyableTest> it = oTests.iterator();
		List<Test> tests = new ArrayList<>();
		while (it.hasNext()) {
			tests.add(it.next().getTest());
		}
		if (tests.size() == 0) {
			return null;
		} else {
			return tests;
		}
	}
	
	
	/**
	 * Deletes the Test with the specified TestId from the datastore
	 * @param sessId
	 */
	public void deleteTestById(Long sessId) {
		ofy().delete().type(ObjectifyableTest.class).id(sessId).now();
	}
}
