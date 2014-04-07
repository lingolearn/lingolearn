package cscie99.team2.lingolearn.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cscie99.team2.lingolearn.shared.FlashCardResponse;

import java.util.List;

@RemoteServiceRelativePath("flashCardResponseService")
public interface FlashCardResponseService extends RemoteService{
	public void deleteFlashCardResponseById(Long sessionId);
	public List<FlashCardResponse> getAllFlashCardResponses();
	public List<FlashCardResponse> getAllFlashCardResponsesByUser(String gplusId);
	public FlashCardResponse getFlashCardResponseById(Long sessionId);
	public FlashCardResponse storeFlashCardResponse(FlashCardResponse fcResp);
		
}
