package cscie99.team2.lingolearn.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cscie99.team2.lingolearn.shared.FlashCardResponse;
import cscie99.team2.lingolearn.shared.QuizResponse;

public interface AnalyticsServiceAsync {

	void getBiographicalData(String gplusId,
			AsyncCallback<Map<String, String>> callback);

	void getMetricsData(String gplusId,
			AsyncCallback<Map<String, Float>> callback);

	void getUsersInCourse(Long courseId, AsyncCallback<List<String>> callback);

	void getCourseBiographicalData(Long courseId,
			AsyncCallback<Map<String, Map<String, String>>> callback);

	void getCourseMetricsData(Long courseId,
			AsyncCallback<Map<String, Map<String, Float>>> callback);

	void getAllBiographicalData(
			AsyncCallback<Map<String, Map<String, String>>> callback);

	void getAllMetricsData(
			AsyncCallback<Map<String, Map<String, Float>>> callback);

	void getAllStudents(AsyncCallback<List<String>> callback);

	void storeFlashCardResponse(FlashCardResponse fcResp,
			AsyncCallback<FlashCardResponse> callback);
	
	public void storeQuizResponse(QuizResponse qResp, 
			AsyncCallback<QuizResponse> callback);

	void generateCsvAllData(AsyncCallback<String> callback);


}
