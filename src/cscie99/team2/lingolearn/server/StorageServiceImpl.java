package cscie99.team2.lingolearn.server;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cscie99.team2.lingolearn.client.StorageService;

/**
 * Implementation of the StorageService interface.  This Service
 * provides an RPC interface to datastore items that are needed 
 * in the GWT client package.
 * 
 * @note - this functionality could be moved to another service, but
 * right now it makes sense to modularize it in case more media upload
 * support is added.
 */
public class StorageServiceImpl extends RemoteServiceServlet implements StorageService {
	private static final long serialVersionUID = 7582065582927014952L;
	public static final String UPLOAD_SUCCESS_PATH = "/import";
	
	public String getBlobstoreUploadUri() {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		return blobstoreService.createUploadUrl(UPLOAD_SUCCESS_PATH);
	}
}
