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
import cscie99.team2.lingolearn.shared.Course;

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

		// Create and store 3 test Course objects
		Course c1 = new Course (11111L, "Best JP course ever", "JP101",
				new Date(), new Date()); 
		Course c2 = new Course (22222L, "Best JP level2 course ever", "JP102",
				new Date(), new Date()); 
		Course c3 = new Course (33333L, "Best JP level3 course ever", "JP103",
				new Date(), new Date()); 
		
		// Store them in the local In-memory datastore
		CourseDAO  courseAccessor = CourseDAO.getInstance();
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
	 * Test Course retrieval by "courseId" 
	 */
	public void testGetById() {
		CourseDAO courseAccessor = CourseDAO.getInstance();
		Course course = null;
		Long result = null;

		course =  courseAccessor.getCourseById(11111L);
		if (course == null) {
			assertTrue(false);
		} else {
		result = course.getCourseId();
		assertTrue("Retrieval Course by courseId failed: expected 101, but obtained: " + result, result == 11111L);
		}
	}
	
	@Test
	public void testGetCourseByName() {
		CourseDAO courseAccessor = CourseDAO.getInstance();
		Course course = null;
		String search = "Best JP level2 course ever";

		course =  courseAccessor.getCourseByDesc(search) ;
		if (course == null) {
			assertTrue(false);
		} else {
		String result = course.getCourseDesc();
		assertTrue("Retrieval Course by course description failed, expected: " + search + " but obtained: " + result, result.equals(search));
		}
	}
	
	@Test
	public void testGetCourseByDesc() {
		CourseDAO courseAccessor = CourseDAO.getInstance();
		Course course = null;
		String search = "JP101";

		course =  courseAccessor.getCourseByName(search) ;
		if (course == null) {
			assertTrue(false);
		} else {
		String result = course.getCourseName();
		assertTrue("Retrieval Course by course name failed, expected: " + search + " but obtained: " + result, result.equals(search));
		}
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
		if (cList != null) {
			// The list should contain 3 Course objects
			assertTrue("Retrieval of the all Courses list failed: expected " + size + " Course (s), but obtained: " + cList.size(), cList.size() == size);
		} else {
			assertTrue(false);
		}
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
		  if (course != null) {
			  assertTrue(false);
		  } else {
			  assertTrue(true);
		  }
	  }
}
