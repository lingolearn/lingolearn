package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cscie99.team2.lingolearn.shared.Gender;
import cscie99.team2.lingolearn.shared.User;

public class UserDAO {

	private static UserDAO instance;
	
	private UserDAO(){
		
	}
	
	public static UserDAO getInstance(){
		if( instance == null )
			instance = new UserDAO();
		
		return instance;
	}
	
	/**
	 * This method stores the User in the datastore
	 * @param user User object to be stored in datastore
	 * @return USer for diagnostic
	 */
	public User storeUser( User user ) {
		
		ObjectifyableUser userEntity = new ObjectifyableUser(user);
		ofy().save().entity(userEntity).now();
		User stored = userEntity.getUser();
		return stored;	
	}
	
	/**
	 * Obtains User by UserId
	 * @param uId userId
	 * @return User stored with the userId or null if not found
	 */
	public User getUserById(String uId) {
		ObjectifyableUser oUser = ofy().load().type(ObjectifyableUser.class).id(uId).now();
		if (oUser != null) {
			User user = oUser.getUser();
			return user;
			}
		return null;
	}
	
	
	/**
	 * Retrieves User by gmail
	 * @param gmail gmail address associated with the user
	 * @return user if datastore has it, null otherwise
	 */
	public User getUserByGmail( String gmail ){
		User user = null;
		ObjectifyableUser oUser = ofy().load().type(ObjectifyableUser.class).filter("gmail", gmail).first().now();
		if (oUser != null) {
			user = oUser.getUser();
		}
		return user;
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
		if (users.size() == 0) {
			return null;
		} else {
			return users;
		}
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
		if (users.size() == 0) {
			return null;
		} else {
			return users;
		}
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
		if (users.size() == 0) {
			return null;
		} else {
			return users;
		}
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
		if (users.size() == 0) {
			return null;
		} else {
			return users;
		}
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
		if (users.size() == 0) {
			return null;
		} else {
			return users;
		}
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
		if (users.size() == 0) {
			return null;
		} else {
			return users;
		}
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
		if (users.size() == 0) {
			return null;
		} else {
			return users;
		}
	}
	
	/**
	 * Deletes the User with the specified uId from the datastore
	 * @param cardId
	 */
	public void deleteUserById(String uId) {
		ofy().delete().type(ObjectifyableUser.class).id(uId).now();
	}
}
