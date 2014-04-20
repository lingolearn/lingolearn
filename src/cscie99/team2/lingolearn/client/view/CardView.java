package cscie99.team2.lingolearn.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import cscie99.team2.lingolearn.shared.Card;

public class CardView extends Composite {
  interface Binder extends UiBinder<Widget, CardView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField FlowPanel card_container;
  @UiField FlowPanel card;
  @UiField FlowPanel front;
  @UiField FlowPanel back;
  @UiField FlowPanel kanji;
  @UiField FlowPanel hirigana;
  @UiField FlowPanel translation;
  @UiField FlowPanel knowledgeAssessmentArea;
  @UiField Button knowledgeLow;
  @UiField Button knowledgeMedium;
  @UiField Button knowledgeHigh;
  private Button flipButton;
  
  public CardView() {
	  initWidget(binder.createAndBindUi(this));
	  
	  this.flipButton = new Button();
	  this.flipButton.setText("Flip");
	  this.flipButton.setStyleName("btn btn-primary btn-lg");
	  this.front.add(flipButton);
  }
  
  public HasClickHandlers getFlipButton() {
	  return flipButton;
  }
  
  public HasClickHandlers getKnowledgeLowButton() {
	  return knowledgeLow;
  }
  
  public HasClickHandlers getKnowledgeMediumButton() {
	  return knowledgeMedium;
  }
  
  public HasClickHandlers getKnowledgeHighButton() {
	  return knowledgeHigh;
  }

  public void setData(Card card) {
	  //"unflip" the card if it is flipped
	  this.card_container.removeStyleName("card-flipped");
	  
	  //clear DOM nodes
	  this.kanji.clear();
	  
	  //add card data to DOM nodes
	  HTML tb1 = new HTML();
	  if (card.getKatakana().equals("")) {
		  tb1.setText(card.getKanji() + "  --  " + card.getHiragana());  
	  } else {
		  tb1.setText(card.getKatakana());
	  }
	  this.kanji.add(tb1);
	  
	  final HTML tb2 = new HTML();
	  tb2.setText(card.getTranslation());
	  
	  // Update UI after a second so the user doesn't get a peek at
	  // the answer during the animation
	  Timer timer = new Timer() {
	      public void run() {
	    	  translation.clear();
	    	  translation.add(tb2);
	      }
	  };
	  
	  timer.schedule(1000);
	  
  }
  
  public void flipCard() {
	  if (this.card_container.getStyleName().contains("card-flipped"))
		  this.card_container.removeStyleName("card-flipped");
	  else
		  this.card_container.addStyleName("card-flipped");
  }
  
  public Widget asWidget() {
    return this;
  }
}
