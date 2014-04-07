package cscie99.team2.lingolearn.client.presenter;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import cscie99.team2.lingolearn.client.AppController;
import cscie99.team2.lingolearn.client.StorageServiceAsync;
import cscie99.team2.lingolearn.client.UserServiceAsync;
import cscie99.team2.lingolearn.client.view.ImportView;

public class ImportPresenter implements Presenter {

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
		    setFormAction();
	  }
	  
	  private void setFormAction(){
		  display.getFileUpload().addChangeHandler( new fileUploadChangeHandler() );
			  
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
