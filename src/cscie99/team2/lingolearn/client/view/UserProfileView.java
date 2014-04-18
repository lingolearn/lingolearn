package cscie99.team2.lingolearn.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import cscie99.team2.lingolearn.shared.User;

public class UserProfileView extends Composite {

  interface Binder extends UiBinder<Widget, UserProfileView> { }
  private static final Binder binder = GWT.create(Binder.class);
	
	@UiField Element userName;
	@UiField Element userInfo;
	
	public UserProfileView(){
		initWidget(binder.createAndBindUi(this));
	}
	
  public Widget asWidget() {
    return this;
  }
  
  public void displayUserProfile( User user ){
  	String fullName = user.getFirstName() + " " + user.getLastName();
  	userName.setInnerHTML(fullName);
  	
  	StringBuilder buf = new StringBuilder();
  	buf.append( getUserInfoElement("email ", user.getGmail()) );
  	buf.append( getUserInfoElement("Native Language ", 
  			user.getNativeLanguage().getLangName()) ); 
  	buf.append( getUserInfoElement( "Gender", 
  									user.getGender().toString()) );
  	
  	userInfo.setInnerHTML(buf.toString());
  									
  }
	
  private String getUserInfoElement( String label, String value ){
  	String element = "<p class='user-profile-info'>"
  							   + "<span class='user-info-label'>"
  							   + label
  							   + "</span>"
  							   + "<span class='user-info-value'>"
  							   + value
  							   + "</span>"
  							   + "</p>";
  	
  	return element;
  }
  
}
