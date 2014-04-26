package cscie99.team2.lingolearn.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Quiz;
import cscie99.team2.lingolearn.shared.Session;
import cscie99.team2.lingolearn.shared.User;
import cscie99.team2.lingolearn.shared.UserSession;

public interface CourseServiceAsync {
  public void getCoursesUserIsInstructing(String gplusId, AsyncCallback<ArrayList<Course>> callback);
  public void getCoursesUserIsEnrolledIn(String gplusId, AsyncCallback<ArrayList<Course>> callback);
  public void getAllAvailableCourses(String gplusId, AsyncCallback<ArrayList<Course>> callback);
  public void getAllCourses(AsyncCallback<ArrayList<Course>> callback);
  public void getCourseById(Long id, AsyncCallback<Course> callback);
  public void getSessionsForCourse(Long courseId, AsyncCallback<ArrayList<Session>> callback);
  public void getSessionById(Long sessionId, AsyncCallback<Session> callback);
  public void createCourse(Course course, AsyncCallback<Course> callback);
  public void createUserSession(Long sessionId, String gplusId, AsyncCallback<UserSession> callback);
  public void enrollInCourse(Course course, User student, AsyncCallback<Boolean> callback);
  public void enrollInCourse(Long courseId, String gplusId, AsyncCallback<Boolean> callback);
  public void createLesson(Long courseId, Long deckId, AsyncCallback<Lesson> callback);
  public void createQuiz(Long courseId, Long deckId, AsyncCallback<Quiz> callback);
  public void getAllDecks(AsyncCallback<List<Deck>> callback);
}

