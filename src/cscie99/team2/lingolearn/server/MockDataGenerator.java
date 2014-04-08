package cscie99.team2.lingolearn.server;

import java.util.ArrayList;
import java.util.HashMap;

import cscie99.team2.lingolearn.server.datastore.CardDAO;
import cscie99.team2.lingolearn.server.datastore.CourseDAO;
import cscie99.team2.lingolearn.server.datastore.DeckDAO;
import cscie99.team2.lingolearn.server.datastore.LessonDAO;
import cscie99.team2.lingolearn.server.datastore.QuizDAO;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.Image;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Quiz;
import cscie99.team2.lingolearn.shared.Sound;
import cscie99.team2.lingolearn.shared.error.CardNotFoundException;

public class MockDataGenerator {
	
	private static boolean dataHasBeenGenerated = false;
	
	public void generateMockData() {
		
		if (MockDataGenerator.dataHasBeenGenerated) {
			return;
		}
		MockDataGenerator.dataHasBeenGenerated = true;
		
		Card tc1 = new Card();
		tc1.setKanji("k1");
		tc1.setHiragana("h1");
		tc1.setKatakana("kt1");
		tc1.setTranslation("card1");
		tc1.setNativeLanguage("us-en");
		tc1.setDesc("TestDeck");

		// same kanji as in tc1
		Card tc2 = new Card();
		tc2.setKanji("k1");
		tc2.setHiragana("h2");
		tc2.setKatakana("kt2");
		tc2.setTranslation("carte2");
		tc2.setNativeLanguage("ca-fr");
		tc2.setDesc("TestDeck");

		// with Sound and Image
		String imgUri = "data:image/png;base64,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";
		Image myImage = new Image();
		myImage.setImageUri(imgUri);

		String soundUri = "data:audio/mpeg;base64,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";
		Sound mySound = new Sound();
		mySound.setSoundUri(soundUri);

		Card tc3 = new Card();
		tc3.setKanji("k3");
		tc3.setHiragana("h3");
		tc3.setKatakana("kt3");
		tc3.setTranslation("card3");
		tc3.setNativeLanguage("us-en");
		tc3.setDesc("MultiMediaDeck");
		tc3.setImage(myImage);
		tc3.setSound(mySound);
		
		
		Card c1 = new Card();
		c1.setKanji("岡");
		c1.setTranslation("card 1 translation");
		Card c2 = new Card();
		c2.setKanji("字");
		c2.setTranslation("card 2 translation");
		


		// Store cards in the local In-memory datastore
		CardDAO cardAccessor = CardDAO.getInstance();
		try {
			tc1 = cardAccessor.storeCard(tc1);
			tc2 = cardAccessor.storeCard(tc2);
			tc3 = cardAccessor.storeCard(tc3);
			c1 = cardAccessor.storeCard(c1);
			c2 = cardAccessor.storeCard(c2);
		} catch (CardNotFoundException myCardNotFoundException) {
			// Duplicate card
			System.err.println(myCardNotFoundException.getMsg() + " for " + myCardNotFoundException.getSearchParam());
		}
		

		// Create 2 decks
		Deck d1 = new Deck();
		Deck d2 = new Deck();

		// Add 3 cards to the deck1
		d1.setLangauge("ja-jp");
		d1.setNativeLangauge("en-us");
		d1.setCardIds(new ArrayList<Long>());
		d1.setCards(new HashMap<Long, Card>());
		d1.add(tc1);
		d1.add(tc2);
		d1.add(tc3);

		// Add 1 cards to the deck2
		d2.setLangauge("ja-jp");
		d2.setNativeLangauge("en-us");
		d2.setCardIds(new ArrayList<Long>());
		d2.setCards(new HashMap<Long, Card>());
		d2.add(c1);
		d2.add(c2);

		// Store decks in the local In-memory datastore
		DeckDAO deckAccessor = DeckDAO.getInstance();
		try {
			d1 = deckAccessor.storeDeck(d1);
			d2 = deckAccessor.storeDeck(d2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		Course course1;
		course1 = new Course();
		course1.setName("Best course eva!");
		Course course2;
		course2 = new Course();
		course2.setName("Difficult course");
		
		
		
		//store courses in data store
		CourseDAO courseAccessor = CourseDAO.getInstance();
		try {
			course1 = courseAccessor.storeCourse(course1);
			course2 = courseAccessor.storeCourse(course2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Lesson l = new Lesson();
		l.setDeck(d2);
		l.setCourseId(course1.getCourseId());
		
		//store lesson in data store
		LessonDAO lessonAccessor = LessonDAO.getInstance();
		lessonAccessor.storeLesson(l);
		
		Quiz q = new Quiz();
		q.setDeck(d1);
		q.setCourseId(course2.getCourseId());
		
		//store quizzes in data store
		QuizDAO quizAccessor = QuizDAO.getInstance();
		quizAccessor.storeQuiz(q);
		
	}
	
	
	

}
