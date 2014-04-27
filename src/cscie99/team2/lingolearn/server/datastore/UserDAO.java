package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cscie99.team2.lingolearn.shared.Gender;
import cscie99.team2.lingolearn.shared.Language;
import cscie99.team2.lingolearn.shared.LanguageTypes;
import cscie99.team2.lingolearn.shared.User;

public class UserDAO {

	private static final String COURSE_GMAIL = "cscie99.2014.team2@gmail.com";
	private static final String COURSE_GPLUS = "113018707842127503948";
	private static final String COURSE_FNAME = "cscie99";
	private static final String COURSE_LNAME = "team2";
	private static final Language COURSE_LANG = 
										new Language( LanguageTypes.English.toString() );
	
	private static UserDAO instance;
	
	private UserDAO() {	}
	
	public static UserDAO getInstance(){
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}
	
	public User getCourseUser() {
		User courseUser;
		if (hasCourseUserBeenAdded()) {
			courseUser = getUserByGmail(COURSE_GMAIL);
		} else {
			courseUser = new User(COURSE_GPLUS, COURSE_GMAIL, COURSE_FNAME,
					COURSE_LNAME, Gender.Male, COURSE_LANG);
			courseUser = this.storeUser(courseUser);
		}
		return courseUser;
	}
	
	public boolean hasCourseUserBeenAdded() {
		boolean beenAdded = false; 
		User courseUser = getUserByGmail(COURSE_GMAIL);
		if (courseUser != null) {
			beenAdded = true;
		}
		return beenAdded;
	}
	
	/**
	 * This method stores the User in the datastore
	 * @param user User object to be stored in datastore
	 * @return USer for diagnostic
	 */
	public User storeUser( User user ) {
		ObjectifyableUser userEntity = new ObjectifyableUser(user);
		ofy().save().entity(userEntity).now();
		return userEntity.getUser();	
	}
	
	public User getUserById( Long uid ){
		if (uid < 1 || uid == null) {
			return null;
		}
		ObjectifyableUser oUser = ofy().load().type(ObjectifyableUser.class).id(uid).now();
		return (oUser != null) ? oUser.getUser() : null;
	}
	
	/**
	 * Obtains User by UserId
	 * @param gplus userId
	 * @return User stored with the userId or null if not found
	 */
	public User getUserByGplusId(String gplus) {
		if (gplus.isEmpty() || gplus == null) {
			return null;
		}
		ObjectifyableUser oUser = ofy().load().type(ObjectifyableUser.class).
											filter("gplusId", gplus).first().now();
		return (oUser != null) ? oUser.getUser() : null;
	}
	
	
	/**
	 * Retrieves User by gmail
	 * @param gmail gmail address associated with the user
	 * @return user if datastore has it, null otherwise
	 */
	public User getUserByGmail( String gmail ){
		ObjectifyableUser oUser = ofy().load().type(ObjectifyableUser.class).filter("gmail", gmail).first().now();
		return (oUser != null) ? oUser.getUser() : null;
	}
	
	/**
	 * Obtains a list of all available Users with specified native language in the datastore
	 * @param lang native language of the User
	 * @return List of all Users in the datastore having the same language or null if nothing found
	 */
	public List<User> getAllUsersByNativeLanguage(String lang) {
		List<ObjectifyableUser> oUsers = ofy().load().type(ObjectifyableUser.class).filter("nativeLanguage.langName", lang).list();
		Iterator<ObjectifyableUser> it = oUsers.iterator();
		List<User> users = new ArrayList<>();
		while (it.hasNext()) {
			users.add(it.next().getUser());
		}
		return (users.size() == 0) ? null : users;
	}
	
