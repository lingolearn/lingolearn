package cscie99.team2.lingolearn.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cscie99.team2.lingolearn.shared.FlashCardResponse;

public interface FlashCardResponseServiceAsync {

	void deleteFlashCardResponseById(Long sessionId,
			AsyncCallback<Void> callback);

	void getAllFlashCardResponses(
			AsyncCallback<List<FlashCardResponse>> callback);

	void getAllFlashCardResponsesByUser(String gplusId,
			AsyncCallback<List<FlashCardResponse>> callback);

	void getFlashCardResponseById(Long sessionId,
			AsyncCallback<FlashCardResponse> callback);

	void storeFlashCardResponse(FlashCardResponse qResp,
			AsyncCallback<FlashCardResponse> callback);

}
