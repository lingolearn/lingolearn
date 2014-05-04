/**
 * 
 */
package cscie99.team2.lingolearn.server;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import cscie99.team2.lingolearn.server.datastore.CourseDAO;
import cscie99.team2.lingolearn.server.datastore.CourseRegistrationDAO;
import cscie99.team2.lingolearn.server.datastore.UserDAO;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.CourseRegistration;
import cscie99.team2.lingolearn.shared.Gender;
import cscie99.team2.lingolearn.shared.Language;
import cscie99.team2.lingolearn.shared.User;

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

		// Create and store 5 test CourseRegistration objects
		CourseRegistration cr1 = new CourseRegistration (111L, "gplusId", 101L);
		CourseRegistration cr2 = new CourseRegistration (112L, "gplusId", 102L);
		CourseRegistration cr3 = new CourseRegistration (113L, "gplusId", 103L);
		CourseRegistration cr4 = new CourseRegistration (114L, "gplusId2", 101L);
		CourseRegistration cr5 = new CourseRegistration (115L, "gplusId2", 102L);
		
		// Store them in the local In-memory datastore
		CourseRegistrationDAO  courseRegAccessor = CourseRegistrationDAO.getInstance();
		courseRegAccessor.storeCourseRegistration(cr1);
		courseRegAccessor.storeCourseRegistration(cr2);
		courseRegAccessor.storeCourseRegistration(cr3);
		courseRegAccessor.storeCourseRegistration(cr4);
		courseRegAccessor.storeCourseRegistration(cr5);
		
		// Create 2 users
		Language lang1 = new Language("1","en-us");
		Language lang2 = new Language("2","fr-ca");
		User u1 = new User("gplusId", "test1@gmail.com", "Joe",
				"Smith", Gender.Male, lang1);
		
		User u2 = new User("gplusId2", "test2@gmail.com", "Josiane",
				"Cadieux", Gender.Female, lang2);
		
		// Store them in the local In-memory datastore
		UserDAO userAccessor = UserDAO.getInstance();
		User instr = userAccessor.storeUser(u1);
		User student = userAccessor.storeUser(u2);
		
		// Create and store 3 test Course objects

		Course c1 = new Course (101L, "Best JP course ever", "JP101",
				new Date(), new Date(), instr); 
		c1.addStudent(student);
		Course c2 = new Course (102L, "Best JP level2 course ever", "JP102",
				new Date(), new Date(), instr); 
		c2.addStudent(student);
		Course c3 = new Course (103L, "Best JP level3 course ever", "JP103",
				new Date(), new Date(), instr);
		c3.addStudent(student);
		
		// Store them in the local In-memory datastore
		CourseDAO courseAccessor = CourseDAO.getInstance();
		courseAccessor.storeCourse(c1);
		courseAccessor.storeCourse(c2);
		courseAccessor.storeCourse(c3);
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
		List<CourseRegistration> cReg = null;
		String gplusId = "gplusId";
		int size = 3;
		cReg = courseRegAccessor.getCourseRegistrationByUserId(gplusId);
		assertNotNull(cReg);
		// The CourseRegistration list should contain 3 elements
		assertEquals("Retrieval of the CoursesRegistration failed: expected " + size + " CourseReservations (s), but obtained: " + cReg.size(), cReg.size(), size);
	}

	@Test
	/**
	 * Test to retrieve all CoursesRegistration from datastore for the specified Course Id
	 */
	public void CourseRegByCourseId() {
		CourseRegistrationDAO  courseRegAccessor = CourseRegistrationDAO.getInstance();
		List<CourseRegistration> cReg = null;
		Long courseId = 101L;
		int size = 2;
		cReg = courseRegAccessor.getCourseRegistrationByCourseId(courseId);
		assertNotNull(cReg);
		assertEquals("Retrieval of the CoursesRegistration failed: expected " + size + " CourseReservations (s), but obtained: " + cReg.size(), cReg.size(), size);
	}
	
	@Test
	/**
	 * Test to retrieve all Users from datastore for the specified Course Id
	 */
	public void UserRegisteredByCourseId() {
		CourseRegistrationDAO  courseRegAccessor = CourseRegistrationDAO.getInstance();
		List<User> uList = null;
		Long courseId = 101L;
		int size = 2;
		uList = courseRegAccessor.getUserCourseId(courseId);
		assertNotNull(uList);
		assertEquals("Retrieval of the Users failed: expected " + size + " CourseReservations (s), but obtained: " + uList.size(), uList.size(), size);
	}
	
	@Test
	/**
	 * Test to retrieve all Users from datastore for the specified Course Id
	 */
	public void GetAllCourseRegistrations() {
		CourseRegistrationDAO  courseRegAccessor = CourseRegistrationDAO.getInstance();
		List<CourseRegistration> cReg = null;
		int size = 5;
		cReg = courseRegAccessor.getAllCourseRegistrations();
		assertNotNull(cReg);
		// The CourseRegistration list should contain 5 elements
		assertEquals("Retrieval of the CoursesRegistration failed: expected " + size + " CourseReservations (s), but obtained: " + cReg.size(), cReg.size(), size);
	}
	
	@Test
	/**
	 * Test delete CourseRegistration by courseRegId;
	 */
	public void testDeleteUserById() {
		CourseRegistrationDAO  courseRegAccessor = CourseRegistrationDAO.getInstance();
		CourseRegistration courseReg = null;
		Long courseRegId = 111L;
		// Delete CourseRegistration with id 111
		courseRegAccessor.deleteCourseRegistrationById(courseRegId);
		// Retrieve non-existing CourseRegistration with gplusId
		courseReg = courseRegAccessor.getCourseRegistrationById(courseRegId);
		assertNull(courseReg);
	}
}
