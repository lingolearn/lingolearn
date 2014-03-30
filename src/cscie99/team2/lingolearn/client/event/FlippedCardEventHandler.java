package cscie99.team2.lingolearn.client.event;


import com.google.gwt.event.shared.EventHandler;

public interface FlippedCardEventHandler extends EventHandler {
  void onFlippedCard(FlippedCardEvent event);
}
