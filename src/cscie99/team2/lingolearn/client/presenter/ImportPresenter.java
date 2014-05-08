package cscie99.team2.lingolearn.client.presenter;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HasWidgets;

import cscie99.team2.lingolearn.client.Notice;
import cscie99.team2.lingolearn.client.StorageServiceAsync;
import cscie99.team2.lingolearn.client.UserServiceAsync;
import cscie99.team2.lingolearn.client.view.ImportView;

/**
 * This presenter class manages the ImportView view.
 * This view and presenter pair are meant to provide an interface
 * for uploading CSV files containing cards / decks.  
 * Each CSV is uploaded to the blobstore, and then parsed with
 * a servlet so that each entity can be persisted with objectify
 * to the datastore.
 */
public class ImportPresenter implements Presenter {
		public static final String CSV_UPLOAD_NAME = "csv_blob_upload";
	
	  private final StorageServiceAsync storageService;
	  private final ImportView display;
	  
	  public ImportPresenter( UserServiceAsync userService,
			    StorageServiceAsync storageService,
				HandlerManager eventBus, ImportView view ){
		  
		  this.storageService = storageService;
		  this.display = view;

	  }
	  
	  public void bind() {

		  // add custome change handler class defined below
		  display.getFileUpload().addChangeHandler( new fileUploadChangeHandler() );
			  
		  // Make sure form submits, so that datastream goes to blobstore!
		  display.getUploadButton().addClickHandler( new ClickHandler(){
		  	public void onClick( ClickEvent event ){
		  		display.getForm().submit();
		  	}
		  });
		  
		  display.getForm().addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				resetForm();
				String results = event.getResults();
				Notice.showNotice("Deck #" + results + " uploaded", "success");
			}
			  
		  });
		  
	  }
	
	  @Override
	  public void go(final HasWidgets container) {
		    bind();
		    container.clear();
		    container.add(display.asWidget());
		    resetForm();
	  }
	  
	  
	  private void resetForm() {

		  display.getForm().reset();
		  display.getForm().setMethod(FormPanel.METHOD_POST);
		  display.getForm().setEncoding(FormPanel.ENCODING_MULTIPART);
		  display.getFileUpload().setName(CSV_UPLOAD_NAME);
		  
		  display.getUploadButton().setEnabled(false);

		  storageService.getBlobstoreUploadUri( new AsyncCallback<String>(){
			  public void onSuccess( String uri ){
				  display.getForm().setAction(uri);
			  }
			  
			  public void onFailure( Throwable caught ){
				  Notice.showNotice("Unable to access storage area", "warning");
			  }
		  });
	  }
	  
	  /*
	   * Change handler for adding blobstore URI to ImportView HTML form.
	   */
	  private class fileUploadChangeHandler implements ChangeHandler {
		  public void onChange( ChangeEvent event ){
			  display.getUploadButton().setEnabled(true);
		  }
	  }
}
