package cscie99.team2.lingolearn.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.SessionTypes;

public class CardView extends Composite {
  interface Binder extends UiBinder<Widget, CardView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField FlowPanel card_container;
  @UiField FlowPanel card;
  @UiField FlowPanel front;
  @UiField FlowPanel cardFrontValue;
  @UiField FlowPanel back;
  @UiField FlowPanel kanji;
  @UiField FlowPanel hiragana;
  @UiField FlowPanel translation;
  @UiField FlowPanel cardBackValue;
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
	  	clearCard();
	  	
		// Prepare the HTML
		HTML frontValue = new HTML();
		final HTML backValue = new HTML();
		// Parse the card type
		SessionTypes types = SessionTypes.getEnum(Window.Location.getParameter("type"));
		switch (types) {
			case Kanji_Translation:
				frontValue.setText(card.getKanji());
				backValue.setText(card.getTranslation());
				break;
			case Hiragana_Translation:
				frontValue.setText(card.getHiragana());
				backValue.setText(card.getTranslation());
				break;
			case Translation_Kanji:
				frontValue.setText(card.getTranslation());
				backValue.setText(card.getKanji());
				break;
			case Translation_Hiragana:
				frontValue.setText(card.getTranslation());
				backValue.setText(card.getHiragana());
				break;
			case Kanji_Hiragana:
				frontValue.setText(card.getKanji());
				backValue.setText(card.getHiragana());
				break;
			case Hiragana_Kanji:
				frontValue.setText(card.getHiragana());
				backValue.setText(card.getKanji());
				break;
			case Confusor:
			default:
				setDefaultType(card);
		}
		cardFrontValue.add(frontValue);

		Timer timer = new Timer() {
			public void run() {
				cardBackValue.clear();
				cardBackValue.add(backValue);
			}
		};
		timer.schedule(1000);
  	}
  
  public Timer setKanjiTranslationType(Card card){
  	HTML frontValue = new HTML();
  	final HTML backValue = new HTML();
  	
  	frontValue.setText(card.getKanji());
  	backValue.setText(card.getTranslation());
  	
	  // Update UI after a second so the user doesn't get a peek at
	  // the answer during the animation
	  Timer timer = new Timer() {
	      public void run() {
	    	  translation.clear();
	    	  translation.add(backValue);
	      }
	  };
	  
	  return timer;
  }
  
  public Timer setDefaultType(Card card){

	  
	  //add card data to DOM nodes
	  HTML tb1 = new HTML();
	  if (card.getKatakana().equals("")) {
		  tb1.setText(card.getKanji() + "  —  " + card.getHiragana());  
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
	  
	  return timer;
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
  
  private void clearCard(){
	  //"unflip" the card if it is flipped
	  this.card_container.removeStyleName("card-flipped");
	  //clear DOM nodes
	  this.kanji.clear();
	  this.hiragana.clear();
	  this.translation.clear();
	  this.cardFrontValue.clear();
	  this.cardBackValue.clear();
  }
}
