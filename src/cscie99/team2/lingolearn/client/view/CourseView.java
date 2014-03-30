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
import cscie99.team2.lingolearn.shared.Session;

public class CourseView extends Composite {
  
  interface Binder extends UiBinder<Widget, CourseView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField Element courseTitle;
  @UiField VerticalPanel assignments;
  @UiField VerticalPanel analytics;
  
  public CourseView() {
	  initWidget(binder.createAndBindUi(this));
  }
  

  public void setCourseData(Course course) {
	  this.courseTitle.setInnerHTML(course.getName());
  }
  
  public void setAssignmentList(ArrayList<Session> sessions) {
	  for (int i=0;i<sessions.size();i++) {
		  InlineHTML text = new InlineHTML();
		  text.setHTML("<a href='app.html?sessionId=" + sessions.get(i).getSessionId() + 
				  "#session'>Deck #" + sessions.get(i).getDeck().getId() + "</a>");
		  assignments.add(text);
	  }	  
  }
  
  public void addStatisticToDisplay(String name, String value) {
	  InlineHTML text = new InlineHTML();
	  text.setHTML(name + ":  " + value);
	  analytics.add(text);
  }
  
  public Widget asWidget() {
    return this;
  }
}
