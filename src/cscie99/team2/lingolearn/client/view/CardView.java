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
import com.google.gwt.user.client.ui.InlineLabel;
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
		final InlineLabel frontValue = new InlineLabel();
		final InlineLabel backValue = new InlineLabel();
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
				if (card.getKatakana().equals(""))
					frontValue.setText(card.getKanji() + "  —  " + card.getHiragana());
				else
					frontValue.setText(card.getKatakana());
				backValue.setText(card.getTranslation());
		}
		cardFrontValue.add(frontValue);
		resizeCardText();

		Timer timer = new Timer() {
			public void run() {
				cardBackValue.clear();
				cardBackValue.add(backValue);
				resizeCardText();
			}
		};
		timer.schedule(1000);
  	}
  
  public static native void resizeCardText() /*-{
  	$wnd.jQuery('.card-value').textfill();
  }-*/;
  
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
	  this.cardFrontValue.clear();
	  this.cardBackValue.clear();
  }
}
