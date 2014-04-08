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

import cscie99.team2.lingolearn.server.datastore.UserSessionDAO;
import cscie99.team2.lingolearn.shared.UserSession;

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
public class UserSessionDAOTest {
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

		// Create and store 3 test UserSession objects
		UserSession us1 = new UserSession(111L, 101L, "gplusId",
				new Date(), new Date());
		UserSession us2 = new UserSession(112L, 102L, "gplusId",
				new Date(), new Date());
		UserSession us3 = new UserSession(113L, 101L, "gplusId2",
				new Date(), new Date());
		
		// Store them in the local In-memory datastore
		UserSessionDAO uSessAccessor = UserSessionDAO.getInstance();
		uSessAccessor.storeUserSession(us1);
		uSessAccessor.storeUserSession(us2);
		uSessAccessor.storeUserSession(us3);
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	/**
	 * Test UserSession retrieval by userSessionId
	 */
	public void testGetById() {
		UserSessionDAO uSessAccessor = UserSessionDAO.getInstance();
		UserSession us = null;
		Long result = null;

		us =  uSessAccessor.getUserSessionById(111L);
		if (us == null) {
			assertTrue(false);
		} else {
		result = us.getUserSessionId();
		assertTrue("Retrieval UserSession by sessionId failed: expected 111, but obtained: " + result, result == 111L);
		}
	}
	
	@Test
	/**
	 * Test to retrieve all UserSession from datastore belonging to the same User
	 */
	public void testGetAllUserSessionByUsers() {
		UserSessionDAO uSessAccessor = UserSessionDAO.getInstance();
		List<UserSession> rList = null;
		int size = 2;

		rList = uSessAccessor.getAllUserSessionsByUser("gplusId");
		if (rList != null) {
			// The list should contain 2 UserSession objects
			assertTrue("Retrieval of the all UserSession list failed: expected " + size + " UserSession(s), but obtained: " + rList.size(), rList.size() == size);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	/**
	 * Test to retrieve all UserSession from datastore belonging to the same User
	 */
	public void testGetAllUserSessionBySession() {
		UserSessionDAO uSessAccessor = UserSessionDAO.getInstance();
		List<UserSession> rList = null;
		int size = 2;

		rList = uSessAccessor.getAllUserSessionsBySession(101L);
		if (rList != null) {
			// The list should contain 2 UserSession objects
			assertTrue("Retrieval of the all UserSession list failed: expected " + size + " UserSession(s), but obtained: " + rList.size(), rList.size() == size);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	/**
	 * Test to retrieve all UserSession from datastore
	 */
	public void testGetAllUserSession() {
		UserSessionDAO uSessAccessor = UserSessionDAO.getInstance();
		List<UserSession> rList = null;
		int size = 3;

		rList = uSessAccessor.getAllUserSessions();
		if (rList != null) {
			// The list should contain 3 UserSession objects
			  System.out.println("!!! " + rList);
			assertTrue("Retrieval of the all UserSession list failed: expected " + size + " UserSession (s), but obtained: " + rList.size(), rList.size() == size);
		} else {
			assertTrue(false);
		}
	}

	  @Test
	  /**
	   * Test card QuizRespone by "uSessAccessor"
	   */
	  public void testDeleteUserSessionById() {
		  UserSessionDAO uSessAccessor = UserSessionDAO.getInstance();
		  UserSession qr = null;
		  // Delete UserSession with sessionId = 111
		  uSessAccessor.deleteUserSessionById(111L);
		  // Retrieve non-existing QuizResp with sessionId = 111
		  qr = uSessAccessor.getUserSessionById(111L);
		  if (qr != null) {
			  assertTrue(false);
		  } else {
			  assertTrue(true);
		  }
	  }
}
