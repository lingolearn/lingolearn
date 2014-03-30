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
							"Smith", Gender.MALE, l);
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
		
		//Temporarily prepopulate
		m.setRecallRate(0.5f);
	 	m.setAvgReactionTime(8.5f);
	 	m.setIndecisionRate(0.33f);
	 	m.setDropRate(0.2f);
	 	m.setAverageSessionTime(23145.4f);
	 	m.setRepetitionsPerWeek(2.5f);
	 	
	 	data.put("recallRate", m.getRecallRate());
	 	data.put("avgReactionTime", m.getAvgReactionTime());
	 	data.put("indecisionRate", m.getIndecisionRate());
	 	data.put("dropRate", m.getDropRate());
	 	data.put("averageSessionTime", m.getAverageSessionTime());
	 	data.put("repetitionsPerWeek", m.getRepetitionsPerWeek());
	 	
	 	return data;
	}

	/**
	 * Gets a list of all student id's enrolled in a specific course 
	 */
	public List<String> getUsersInCourse(String courseId) {
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
	public Map<String, Map<String, String>> getCourseBiographicalData(String courseId) {
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
	public Map<String, Map<String, Float>> getCourseMetricsData(String courseId) {
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

}
