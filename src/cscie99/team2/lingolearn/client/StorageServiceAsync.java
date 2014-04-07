package cscie99.team2.lingolearn.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StorageServiceAsync {

	public void getBlobstoreUploadUri( AsyncCallback<String> callback );
}
