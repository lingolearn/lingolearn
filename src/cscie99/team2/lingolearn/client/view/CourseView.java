package cscie99.team2.lingolearn.client.view;


import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Session;

public class CourseView extends Composite {
  
  interface Binder extends UiBinder<Widget, CourseView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField Element courseTitle;
  @UiField Element instructor;
  @UiField Element courseDesc;
  @UiField VerticalPanel assignments;
  @UiField VerticalPanel analytics;
  @UiField Element addAssignmentLink;
  
  public CourseView() {
	  initWidget(binder.createAndBindUi(this));
  }
  

  public void setCourseData(Course course) {
	  this.courseTitle.setInnerHTML(course.getName());
	  String addLink = "app.html?courseId=" + course.getCourseId() + "#addAssignment";
	  this.addAssignmentLink.setAttribute("href", addLink);

	  setCourseInfo(course);
  }
  
  public void setCourseInfo(Course course){
  	String name = course.getInstructor().getFirstName() + " "
  				+ course.getInstructor().getLastName();
  	this.instructor.setInnerHTML( name );
  	String instructorLink = "/?profile=" + 
  							course.getInstructor().getUserId() + "#profile";
  	this.instructor.setAttribute("href", instructorLink);
  	
  	this.courseDesc.setId(course.getCourseDesc());
  }
  
  public void setAssignmentList(ArrayList<Session> sessions) {
	  String type;
	  for (int i=0;i<sessions.size();i++) {
		  if (sessions.get(i) instanceof Lesson) {
			  type = "Lesson";
		  } else {
			  type = "Quiz";
		  }
		  InlineHTML text = new InlineHTML();
		  text.setHTML("<a href='app.html?sessionId=" + sessions.get(i).getSessionId() + 
				  "#session'>" + type + " (Deck #" + 
				  sessions.get(i).getDeck().getId() + ")</a>");
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
