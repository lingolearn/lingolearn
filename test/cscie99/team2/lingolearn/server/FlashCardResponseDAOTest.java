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

import cscie99.team2.lingolearn.server.datastore.FlashCardResponseDAO;
import cscie99.team2.lingolearn.shared.Assessment;
import cscie99.team2.lingolearn.shared.FlashCardResponse;

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
public class FlashCardResponseDAOTest {
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

		// Create and store 3 test FlashCardResponse objects
		FlashCardResponse fcr1 = new FlashCardResponse(111L, 111L, 111L, 101L, "gplusId",
				"difficultConfuser", 3, 3, false, 2.5f, Assessment.SORTAKNEWIT, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" ).parse("2014-04-15 20:27:30 EST"), "Sequence");
		FlashCardResponse fcr2 = new FlashCardResponse(112L, 111L, 112L, 102L, "gplusId",
				"difficultConfuser", 2, 2, false, 12.5f, Assessment.DEFINITELYKNEWIT, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" ).parse("2014-04-15 20:28:30 EST"));
		FlashCardResponse fcr3 = new FlashCardResponse(113L, 112L, 113L, 102L, "gplusId2",
				"easyConfuser", 1, 2, false, 1.5f, Assessment.DEFINITELYKNEWIT, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" ).parse("2014-04-15 20:29:30 EST"));

		// Store them in the local In-memory datastore
		FlashCardResponseDAO qRespAccessor = FlashCardResponseDAO.getInstance();
		qRespAccessor.storeFlashCardResponse(fcr1);
		qRespAccessor.storeFlashCardResponse(fcr2);
		qRespAccessor.storeFlashCardResponse(fcr3);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	/**
	 * Test FlashCardResponse retrieval by "sessionId" 
	 */
	public void testGetById() {
		FlashCardResponseDAO qRespAccessor = FlashCardResponseDAO.getInstance();
		FlashCardResponse fcr = null;
		Long result = null;

		fcr =  qRespAccessor.getFlashCardResponseById(111L);
		if (fcr == null) {
			assertTrue(false);
		} else {
			result = fcr.getSessionId();
			assertTrue("Retrieval FlashCardResponse by sessionId failed: expected 101, but obtained: " + result, result == 111L);
		}
	}

	@Test
	/**
	 * Test to retrieve all FlashCardResponse from datastore belonging to the same User
	 */
	public void testGetAllFlashCardResponseByUsers() {
		FlashCardResponseDAO qRespAccessor = FlashCardResponseDAO.getInstance();
		List<FlashCardResponse> rList = null;
		int size = 2;

		rList = qRespAccessor.getAllFlashCardResponsesByUser("gplusId");
		if (rList != null) {
			// The list should contain 2 FlashCardResponse objects
			assertTrue("Retrieval of the all FlashCardResponse list failed: expected " + size + " FlashCardResponse (s), but obtained: " + rList.size(), rList.size() == size);
		} else {
			assertTrue(false);
		}
	}

	@Test
	/**
	 * Test to retrieve all FlashCardResponse from datastore
	 */
	public void testGetAllFlashCardResponse() {
		FlashCardResponseDAO qRespAccessor = FlashCardResponseDAO.getInstance();
		List<FlashCardResponse> rList = null;
		int size = 3;

		rList = qRespAccessor.getAllFlashCardResponses();
		if (rList != null) {
			// The list should contain 3 FlashCardResponse objects
			assertTrue("Retrieval of the all FlashCardResponse list failed: expected " + size + " FlashCardResponse (s), but obtained: " + rList.size(), rList.size() == size);
		} else {
			assertTrue(false);
		}
	}


	@Test
	/**
	 * Test to retrieve all FlashCardResponse from datastore
	 */
	public void testGetAllFlashCardResponseOn() {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" ).parse("2014-04-15 20:27:30 EST");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		FlashCardResponseDAO qRespAccessor = FlashCardResponseDAO.getInstance();
		List<FlashCardResponse> rList = null;
		int size = 1;

		rList = qRespAccessor.getAllFlashCardResponsesOn(date);
		if (rList != null) {
			// The list should contain 1 FlashCardResponse object(s)
			assertTrue("Retrieval of the all FlashCardResponse list failed: expected " + size + " FlashCardResponse(s), but obtained: " + rList.size(), rList.size() == size);
		} else {
			assertTrue(false);
		}
	}

	@Test
	/**
	 * Test to retrieve all FlashCardResponse from datastore
	 */
	public void testGetAllFlashCardResponseAfter() {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" ).parse("1917-10-07 20:27:30 EST");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		FlashCardResponseDAO qRespAccessor = FlashCardResponseDAO.getInstance();
		List<FlashCardResponse> rList = null;
		int size = 3;

		rList = qRespAccessor.getAllFlashCardResponsesAfter(date);
		if (rList != null) {
			// The list should contain 1 FlashCardResponse object(s)
			assertTrue("Retrieval of the all FlashCardResponse list failed: expected " + size + " FlashCardResponse(s), but obtained: " + rList.size(), rList.size() == size);
		} else {
			assertTrue(false);
		}
	}

	@Test
	/**
	 * Test to retrieve all FlashCardResponse from datastore
	 */
	public void testGetAllFlashCardResponseBefore() {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" ).parse("2020-10-07 20:27:30 EST");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		FlashCardResponseDAO qRespAccessor = FlashCardResponseDAO.getInstance();
		List<FlashCardResponse> rList = null;
		int size = 3;

		rList = qRespAccessor.getAllFlashCardResponsesBefore(date);
		if (rList != null) {
			// The list should contain 1 FlashCardResponse object(s)
			assertTrue("Retrieval of the all FlashCardResponse list failed: expected " + size + " FlashCardResponse(s), but obtained: " + rList.size(), rList.size() == size);
		} else {
			assertTrue(false);
		}
	}

	@Test
	/**
	 * Test card FlashCardRespone by "qRespAccessor"
	 */
	public void testDeleteFlashCardResponseById() {
		FlashCardResponseDAO qRespAccessor = FlashCardResponseDAO.getInstance();
		FlashCardResponse fcr = null;
		// Delete FlashCardResponse with sessionId = 111
		qRespAccessor.deleteFlashCardResponseById(111L);
		// Retrieve non-existing FlashCardResp with sessionId = 111
		fcr = qRespAccessor.getFlashCardResponseById(111L);
		if (fcr != null) {
			assertTrue(false);
		} else {
			assertTrue(true);
		}
	}
}
