package cscie99.team2.lingolearn.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cscie99.team2.lingolearn.shared.FlashCardResponse;
import cscie99.team2.lingolearn.shared.QuizResponse;
import cscie99.team2.lingolearn.shared.User;

@RemoteServiceRelativePath("analyticsService")
public interface AnalyticsService extends RemoteService{
	
	public Map<String, String> getBiographicalData (String gplusId);
	public Map<String, Float> getMetricsData (String gplusId);
	
	public List<User> getUsersInCourse (Long courseId);
	public List<User> getAllStudents();
	
	public Map<String, Map<String, String>> getCourseBiographicalData(Long courseId);
	public Map<String, Map<String, Float>> getCourseMetricsData(Long courseId);
	
	public Map<String, Map<String, String>> getAllBiographicalData();
	public Map<String, Map<String, Float>> getAllMetricsData();
	
	public FlashCardResponse storeFlashCardResponse(FlashCardResponse fcResp);
	public QuizResponse storeQuizResponse(QuizResponse qResp);
	
	public String generateCsvAllData();
	

}
