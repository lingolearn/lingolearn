package cscie99.team2.lingolearn.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

import cscie99.team2.lingolearn.shared.User;

public class UserProfileView extends Composite {

  interface Binder extends UiBinder<Widget, UserProfileView> { }
  private static final Binder binder = GWT.create(Binder.class);
	
	@UiField Element userName;
	@UiField HTMLPanel userInfo;
	
	public UserProfileView(){
		initWidget(binder.createAndBindUi(this));
	}
	
	public Widget asWidget() {
		return this;
	}
	  
	public void displayUserProfile( User user ){
		String fullName = user.getFirstName() + " " + user.getLastName();
		userName.setInnerHTML(fullName);
  	
		userInfo.add(new Anchor(user.getGmail(), "mailto:" + user.getGmail()));
		userInfo.add(new InlineLabel(" · "));
		userInfo.add(new InlineLabel(user.getNativeLanguage().getLangName()));
		userInfo.add(new InlineLabel(" · "));
		userInfo.add(new InlineLabel(user.getGender().toString()));								
	}
  
}
