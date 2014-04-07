package cscie99.team2.lingolearn.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("storageService")
public interface StorageService extends RemoteService {

	public String getBlobstoreUploadUri();
}
