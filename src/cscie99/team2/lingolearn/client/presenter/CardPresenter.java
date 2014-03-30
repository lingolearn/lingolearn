package cscie99.team2.lingolearn.client.presenter;


import cscie99.team2.lingolearn.client.CardServiceAsync;
import cscie99.team2.lingolearn.client.event.FlippedCardEvent;
import cscie99.team2.lingolearn.client.event.ViewCardEvent;
import cscie99.team2.lingolearn.client.view.CardView;
import cscie99.team2.lingolearn.shared.Card;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.List;

public class CardPresenter implements Presenter {  

  private final CardServiceAsync cardService;
  private final HandlerManager eventBus;
  private final CardView display;
  
  public CardPresenter(CardServiceAsync cardService, HandlerManager eventBus, CardView display) {
    this.cardService = cardService;
    this.eventBus = eventBus;
    this.display = display;
  }
  
  public void bind() {
    
	display.getFlipButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
        eventBus.fireEvent(new FlippedCardEvent());
    	display.flipCard();
      }
    });
    
  }
  
  public void go(final HasWidgets container) {
    bind();
    container.clear();
    container.add(display.asWidget());
    setCardData((long)1);
  }
  
  public void setCardData(Long cardId) {
	  cardService.getCardById(cardId, new AsyncCallback<Card>() {
	      public void onSuccess(Card card) {
	          display.setData(card);
	      }
	      
	      public void onFailure(Throwable caught) {
	        Window.alert("Error fetching card.");
	      }
	    });
  }

}
