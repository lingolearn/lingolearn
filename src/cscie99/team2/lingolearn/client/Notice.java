package cscie99.team2.lingolearn.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Notice {
	/**
	 * @param message	message to be displayed
	 * @param type	one of success, info, warning or danger
	 */
	public static void showNotice(String message, String type) {
		FlowPanel alert = new FlowPanel();
		alert.setStyleName("alert alert-dismissable alert-" + type.toLowerCase());
		
		Button close = new Button("&times;");
		close.setStyleName("close");
		close.getElement().setAttribute("data-dismiss", "alert");
		
		alert.add(close);
		alert.add(new Label(message));
		RootPanel.get("alerts").add(alert);
	}
	
	public static void clearNotices() {
		RootPanel.get("alerts").clear(true);
	}

}
