/**
 * 
 */
package cscie99.team2.lingolearn.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import cscie99.team2.lingolearn.server.datastore.QuizResponseDAO;
import cscie99.team2.lingolearn.shared.QuizResponse;

/**
 * @author YPolyanskyy
 * 
 * Unit tests for QuizResponsDAO class.
 * 
 * Make sure the following jars are in the classpath:
 * 
 * ${SDK_ROOT}/lib/testing/appengine-testing.jar
 * ${SDK_ROOT}/lib/impl/appengine-api.jar
 * ${SDK_ROOT}/lib/impl/appengine-api-labs.jar
 * ${SDK_ROOT}/lib/impl/appengine-api-stubs.jar
 * 
 */
public class QuizResponseDAOTest {
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

		// Create and store 3 test QuizResponse objects
		QuizResponse qr1 = new QuizResponse(111L, 111L, 111L, 101L, "gplusId",
				"difficultConfuser", false, false, 2.5f, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" ).parse("2014-04-15 20:27:30 EST"),3,"dog,bird,cow", "Sequence");
		QuizResponse qr2 = new QuizResponse(112L, 111L, 112L, 102L, "gplusId",
				"simpleConfuser", true, false, 1.5f, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" ).parse("2014-04-15 20:28:30 EST"));
		QuizResponse qr3 = new QuizResponse(113L, 112L, 113L, 103L, "gplusId2",
				"difficultConfuser", true, false, 4.5f, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" ).parse("2014-04-15 20:29:30 EST"));

		// Store them in the local In-memory datastore
		QuizResponseDAO qRespAccessor = QuizResponseDAO.getInstance();
		qRespAccessor.storeQuizResponse(qr1);
		qRespAccessor.storeQuizResponse(qr2);
		qRespAccessor.storeQuizResponse(qr3);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	/**
	 * Test User retrieval by "uId" 
	 */
	public void testGetById() {
		QuizResponseDAO qRespAccessor = QuizResponseDAO.getInstance();
		QuizResponse qr = null;
		Long result = null;

		qr =  qRespAccessor.getQuizResponseById(111L);
		if (qr == null) {
			assertTrue(false);
		} else {
			result = qr.getSessionId();
			assertTrue("Retrieval QuizResponse by sessionId failed: expected 101, but obtained: " + result, result == 111L);
		}
	}

	@Test
	/**
	 * Test to retrieve all QuizResponse from datastore belonging to the same User
	 */
	public void testGetAllQuizResponseByUsers() {
		QuizResponseDAO qRespAccessor = QuizResponseDAO.getInstance();
		List<QuizResponse> rList = null;
		int size = 2;

		rList = qRespAccessor.getAllQuizResponsesByUser("gplusId");
		if (rList != null) {
			// The list should contain 2 QuizResponse objects
			assertTrue("Retrieval of the all QuizResponse list failed: expected " + size + " QuizResponse (s), but obtained: " + rList.size(), rList.size() == size);
		} else {
			assertTrue(false);
		}
	}

	@Test
	/**
	 * Test to retrieve all QuizResponse from datastore
	 */
	public void testGetAllQuizResponse() {
		QuizResponseDAO qRespAccessor = QuizResponseDAO.getInstance();
		List<QuizResponse> rList = null;
		int size = 3;

		rList = qRespAccessor.getAllQuizResponses();
		if (rList != null) {
			// The list should contain 3 QuizResponse objects
			assertTrue("Retrieval of the all QuizResponse list failed: expected " + size + " QuizResponse (s), but obtained: " + rList.size(), rList.size() == size);
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testGetAllQuizResponseOn() {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" ).parse("2014-04-15 20:27:30 EST");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		QuizResponseDAO qRespAccessor = QuizResponseDAO.getInstance();
		List<QuizResponse> rList = null;
		int size = 1;

		rList = qRespAccessor.getAllQuizResponsesOn(date);
		if (rList != null) {
			// The list should contain 3 QuizResponse objects
			assertTrue("Retrieval of the all QuizResponse list failed: expected " + size + " QuizResponse (s), but obtained: " + rList.size(), rList.size() == size);
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testGetAllQuizResponseBefore() {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" ).parse("2020-10-07 20:27:30 EST");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		QuizResponseDAO qRespAccessor = QuizResponseDAO.getInstance();
		List<QuizResponse> rList = null;
		int size = 3;

		rList = qRespAccessor.getAllQuizResponsesBefore(date);
		if (rList != null) {
			// The list should contain 3 QuizResponse objects
			assertTrue("Retrieval of the all QuizResponse list failed: expected " + size + " QuizResponse (s), but obtained: " + rList.size(), rList.size() == size);
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void testGetAllQuizResponseAfter() {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" ).parse("1917-10-07 20:27:30 EST");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		QuizResponseDAO qRespAccessor = QuizResponseDAO.getInstance();
		List<QuizResponse> rList = null;
		int size = 3;

		rList = qRespAccessor.getAllQuizResponsesAfter(date);
		if (rList != null) {
			// The list should contain 3 QuizResponse objects
			assertTrue("Retrieval of the all QuizResponse list failed: expected " + size + " QuizResponse (s), but obtained: " + rList.size(), rList.size() == size);
		} else {
			assertTrue(false);
		}
	}

	@Test
	/**
	 * Test card QuizRespone by "qRespAccessor"
	 */
	public void testDeleteQuizResponseById() {
		QuizResponseDAO qRespAccessor = QuizResponseDAO.getInstance();
		QuizResponse qr = null;
		// Delete QuizResponse with sessionId = 111
		qRespAccessor.deleteQuizResponseById(111L);
		// Retrieve non-existing QuizResp with sessionId = 111
		qr = qRespAccessor.getQuizResponseById(111L);
		if (qr != null) {
			System.out.println("!!! " + qr);
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}
}
