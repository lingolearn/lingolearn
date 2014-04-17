package cscie99.team2.lingolearn.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import cscie99.team2.lingolearn.client.Notice;
import cscie99.team2.lingolearn.client.UserServiceAsync;
import cscie99.team2.lingolearn.client.view.UserProfileView;
import cscie99.team2.lingolearn.shared.User;

public class UserProfilePresenter implements Presenter {
	
	public final String UID_QUERY_KEY = "profile";
	
  private final UserServiceAsync userService;
  private final HandlerManager eventBus;
  private final UserProfileView display;
  
  private User currentUser;
	
  public UserProfilePresenter( UserServiceAsync userService,
  		User user,
			HandlerManager eventBus, UserProfileView view ){
	  
	  this.userService = userService;
	  this.eventBus = eventBus;
	  this.display = view;

	}
  
  public void bind(){
  	
  }
  
  public void go(final HasWidgets container) {
    bind();
    container.clear();
    container.add(display.asWidget());
    try{
    	//Set course based on query parameter in URL
    	Long userId = Long.valueOf(Window.Location.getParameter(UID_QUERY_KEY));
    	userService.getUserByUid(userId, new AsyncCallback<User>(){
    		public void onSuccess( User user ){
    			display.displayUserProfile(user);
    		}
    		
    		public void onFailure( Throwable caught ){
    			Notice.showNotice(
    					"An error occurred finding the user with the specified profile",
    					"Error");
    		}
    	});
    	
    }catch( NumberFormatException nfe ){
    	displayLoggedInUserProfile();
    }
   
  }
  
  private void displayLoggedInUserProfile(){
  	display.displayUserProfile(currentUser);
  }
  

}
