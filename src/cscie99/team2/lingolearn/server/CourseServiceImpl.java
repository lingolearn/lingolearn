package cscie99.team2.lingolearn.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cscie99.team2.lingolearn.client.CardService;
import cscie99.team2.lingolearn.client.CourseService;
import cscie99.team2.lingolearn.shared.UserSession;
import cscie99.team2.lingolearn.server.datastore.CardDAO;
import cscie99.team2.lingolearn.server.datastore.CourseDAO;
import cscie99.team2.lingolearn.server.datastore.CourseRegistrationDAO;
import cscie99.team2.lingolearn.server.datastore.DeckDAO;
import cscie99.team2.lingolearn.server.datastore.LessonDAO;
import cscie99.team2.lingolearn.server.datastore.QuizDAO;
import cscie99.team2.lingolearn.server.datastore.UserSessionDAO;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.CourseRegistration;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.Image;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Quiz;
import cscie99.team2.lingolearn.shared.Session;
import cscie99.team2.lingolearn.shared.User;
import cscie99.team2.lingolearn.shared.error.DeckNotFoundException;

@SuppressWarnings("serial")
public class CourseServiceImpl extends RemoteServiceServlet implements CourseService {
	
	private CourseDAO courseAccessor;
	private LessonDAO lessonAccessor;
	private QuizDAO quizAccessor;
	private UserSessionDAO userSessionAccessor;
	private CourseRegistrationDAO courseRegistrationAccessor;
	private DeckDAO deckAccessor;
	
	public CourseServiceImpl() {
		new MockDataGenerator().generateMockData();
		courseAccessor = CourseDAO.getInstance();
		lessonAccessor = LessonDAO.getInstance();
		quizAccessor = QuizDAO.getInstance();
		userSessionAccessor = UserSessionDAO.getInstance();
		courseRegistrationAccessor = CourseRegistrationDAO.getInstance();
		deckAccessor = DeckDAO.getInstance();
	}
	
	public ArrayList<Course> getCoursesUserIsInstructing(String gplusId) {
		List<Course> rawList;
		ArrayList<Course> list = new ArrayList<Course>();
		
		//Temporarily populate with all
		rawList = courseAccessor.getAllCourses();
		if (rawList != null) {
			for (int i=0;(i < 5) && (i < rawList.size());i++) {
				list.add(rawList.get(i));
			}
		}
		
		return list;
		
	}
	
	
	public ArrayList<Course> getCoursesUserIsEnrolledIn(String gplusId) {
		List<CourseRegistration> rawList;
		ArrayList<Course> list = new ArrayList<Course>();
		
		rawList = courseRegistrationAccessor.getCourseRegistrationByUserId(gplusId);
		if (rawList != null) {
			for (int i=0;i < rawList.size();i++) {
				list.add(courseAccessor.getCourseById(rawList.get(i).getCourseId()));
			}
		}
		
		return list;
		
	}

	
	public ArrayList<Course> getAllAvailableCourses(String gplusId) {
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
		//TEMPORARY MOCK
		new MockDataGenerator().generateMockData();
		
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
	public UserSession createUserSession(Long sessionId, String gplusId) {
		UserSession u = new UserSession();
		u.setSessionId(sessionId);
		u.setGplusId(gplusId);
		u.setSessStart(new Date());
		u = userSessionAccessor.storeUserSession(u);
		return u;
	}
	
	public Boolean enrollInCourse(Long courseId, String gplusId) {
		Boolean b = true;
		CourseRegistration cr = new CourseRegistration();
		cr.setCourseId(courseId);
		cr.setGplusId(gplusId);
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
	
	public Quiz createQuiz(Long courseId, Long deckId) {
		Quiz q = new Quiz();
		q.setCourseId(courseId);
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
