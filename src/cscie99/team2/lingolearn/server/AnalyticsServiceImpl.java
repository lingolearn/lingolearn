package cscie99.team2.lingolearn.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cscie99.team2.lingolearn.client.AnalyticsService;
import cscie99.team2.lingolearn.server.datastore.CourseRegistrationDAO;
import cscie99.team2.lingolearn.server.datastore.FlashCardResponseDAO;
import cscie99.team2.lingolearn.server.datastore.QuizResponseDAO;
import cscie99.team2.lingolearn.server.datastore.UserDAO;
import cscie99.team2.lingolearn.shared.FlashCardResponse;
import cscie99.team2.lingolearn.shared.OutsideCourse;
import cscie99.team2.lingolearn.shared.Language;
import cscie99.team2.lingolearn.shared.QuizResponse;
import cscie99.team2.lingolearn.shared.Textbook;
import cscie99.team2.lingolearn.shared.User;

public class AnalyticsServiceImpl extends RemoteServiceServlet implements AnalyticsService{
	
	UserDAO uAccessor;
	CourseRegistrationDAO crAccessor;
	QuizResponseDAO qRespAccessor;
	FlashCardResponseDAO fcRespAccessor;
	MetricsCalculator mc;
	
	
	public AnalyticsServiceImpl() {
		uAccessor = UserDAO.getInstance();
		crAccessor = CourseRegistrationDAO.getInstance();
		qRespAccessor = QuizResponseDAO.getInstance();
		fcRespAccessor = FlashCardResponseDAO.getInstance();
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
		data.put("nativeLanguage", u.getNativeLanguage().toString());
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
	public List<String> getUsersInCourse(Long courseId) {
		
		List<User> users = new ArrayList<User>();
		if (crAccessor.getUserCourseId(courseId) != null) {
			users = crAccessor.getUserCourseId(courseId);
		}
		List<String> students = new ArrayList<String>();
				
		if (users != null) {
			for (User u: users) {
				students.add(u.getGplusId());
			}
		}
				
		return students;
	}
	
	/**
	 * Gets a list of all students in the system.
	 */
	public List<String> getAllStudents() {
		List<String> students = new ArrayList<String>();
		List<User> users = new ArrayList<User>();
		
		if (uAccessor.getAllUsers() != null) {
			users = uAccessor.getAllUsers();
		}
		
		if (users != null) {
			for (User u: users) {
				students.add(u.getGplusId());
			}
		}
		
		return students;
	}

	/**
	 * Pulls all biographical data for students in a particular course
	 */
	public Map<String, Map<String, String>> getCourseBiographicalData(Long courseId) {
		List<String> students = getUsersInCourse(courseId);
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		
		for (String s: students) {
			Map<String, String> bioData = getBiographicalData (s);
			data.put(s, bioData);
		}
		
		return data;
	}

	/**
	 *  Pulls all metrics data for students in a particular course.
	 */
	public Map<String, Map<String, Float>> getCourseMetricsData(Long courseId) {
		List<String> students = getUsersInCourse(courseId);
		Map<String, Map<String, Float>> data = new HashMap<String, Map<String, Float>>();
		
		for (String s: students) {
			Map<String, Float> metricsData = getMetricsData (s);
			data.put(s, metricsData);
		}
		
		return data;
	}

	/**
	 * Pulls all biographical data for all students in the system.
	 */
	public Map<String, Map<String, String>> getAllBiographicalData() {
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		
		List<String> students = getAllStudents();
		
		for (String s: students) {
			Map<String, String> bioData = getBiographicalData (s);
			data.put(s, bioData);
		}
		
		return data;
	}

	/**
	 * Pulls all metrics data for all students in the system.
	 */
	public Map<String, Map<String, Float>> getAllMetricsData() {
		Map<String, Map<String, Float>> data = new HashMap<String, Map<String, Float>>();
		
		List<String> students = getAllStudents();
		
		for (String s: students) {
			Map<String, Float> metricsData = getMetricsData (s);
			data.put(s, metricsData);
		}
		
		return data;
	}
	
	public String generateCsvAllData() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("StudentID,GMail,Gender,NativeLanguage,NumberOfLanguages,NumberOfTextbooks,NumberOfOutsideCourses,"
				+ "Languages,Textbooks,OutsideCourses,RecallRate,AverageQuizReactionTime,AverageFlashCardReactionTime,IndecisionRate,"
				+ "DropRate,AverageSessionTime,RepetitionsPerWeek,PercentNoClue,PercentSortaKnewIt,PercentDefinitelyKnewIt");
		sb.append("\n");
		
		List<String> allStudents = this.getAllStudents();
		for (String s: allStudents) {
			Map<String, String> bioData = this.getBiographicalData(s);
			Map<String, Float> metricsData = this.getMetricsData(s);
			sb.append(s + ",");
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

}
