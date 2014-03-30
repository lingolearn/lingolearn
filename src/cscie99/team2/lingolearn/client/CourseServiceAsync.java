package cscie99.team2.lingolearn.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Session;
import cscie99.team2.lingolearn.shared.User;

public interface CourseServiceAsync {
  public void getCoursesUserIsInstructing(User user, AsyncCallback<ArrayList<Course>> callback);
  public void getCoursesUserIsEnrolledIn(User user, AsyncCallback<ArrayList<Course>> callback);
  public void getCourseById(String id, AsyncCallback<Course> callback);
  public void getSessionsForCourse(String courseId, AsyncCallback<ArrayList<Session>> callback);
  public void getSessionById(String sessionId, AsyncCallback<Session> callback);
}

