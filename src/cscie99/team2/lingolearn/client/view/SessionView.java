package cscie99.team2.lingolearn.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class SessionView extends Composite {
  
  interface Binder extends UiBinder<Widget, SessionView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField HeadingElement sessionHeader;
  @UiField FlowPanel cardArea;
  @UiField Anchor courseLink;
  
  public SessionView() {
	  initWidget(binder.createAndBindUi(this));
  }
  

  public void setSessionName(String name) {
//	  InlineHTML title = new InlineHTML();
//	  title.setText(name);
	  sessionHeader.setInnerText(name);
  }
  
  public HasWidgets getCardContainer() {
	  return this.cardArea;
  }
  
  public void setReturnToCourseLink(Long courseId) {
	  courseLink.setHref("app.html?courseId=" + courseId + "#course");
  }
  
  public Widget asWidget() {
      return this;
  }
}
