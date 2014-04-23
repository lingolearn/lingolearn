package cscie99.team2.lingolearn.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cscie99.team2.lingolearn.client.AnalyticsService;
import cscie99.team2.lingolearn.server.datastore.CardDAO;
import cscie99.team2.lingolearn.server.datastore.CourseDAO;
import cscie99.team2.lingolearn.server.datastore.CourseRegistrationDAO;
import cscie99.team2.lingolearn.server.datastore.FlashCardResponseDAO;
import cscie99.team2.lingolearn.server.datastore.LessonDAO;
import cscie99.team2.lingolearn.server.datastore.QuizResponseDAO;
import cscie99.team2.lingolearn.server.datastore.UserDAO;
import cscie99.team2.lingolearn.shared.FlashCardResponse;
import cscie99.team2.lingolearn.shared.OutsideCourse;
import cscie99.team2.lingolearn.shared.Language;
import cscie99.team2.lingolearn.shared.QuizResponse;
import cscie99.team2.lingolearn.shared.Textbook;
import cscie99.team2.lingolearn.shared.User;

public class AnalyticsServiceImpl extends RemoteServiceServlet implements AnalyticsService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDAO uAccessor;
	private CourseRegistrationDAO crAccessor;
	private QuizResponseDAO qRespAccessor;
	private FlashCardResponseDAO fcRespAccessor;
	private CardDAO cAccessor;
	private CourseDAO courseAccessor;
	private LessonDAO lAccessor;
	private MetricsCalculator mc;
	
	
	public AnalyticsServiceImpl() {
		uAccessor = UserDAO.getInstance();
		crAccessor = CourseRegistrationDAO.getInstance();
		qRespAccessor = QuizResponseDAO.getInstance();
		fcRespAccessor = FlashCardResponseDAO.getInstance();
		cAccessor = CardDAO.getInstance();
		courseAccessor = CourseDAO.getInstance();
		lAccessor = LessonDAO.getInstance();
		mc = new MetricsCalculator();
	}

	/**
	 * Pulls the biographical data on a particular user
	 */
	public Map<String, String> getBiographicalData(String gplusId) {
		Map<String, String> data = new HashMap<String, String>();
		
		User u = uAccessor.getUserByGplusId(gplusId);
		String languageString = "";
		String textbookString = "";
		String outsideCourseString = "";
		

		for (Language lan: u.getLanguages()) {
			languageString += lan.getLangId() + ";" + lan.getLangName() +";";
		}
		for (Textbook tex: u.getTextbooks()) {
			textbookString += tex.getTextbookID() + ";" + tex.getName() +";" + tex.getYear() + ";";
		}
		for (OutsideCourse oc: u.getOutsideCourses()) {
			outsideCourseString += oc.getOutsideCourseID() + ";" + oc.getName() + ";" + oc.getInstitution() + ";";
		}
		
		
		data.put("id", u.getUserId().toString());
		data.put("firstName", u.getFirstName());
		data.put("lastName", u.getLastName());
		data.put("gmail", u.getGmail());
		data.put("gender", u.getGender().toString());
		data.put("nativeLanguage", u.getNativeLanguage().getLangName());
		data.put("noLanguages", Integer.toString(u.getLanguages().size()));
		data.put("noTextbooks", Integer.toString(u.getTextbooks().size()));
		data.put("noOutsideCourses", Integer.toString(u.getOutsideCourses().size()));
		data.put("languages",  languageString);
		data.put("textbooks",  textbookString);
		data.put("outsideCourses", outsideCourseString);
				
		return data;
	}
	
	/**
	 * Pulls the metrics data on a particular user
	 */
	public Map<String, Float> getMetricsData (String gplusId) {
		Map<String, Float> data = new HashMap<String, Float>();
			
	 	data.put("recallRate", mc.calculateRecallRate(gplusId));
	 	data.put("avgQuizReactionTime", mc.calculateAvgQuizReactionTime(gplusId));
	 	data.put("avgFlashCardReactionTime", mc.calculateAvgFlashCardReactionTime(gplusId));
	 	data.put("indecisionRate", mc.calculateIndecisionRate(gplusId));
	 	data.put("dropRate", mc.calculateDropRate(gplusId));
	 	data.put("averageSessionTime", mc.calculateAverageSessionTime(gplusId));
	 	data.put("repetitionsPerWeek", mc.calculateRepetitionsPerWeek(gplusId));
	 	data.put("percentNoClue", mc.calculatePercentNoClue(gplusId));
	 	data.put("percentSortaKnewIt", mc.calculatePercentSortaKnewIt(gplusId));
	 	data.put("percentDefinitelyKnewIt", mc.calculatePercentDefinitelyKnewIt(gplusId));
	 	
	 	return data;
	}

	/**
	 * Gets a list of all student id's enrolled in a specific course 
	 */
	public List<User> getUsersInCourse(Long courseId) {
		List<User> users = new ArrayList<User>();
		if (crAccessor.getUserCourseId(courseId) != null) {
			users = courseAccessor.getCourseById(courseId).getStudents();
		}
		return users;
	}
	
	/**
	 * Gets a list of all students in the system.
	 */
	public List<User> getAllStudents() {
		List<User> users = new ArrayList<User>();
		if (uAccessor.getAllUsers() != null) {
			users = uAccessor.getAllUsers();
		}
		return users;
	}

	/**
	 * Pulls all biographical data for students in a particular course
	 */
	public Map<String, Map<String, String>> getCourseBiographicalData(Long courseId) {
		List<User> students = getUsersInCourse(courseId);
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		
		for (User s: students) {
			Map<String, String> bioData = getBiographicalData (s.getGplusId());
			data.put(s.getUserId().toString(), bioData);
		}
		
		return data;
	}

	/**
	 *  Pulls all metrics data for students in a particular course.
	 */
	public Map<String, Map<String, Float>> getCourseMetricsData(Long courseId) {
		List<User> students = getUsersInCourse(courseId);
		Map<String, Map<String, Float>> data = new HashMap<String, Map<String, Float>>();
		
		for (User s: students) {
			Map<String, Float> metricsData = getMetricsData (s.getGplusId());
			data.put(s.getUserId().toString(), metricsData);
		}
		
		return data;
	}

	/**
	 * Pulls all biographical data for all students in the system.
	 */
	public Map<String, Map<String, String>> getAllBiographicalData() {
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		
		List<User> students = getAllStudents();
		
		for (User s: students) {
			Map<String, String> bioData = this.getBiographicalData(s.getGplusId());
			data.put(s.getUserId().toString(), bioData);
		}
		
		return data;
	}

	/**
	 * Pulls all metrics data for all students in the system.
	 */
	public Map<String, Map<String, Float>> getAllMetricsData() {
		Map<String, Map<String, Float>> data = new HashMap<String, Map<String, Float>>();
		
		List<User> students = getAllStudents();
		
		for (User s: students) {
			Map<String, Float> metricsData = getMetricsData (s.getGplusId());
			data.put(s.getUserId().toString(), metricsData);
		}
		
		return data;
	}
	
	public String generateCsvAllData() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("StudentID,GMail,Gender,NativeLanguage,NumberOfLanguages,NumberOfTextbooks,NumberOfOutsideCourses,"
				+ "Languages,Textbooks,OutsideCourses,RecallRate,AverageQuizReactionTime,AverageFlashCardReactionTime,IndecisionRate,"
				+ "DropRate,AverageSessionTime,RepetitionsPerWeek,PercentNoClue,PercentSortaKnewIt,PercentDefinitelyKnewIt");
		sb.append("\n");
		
		List<User> allStudents = this.getAllStudents();
		for (User s: allStudents) {
			Map<String, String> bioData = this.getBiographicalData(s.getGplusId());
			Map<String, Float> metricsData = this.getMetricsData(s.getGplusId());
			sb.append(s.getUserId() + ",");
			sb.append(bioData.get("gmail") + ",");
			sb.append(bioData.get("gender") + ",");
			sb.append(bioData.get("nativeLanguage") + ",");
			sb.append(bioData.get("noLanguages") + ",");
			sb.append(bioData.get("noTextbooks") + ",");
			sb.append(bioData.get("noOutsideCourses") + ",");
			sb.append(bioData.get("languages") + ",");
			sb.append(bioData.get("textbooks") + ",");
			sb.append(bioData.get("outsideCourses") + ",");
			sb.append(metricsData.get("recallRate") + ",");
			sb.append(metricsData.get("avgQuizReactionTime") + ",");
			sb.append(metricsData.get("avgFlashCardReactionTime") + ",");
			sb.append(metricsData.get("indecisionRate") + ",");
			sb.append(metricsData.get("dropRate") + ",");
			sb.append(metricsData.get("averageSessionTime") + ",");
			sb.append(metricsData.get("repetitionsPerWeek") + ",");
			sb.append(metricsData.get("percentNoClue") + ",");
			sb.append(metricsData.get("percentSortaKnewIt") + ",");
			sb.append(metricsData.get("percentDefinitelyKnewIt") + "\n");
		}
				
		return sb.toString();
	}
	
	/**
	 * Stores a QuizResponse object in the datastore.
	 */
	public QuizResponse storeQuizResponse(QuizResponse qResp) {
		qRespAccessor.storeQuizResponse(qResp);
		return qResp;
	}
	
	/**
	 * Deletes a QuizResponse by session id.
	 */
	public void deleteQuizResponseById(Long sessionId) {
		if (qRespAccessor.getQuizResponseById(sessionId) != null) {
			qRespAccessor.deleteQuizResponseById(sessionId);
		}
	}
	
	/**
	 * Stores flashcard response in the datastore
	 */
	public FlashCardResponse storeFlashCardResponse(FlashCardResponse fcResp) {
		fcRespAccessor.storeFlashCardResponse(fcResp);
		return fcResp;
	}
	
	public void deleteFlashCardResponseById(Long sessionId) {
		if (fcRespAccessor.getFlashCardResponseById(sessionId) != null) {
			fcRespAccessor.deleteFlashCardResponseById(sessionId);
		}
	}
	
	public String generateFlashCardResponseDownload(Long courseId, Date startDate, Date endDate) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("UserID,Gender,NativeLanguage,NumberOfLanguages,NumberOfTextbooks,NumberOfOutsideCourses,"
				+ "Languages,Textbooks,OutsideCourses,CourseID,ResponseTimestamp,"
				+ "QuestionSequence,Kanji,Hiragana,Katakana,Translation,UserAssessment");
		sb.append("\n");
		
		List<FlashCardResponse> fcResps = fcRespAccessor.getAllFlashCardResponses();
		List<FlashCardResponse> queriedList1 = new ArrayList<FlashCardResponse>();
		List<FlashCardResponse> queriedList2 = new ArrayList<FlashCardResponse>();
		List<FlashCardResponse> queriedList3 = new ArrayList<FlashCardResponse>();
		
		if (courseId != null) {
			for (FlashCardResponse fcr: fcResps) {
				if (lAccessor.getLessonById(fcr.getSessionId()).getCourseId() == courseId) {
					queriedList1.add(fcr);
				}
			}
		}
		else {
			queriedList1 = fcResps;
		}
		
		
		if (startDate != null) {
			for (FlashCardResponse fcr: queriedList1) {
				if (startDate.compareTo(fcr.getAnswerTimeRec()) < 0) {
					queriedList2.add(fcr);
				}
			}
		}
		else {
			queriedList2 = queriedList1;
		}
		
		if (endDate != null) {
			for (FlashCardResponse fcr: queriedList2) {
				if (endDate.compareTo(fcr.getAnswerTimeRec()) >= 0) {
					queriedList3.add(fcr);
				}
			}
		}
		else {
			queriedList3 = queriedList2;
		}
		
		for (FlashCardResponse fcr: queriedList3) {
			User u = uAccessor.getUserByGplusId(fcr.getGplusId());
			Map<String, String> bioData = this.getBiographicalData(u.getGplusId());
			sb.append(u.getUserId().toString() + ",");
			sb.append(bioData.get("gender") + ",");
			sb.append(bioData.get("nativeLanguage") + ",");
			sb.append(bioData.get("noLanguages") + ",");
			sb.append(bioData.get("noTextbooks") + ",");
			sb.append(bioData.get("noOutsideCourses") + ",");
			sb.append(bioData.get("languages") + ",");
			sb.append(bioData.get("textbooks") + ",");
			sb.append(bioData.get("outsideCourses") + ",");
			sb.append(lAccessor.getLessonById(fcr.getSessionId()).getCourseId().toString() + ",");
			sb.append(fcr.getAnswerTimeRec().toString() + ",");
			sb.append(fcr.getSeq() + ",");
			sb.append(cAccessor.getCardById(fcr.getCardId()).getKanji() + ",");
			sb.append(cAccessor.getCardById(fcr.getCardId()).getHiragana() + ",");
			sb.append(cAccessor.getCardById(fcr.getCardId()).getKatakana() + ",");
			sb.append(cAccessor.getCardById(fcr.getCardId()).getTranslation() + ",");
			sb.append(fcr.getAssessment().toString() + "\n");						
		}
		return sb.toString();		
	}
	
	public String generateQuizResponseDownload(Long courseId, Date startDate, Date endDate) {
	
		StringBuilder sb = new StringBuilder();
		sb.append("UserID,Gender,NativeLanguage,NumberOfLanguages,NumberOfTextbooks,NumberOfOutsideCourses,"
				+ "Languages,Textbooks,OutsideCourses,CourseID,ResponseTimestamp,"
				+ "QuestionSequence,Kanji,Hiragana,Katakana,Translation,"
				+ "ConfuserType,NumberofConfusers,WrongAnswers,IsCorrect");
		sb.append("\n");
		
		List<QuizResponse> qResps = qRespAccessor.getAllQuizResponses();
		List<QuizResponse> queriedList1 = new ArrayList<QuizResponse>();
		List<QuizResponse> queriedList2 = new ArrayList<QuizResponse>();
		List<QuizResponse> queriedList3 = new ArrayList<QuizResponse>();
		
		if (courseId != null) {
			for (QuizResponse qr: qResps) {
				if (lAccessor.getLessonById(qr.getSessionId()).getCourseId() == courseId) {
					queriedList1.add(qr);
				}
			}
		}
		else {
			queriedList1 = qResps;
		}
		
		
		if (startDate != null) {
			for (QuizResponse qr: queriedList1) {
				if (startDate.compareTo(qr.getAnswerTimeRec()) < 0) {
					queriedList2.add(qr);
				}
			}
		}
		else {
			queriedList2 = queriedList1;
		}
		
		if (endDate != null) {
			for (QuizResponse qr: queriedList2) {
				if (endDate.compareTo(qr.getAnswerTimeRec()) >= 0) {
					queriedList3.add(qr);
				}
			}
		}
		else {
			queriedList3 = queriedList2;
		}
		
		for (QuizResponse qr: queriedList3) {
			User u = uAccessor.getUserByGplusId(qr.getGplusId());
			Map<String, String> bioData = this.getBiographicalData(u.getGplusId());
			sb.append(u.getUserId().toString() + ",");
			sb.append(bioData.get("gender") + ",");
			sb.append(bioData.get("nativeLanguage") + ",");
			sb.append(bioData.get("noLanguages") + ",");
			sb.append(bioData.get("noTextbooks") + ",");
			sb.append(bioData.get("noOutsideCourses") + ",");
			sb.append(bioData.get("languages") + ",");
			sb.append(bioData.get("textbooks") + ",");
			sb.append(bioData.get("outsideCourses") + ",");
			sb.append(lAccessor.getLessonById(qr.getSessionId()).getCourseId().toString() + ",");
			sb.append(qr.getAnswerTimeRec().toString() + ",");
			sb.append(qr.getSeq() + ",");
			sb.append(cAccessor.getCardById(qr.getCardId()).getKanji() + ",");
			sb.append(cAccessor.getCardById(qr.getCardId()).getHiragana() + ",");
			sb.append(cAccessor.getCardById(qr.getCardId()).getKatakana() + ",");
			sb.append(cAccessor.getCardById(qr.getCardId()).getTranslation() + ",");
			sb.append(qr.getConfuserType() + ",");
			sb.append(qr.getNumConfusersUsed() + ",");
			sb.append(qr.getWrongAnswers() + ",");
			sb.append(String.valueOf(qr.isCorrect()) + "\n");
		}
		
		return sb.toString();
	}
}
