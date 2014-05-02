package cscie99.team2.lingolearn.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cscie99.team2.lingolearn.shared.FlashCardResponse;
import cscie99.team2.lingolearn.shared.QuizResponse;
import cscie99.team2.lingolearn.shared.Session;
import cscie99.team2.lingolearn.shared.User;
import cscie99.team2.lingolearn.shared.UserSession;

@RemoteServiceRelativePath("analyticsService")
public interface AnalyticsService extends RemoteService{
	
	public String getUserName (String gplusId);
	public Map<String, String> getBiographicalData (String gplusId);
	public Map<String, Float> getMetricsData (String gplusId);
	public Map<String, Float> getMetricsDataByUser (String gplusId);
	public Map<String, Float> getMetricsDataBySessions (long sessionId, long userSessionId);
	public Map<String, Float> getMetricsDataByUserAndAssignment (String gplusId, long sessionId);
	
	public List<User> getUsersInCourse (Long courseId);
	public List<User> getAllStudents();
	public List<UserSession> getUserSessions(String gplusId);
	public List<Session> getCourseAssignments (long courseId);
	
	public Map<String, Map<String, String>> getCourseBiographicalData(Long courseId);
	public Map<String, Map<String, Float>> getCourseMetricsData(Long courseId);
	public Map<String, Map<String, Float>> getCourseMetricsDataResearcherView (Long courseId);
	public Map<String, Map<String, Float>> getCourseMetricsDataInstructorView (Long courseId, Long sessionId);
	public List<List<Object>> getCourseMetricsDataStudentView (Long courseId, String gplusId);
	
	public Map<String, Map<String, String>> getAllBiographicalData();
	public Map<String, Map<String, Float>> getAllMetricsData();
	
	public FlashCardResponse storeFlashCardResponse(FlashCardResponse fcResp);
	public QuizResponse storeQuizResponse(QuizResponse qResp);
	public void deleteQuizResponseById(Long sessionId);
	public void deleteFlashCardResponseById(Long sessionId);
	
	public String generateCsvAllData();
	public String generateFlashCardResponseDownload(Long courseId, Date startDate, Date endDate);
	public String generateQuizResponseDownload(Long courseId, Date startDate, Date endDate);
}
