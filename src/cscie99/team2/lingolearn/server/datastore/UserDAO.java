package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;
import cscie99.team2.lingolearn.shared.Card;
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
	
	public User storeUser( User user ) {
		ofy().save().entity(new ObjectifyableUser(user)).now();
		return user;	
	}
	
	public User getUserByGmail( String gmail ){
		
/*		User u = new User();
		u.setGmail("test@gmail.com");
		u.setGplusId("12343243134143134134");
		u.setFirstName("Mufasa");
		
		return u;*/
		
		ObjectifyableUser oUser = ofy().load().type(ObjectifyableUser.class).filter("gmail", gmail).first().now();
		User user = oUser.getUser();
		return user;
	}
}
