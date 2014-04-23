package cscie99.team2.lingolearn.client.event;

import com.google.gwt.event.shared.EventHandler;

import cscie99.team2.lingolearn.shared.FlashCardResponse;
import cscie99.team2.lingolearn.shared.QuizResponse;

public interface AnalyticsEventHandler extends EventHandler {
  void onQuizResponse(AnalyticsEvent event, QuizResponse quizResponse);
  void onFlashCardResponse(AnalyticsEvent event, FlashCardResponse flashCardResponse);
}
