package cscie99.team2.lingolearn.client.presenter;


import cscie99.team2.lingolearn.client.AnalyticsServiceAsync;
import cscie99.team2.lingolearn.client.view.ResearchView;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class ResearchPresenter implements Presenter {  

  private final AnalyticsServiceAsync analyticsService;
  private final HandlerManager eventBus;
  private final ResearchView display;
  private String allCsvDataUri;
  
  public ResearchPresenter(AnalyticsServiceAsync analyticsService, HandlerManager eventBus, ResearchView display) {
    this.analyticsService = analyticsService;
    this.eventBus = eventBus;
    this.display = display;
    this.allCsvDataUri = "";
  }
  
  public void bind() {
    
	display.getDownloadAllButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
    	downloadAllData();
      }
    });
    
  }
  
  public void go(final HasWidgets container) {
    bind();
    container.clear();
    container.add(display.asWidget());
  }
  
  public void downloadAllData() {
	  if (allCsvDataUri.equals("")) {
		  analyticsService.generateCsvAllData(new AsyncCallback<String>() {
		      public void onSuccess(String csvdata) {
		    	  allCsvDataUri = "data:text/csv;charset=utf-8,";
		    	  allCsvDataUri += csvdata;
		    	  display.setDownloadAllButtonText("Save");
		      }
		      
		      public void onFailure(Throwable caught) {
		        Window.alert("Error fetching card.");
		      }
		    });
	  } else {
		  Window.open(URL.encode(allCsvDataUri), "", "");
	  }
  }

}
