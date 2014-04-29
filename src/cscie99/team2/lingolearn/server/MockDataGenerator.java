package cscie99.team2.lingolearn.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import cscie99.team2.lingolearn.server.datastore.CardDAO;
import cscie99.team2.lingolearn.server.datastore.CourseDAO;
import cscie99.team2.lingolearn.server.datastore.DeckDAO;
import cscie99.team2.lingolearn.server.datastore.LessonDAO;
import cscie99.team2.lingolearn.server.datastore.QuizDAO;
import cscie99.team2.lingolearn.server.datastore.UserDAO;
import cscie99.team2.lingolearn.server.tools.CardFileLoader;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.Gender;
import cscie99.team2.lingolearn.shared.Image;
import cscie99.team2.lingolearn.shared.Language;
import cscie99.team2.lingolearn.shared.LanguageTypes;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Quiz;
import cscie99.team2.lingolearn.shared.Sound;
import cscie99.team2.lingolearn.shared.User;
import cscie99.team2.lingolearn.shared.error.CardNotFoundException;

public class MockDataGenerator {
	
	private CardDAO cardAccessor;
	private DeckDAO deckAccessor;
	private UserDAO userAccessor;
	private CourseDAO courseAccessor;
	private LessonDAO lessonAccessor;
	private QuizDAO quizAccessor;
	private ServletContext servletContext;
	
	
	public MockDataGenerator(ServletContext servletContext) {
		cardAccessor = CardDAO.getInstance();
		deckAccessor = DeckDAO.getInstance();
		userAccessor = UserDAO.getInstance();
		courseAccessor = CourseDAO.getInstance();
		lessonAccessor = LessonDAO.getInstance();
		quizAccessor = QuizDAO.getInstance();
		
		this.servletContext = servletContext;
		
		System.out.println("creating mock generator");

		if (!hasDataBeenAdded()) {
			System.out.println("adding data");
			addBaseData();
		}
	}
	
	
	/**
	 * Determine if we've already put the base data in 
	 * the data store. This ~must~ make the determination by 
	 * looking at the data store contents, since this class 
	 * could be instantiated many times as people start/stop 
	 * the web application
	 * @return true if the base data is already in the data store
	 */
	private boolean hasDataBeenAdded() {
		//Here, we are using the course user's existence in the 
		// database to determine whether or not the entire set 
		// of data has already been added.
		boolean beenAdded = userAccessor.hasCourseUserBeenAdded();
		return beenAdded;
	}
	
	
	
	private void loadDecksFromTsv() {
		String[] fileNames = new String[] {
			"/data/cards.tsv",
			"/data/Grade1.tsv",
			"/data/Grade2.tsv",
			"/data/Grade3.tsv",
			"/data/Grade4.tsv",
			"/data/Grade5.tsv",
			"/data/Grade6.tsv",
			"/data/confusers.tsv"
		};
		
		for (int i=0;i<fileNames.length;i++) {
			System.out.println("adding file " + fileNames[i]);
			try {
				InputStream is = servletContext.getResourceAsStream(fileNames[i]);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				CardFileLoader cardLoader = new CardFileLoader();
				List<Card> importedCards = cardLoader.loadCards(reader);
				Iterator<Card> cardItr = importedCards.iterator();
	
				Deck deck = new Deck();
				while (cardItr.hasNext()) {
					//TODO break cards into separate decks based on description?
					Card c = cardItr.next();
					deck.add(c);
					deck.setDesc(c.getDesc());
				}
				deck = deckAccessor.storeDeck(deck);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void addBaseData() {

		//Load decks
		loadDecksFromTsv();
		
		//This will create and store the course user
		User courseUser = userAccessor.getCourseUser();
		
		String name1 = "Best course eva!";
		String name2 = "Difficult course";
		
		// only create dummy course1 if it doesn't exist
		Course course1 = courseAccessor.getCourseByName(name1);
		if( course1 == null ){
			course1 = new Course();
			course1.setName(name1);
			course1.setInstructor(courseUser);
		}
	// only create dummy course1 if it doesn't exist
		Course course2 = courseAccessor.getCourseByName(name2);
		if( course2 == null ){
			course2 = new Course();
			course2.setName(name2);
			course2.setInstructor(courseUser);
		}
		//store courses in data store

		// if the courses exist this will just update them (with no changes)
		try {
			course1 = courseAccessor.storeCourse(course1);
			course2 = courseAccessor.storeCourse(course2);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
		//TODO: fill in assignments for the courses
		/*
		Lesson l = new Lesson();
		l.setDeck(d2);
		l.setCourseId(course1.getCourseId());
		
		//store lesson in data store
		lessonAccessor.storeLesson(l);
		
		Quiz q = new Quiz();
		q.setDeck(d1);
		q.setCourseId(course2.getCourseId());
		
		Quiz q2 = new Quiz();
		q2.setDeck(getConfuserDeck());
		q2.setCourseId(course2.getCourseId());
		
		//store quizzes in data store
		quizAccessor.storeQuiz(q);
		quizAccessor.storeQuiz(q2);
		*/
	}
}
