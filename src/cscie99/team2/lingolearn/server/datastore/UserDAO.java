package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;
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
		ofy().save().entity(new ObjectifyableUser(user)).now();
		return user;	
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
	 * Deletes the User with the specified uId from the datastore
	 * @param cardId
	 */
	public void deleteUserById(String uId) {
		ofy().delete().type(ObjectifyableUser.class).id(uId).now();
	}
}
