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

import cscie99.team2.lingolearn.server.datastore.CardDAO;
import cscie99.team2.lingolearn.server.datastore.UserDAO;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Gender;
import cscie99.team2.lingolearn.shared.Image;
import cscie99.team2.lingolearn.shared.Language;
import cscie99.team2.lingolearn.shared.OutsideCourse;
import cscie99.team2.lingolearn.shared.Sound;
import cscie99.team2.lingolearn.shared.Textbook;
import cscie99.team2.lingolearn.shared.User;
import cscie99.team2.lingolearn.shared.error.CardNotFoundException;

/**
 * @author YPolyanskyy
 * 
 * Unit tests for CardDAO class.
 * 
 * Make sure the following jars are in the classpath:
 * 
 * ${SDK_ROOT}/lib/testing/appengine-testing.jar
 * ${SDK_ROOT}/lib/impl/appengine-api.jar
 * ${SDK_ROOT}/lib/impl/appengine-api-labs.jar
 * ${SDK_ROOT}/lib/impl/appengine-api-stubs.jar
 * 
 */
public class UserDAOTest {
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

		Language l = new Language();
		l.setLangId("1");
		l.setLangName("en-us");
		User u1 = new User("googleID", "test@gmail.com", "John",
				"Smith", Gender.MALE, l);
		u1.addOutsideCourse(new OutsideCourse("123", "Fun Course", "Fun School"));
		u1.addTextbook(new Textbook("ABC", "Sweet Book", 2005));
		u1.addLanguages(new Language("2", "fr-ca"));
		
		// Store them in the local In-memory datastore
		UserDAO userAccessor = UserDAO.getInstance();
		userAccessor.storeUser(u1);
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
		UserDAO userAccessor = UserDAO.getInstance();
		User u = null;
		String result = null;

		u = userAccessor.getUserById("googleID");
		if (u == null) {
			assertTrue(false);
		} else {
		result = u.getLastName();
		System.out.println(u.getNativeLanguage().getLangName());
		assertTrue("Retrieval user by userId failed: expected Smith, but obtained: " + result, result.equals("Smith"));
		}
	}
	
	@Test
	/**
	 * Test User retrieval by "Gmail" 
	 */
	public void testGetByGmail() {
		UserDAO userAccessor = UserDAO.getInstance();
		User u = null;
		String result = null;

		u = userAccessor.getUserByGmail("test@gmail.com");
		if (u == null) {
			assertTrue(false);
		} else {
		result = u.getLastName();
		System.out.println(u.getNativeLanguage().getLangName());
		assertTrue("Retrieval user by Gmail failed: expected Smith, but obtained: " + result, result.equals("Smith"));
		}
	}

	  @Test
	  /**
	   * Test card delete by "uId"
	   */
	  public void testDeleteUserByCardId() {
			UserDAO userAccessor = UserDAO.getInstance();
			User u = null;
		  // Delete User with uId googleID
		  userAccessor.deleteUserById("googleID");
		  // Retrieve non-existing card with uId 101 yahooID
		  u = userAccessor.getUserById("yahooID");
		  if (u != null) {
			  assertTrue(false);
		  }
	  }
	
}
