package cscie99.team2.lingolearn.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import cscie99.team2.lingolearn.client.AppController;
import cscie99.team2.lingolearn.client.UserServiceAsync;
import cscie99.team2.lingolearn.client.view.AppRegisterView;
import cscie99.team2.lingolearn.shared.Gender;
import cscie99.team2.lingolearn.shared.GoogleIdPackage;
import cscie99.team2.lingolearn.shared.Language;
import cscie99.team2.lingolearn.shared.User;

public class RegistrationPresenter implements Presenter {
	
	  private final UserServiceAsync userService;
	  private final AppRegisterView display;
	  
	  private String gmail;
	  private String gplusId;
	  
	  public RegistrationPresenter( UserServiceAsync userService,
				HandlerManager eventBus, AppRegisterView view ){
		  
		  this.userService = userService;
		  this.display = view;

		}
	
	  public void bind(){

	  }
	
	  public void go(final HasWidgets container) {
		  userService.getSessionGoogleIds( 
			new AsyncCallback<GoogleIdPackage>(){
				public void onSuccess( GoogleIdPackage gpack ){
					if( !gpack.isValid() ){
						AppController.redirectUser("/login.html");
					}else{
						showRegistrationView( container, gpack );
					}
				}
				
				public void onFailure( Throwable caught ){
					AppController.redirectUser("/error.html");
				}
					 
		  });
		   
	  }
	  
	  
	  /**
	   * Determine if the registration view form data
	   * is valid for a user registration
	   * @param view The AppRegisterView
	   * @return boolean - true if the data is valid, false othewise.
	   */
	  public static boolean isRegistrationValid( final AppRegisterView view ){
		  
		  if( view.getFirstName().getText().toString().trim().isEmpty() )
			  return false;
		  
		  if( view.getLastName().getText().toString().trim().isEmpty() )
			  return false;
		  
		  if( view.getGmail().getText().toString().trim().isEmpty() )
			  return false;
		  
		  if( view.getGplusId().getValue().toString().trim().isEmpty() )
			  return false;
		  
		  return true;
	  }
	  
	  /*
	   * Helper method for go that is called after
	   * async services return.
	   */
	  private void showRegistrationView(final HasWidgets container,
			  									GoogleIdPackage gpack ){
		    bind();
		    container.clear();
		    
		    this.gmail = gpack.getGmail();
		    this.gplusId = gpack.getGplusId();
		    
		    display.setRegistrationGmail(gmail);
		    display.setRegistrationGplusId(gplusId);
		    display.addRegistrationHandler(
		    			new RegisterClickHandler(display, userService)
		    		);
		    
		    container.add(display.asWidget());
	  }
	  
	  /*
	   * Registration Click Handler class.
	   * This click handler checks that the form data is valid
	   * and if it is, the user is registered with the 
	   * user service.
	   */
	  private class RegisterClickHandler implements ClickHandler {
		  
		  final AppRegisterView view;
		  final UserServiceAsync userService;
		  
		public RegisterClickHandler(final AppRegisterView view, 
									final UserServiceAsync userService ) {
			
			  this.view = view;
			  this.userService = userService;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			
			if( !RegistrationPresenter.isRegistrationValid(this.view))
				return;
			
			String firstName = view.getFirstName().getText().toString().trim();
			String lastName = view.getLastName().getText().toString().trim();
			String selectedLanguage = view.getNativeLanguage().getItemText( 
					view.getNativeLanguage().getSelectedIndex() );
			Language nativeLanguage = new Language( selectedLanguage );
			String selectedGender = view.getGender().getValue(
					view.getGender().getSelectedIndex());
			Gender userGender = Gender.valueOf(selectedGender);
			String userGmail = view.getGmail().getText().toString().trim();
			String userGplusId = 
					view.getGplusId().getValue().toString().trim();
			User newUser = new User( userGplusId, userGmail, firstName,
							lastName, userGender, nativeLanguage );
			
			registerUser( newUser );
			
		} 
		
		private void registerUser( User user ){
			userService.registerUser( user, new AsyncCallback<User>(){
				public void onSuccess(User user) {
					  AppController.redirectUser("/app.html");
				}
				      
				public void onFailure(Throwable caught) {
					AppController.redirectUser("/error.html");
				}	
				
			});
		}
	  }
}
