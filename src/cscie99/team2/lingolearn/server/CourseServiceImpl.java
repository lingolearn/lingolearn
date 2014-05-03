package cscie99.team2.lingolearn.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cscie99.team2.lingolearn.client.CourseService;
import cscie99.team2.lingolearn.server.datastore.CourseDAO;
import cscie99.team2.lingolearn.server.datastore.CourseRegistrationDAO;
import cscie99.team2.lingolearn.server.datastore.DeckDAO;
import cscie99.team2.lingolearn.server.datastore.LessonDAO;
import cscie99.team2.lingolearn.server.datastore.QuizDAO;
import cscie99.team2.lingolearn.server.datastore.UserDAO;
import cscie99.team2.lingolearn.server.datastore.UserSessionDAO;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.CourseRegistration;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Quiz;
import cscie99.team2.lingolearn.shared.Session;
import cscie99.team2.lingolearn.shared.SessionTypes;
import cscie99.team2.lingolearn.shared.User;
import cscie99.team2.lingolearn.shared.UserSession;
import cscie99.team2.lingolearn.shared.error.DeckNotFoundException;

@SuppressWarnings("serial")
public class CourseServiceImpl extends RemoteServiceServlet implements CourseService {
	
	private CourseDAO courseAccessor;
	private LessonDAO lessonAccessor;
	private QuizDAO quizAccessor;
	private UserSessionDAO userSessionAccessor;
	private CourseRegistrationDAO courseRegistrationAccessor;
	private DeckDAO deckAccessor;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		//Ensure some standard amount of data exists. My (jarrod's) understanding is that 
		// the init method here will be run once when the web app starts. The data generator
		// then must decide whether to add data or not, based upon whether the data 
		// already exists in the data store. The servlet context is used to help provide 
		// the location of the resources (tsv files) within the /war/ folder.
		// Example was taken from:
		//    http://stackoverflow.com/questions/4340653/file-path-to-resource-in-our-war-web-inf-folder
		new MockDataGenerator(config.getServletContext());
		
		courseAccessor = CourseDAO.getInstance();
		lessonAccessor = LessonDAO.getInstance();
		quizAccessor = QuizDAO.getInstance();
		userSessionAccessor = UserSessionDAO.getInstance();
		courseRegistrationAccessor = CourseRegistrationDAO.getInstance();
		deckAccessor = DeckDAO.getInstance();
	}
	
	public ArrayList<Course> getCoursesUserIsInstructing(String gplusId) {
		User instructor = UserDAO.getInstance().getUserByGplusId(gplusId);
		ArrayList<Course> instructingCourses = (ArrayList<Course>)
				courseAccessor.getCoursesUserIsInstructing(instructor);
		return instructingCourses;
	}
	
	
	public ArrayList<Course> getCoursesUserIsEnrolledIn(String gplusId) {
		User student = UserDAO.getInstance().getUserByGplusId(gplusId);
		ArrayList<Course> enrolledCourses = (ArrayList<Course>)
				courseAccessor.getStudentEnrolledCourses(student);
		
		return enrolledCourses;
	}

	
	public ArrayList<Course> getAllAvailableCourses(String gplusId) {
		User user = UserDAO.getInstance().getUserByGplusId(gplusId);
		return (ArrayList<Course>) courseAccessor.getAvailableCourses(user);
	}
	
	
	public ArrayList<Course> getAllCourses() {
		List<Course> rawList;
		ArrayList<Course> list = new ArrayList<Course>();
		
		//Temporarily populate with all
		rawList = courseAccessor.getAllCourses();
		if (rawList != null) {
			for (int i=0;i < rawList.size();i++) {
				list.add(rawList.get(i));
			}
		}
		
		return list;
	}
	


	@Override
	public Course getCourseById(Long id) {
		Course c = courseAccessor.getCourseById(id);
		return c;
	}


	@Override
	public ArrayList<Session> getSessionsForCourse(Long courseId) {
		
		ArrayList<Session> sAll = new ArrayList<Session>();
		
		List<Lesson> lessons = lessonAccessor.getAllLessonsByCourseId(courseId);
		if (lessons != null) {
			for (int i=0;i<lessons.size();i++) {
				sAll.add(lessons.get(i));
			}
		}
	
		List<Quiz> quizzes = quizAccessor.getAllQuizsByCourseId(courseId);
		if (quizzes != null) {
			for (int i=0;i<quizzes.size();i++) {
				sAll.add(quizzes.get(i));
			}
		}
		
		return sAll;
	}


	@Override
	public Session getSessionById(Long sessionId) {
		Session s;
		s = lessonAccessor.getLessonById(sessionId);
		if (s == null) {
			s = quizAccessor.getQuizById(sessionId);
		}
		return s;
	}

	@Override
	public Course createCourse(Course course) {
		// Put the course in the data store (after it has an id)
		course = courseAccessor.storeCourse(course);
		return course;
	}

	@Override
	public UserSession createUserSession(Long sessionId, String gplusId,
																		SessionTypes sessionType ) {
		UserSession u = new UserSession();
		u.setSessionId(sessionId);
		u.setGplusId(gplusId);
		u.setSessStart(new Date());
		u.setSessionType(sessionType);
		u = userSessionAccessor.storeUserSession(u);
		return u;
	}
	
	public Boolean enrollInCourse( Course course, User user ){
		CourseDAO courseAccessor = CourseDAO.getInstance();
		course.addStudent(user);
		courseAccessor.storeCourse(course);
		
		return true;
		
	}
	
	public Boolean enrollInCourse(Long courseId, String gplusId) {
		Boolean b = true;
		CourseRegistration cr = new CourseRegistration();
		cr.setCourseId(courseId);
		cr.setGplusId(gplusId);
		System.out.println(gplusId);
		courseRegistrationAccessor.storeCourseRegistration(cr);
		return b;
	}
	
	public Lesson createLesson(Long courseId, Long deckId) {
		Lesson l = new Lesson();
		l.setCourseId(courseId);
		try {
			l.setDeck(deckAccessor.getDeckById(deckId));
		} catch (DeckNotFoundException e) {
			e.printStackTrace();
		}
		l = lessonAccessor.storeLesson(l);
		return l;
	}
	
	public Quiz createQuiz(Long courseId, Long deckId, 
			Boolean useConfuser, SessionTypes sessionType) {
		Quiz q = new Quiz();
		q.setCourseId(courseId);
		q.setSessionType(sessionType);
		if (useConfuser) {
			q.setMode("yes");
		} else {
			q.setMode("no");
		}
		try {
			q.setDeck(deckAccessor.getDeckById(deckId));
		} catch (DeckNotFoundException e) {
			e.printStackTrace();
		}
		q = quizAccessor.storeQuiz(q);
		return q;
	}
	
	public List<Deck> getAllDecks() {
		List<Deck> decks;
		decks = deckAccessor.getAllDecks();
		return decks;
	}

}
