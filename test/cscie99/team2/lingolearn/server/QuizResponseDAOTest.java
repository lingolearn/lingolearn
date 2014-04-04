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
		QuizResponse qr1 = new QuizResponse(111L, 101L, "gplusId",
				"difficultConfuser", false, false, 2.5f);
		QuizResponse qr2 = new QuizResponse(112L, 102L, "gplusId",
				"simpleConfuser", true, false, 1.5f);
		QuizResponse qr3 = new QuizResponse(113L, 103L, "gplusId2",
				"difficultConfuser", true, false, 4.5f);
		
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
