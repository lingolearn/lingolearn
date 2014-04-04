package cscie99.team2.lingolearn.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cscie99.team2.lingolearn.shared.QuizResponse;

import java.util.List;

public interface QuizResponseServiceAsync {
	public void deleteQuizResponseById(Long sessionId, AsyncCallback<Void> callback);
	public void getAllQuizResponses(AsyncCallback<List<QuizResponse>> callback);
	public void getAllQuizResponsesByUser(String gplusId, AsyncCallback<List<QuizResponse>> callback);
	public void getQuizResponseById(Long sessionId, AsyncCallback<QuizResponse> callback);
	public void storeQuizResponse(QuizResponse qResp, AsyncCallback<QuizResponse> callback);
}
