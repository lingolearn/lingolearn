/**
 * 
 */
package cscie99.team2.lingolearn.server;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import cscie99.team2.lingolearn.server.datastore.CourseRegistrationDAO;
import cscie99.team2.lingolearn.shared.CourseRegistration;

/**
 * @author YPolyanskyy
 * 
 * Unit tests for FlashCardResponsDAO class.
 * 
 * Make sure the following jars are in the classpath:
 * 
 * ${SDK_ROOT}/lib/testing/appengine-testing.jar
 * ${SDK_ROOT}/lib/impl/appengine-api.jar
 * ${SDK_ROOT}/lib/impl/appengine-api-labs.jar
 * ${SDK_ROOT}/lib/impl/appengine-api-stubs.jar
 * 
 */
public class CourseRegistrationDAOTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		helper.setUp();

		// Create and store 1 test CourseRegistration objects
		Set<Long> courseIdList1 = new HashSet<Long>();
		courseIdList1.add(111L);
		courseIdList1.add(112L);
		courseIdList1.add(113L);

		CourseRegistration cr1 = new CourseRegistration ("gplusId", courseIdList1);

		// Store them in the local In-memory datastore
		CourseRegistrationDAO  courseRegAccessor = CourseRegistrationDAO.getInstance();
		courseRegAccessor.storeCourseRegistration(cr1);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	/**
	 * Test to retrieve all CoursesRegistration from datastore for the specified User Id
	 */
	public void CourseRegByUserId() {
		CourseRegistrationDAO  courseRegAccessor = CourseRegistrationDAO.getInstance();
		CourseRegistration cReg = null;
		String gplusId = "gplusId";
		int size = 3;

		cReg = courseRegAccessor.getCourseRegistrationById(gplusId);
		if (cReg != null) {
			// The CourseRegistration list should contain 3 Course Id
			assertTrue("Retrieval of the CoursesRegistration failed: expected " + size + " Course (s), but obtained: " + cReg.getCourseIdList().size(), cReg.getCourseIdList().size() == size);
		} else {
			assertTrue(false);
		}
	}

	@Test
	/**
	 * Test delete CourseRegistration by gplusId
	 */
	public void testDeleteUserById() {
		CourseRegistrationDAO  courseRegAccessor = CourseRegistrationDAO.getInstance();
		CourseRegistration courseReg = null;
		String gplusId = "gplusId";
		// Delete CourseRegistration with gplusId
		courseRegAccessor.deleteCourseRegistrationById(gplusId);
		// Retrieve non-existing CourseRegistration with gplusId
		courseReg = courseRegAccessor.getCourseRegistrationById(gplusId);
		if (courseReg != null) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}
}
