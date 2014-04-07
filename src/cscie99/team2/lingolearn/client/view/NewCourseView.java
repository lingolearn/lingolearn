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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.InlineHTML;

import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Session;

public class NewCourseView extends Composite {
  
  interface Binder extends UiBinder<Widget, NewCourseView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField TextBox courseName;
  @UiField TextArea courseDescription;
  @UiField Button createCourseButton;
  
  public NewCourseView() {
	  initWidget(binder.createAndBindUi(this));
  }
  
  public HasClickHandlers getCreateCourseButton() {
	  return createCourseButton;
  }
  
  public String getCourseName() {
	  return courseName.getText();
  }
  
  public String getCourseDescription() {
	  return courseDescription.getText();
  }
  
  public Widget asWidget() {
    return this;
  }
}