	/**
	 * Obtains a list of all available Users of specified gender in the datastore
	 * @param gender 
	 * @return List of all Users in the datastore having of the same gender or null if nothing found
	 */
	public List<User> getAllUsersByGender(Gender gender) {
		List<ObjectifyableUser> oUsers = ofy().load().type(ObjectifyableUser.class).filter("gender", gender).list();
		Iterator<ObjectifyableUser> it = oUsers.iterator();
		List<User> users = new ArrayList<>();
		while (it.hasNext()) {
			users.add(it.next().getUser());
		}
		return (users.size() == 0) ? null : users;
	}
	
	/**
	 * Obtains a list of all available Users which previously studied specified language
	 * @param lang previously studied language
	 * @return List of all Users in the datastore or null if nothing found
	 */
	public List<User> getAllUsersByLanguage(String lang) {
		List<ObjectifyableUser> oUsers = ofy().load().type(ObjectifyableUser.class).filter("languages.langName =", lang).list();
		Iterator<ObjectifyableUser> it = oUsers.iterator();
		List<User> users = new ArrayList<>();
		while (it.hasNext()) {
			users.add(it.next().getUser());
		}
		return (users.size() == 0) ? null : users;
	}
	
	/**
	 * Obtains a list of all available Users which previously used specified book
	 * @param book previously used book
	 * @return List of all Users in the datastore or null if nothing found
	 */
	public List<User> getAllUsersByTextbook(String book) {
		List<ObjectifyableUser> oUsers = ofy().load().type(ObjectifyableUser.class).filter("textbooks.name =", book).list();
		Iterator<ObjectifyableUser> it = oUsers.iterator();
		List<User> users = new ArrayList<>();
		while (it.hasNext()) {
			users.add(it.next().getUser());
		}
		return (users.size() == 0) ? null : users;
	}
	
	/**
	 * Obtains a list of all available Users which previously took specified course
	 * @param course previously used course
	 * @return List of all Users in the datastore or null if nothing found
	 */
	public List<User> getAllUsersByOutsideCourse(String course) {
		List<ObjectifyableUser> oUsers = ofy().load().type(ObjectifyableUser.class).filter("outsideCourses.name =", course).list();
		Iterator<ObjectifyableUser> it = oUsers.iterator();
		List<User> users = new ArrayList<>();
		while (it.hasNext()) {
			users.add(it.next().getUser());
		}
		return (users.size() == 0) ? null : users;
	}
	
	/**
	 * Obtains a list of all available Users which previously took a course at a specified institution
	 * @param course previously taken at this institution
	 * @return List of all Users in the datastore or null if nothing found
	 */
	public List<User> getAllUsersByOutsideInstitution(String inst) {
		List<ObjectifyableUser> oUsers = ofy().load().type(ObjectifyableUser.class).filter("outsideCourses.institution =", inst).list();
		Iterator<ObjectifyableUser> it = oUsers.iterator();
		List<User> users = new ArrayList<>();
		while (it.hasNext()) {
			users.add(it.next().getUser());
		}
		return (users.size() == 0) ? null : users;
	}
	
	/**
	 * Obtains a list of all available Users in the datastore
	 * @return list of all Users or Null if no Users in the datastore
	 */
	public List<User> getAllUsers() {
		List<ObjectifyableUser> oUsers = ofy().load().type(ObjectifyableUser.class).list();
		Iterator<ObjectifyableUser> it = oUsers.iterator();
		List<User> users = new ArrayList<>();
		while (it.hasNext()) {
			users.add(it.next().getUser());
		}
		return (users.size() == 0) ? null : users;
	}
	
	public void deleteUserByUid( Long uid ){
		if( uid > 1 && uid != null )
			ofy().delete().type(ObjectifyableUser.class).id(uid).now();
	}
	
	/**
	 * Deletes the User with the specified uId from the datastore
	 * @param gplus
	 */
	public void deleteUserByGplusId(String gplus) {
		if (gplus.isEmpty() || gplus == null) {} else {
			ofy().delete().type(ObjectifyableUser.class).id(gplus).now();
		}
	}
}
