package cscie99.team2.lingolearn.server;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cscie99.team2.lingolearn.client.StorageService;

public class StorageServiceImpl extends RemoteServiceServlet implements StorageService {

	public static final String UPLOAD_SUCCESS_PATH = "/import";
	
	public String getBlobstoreUploadUri(){
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		return blobstoreService.createUploadUrl(UPLOAD_SUCCESS_PATH);
				
	}
}
