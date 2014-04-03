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

import cscie99.team2.lingolearn.server.datastore.UserDAO;
import cscie99.team2.lingolearn.shared.Gender;
import cscie99.team2.lingolearn.shared.Language;
import cscie99.team2.lingolearn.shared.OutsideCourse;
import cscie99.team2.lingolearn.shared.Textbook;
import cscie99.team2.lingolearn.shared.User;

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

		// Create and store 3 test Users
		Language lang1 = new Language("1","en-us");
		Language lang2 = new Language("2","fr-ca");
		OutsideCourse course1 = new OutsideCourse("11111", "JP101", "Harvard");
		
		User u1 = new User("googleID", "test@gmail.com", "John",
				"Smith", Gender.MALE, lang1);
		u1.addOutsideCourse(new OutsideCourse("123", "Fun Course", "Fun School"));
		u1.addTextbook(new Textbook("ABC", "Sweet Book", 2005));
		u1.addLanguages(lang2);
			
		User u2 = new User("googleID2", "test2@gmail.com", "Joe",
				"Smith", Gender.MALE, lang1);
		u2.addOutsideCourse(course1);
		
		User u3 = new User("googleID3", "test2@gmail.com", "Josiane",
				"Cadieux", Gender.FEMALE, lang2);
		
		// Store them in the local In-memory datastore
		UserDAO userAccessor = UserDAO.getInstance();
		userAccessor.storeUser(u1);
		userAccessor.storeUser(u2);
		userAccessor.storeUser(u3);	
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
		assertTrue("Retrieval user by userId failed: expected Smith, but obtained: " + result, result.equals("Smith"));
		}
	}
	
	@Test
	/**
	 * Test Users List retrieval by Native Language
	 */
	public void testGetListByNativeLanguage() {
		UserDAO userAccessor = UserDAO.getInstance();
		List<User> userList = null;
		String nLang = "en-us";
		int size = 2;
		
		userList = userAccessor.getAllUsersByNativeLanguage(nLang);
		if (userList != null) {
			// The list should contain 2 Users
			assertTrue("Retrieval of the Users list with the same Native Language failed: expected " + size + " User(s), but obtained: " + userList.size(), userList.size() == size);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	/**
	 * Test Users List retrieval by Other Language
	 */
	public void testGetListByOtherLanguage() {
		UserDAO userAccessor = UserDAO.getInstance();
		List<User> userList = null;
		String lang = "fr-ca";
		int size = 1;
		
		userList = userAccessor.getAllUsersByLanguage(lang);
		if (userList != null) {
			// The list should contain 1 Users
			assertTrue("Retrieval of the Users list with the same Other Language failed: expected " + size + " User(s), but obtained: " + userList.size(), userList.size() == size);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	/**
	 * Test Users List retrieval by Textbook
	 */
	public void testGetListByTextbook() {
		UserDAO userAccessor = UserDAO.getInstance();
		List<User> userList = null;
		String book = "Sweet Book";
		int size = 1;
		
		userList = userAccessor.getAllUsersByTextbook(book);
		if (userList != null) {
			// The list should contain 1 Users
			assertTrue("Retrieval of the Users list with the same Textbook failed: expected " + size + " User(s), but obtained: " + userList.size(), userList.size() == size);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	/**
	 * Test Users List retrieval by Course Name
	 */
	public void testGetListByOutsideCourse() {
		UserDAO userAccessor = UserDAO.getInstance();
		List<User> userList = null;
		String course = "JP101";
		int size = 1;
		
		userList = userAccessor.getAllUsersByOutsideCourse(course);
		if (userList != null) {
			// The list should contain 1 Users
			assertTrue("Retrieval of the Users list with the same Outside Course failed: expected " + size + " User(s), but obtained: " + userList.size(), userList.size() == size);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	/**
	 * Test Users List retrieval by Institution Name
	 */
	public void testGetListByOutsideInstitution() {
		UserDAO userAccessor = UserDAO.getInstance();
		List<User> userList = null;
		String inst = "Harvard";
		int size = 1;
		
		userList = userAccessor.getAllUsersByOutsideInstitution(inst);
		if (userList != null) {
			// The list should contain 1 Users
			assertTrue("Retrieval of the Users list with the same Outside Institition failed: expected " + size + " User(s), but obtained: " + userList.size(), userList.size() == size);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	public void testGetListByGender() {
		UserDAO userAccessor = UserDAO.getInstance();
		List<User> userList = null;
		Gender gender = Gender.MALE;
		int size = 2;
		
		userList = userAccessor.getAllUsersByGender(gender);
		if (userList != null) {
			// The list should contain 2 Users
			assertTrue("Retrieval of the Users list with of same Gender failed: expected " + size + " User(s), but obtained: " + userList.size(), userList.size() == size);
		} else {
			assertTrue(false);
		}
	}
	
	@Test
	/**
	 * Test to retrieve all Users from datastore
	 */
	public void testGetAllUsers() {
		UserDAO userAccessor = UserDAO.getInstance();
		List<User> userList = null;
		int size = 3;
		
		userList = userAccessor.getAllUsers();
		if (userList != null) {
			// The list should contain 3 Users
			assertTrue("Retrieval of the all Users list failed: expected " + size + " User(s), but obtained: " + userList.size(), userList.size() == size);
		} else {
			assertTrue(false);
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
		assertTrue("Retrieval user by Gmail failed: expected Smith, but obtained: " + result, result.equals("Smith"));
		}
	}

	  @Test
	  /**
	   * Test card delete by "uId"
	   */
	  public void testDeleteUserById() {
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
