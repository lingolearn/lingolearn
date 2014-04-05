package cscie99.team2.lingolearn.client.event;


import com.google.gwt.event.shared.GwtEvent;

import cscie99.team2.lingolearn.shared.FlashCardResponse;
import cscie99.team2.lingolearn.shared.QuizResponse;

public class AnalyticsEvent extends GwtEvent<AnalyticsEventHandler> {
  public static Type<AnalyticsEventHandler> TYPE = new Type<AnalyticsEventHandler>();
  
  private QuizResponse quizResponse;
  private FlashCardResponse flashCardResponse;
  
  public AnalyticsEvent(QuizResponse quizResponse) {
	  this.quizResponse = quizResponse;
	  this.flashCardResponse = null;
  }
  
  public AnalyticsEvent(FlashCardResponse flashCardResponse) {
	  this.quizResponse = null;
	  this.flashCardResponse = flashCardResponse;
  }
  
  @Override
  public Type<AnalyticsEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(AnalyticsEventHandler handler) {
	  if (flashCardResponse != null) {
		  handler.onFlashCardResponse(this,flashCardResponse);
	  }
	  if (quizResponse != null) {
		  handler.onQuizResponse(this,quizResponse);
	  }
  }
  
}
