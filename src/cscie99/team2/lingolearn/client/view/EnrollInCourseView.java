package cscie99.team2.lingolearn.client.view;


import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import cscie99.team2.lingolearn.shared.Course;

public class EnrollInCourseView extends Composite {
  
  interface Binder extends UiBinder<Widget, EnrollInCourseView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField ListBox courseList;
  @UiField Button enrollButton;
  private List<Course> listOfCourses;
  
  public EnrollInCourseView() {
	  initWidget(binder.createAndBindUi(this));
  }
  
  public HasClickHandlers getEnrollButton() {
	  return enrollButton;
  }
  
  public Course getSelectedCourse() {
	  int idx = courseList.getSelectedIndex();
	  Course c = listOfCourses.get(idx);
	  return c;
  }
  
  public void setCourseList(List<Course> courses) {
	  listOfCourses = courses;
	  for (int i=0;i<courses.size();i++) {
		  courseList.addItem(courses.get(i).getCourseName());
	  }
  }
  
}
