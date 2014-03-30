package cscie99.team2.lingolearn.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cscie99.team2.lingolearn.client.UserService;
import cscie99.team2.lingolearn.shared.User;
import cscie99.team2.lingolearn.server.datastore.UserDAO;

public class UserServiceImpl  extends RemoteServiceServlet implements UserService {
	
	private static final long serialVersionUID = 6050413322421684533L;
	public static final String GMAIL_SESSION_KEY = "user_email";
	public static final String GID_SESSION_KEY = "gplus_id";
	public static final String USER_SESSION_KEY = "session_user";
	
	/**
	 * Determine if a user is currently logged in for this
	 * current session.
	 * 
	 * @return Boolean - true if a user is currently logged in,
	 * false otherwise.
	 */
	public Boolean isUserLoggedIn(){
		HttpSession session = this.getThreadLocalRequest().getSession();
		Object sessionUser = session.getAttribute(USER_SESSION_KEY);
		if( sessionUser == null )
			return false;
		
		if( !(sessionUser instanceof User) )
			return false;
		
		User loggedInUser = (User) sessionUser;
		if( loggedInUser.getGmail().equals("") )
			return false;
		
		return true;
		
	}
	
	/**
	 * login the user with the current gmail address.
	 * 
	 * @param gmail - the gmail address of the user to be logged in.
	 * @return Boolean - true if the user was successfully
	 * logged into this session, false otherwise.
	 */
	public Boolean loginUser( String gmail ){
		
		//TODO:: add exception handling, e.g. db error
		UserDAO udao = UserDAO.getInstance();
		User loggedInUser = udao.getUserByGmail(gmail);
		
		return loggedInUser != null;
	}
	
	/**
	 * Logout the user that is currently logged infor this session.
	 * 
	 * @param user - the User that is currently loged in.
	 * @return Boolean - true if the user was successfully logged
	 * out, false otherwise.
	 */
	public Boolean logoutUser( User user ){
		try{
			HttpSession session = this.getThreadLocalRequest().getSession();
			session.setAttribute(USER_SESSION_KEY, null);
			return true;
		}catch(Exception e ){
			return false;
		}
		
		
	}
	
	public User registerUser( User u ){
		//TODO:: add exception handling, e.g. db error
		UserDAO udao = UserDAO.getInstance();
		User registered = udao.storeUser(u);
		
		return registered;
	}
	
	/**
	 *  Get the user who is currently logged into this session
	 *  
	 * @return User - the user that is currently logged into this
	 * session.
	 */
	public User getCurrentUser(){
		
		//TODO this should throw an exception
		if( !isUserLoggedIn() )
			return null;
		
		HttpSession session = this.getThreadLocalRequest().getSession();
		Object sessionUser = session.getAttribute(USER_SESSION_KEY);
		
		//TODO this should throw an exception
		if( sessionUser == null )
			return null;
		
		//TODO this should throw an exception
		if( !(sessionUser instanceof User) )
			return null;
		
		User currentUser = (User) sessionUser;
		return currentUser;
	}
	
	/**
	 * Get the gmail address of the session user.
	 * This method is used for users who are not currently registered.
	 * This method should be used in conjunction with the login methods.
	 * 
	 * @return String the gmail address of the current non registered
	 * user for this session.
	 */
	public String getSessionGmail(){
		HttpSession session = this.getThreadLocalRequest().getSession();
		Object sessionGmail = session.getAttribute(GMAIL_SESSION_KEY);
		if( !(sessionGmail instanceof String ) )
			return "";
		
		String gmailAddress = (String) sessionGmail;
		
		return gmailAddress;
	}
	
	/**
	 * Get the gplusid address of the session user.
	 * 
	 */
	public String getSessionGplusId(){
		HttpSession session = this.getThreadLocalRequest().getSession();
		Object sessionGid = session.getAttribute(GID_SESSION_KEY);
		if( !(sessionGid instanceof String ) )
			return "";
		
		String gplusId = (String) sessionGid;
		
		return gplusId;
	}
}