package cscie99.team2.lingolearn.client.presenter;

import java.util.ArrayList;
import java.util.Date;

import cscie99.team2.lingolearn.client.AnalyticsServiceAsync;
import cscie99.team2.lingolearn.client.CourseServiceAsync;
import cscie99.team2.lingolearn.client.CurrentUser;
import cscie99.team2.lingolearn.client.Notice;
import cscie99.team2.lingolearn.client.view.ResearchView;
import cscie99.team2.lingolearn.shared.Course;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class ResearchPresenter implements Presenter {  

  private final AnalyticsServiceAsync analyticsService;
  private final CourseServiceAsync courseService;
  private final HandlerManager eventBus;
  private final ResearchView display;
  private String allCsvDataUri;
  private String flashCardCsvDataUri;
  private String quizCsvDataUri;
  
  public ResearchPresenter(AnalyticsServiceAsync analyticsService, CourseServiceAsync courseService, 
		  HandlerManager eventBus, ResearchView display) {
    this.analyticsService = analyticsService;
    this.courseService = courseService;
    this.eventBus = eventBus;
    this.display = display;
    this.allCsvDataUri = "";
    this.flashCardCsvDataUri = "";
    this.quizCsvDataUri = "";
    
    display.disableSaveAllButton();
    display.disableSaveFlashCardResponsesButton();
    display.disableSaveQuizResponsesButton();
  }
  
  public void bind() {
    
	display.getDownloadAllButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
    	downloadAllData();
      }
    });
    
	display.getSaveAllButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
    	saveAllData();
      }
    });
	
	display.getDownloadFlashCardResponsesButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
    	Course c = display.getSelectedCourse();
    	downloadFlashCardResponseData(c.getCourseId());
      }
    });
    
	display.getSaveFlashCardResponsesButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
    	saveFlashCardResponsesData();
      }
    });
	
	
	display.getDownloadQuizResponsesButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
    	Course c = display.getSelectedCourse();
    	downloadQuizResponseData(c.getCourseId());
      }
    });
    
	display.getSaveQuizResponsesButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
    	saveQuizResponsesData();
      }
    });
    
  }
  
  public void go(final HasWidgets container) {
    bind();
    container.clear();
    container.add(display.asWidget());
    addAvailableCoursesToList();
  }
  

	
	private void addAvailableCoursesToList() {
		  courseService.getAllCourses(new AsyncCallback<ArrayList<Course>>() {
	
				@Override
				public void onFailure(Throwable caught) {
					Notice.showNotice("Trouble obtaining list of courses","warning");
				}
	
				@Override
				public void onSuccess(ArrayList<Course> courses) {
					display.setCourseList(courses);
				}
				
			});
	  }
  
	public void downloadAllData() {
		if (!allCsvDataUri.equals("")) {
			Window.open(URL.encode(allCsvDataUri), "", "");
			return;
		}
		analyticsService.generateCsvAllData(new AsyncCallback<String>() {
			public void onSuccess(String csvdata) {
				allCsvDataUri = "data:text/csv;charset=utf-8,";
				allCsvDataUri += csvdata;
				display.enableSaveAllButton();
			}

			public void onFailure(Throwable caught) {
				Notice.showNotice("Unable to download statistics", "warning");
			}
		});
	}
	

	private void saveAllData() {
		if (!allCsvDataUri.equals("")) {
			Window.open(URL.encode(allCsvDataUri), "", "");
			return;
		}
	}
	
	public void downloadFlashCardResponseData(Long courseId) {
		Date startDate = display.getStartDate();
		Date endDate = display.getEndDate();
		display.disableSaveFlashCardResponsesButton();
		analyticsService.generateFlashCardResponseDownload(courseId, startDate, endDate, new AsyncCallback<String>() {
			public void onSuccess(String csvdata) {
				flashCardCsvDataUri = "data:text/csv;charset=utf-8,";
				flashCardCsvDataUri += csvdata;
				display.enableSaveFlashCardResponsesButton();
			}

			public void onFailure(Throwable caught) {
				Notice.showNotice("Unable to download statistics", "warning");
			}
		});
	}
	

	private void saveFlashCardResponsesData() {
		if (!flashCardCsvDataUri.equals("")) {
			Window.open(URL.encode(flashCardCsvDataUri), "", "");
			return;
		}
	}
	
	
	
	public void downloadQuizResponseData(Long courseId) {
		Date startDate = display.getStartDate();
		Date endDate = display.getEndDate();
		display.disableSaveQuizResponsesButton();
		analyticsService.generateQuizResponseDownload(courseId, startDate, endDate, new AsyncCallback<String>() {
			public void onSuccess(String csvdata) {
				quizCsvDataUri = "data:text/csv;charset=utf-8,";
				quizCsvDataUri += csvdata;
				display.enableSaveFlashCardResponsesButton();
			}

			public void onFailure(Throwable caught) {
				Notice.showNotice("Unable to download statistics", "warning");
			}
		});
	}
	

	private void saveQuizResponsesData() {
		if (!quizCsvDataUri.equals("")) {
			Window.open(URL.encode(quizCsvDataUri), "", "");
			return;
		}
	}
	
	

}
