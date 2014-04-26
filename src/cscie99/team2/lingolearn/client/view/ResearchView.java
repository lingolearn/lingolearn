package cscie99.team2.lingolearn.client.view;


import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

import cscie99.team2.lingolearn.shared.Course;

public class ResearchView extends Composite {
  
  interface Binder extends UiBinder<Widget, ResearchView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField Button downloadAllButton;
  @UiField Button saveAllButton;
  @UiField Button downloadFlashCardResponsesButton;
  @UiField Button saveFlashCardResponsesButton;
  @UiField Button downloadQuizResponsesButton;
  @UiField Button saveQuizResponsesButton;
  @UiField ListBox courseList;
  @UiField DateBox startDate;
  @UiField DateBox endDate;
  private List<Course> listOfCourses;
  
  public ResearchView() {
	  initWidget(binder.createAndBindUi(this));
  }

  public HasClickHandlers getDownloadAllButton() {
	  return downloadAllButton;
  }
  
  public HasClickHandlers getSaveAllButton() {
	  return saveAllButton;
  }
  
  public void enableSaveAllButton() {
	  saveAllButton.setEnabled(true);
  }
  
  public void disableSaveAllButton() {
	  saveAllButton.setEnabled(false);
  }
  

  
  public HasClickHandlers getDownloadFlashCardResponsesButton() {
	  return downloadFlashCardResponsesButton;
  }
  
  public HasClickHandlers getSaveFlashCardResponsesButton() {
	  return saveFlashCardResponsesButton;
  }
  
  public void enableSaveFlashCardResponsesButton() {
	  saveFlashCardResponsesButton.setEnabled(true);
  }
  
  public void disableSaveFlashCardResponsesButton() {
	  saveFlashCardResponsesButton.setEnabled(false);
  }
  


  public HasClickHandlers getDownloadQuizResponsesButton() {
	  return downloadQuizResponsesButton;
  }
  
  public HasClickHandlers getSaveQuizResponsesButton() {
	  return saveQuizResponsesButton;
  }
  
  public void enableSaveQuizResponsesButton() {
	  saveQuizResponsesButton.setEnabled(true);
  }
  
  public void disableSaveQuizResponsesButton() {
	  saveQuizResponsesButton.setEnabled(false);
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
  
  public Date getStartDate() {
	  return startDate.getValue();
  }
  
  public Date getEndDate() {
	  return endDate.getValue();
  }
  
  
  public Widget asWidget() {
    return this;
  }
}
