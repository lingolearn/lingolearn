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
	
	public ArrayList<Course> getCoursesUserIsInstructing(User user) {
		ArrayList<Course> list = new ArrayList<Course>();
		Course c;
		
		//Temporarily prepopulate
		c = new Course();
		c.setName("Best course eva!");
		list.add(c);
		
		return list;
		
	}
	
	
	public ArrayList<Course> getCoursesUserIsEnrolledIn(User user) {
		ArrayList<Course> list = new ArrayList<Course>();
		Course c;
		
		
		//Temporarily prepopulate
		c = new Course();
		c.setName("Difficult course");
		list.add(c);
		
		return list;
		
	}


	@Override
	public Course getCourseById(String id) {
		//Stub
		Course c;
		
		
		//Temporarily prepopulate
		c = new Course();
		c.setName("Difficult course");
		return c;
	}


	@Override
	public ArrayList<Session> getSessionsForCourse(String courseId) {
		// TODO Auto-generated method stub
		Card c1 = new Card();
		c1.setKanji("岡");
		c1.setId((long)1);
		c1.setTranslation("card 1 translation");
		Card c2 = new Card();
		c2.setKanji("字");
		c2.setId((long)2);
		c2.setTranslation("card 2 translation");
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(c1);
		cards.add(c2);
		Deck d = new Deck((long) 7, cards, "japanese", "english");
		Lesson l = new Lesson();
		l.setDeck(d);
		Quiz q = new Quiz();
		q.setDeck(d);
		ArrayList<Session> s = new ArrayList<Session>();
		s.add(l);
		s.add(q);
		l.setSessionId("session84");
		q.setSessionId("session85");
		return s;
	}


	@Override
	public Session getSessionById(String sessionId) {
		Card c1 = new Card();
		c1.setKanji("岡");
		c1.setId((long)1);
		c1.setTranslation("card 1 translation");
		Card c2 = new Card();
		c2.setKanji("字");
		c2.setId((long)2);
		c2.setTranslation("card 2 translation");
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(c1);
		cards.add(c2);
		Deck d = new Deck((long) 7, cards, "japanese", "english");
		Lesson l = new Lesson();
		l.setDeck(d);
		l.setSessionId("session84");
		Quiz q = new Quiz();
		q.setDeck(d);
		q.setSessionId("session85");
		if (sessionId.equals("session84")) {
			return l;
		} else {
			return q;
		}
	}

}
