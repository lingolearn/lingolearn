package cscie99.team2.lingolearn.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cscie99.team2.lingolearn.client.AnalyticsService;
import cscie99.team2.lingolearn.server.datastore.CourseRegistrationDAO;
import cscie99.team2.lingolearn.server.datastore.UserDAO;
import cscie99.team2.lingolearn.shared.CourseRegistration;
import cscie99.team2.lingolearn.shared.Metrics;
import cscie99.team2.lingolearn.shared.OutsideCourse;
import cscie99.team2.lingolearn.shared.Language;
import cscie99.team2.lingolearn.shared.Textbook;
import cscie99.team2.lingolearn.shared.User;

public class AnalyticsServiceImpl extends RemoteServiceServlet implements AnalyticsService{
	
	UserDAO uAccessor;
	CourseRegistrationDAO crAccessor;
	
	public AnalyticsServiceImpl() {
		uAccessor = UserDAO.getInstance();
		crAccessor = CourseRegistrationDAO.getInstance();
	}

	/**
	 * Pulls the biographical data on a particular user
	 */
	public Map<String, String> getBiographicalData(String gplusId) {
		Map<String, String> data = new HashMap<String, String>();
		
		User u = uAccessor.getUserById(gplusId);
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
		
		
		data.put("id", gplusId);
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
		Metrics m = new Metrics(gplusId);
			
	 	data.put("recallRate", m.getRecallRate());
	 	data.put("avgQuizReactionTime", m.getAvgQuizReactionTime());
	 	data.put("avgFlashCardReactionTime", m.getAvgFlashCardReactionTime());
	 	data.put("indecisionRate", m.getIndecisionRate());
	 	data.put("dropRate", m.getDropRate());
	 	data.put("averageSessionTime", m.getAverageSessionTime());
	 	data.put("repetitionsPerWeek", m.getRepetitionsPerWeek());
	 	data.put("percentNoClue", m.getPercentNoClue());
	 	data.put("percentSortaKnewIt", m.getPercentSortaKnewIt());
	 	data.put("percentDefinitelyKnewIt", m.getPercentDefinitelyKnewIt());
	 	
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
		String csvText = "StudentID,GMail,Gender,NativeLanguage,NumberOfLanguages,NumberOfTextbooks,NumberOfOutsideCourses,"
				+ "Languages,Textbooks,OutsideCourses,RecallRate,AverageQuizReactionTime,AverageFlashCardReactionTime,IndecisionRate,"
				+ "DropRate,AverageSessionTime,RepetitionsPerWeek,PercentNoClue,PercentSortaKnewIt,PercentDefinitelyKnewIt";
		
		List<String> allStudents = this.getAllStudents();
		for (String s: allStudents) {
			Map<String, String> bioData = this.getBiographicalData(s);
			Map<String, Float> metricsData = this.getMetricsData(s);
			csvText += s +",";
			csvText += bioData.get("gmail") + ",";
			csvText += bioData.get("gender") + ",";
			csvText += bioData.get("nativeLanguage") + ",";
			csvText += bioData.get("noLanguages") + ",";
			csvText += bioData.get("noTextbooks") + ",";
			csvText += bioData.get("noOutsideCourses") + ",";
			csvText += bioData.get("languages") + ",";
			csvText += bioData.get("textbooks") + ",";
			csvText += bioData.get("outsideCourses") + ",";
			csvText += metricsData.get("recallRate") + ",";
			csvText += metricsData.get("avgQuizReactionTime") + ",";
			csvText += metricsData.get("avgFlashCardReactionTime") + ",";
			csvText += metricsData.get("indecisionRate") + ",";
			csvText += metricsData.get("dropRate") + ",";
			csvText += metricsData.get("averageSessionTime") + ",";
			csvText += metricsData.get("repetitionsPerWeek") + ",";
			csvText += metricsData.get("percentNoClue") + ",";
			csvText += metricsData.get("percentSortaKnewIt") + ",";
			csvText += metricsData.get("percentDefinitelyKnewIt") + "\n";
		}
		
		
		return csvText;
	}

}
