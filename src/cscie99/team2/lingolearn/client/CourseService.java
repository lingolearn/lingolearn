package cscie99.team2.lingolearn.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Quiz;
import cscie99.team2.lingolearn.shared.Session;
import cscie99.team2.lingolearn.shared.User;
import cscie99.team2.lingolearn.shared.UserSession;

@RemoteServiceRelativePath("courseService")
public interface CourseService extends RemoteService {
	public ArrayList<Course> getCoursesUserIsInstructing(String gplusId);
	public ArrayList<Course> getCoursesUserIsEnrolledIn(String gplusId);
	public ArrayList<Course> getAllAvailableCourses(String gplusId);
	public Course getCourseById(Long courseId);
	public ArrayList<Session> getSessionsForCourse(Long courseId);
	public Session getSessionById(Long sessionId);
	public Course createCourse(Course course);
	public UserSession createUserSession(Long sessionId, String gplusId);
	public Boolean enrollInCourse(Course course, User student);
	public Boolean enrollInCourse(Long courseId, String gplusId);
	public Lesson createLesson(Long courseId, Long deckId);
	public Quiz createQuiz(Long courseId, Long deckId);
	public List<Deck> getAllDecks();
}
