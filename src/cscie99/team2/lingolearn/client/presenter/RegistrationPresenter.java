package cscie99.team2.lingolearn.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

import cscie99.team2.lingolearn.client.UserServiceAsync;
import cscie99.team2.lingolearn.client.view.AppRegisterView;
import cscie99.team2.lingolearn.shared.User;

public class RegistrationPresenter implements Presenter {
	
	  private final UserServiceAsync userService;
	  private final HandlerManager eventBus;
	  private final AppRegisterView display;
	  
	  public RegistrationPresenter( UserServiceAsync userService,
			  				HandlerManager eventBus, AppRegisterView view ){
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
		    
	  }
	  
	  public static boolean isRegistrationValid( final AppRegisterView view ){
		  
		  if( view.getFirstName().getText().toString().trim().isEmpty() )
			  return false;
		  
		  if( view.getLastName().getText().toString().trim().isEmpty() )
			  return false;
		  
		  return true;
	  }
	  
	  private class RegisterClickHandler implements ClickHandler {
		  
		  final AppRegisterView view;
		  
		  public RegisterClickHandler(final AppRegisterView view) {
			  this.view = view;
		  }
		@Override
		public void onClick(ClickEvent event) {
			
			if( RegistrationPresenter.isRegistrationValid(this.view))
				return;
			
			User newUser = new User();
			newUser.setFirstName(
					view.getFirstName().getText().toString().trim() );
			
			newUser.setLastName(
					view.getLastName().getText().toString().trim() );
			
			//newUser.addLanguage(
			//		view.getNativeLanguage().getItemText( 
			//				view.getNativeLanguage().getSelectedIndex() ));
		} 
	  }
}
