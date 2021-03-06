package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cscie99.team2.lingolearn.server.datastore.ObjectifyableQuiz;
import cscie99.team2.lingolearn.shared.Quiz;

public class QuizDAO {
	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class QuizDAOHolder { 
		public static final QuizDAO INSTANCE = new QuizDAO();
	}

	public static QuizDAO getInstance() {
		return QuizDAOHolder.INSTANCE;
	}   
	
	/**
	 * This method stores the Quiz in the datastore
	 * @param course Quiz to be stored in the datastore
	 * @return stored Quiz for diagnostic purpose
	 */
	public Quiz storeQuiz(Quiz quiz) {
		ObjectifyableQuiz oQuiz = new ObjectifyableQuiz(quiz); 
		ofy().save().entity(oQuiz).now();
		ObjectifyableQuiz fetched = ofy().load().entity(oQuiz).now();
		return fetched.getQuiz();
	}
	
	/**
	 * Obtains Quiz by QuizId
	 * @param courseId courseId
	 * @return Quiz stored with the courseId or null if not found
	 */
	public Quiz getQuizById(Long sessId) {
		if (sessId == null) {
			return null;
		}
		ObjectifyableQuiz oQuiz = ofy().load().type(ObjectifyableQuiz.class).id(sessId).now();
		return (oQuiz != null) ? oQuiz.getQuiz() : null;
	}
	
	public List<Quiz> getAllQuizsByCourseId(Long courseId) {
		List<ObjectifyableQuiz> oQuizs = ofy().load().type(ObjectifyableQuiz.class).filter("courseId", courseId).list();
		Iterator<ObjectifyableQuiz> it = oQuizs.iterator();
		List<Quiz> quizes = new ArrayList<>();
		while (it.hasNext()) {
			quizes.add(it.next().getQuiz());
		}
		return (quizes.size() == 0) ? null : quizes;
	}
	
	
	public List<Quiz> getAllQuizs() {
		List<ObjectifyableQuiz> oQuizs = ofy().load().type(ObjectifyableQuiz.class).list();

		Iterator<ObjectifyableQuiz> it = oQuizs.iterator();
		List<Quiz> quizes = new ArrayList<>();
		while (it.hasNext()) {
			quizes.add(it.next().getQuiz());
		}
		return (quizes.size() == 0) ? null : quizes;
	}
	
	
	/**
	 * Deletes the Quiz with the specified QuizId from the datastore
	 * @param sessId
	 */
	public void deleteQuizById(Long sessId) {
		if (sessId != null) {
			ofy().delete().type(ObjectifyableQuiz.class).id(sessId).now();
		}
	}
}
