package cscie99.team2.lingolearn.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cscie99.team2.lingolearn.shared.GoogleIdPackage;
import cscie99.team2.lingolearn.shared.User;

@RemoteServiceRelativePath("userService")
public interface UserService extends RemoteService {
	public Boolean isUserLoggedIn();
	public User loginUser(String gmail);
	public Boolean logoutUser(User user);
	public User registerUser(User u);
	public User getUserByUid(Long uid);
	public User getUserByGplusId(String gplusId);

	/** Get the user who is currently logged into this session */
	public User getCurrentUser();
	
	/** Get the gmail address of the session user - who is not yet registered */
	public String getSessionGmail();
	
	/** Get the gplusid address of the session user - who is not yet registered */
	public String getSessionGplusId();

	public GoogleIdPackage getSessionGoogleIds();
}
