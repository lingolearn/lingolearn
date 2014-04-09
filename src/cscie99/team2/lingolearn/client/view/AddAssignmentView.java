package cscie99.team2.lingolearn.client.view;


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.InlineHTML;

import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Session;

public class AddAssignmentView extends Composite {
  
  interface Binder extends UiBinder<Widget, AddAssignmentView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField ListBox deckList;
  @UiField Button createQuizButton;
  @UiField Button createLessonButton;
  private List<Deck> listOfDecks;
  
  public AddAssignmentView() {
	  initWidget(binder.createAndBindUi(this));
  }
  
  public HasClickHandlers getCreateQuizButton() {
	  return createQuizButton;
  }
  
  public HasClickHandlers getCreateLessonButton() {
	  return createLessonButton;
  }
  
  public Deck getSelectedDeck() {
	  int idx = deckList.getSelectedIndex();
	  Deck d = listOfDecks.get(idx);
	  return d;
  }
  
  public void setDeckList(List<Deck> decks) {
	  listOfDecks = decks;
	  for (int i=0;i<decks.size();i++) {
		  deckList.addItem(decks.get(i).getId().toString());
	  }
  }
  
}
