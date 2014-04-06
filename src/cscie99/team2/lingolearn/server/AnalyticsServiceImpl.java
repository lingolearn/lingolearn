package cscie99.team2.lingolearn.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cscie99.team2.lingolearn.client.AnalyticsService;
import cscie99.team2.lingolearn.client.CourseService;
import cscie99.team2.lingolearn.shared.Metrics;
import cscie99.team2.lingolearn.shared.OutsideCourse;
import cscie99.team2.lingolearn.shared.Gender;
import cscie99.team2.lingolearn.shared.Language;
import cscie99.team2.lingolearn.shared.Textbook;
import cscie99.team2.lingolearn.shared.User;

public class AnalyticsServiceImpl extends RemoteServiceServlet implements AnalyticsService{

	/**
	 * Pulls the biographical data on a particular user
	 */
	public Map<String, String> getBiographicalData(String gplusId) {
		Map<String, String> data = new HashMap<String, String>();
		
		//Temporarily prepopulate
		Language l = new Language();
		User u = new User("googleID", "gmailaddress", "John",
							"Smith", Gender.Male, l);
		u.addOutsideCourse(new OutsideCourse("123", "Fun Course", "Fun School"));
		u.addTextbook(new Textbook("ABC", "Sweet Book", 2005));
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
		//temporarily prepopulates the data. This would normally pull all student ids currently registered in the course.
		//This data can be found in the CourseRegistration object
		List<String> students = new ArrayList<String>();
		students.add("123");
		students.add("456");
		students.add("789");
		
		return students;
	}
	
	/**
	 * Gets a list of all students in the system.
	 */
	public List<String> getAllStudents() {
		//temporarily prepopulates the data. This would normally pull all students registered in the system, regardless of course.
		List<String> students = new ArrayList<String>();
		students.add("123");
		students.add("456");
		students.add("789");
		students.add("ABC");
		students.add("DEF");
		
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
		String csvText = "StudentID,Gender,NativeLanguage,NumberOfLanguages,NumberOfTextbooks,NumberOfOutsideCourses,"
				+ "Languages,Textbooks,OutsideCourses,RecallRate,AverageQuizReactionTime,AverageFlashCardReactionTime,IndecisionRate,"
				+ "DropRate,AverageSessionTime,RepetitionsPerWeek,PercentNoClue,PercentSortaKnewIt,PercentDefinitelyKnewIt\n";
		
		List<String> allStudents = this.getAllStudents();
		for (String s: allStudents) {
			Map<String, String> bioData = this.getBiographicalData(s);
			Map<String, Float> metricsData = this.getMetricsData(s);
			csvText += s +",";
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
