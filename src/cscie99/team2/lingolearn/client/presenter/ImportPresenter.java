package cscie99.team2.lingolearn.client.presenter;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasWidgets;

import cscie99.team2.lingolearn.client.AppController;
import cscie99.team2.lingolearn.client.StorageServiceAsync;
import cscie99.team2.lingolearn.client.UserServiceAsync;
import cscie99.team2.lingolearn.client.view.ImportView;

public class ImportPresenter implements Presenter {

		public static final String CSV_UPLOAD_NAME = "csv_blob_upload";
	
	  private final UserServiceAsync userService;
	  private final StorageServiceAsync storageService;
	  private final HandlerManager eventBus;
	  private final ImportView display;
	  
	  public ImportPresenter( UserServiceAsync userService,
			    StorageServiceAsync storageService,
				HandlerManager eventBus, ImportView view ){
		  
		  this.userService = userService;
		  this.storageService = storageService;
		  this.eventBus = eventBus;
		  this.display = view;

	  }
	  
	  public void bind(){
		  
	  }
	
	  public void go(final HasWidgets container) {
		    bind();
		    container.clear();
		    container.add(display.asWidget());
		    initForm();
		    
		    
	  }
	  
	  private void initForm(){
		  display.getFileUpload().addChangeHandler( new fileUploadChangeHandler() );
			display.getForm().setMethod(FormPanel.METHOD_POST);
			display.getForm().setEncoding(FormPanel.ENCODING_MULTIPART);
		  display.getFileUpload().setName(CSV_UPLOAD_NAME);
		  
		  display.getUploadButton().addClickHandler( new ClickHandler(){
		  	public void onClick( ClickEvent event ){
		  		display.getForm().submit();
		  	}
		  });
	  }
	  
	  private class fileUploadChangeHandler implements ChangeHandler {
		  public void onChange( ChangeEvent event ){
			  storageService.getBlobstoreUploadUri( new AsyncCallback<String>(){
				  public void onSuccess( String uri ){
					  display.getForm().setAction(uri);
				  }
				  
				  public void onFailure( Throwable caught ){
					  caught.printStackTrace();
					  AppController.redirectUser("/error.html");
				  }
			  });
		  }
	  }
}
