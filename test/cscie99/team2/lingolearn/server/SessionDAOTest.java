/**
 * 
 */
package cscie99.team2.lingolearn.server;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import cscie99.team2.lingolearn.server.datastore.LessonDAO;
import cscie99.team2.lingolearn.server.datastore.QuizDAO;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Quiz;

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
public class SessionDAOTest {
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

		// Create and store 3 test Lessons, 2 Quizes and 2 Tests objects
		Deck d1 = new Deck();
		Deck d2 = new Deck();
		d1.setId(101L);
		d2.setId(102L);
		Lesson l1 = new Lesson(111L, d1, 101L);
		Lesson l2 = new Lesson(112L, d2, 102L);
		Lesson l3 = new Lesson(113L, d1, 103L);
		Quiz q1 = new Quiz(114L, d1, 104L, "learn");
		Quiz q2 = new Quiz(115L, d1, 104L, "learn");
		//Test t1 = new Test(116L, d1, 105L, 30, null);

		// Store them in the local In-memory datastore
		LessonDAO lessonAccessor = LessonDAO.getInstance();
		lessonAccessor.storeLesson(l1);
		lessonAccessor.storeLesson(l2);
		lessonAccessor.storeLesson(l3);
		
		QuizDAO quizAccessor = QuizDAO.getInstance();
		quizAccessor.storeQuiz(q1);
		quizAccessor.storeQuiz(q2);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	/**
	 * Test Lesson retrieval by "lessonId" 
	 */
	public void testGetLessonById() {
		LessonDAO lessonAccessor = LessonDAO.getInstance();
		Lesson lesson = null;
		Long result = null;

		lesson =  lessonAccessor.getLessonById(111L);
		if (lesson == null) {
			assertTrue(false);
		} else {
			result = lesson.getSessionId();
			assertTrue("Retrieval Lesson by lessonId failed: expected 111, but obtained: " + result, result == 111L);
		}
	}

	@Test
	/**
	 * Test Quiz retrieval by "quizId" 
	 */
	public void testGetQuizById() {
		QuizDAO quizAccessor = QuizDAO.getInstance();
		Quiz quiz = null;
		Long result = null;

		quiz =  quizAccessor.getQuizById(114L);
		if (quiz == null) {
			assertTrue(false);
		} else {
			result = quiz.getSessionId();
			assertTrue("Retrieval Lesson by lessonId failed: expected 114, but obtained: " + result, result == 114L);
		}
	}
	
	@Test
	public void testGetLessonsByCourseId() {
		LessonDAO lessonAccessor = LessonDAO.getInstance();
		List<Lesson> lList = null;
		int size = 1;

		lList = lessonAccessor.getAllLessonsByCourseId(101L);
		if (lList != null) {
			// The list should contain 1 Lesson objects
			assertTrue("Retrieval of the all Lessons list failed: expected " + size + " Lesson(s), but obtained: " + lList.size(), lList.size() == size);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	public void testGetQuizesByCourseId() {
		QuizDAO quizAccessor = QuizDAO.getInstance();
		List<Quiz> qList = null;
		int size = 2;

		qList = quizAccessor.getAllQuizsByCourseId(104L);
		if (qList != null) {
			// The list should contain 2 Quiz objects
			assertTrue("Retrieval of the all Quiz list failed: expected " + size + " Lesson(s), but obtained: " + qList.size(), qList.size() == size);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	public void testGetAllLessons() {
		LessonDAO lessonAccessor = LessonDAO.getInstance();
		List<Lesson> lList = null;
		int size = 3;

		lList = lessonAccessor.getAllLessons();
		if (lList != null) {
			// The list should contain 3 Lesson objects
			assertTrue("Retrieval of the all Lessons list failed: expected " + size + " Lesson(s), but obtained: " + lList.size(), lList.size() == size);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	public void testGetAllQuizes() {
		QuizDAO quizAccessor = QuizDAO.getInstance();
		List<Quiz> qList = null;
		int size = 2;

		qList = quizAccessor.getAllQuizs();
		if (qList != null) {
			// The list should contain 2 Quiz objects
			assertTrue("Retrieval of the all Quiz list failed: expected " + size + " Quiz(s), but obtained: " + qList.size(), qList.size() == size);
		} else {
			assertTrue(false);
		}
	}

	@Test
	/**
	 * Test delete Lesson by lessonId
	 */
	public void testDeleteLessonById() {
		LessonDAO lessonAccessor = LessonDAO.getInstance();
		Lesson lesson = null;
		// Delete Lesson with lessonId = 111
		lessonAccessor.deleteLessonById(111L);
		// Retrieve non-existing Lesson with lessonId = 111
		lesson = lessonAccessor.getLessonById(111L);
		if (lesson != null) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}
	
	@Test
	/**
	 * Test delete Quiz by quizId
	 */
	public void testDeleteQuizById() {
		QuizDAO quizAccessor = QuizDAO.getInstance();
		Quiz quiz = null;
		// Delete quiz with quizId = 114
		quizAccessor.deleteQuizById(114L);
		// Retrieve non-existing Lesson with quizId = 114
		quiz = quizAccessor.getQuizById(114L);
		if (quiz != null) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}
	
}
