package cscie99.team2.lingolearn.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AnalyticsServiceAsync {

	void getBiographicalData(String gplusId,
			AsyncCallback<Map<String, String>> callback);

	void getMetricsData(String gplusId,
			AsyncCallback<Map<String, Float>> callback);

	void getUsersInCourse(String courseId, AsyncCallback<List<String>> callback);

	void getCourseBiographicalData(String courseId,
			AsyncCallback<Map<String, Map<String, String>>> callback);

	void getCourseMetricsData(String courseId,
			AsyncCallback<Map<String, Map<String, Float>>> callback);

	void getAllBiographicalData(
			AsyncCallback<Map<String, Map<String, String>>> callback);

	void getAllMetricsData(
			AsyncCallback<Map<String, Map<String, Float>>> callback);

	void getAllStudents(AsyncCallback<List<String>> callback);

}
