package cscie99.team2.lingolearn.client.presenter;


import cscie99.team2.lingolearn.client.CardServiceAsync;
import cscie99.team2.lingolearn.client.event.FlippedCardEvent;
import cscie99.team2.lingolearn.client.event.ViewCardEvent;
import cscie99.team2.lingolearn.client.view.CardView;
import cscie99.team2.lingolearn.client.view.QuizView;
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

public class QuizPresenter implements Presenter {  

  private final CardServiceAsync cardService;
  private final HandlerManager eventBus;
  private final QuizView display;
  
  public QuizPresenter(CardServiceAsync cardService, HandlerManager eventBus, QuizView display) {
    this.cardService = cardService;
    this.eventBus = eventBus;
    this.display = display;
  }
  
  public void bind() {
    
	display.getSubmitButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
    	display.flipCard();
    	
      }
    });
    
  }
  
  public void go(final HasWidgets container) {
    bind();
    container.clear();
    container.add(display.asWidget());
  }
  
  public void setCardData(Long cardId) {
	  cardService.getCardById(cardId, new AsyncCallback<Card>() {
	      public void onSuccess(Card card) {
	          //display.setData(card);
	      }
	      
	      public void onFailure(Throwable caught) {
	        Window.alert("Error fetching card.");
	      }
	    });
  }

}
