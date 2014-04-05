package cscie99.team2.lingolearn.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cscie99.team2.lingolearn.shared.QuizResponse;

import java.util.List;

@RemoteServiceRelativePath("quizResponseService")
public interface QuizResponseService extends RemoteService {
	public void deleteQuizResponseById(Long sessionId);
	public List<QuizResponse> getAllQuizResponses();
	public List<QuizResponse> getAllQuizResponsesByUser(String gplusId);
	public QuizResponse getQuizResponseById(Long sessionId);
	public QuizResponse storeQuizResponse(QuizResponse qResp);
}
