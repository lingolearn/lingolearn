package cscie99.team2.lingolearn.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import cscie99.team2.lingolearn.client.CourseServiceAsync;
import cscie99.team2.lingolearn.client.DeckServiceAsync;
import cscie99.team2.lingolearn.client.Notice;
import cscie99.team2.lingolearn.client.view.AddAssignmentView;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Quiz;
import cscie99.team2.lingolearn.shared.SessionTypes;
import cscie99.team2.lingolearn.shared.User;

public class AddAssignmentPresenter implements Presenter {

	private final HandlerManager eventBus;
	private final AddAssignmentView display;
	private final CourseServiceAsync courseService;
	private final DeckServiceAsync deckService;
	private Long courseId;
	private Course currentCourse;
	private User currentUser;
	private boolean userIsInstructor = false;
	
	public AddAssignmentPresenter(CourseServiceAsync courseService,
			DeckServiceAsync deckService, User user,
			HandlerManager eventBus,
			AddAssignmentView display) {
		this.courseService = courseService;
		this.deckService = deckService;
		this.eventBus = eventBus;
		this.display = display;
		this.currentUser = user;
	}
	
	public AddAssignmentPresenter(CourseServiceAsync courseService,
			DeckServiceAsync deckService, HandlerManager eventBus,
			AddAssignmentView display) {
		
		this( courseService, deckService, null, eventBus, display);
	}

	public void bind() {
		
		populateDeckList();
		userIsInstructor = currentCourse.getInstructor().equals(currentUser);
		
		createLessonButton();
		if( userIsInstructor ){
			createQuizButton();
		}else{
			display.getCreateQuizButton().setEnabled(false);
		}
		
		display.setSessionTypes(SessionTypes.values());
	}

	public void go(final HasWidgets container) {
		try{
			
			// Set course based on query parameter in URL
			courseId = Long.valueOf(Window.Location.getParameter("courseId"));
			courseService.getCourseById(this.courseId, 
					new AsyncCallback<Course>(){
						public void onSuccess(Course course){
							currentCourse = course;
							
							bind();
							container.clear();
							container.add(display.asWidget());
						}
						
						public void onFailure(Throwable caught){
							Notice.showNotice("The specified course was not found.", "error");
						}
			});
		}catch( NumberFormatException nfe ){
			Notice.showNotice("The specified course was not found.", "error");
		}
		
	}

	private void createQuizButton(){
		
		display.getCreateQuizButtonHandlers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Deck selectedDeck = display.getSelectedDeck();
				courseService.createQuiz(courseId, selectedDeck.getId(),
						display.isUseConfuserSelected(),
						display.getSelectedSessionType(),
						new AsyncCallback<Quiz>() {
							public void onFailure(Throwable caught) {
								Notice.showNotice("Unable to create quiz.", "warning");
							}

							public void onSuccess(Quiz result) {
								Notice.showNotice("Quiz created!", "success");
							}
						});
			}
		});
	}
	
	private void createLessonButton(){
		display.getCreateLessonButtonHandlers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Deck selectedDeck = display.getSelectedDeck();
				courseService.createLesson(courseId, selectedDeck.getId(),
						new AsyncCallback<Lesson>() {
							public void onFailure(Throwable caught) {
								Notice.showNotice("Unable to create lesson.", "warning");
							}

							public void onSuccess(Lesson result) {
								Notice.showNotice("Lesson created!", "success");
							}
						});
			}
		});
	}
	
	private void populateDeckList() {
		deckService.getAllDecks(new AsyncCallback<List<Deck>>() {
			@Override
			public void onFailure(Throwable caught) {
				Notice.showNotice("Uh oh, we couldn't get the list of decks.", "warning");
			}

			@Override
			public void onSuccess(List<Deck> result) {
				display.setDeckList(result);
			}
		});
	}
}
