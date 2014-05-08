package cscie99.team2.lingolearn.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.SessionTypes;

public class AddAssignmentView extends Composite {
  
  interface Binder extends UiBinder<Widget, AddAssignmentView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField ListBox deckList;
  @UiField Button createQuizButton;
  @UiField Button createLessonButton;
  @UiField CheckBox useConfuser;
  @UiField FlowPanel sessionTypeRadios;
  private List<Deck> listOfDecks;
  
  public AddAssignmentView() {
	  initWidget(binder.createAndBindUi(this));
  }
  
  public HasClickHandlers getCreateQuizButtonHandlers() {
	  return createQuizButton;
  }
  
  public HasClickHandlers getCreateLessonButtonHandlers() {
	  return createLessonButton;
  }
  
  public Button getCreateQuizButton() {
		return createQuizButton;
	}

	public Button getCreateLessonButton() {
		return createLessonButton;
	}

	public Boolean isUseConfuserSelected() {
	  return useConfuser.getValue();
  }
  
  public Deck getSelectedDeck() {
	  int idx = deckList.getSelectedIndex();
	  Deck d = listOfDecks.get(idx);
	  return d;
  }
  
  public void hideQuizControls(){
  	this.sessionTypeRadios.setVisible(false);
  	this.useConfuser.setVisible(false);
  	this.createQuizButton.setEnabled(false);
  }
  
  public void setDeckList(List<Deck> decks) {
	  listOfDecks = decks;
	  for (int i=0;i<decks.size();i++) {
		  deckList.addItem(decks.get(i).getDesc() + "(" + decks.get(i).getId().toString() + ")");
	  }
  }
  
  public void setSessionTypes( SessionTypes []types ){
  	String radioGroup = "session-type";
  	for( int i = 0; i < types.length; i++ ){
  		SessionTypes type = types[i];
  		RadioButton radioButton = new RadioButton(radioGroup, type.toString());
  		if( !SessionTypes.confuserSupported(type) ){
  			radioButton.setStyleName("session-type-radio no-confuser");
  		}else{
  			radioButton.setStyleName("session-type-radio confuser-supported");
  		}
  		if( type == SessionTypes.Translation_Kanji ) {
  			radioButton.setValue(true);
  		}
  		sessionTypeRadios.add(radioButton);
  	}
  }
  
  public SessionTypes getSelectedSessionType(){
  	RadioButton selected = getSelectedSessionTypeRadio();
  	try{
  		return SessionTypes.getEnum(selected.getText());
  	}catch( IllegalArgumentException iae ){
  		// Return the default on error
  		return SessionTypes.Kanji_Translation;
  	}
  }
  
  private RadioButton getSelectedSessionTypeRadio(){
  	int totalWidgets = sessionTypeRadios.getWidgetCount();
  	for( int i = 0; i < totalWidgets; i++ ){
  		Widget widget = sessionTypeRadios.getWidget(i);
  		if( widget instanceof RadioButton ){
  			RadioButton radio = (RadioButton) widget;
  			if( radio.getValue() )
  				return radio;
  				
  		}
  	}
  	
  	return null;
  }
  

  
}
