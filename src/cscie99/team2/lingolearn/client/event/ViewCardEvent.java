package cscie99.team2.lingolearn.client.event;


import com.google.gwt.event.shared.GwtEvent;

public class ViewCardEvent extends GwtEvent<ViewCardEventHandler> {
  public static Type<ViewCardEventHandler> TYPE = new Type<ViewCardEventHandler>();
  
  @Override
  public Type<ViewCardEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ViewCardEventHandler handler) {
    handler.onViewCard(this);
  }
}
