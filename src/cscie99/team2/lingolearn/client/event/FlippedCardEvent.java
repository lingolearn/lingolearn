package cscie99.team2.lingolearn.client.event;


import com.google.gwt.event.shared.GwtEvent;

public class FlippedCardEvent extends GwtEvent<FlippedCardEventHandler> {
  public static Type<FlippedCardEventHandler> TYPE = new Type<FlippedCardEventHandler>();
  
  @Override
  public Type<FlippedCardEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(FlippedCardEventHandler handler) {
    handler.onFlippedCard(this);
  }
  
}
