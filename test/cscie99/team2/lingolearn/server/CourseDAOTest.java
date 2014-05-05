/**
 * 
 */
package cscie99.team2.lingolearn.server;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import cscie99.team2.lingolearn.server.datastore.CourseDAO;
import cscie99.team2.lingolearn.server.datastore.UserDAO;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Gender;
import cscie99.team2.lingolearn.shared.Language;
import cscie99.team2.lingolearn.shared.OutsideCourse;
import cscie99.team2.lingolearn.shared.Textbook;
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
public class CourseDAOTest {
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

		UserDAO userAccessor = UserDAO.getInstance();
		User courseUser = userAccessor.getCourseUser();

		// Create and store 3 test Course objects
		Course c1 = new Course (11111L, "Best JP course ever", "JP101",
				new Date(), new Date());
		c1.setInstructor(courseUser);
		Course c2 = new Course (22222L, "Best JP level2 course ever", "JP102",
				new Date(), new Date()); 
		c2.setInstructor(courseUser);
		Course c3 = new Course (33333L, "Best JP level3 course ever", "JP103",
				new Date(), new Date()); 
		c3.setInstructor(courseUser);

		// Store them in the local In-memory datastore
		CourseDAO  courseAccessor = CourseDAO.getInstance();
		courseAccessor.storeCourse(c1);
		courseAccessor.storeCourse(c2);
		courseAccessor.storeCourse(c3);

		// Create and store 1 test User
		Language lang1 = new Language("1","en-us");
		Language lang2 = new Language("2","fr-ca");

		User u1 = new User("googleID", "test@gmail.com", "John",
				"Smith", Gender.Male, lang1);
		u1.addOutsideCourse(new OutsideCourse("123", "Fun Course", "Fun School"));
		u1.addTextbook(new Textbook("ABC", "Sweet Book", 2005));
		u1.addLanguages(lang2);


		// Store them in the local In-memory datastore
		userAccessor.storeUser(u1);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	/**
	 * Test Course retrieval by "courseId" 
	 */
	public void testGetById() {
		CourseDAO courseAccessor = CourseDAO.getInstance();
		Course course = null;
		long result;
		course =  courseAccessor.getCourseById(11111L);
		assertNotNull(course);
		result = course.getCourseId();
		assertEquals("Retrieval Course by courseId failed: expected 101, but obtained: " + result, result, 11111L);
	}

	@Test
	public void testGetCourseByName() {
		CourseDAO courseAccessor = CourseDAO.getInstance();
		Course course = null;
		String search = "Best JP level2 course ever";
		course =  courseAccessor.getCourseByDesc(search) ;
		assertNotNull(course);
		String result = course.getCourseDesc();
		assertEquals("Retrieval Course by course description failed, expected: " + search + " but obtained: ", result, search);
	}

	@Test
	public void testGetCourseByDesc() {
		CourseDAO courseAccessor = CourseDAO.getInstance();
		Course course = null;
		String search = "JP101";
		course =  courseAccessor.getCourseByName(search) ;
		assertNotNull(course);
		String result = course.getCourseName();
		assertEquals("Retrieval Course by course name failed, expected: " + search + " but obtained: ", result, search);
	}

	@Test
	/**
	 * Test to retrieve all Courses from datastore
	 */
	public void testGetAllCourses() {
		CourseDAO courseAccessor = CourseDAO.getInstance();
		List<Course> cList = null;
		int size = 3;
		cList = courseAccessor.getAllCourses();
		assertNotNull(cList);
		assertEquals("Retrieval of the all Courses list failed: expected " + size + " Course (s), but obtained: " + cList.size(), cList.size(), size);
	}

	@Test
	public void testGetAvailableCoursesForStudent() {
		CourseDAO courseAccessor = CourseDAO.getInstance();
		UserDAO userAccessor = UserDAO.getInstance();
		User student = userAccessor.getUserByGplusId("googleID");
		List<Course> cList = null;
		int size = 3;
		cList = courseAccessor.getAvailableCourses(student);
		assertEquals(cList.size(), size);
	}
	
	@Test
	public void testGetAvailableCoursesInstructing() {
		CourseDAO courseAccessor = CourseDAO.getInstance();
		UserDAO userAccessor = UserDAO.getInstance();
		User instructor = userAccessor.getUserByGplusId("googleID");
		List<Course> cList = null;
		int size = 0;
		// Should be 0, not instructing any course
		cList = courseAccessor.getCoursesUserIsInstructing(instructor);
		assertEquals(cList.size(), size);
	}
	
	@Test
	public void testGetAvailableCoursesEnrolled() {
		CourseDAO courseAccessor = CourseDAO.getInstance();
		UserDAO userAccessor = UserDAO.getInstance();
		User student = userAccessor.getUserByGplusId("googleID");
		List<Course> cList = null;
		int size = 0;
		// Should be 0, not enrolled in any course
		cList = courseAccessor.getStudentEnrolledCourses(student);
		assertEquals(cList.size(), size);
	}
	
	@Test
	/**
	 * Test delete Course by courseId
	 */
	public void testDeleteCourseById() {
		CourseDAO courseAccessor = CourseDAO.getInstance();
		Course course = null;
		// Delete Course with courseId = 11111
		courseAccessor.deleteCourseById(11111L);
		// Retrieve non-existing Course with courseId = 11111
		course = courseAccessor.getCourseById(11111L);
		assertNull(course);
	}
}
