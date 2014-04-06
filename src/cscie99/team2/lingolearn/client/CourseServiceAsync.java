package cscie99.team2.lingolearn.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Session;
import cscie99.team2.lingolearn.shared.User;

public interface CourseServiceAsync {
  public void getCoursesUserIsInstructing(User user, AsyncCallback<ArrayList<Course>> callback);
  public void getCoursesUserIsEnrolledIn(User user, AsyncCallback<ArrayList<Course>> callback);
  public void getCourseById(Long id, AsyncCallback<Course> callback);
  public void getSessionsForCourse(Long courseId, AsyncCallback<ArrayList<Session>> callback);
  public void getSessionById(Long sessionId, AsyncCallback<Session> callback);
  public void createCourse(Course course, AsyncCallback<Course> callback);
}

