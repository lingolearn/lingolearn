package cscie99.team2.lingolearn.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cscie99.team2.lingolearn.client.CardService;
import cscie99.team2.lingolearn.client.CourseService;
import cscie99.team2.lingolearn.server.datastore.CardDAO;
import cscie99.team2.lingolearn.server.datastore.DeckDAO;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.Image;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Quiz;
import cscie99.team2.lingolearn.shared.Session;
import cscie99.team2.lingolearn.shared.User;
import cscie99.team2.lingolearn.shared.error.DeckNotFoundException;

@SuppressWarnings("serial")
public class CourseServiceImpl extends RemoteServiceServlet implements CourseService {
	
	public CourseServiceImpl() {
		new MockDataGenerator().generateMockData();
	}
	
	public ArrayList<Course> getCoursesUserIsInstructing(User user) {
		ArrayList<Course> list = new ArrayList<Course>();
		Course c;
		
		//Temporarily prepopulate
		c = new Course();
		c.setCourseId((long) 31);
		c.setName("Best course eva!");
		list.add(c);
		
		return list;
		
	}
	
	
	public ArrayList<Course> getCoursesUserIsEnrolledIn(User user) {
		ArrayList<Course> list = new ArrayList<Course>();
		Course c;
		
		
		//Temporarily prepopulate
		c = new Course();
		c.setCourseId((long) 32);
		c.setName("Difficult course");
		list.add(c);
		
		return list;
		
	}


	@Override
	public Course getCourseById(Long id) {
		//Stub
		Course c;
		
		
		//Temporarily prepopulate
		c = new Course();
		c.setCourseId(id);
		c.setName("Difficult course");
		return c;
	}


	@Override
	public ArrayList<Session> getSessionsForCourse(Long courseId) {
		new MockDataGenerator().generateMockData();
		
		ArrayList<Session> sAll = new ArrayList<Session>();
		Session s1 = new Lesson();
		try {
			s1.setDeck(DeckDAO.getInstance().getDeckById((long) 102));
		} catch (Exception e) {}
		s1.setSessionId((long)84);
		
		Session s2 = new Quiz();
		try {
			s2.setDeck(DeckDAO.getInstance().getDeckById((long) 101));
		} catch (Exception e) {}
		s2.setSessionId((long)85);
		
		sAll.add(s1);
		sAll.add(s2);
		return sAll;
	}


	@Override
	public Session getSessionById(Long sessionId) {
		
		Session s;
		if (sessionId.equals((long)84)) {
			s = new Lesson();
			try {
				s.setDeck(DeckDAO.getInstance().getDeckById((long) 102));
			} catch (Exception e) {}
		} else {
			s = new Quiz();
			try {
				s.setDeck(DeckDAO.getInstance().getDeckById((long) 101));
			} catch (Exception e) {}
		}
		s.setSessionId(sessionId);
		return s;
	}

}
