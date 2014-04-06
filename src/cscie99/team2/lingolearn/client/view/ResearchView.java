package cscie99.team2.lingolearn.client.view;


import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.InlineHTML;

import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Session;

public class ResearchView extends Composite {
  
  interface Binder extends UiBinder<Widget, ResearchView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField Button downloadAllButton;
  
  public ResearchView() {
	  initWidget(binder.createAndBindUi(this));
  }

  public HasClickHandlers getDownloadAllButton() {
	  return this.downloadAllButton;
  }
  
  public void setDownloadAllButtonText(String text) {
	  downloadAllButton.setHTML(text);
  }
  
  public Widget asWidget() {
    return this;
  }
}
