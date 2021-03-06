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
				"Smith", Gender.Male, lang1);
		u1.addOutsideCourse(new OutsideCourse("123", "Fun Course", "Fun School"));
		u1.addTextbook(new Textbook("ABC", "Sweet Book", 2005));
		u1.addLanguages(lang2);

		User u2 = new User("googleID2", "test2@gmail.com", "Joe",
				"Smith", Gender.Male, lang1);
		u2.addOutsideCourse(course1);

		User u3 = new User("googleID3", "test2@gmail.com", "Josiane",
				"Cadieux", Gender.Female, lang2);

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
	public void testGetByGplusId() {
		UserDAO userAccessor = UserDAO.getInstance();
		User u = null;
		String result = null;
		u = userAccessor.getUserByGplusId("googleID");
		assertNotNull(u);
		result = u.getLastName();
		assertEquals("Retrieval user by userId failed: expected Smith, but obtained: ", result, "Smith");
		assertEquals(userAccessor.getUserByGplusId(""), null);
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
		assertNotNull(userList);
		// The list should contain 2 Users
		assertEquals("Retrieval of the Users list with the same Native Language failed: expected " + size + " User(s), but obtained: " + userList.size(), userList.size(), size);
		nLang = "non-existing";
		assertEquals(userAccessor.getAllUsersByNativeLanguage(nLang), null);
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
		assertNotNull(userList);
		// The list should contain 1 Users
		assertEquals("Retrieval of the Users list with the same Other Language failed: expected " + size + " User(s), but obtained: " + userList.size(), userList.size(), size);
		lang = "non-existing";
		assertEquals(userAccessor.getAllUsersByLanguage(lang), null);
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
		assertNotNull(userList);
		// The list should contain 1 Users
		assertEquals("Retrieval of the Users list with the same Textbook failed: expected " + size + " User(s), but obtained: " + userList.size(), userList.size(), size);
		book = "non-existing";
		assertEquals(userAccessor.getAllUsersByTextbook(book), null);
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
		assertNotNull(userList);
		// The list should contain 1 Users
		assertEquals("Retrieval of the Users list with the same Outside Course failed: expected " + size + " User(s), but obtained: " + userList.size(), userList.size(), size);
		course = "non-existing";
		assertEquals(userAccessor.getAllUsersByOutsideCourse(course), null);
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
		assertNotNull(userList);
		// The list should contain 1 Users
		assertEquals("Retrieval of the Users list with the same Outside Institition failed: expected " + size + " User(s), but obtained: " + userList.size(), userList.size(), size);
		inst = "non-existing";
		assertEquals(userAccessor.getAllUsersByOutsideInstitution(inst), null);
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
		assertNotNull(userList);
		// The list should contain 3 Users
		assertEquals("Retrieval of the all Users list failed: expected " + size + " User(s), but obtained: " + userList.size(), userList.size(), size);
		userList = null;
		assertEquals(userList, null);
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
		assertNotNull(u);
		result = u.getLastName();
		assertEquals("Retrieval user by Gmail failed: expected Smith, but obtained: ", result, "Smith");
	}

	@Test
	/**
	 * Test User retrieval by "Id" 
	 */
	public void testGetById() {
		UserDAO userAccessor = UserDAO.getInstance();
		User u = null;
		long uid;
		u = userAccessor.getUserByGplusId("googleID");
		assertNotNull(u);
		uid = u.getUserId();
		u = userAccessor.getUserById(uid);
		assertNotNull(u);
	}
	
	@Test
	/**
	 * Test User deletion by "Id" 
	 */
	public void testDeleteById() {
		UserDAO userAccessor = UserDAO.getInstance();
		User u = null;
		long uid;
		u = userAccessor.getUserByGplusId("googleID3");
		assertNotNull(u);
		uid = u.getUserId();
		u = userAccessor.getUserById(uid);
		assertNotNull(u);
		userAccessor.deleteUserByUid(uid);
		u = userAccessor.getUserById(uid);
		assertNull(u);
	}
	
	@Test
	public void testGetListByGender() {
		UserDAO userAccessor = UserDAO.getInstance();
		List<User> userList = null;
		Gender gender = Gender.Female;
		int size = 1;
		userList = userAccessor.getAllUsersByGender(gender);
		assertNotNull(userList);
		// The list should contain 1 User
		assertEquals("Retrieval of the Users list with of same Gender failed: expected " + size + " User(s), but obtained: " + userList.size(), userList.size(), size);
		long uid;
		User u = null;
		u = userAccessor.getUserByGplusId("googleID3");
		assertNotNull(u);
		uid = u.getUserId();
		u = userAccessor.getUserById(uid);
		assertNotNull(u);
		userAccessor.deleteUserByUid(uid);
		userList = userAccessor.getAllUsersByGender(gender);
		assertNull(userList);
	}
	
	@Test
	/**
	 * Test card delete by "uId"
	 */
	public void testDeleteUserById() {
		UserDAO userAccessor = UserDAO.getInstance();
		User u = null;
		// Delete User with uId googleID
		userAccessor.deleteUserByGplusId("googleID");
		// Retrieve non-existing card with uId 101 yahooID
		u = userAccessor.getUserByGplusId("yahooID");
		assertNull(u);
	}
}
