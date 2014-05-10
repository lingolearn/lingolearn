package cscie99.team2.lingolearn.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.Quiz;
import cscie99.team2.lingolearn.shared.Session;
import cscie99.team2.lingolearn.shared.SessionTypes;

public class CourseView extends Composite {
  
  interface Binder extends UiBinder<Widget, CourseView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField Element courseTitle;
  @UiField Element instructor;
  @UiField Element courseDesc;
  @UiField HTMLPanel assignments;
  @UiField HTMLPanel lessons;
  @UiField HTMLPanel quizes;
  @UiField UListElement lessons_list;
  @UiField UListElement quizes_list;
  @UiField Element addAssignmentLink;
  @UiField TableElement analytics;
  @UiField VerticalPanel flashCardAssessments;
  @UiField ListBox sessionList;
  private List<Session> listOfSessions;
  
  public CourseView() {
	  initWidget(binder.createAndBindUi(this));
  }
  
  public void setCourseData(Course course) {
	  this.courseTitle.setInnerHTML(course.getName());
	  String addLink = "app.html?courseId=" + course.getCourseId() + "#addAssignment";
	  this.addAssignmentLink.setAttribute("href", addLink);

	  setCourseInfo(course);
  }
  
  public void setCourseInfo(Course course){
  	String name = course.getInstructor().getFirstName() + " "
  				+ course.getInstructor().getLastName();
  	this.instructor.setInnerHTML( name );
  	String instructorLink = "/?profile=" + 
  							course.getInstructor().getUserId() + "#profile";
  	this.instructor.setAttribute("href", instructorLink);
  	
  	this.courseDesc.setId(course.getCourseDesc());
  }
  
  public void setAssignmentList(ArrayList<Session> sessions) {
	  assignments.removeStyleName("loading");

	  for (int i=0;i<sessions.size();i++) {
	  	addAssignmentLinks( sessions.get(i) );
	  }	  
  }
  
  
  public void hideFlashCardAssessments() {
	  flashCardAssessments.setVisible(false);
  }
  
  
  private void addAssignmentLinks( Session session ){
	Long sessionId = session.getSessionId();
	Anchor anchor;
	
	if (session instanceof Quiz) {
		anchor = new Anchor(session.getDeck().getDesc() + " (" +
			session.getSessionType() + ")", "app.html?sessionId=" +
			sessionId + "#session");
	} else {
		anchor = new Anchor(session.getDeck().getDesc(), 
			"app.html?sessionId=" + sessionId + "#session");
	}
  	
  	// GWT doesn't support create LI elements, we have to do it manually... sigh.
	final LIElement topItem = Document.get().createLIElement();
	topItem.appendChild(anchor.getElement());
	
	// We only display the session type choices for lessons.
	// quiz session types are set by the instructor
	if (session instanceof Quiz) {
		quizes_list.appendChild(topItem);
		quizes.removeStyleName("hidden");  // might already be removed
	} else {
		anchor.setStyleName("dropdown-toggle");
		anchor.getElement().setAttribute("data-toggle", "dropdown");
		
		final SpanElement caret = Document.get().createSpanElement();
		caret.setClassName("caret");
		anchor.getElement().appendChild(caret);
		
		lessons_list.appendChild(topItem);
		lessons.removeStyleName("hidden");  // might already be removed
		
		UListElement dropdown = Document.get().createULElement();
		dropdown.setClassName("dropdown-menu");
		
		for (SessionTypes type : SessionTypes.values()) {
			String href = "app.html?sessionId=" + sessionId
								+ "&type=" + type.name() + "#session";
			Anchor typeAnchor = new Anchor(type.toString(), href);
			
			final LIElement item = Document.get().createLIElement();
			item.appendChild(typeAnchor.getElement());
			
			dropdown.appendChild(item);
		}
		topItem.appendChild(dropdown);
	}
  }
  
  public void setSessionList(List<Session> sessions) {
	  listOfSessions = sessions;
	  
	  //Add the null course to represent "all sessions"
	  listOfSessions.add(0,null);
	  sessionList.addItem("All sessions");
	  
	  for (int i=1;i<sessions.size();i++) {
		  Session s = sessions.get(i);
		  if (s instanceof Quiz) {
			  sessionList.addItem(s.getDeck().getDesc() + " (" + s.getSessionType() + ")");
		  } else {
			  sessionList.addItem(s.getDeck().getDesc());
		  }
	  }
  }
  
  public Session getSelectedSession() {
	  int idx = sessionList.getSelectedIndex();
	  Session s = listOfSessions.get(idx);
	  return s;
  }
  
  public ListBox getSessionListControl() {
	  return sessionList;
  }
  
  
  public void setStatisticsHeader(String[] data) {
	  TableRowElement row = analytics.getTHead().insertRow(-1);
	  for (String element : data) {
		  row.insertCell(-1).setInnerText(element);
	  }
  }
  
  public void addStatisticsRow(String[] data) {
	  TableRowElement row = analytics.insertRow(-1);
	  for (String element : data) {
		  row.insertCell(-1).setInnerText(element);
	  }
  }
  
  public void resetStatistics() {
	  analytics.removeAllChildren();
	  analytics.setInnerHTML("<thead></thead><tbody></tbody>");
  }
  
  public void resetVisualizations() {
	  flashCardAssessments.clear();
  }
  
  public void setVisualizations(final float noClue, final float sortaKnewIt, final float definitelyKnewIt) {
	// Create a callback to be called when the visualization API
	    // has been loaded.
	    Runnable onLoadCallback = new Runnable() {
	      public void run() {
	        Panel panel = flashCardAssessments;
	 
	        // Create a pie chart visualization.
	        PieChart pie = new PieChart(createTable(noClue, sortaKnewIt, definitelyKnewIt), createPieOptions());

	        pie.addSelectHandler(createSelectHandler(pie));
	        panel.add(pie);
	      }
	    };

	    // Load the visualization api, passing the onLoadCallback to be called
	    // when loading is done.
	    VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);
  }
  
  private PieChart.PieOptions createPieOptions() {
	    PieChart.PieOptions options = PieChart.PieOptions.create();
	    options.setWidth(500);
	    options.setHeight(300);
	    options.set3D(true);
	    options.setTitle("Responses");
	    options.set("tooltip.text", "percentage");
	    return options;
	  }

  private SelectHandler createSelectHandler(final PieChart chart) {
	  return new SelectHandler() {
		// TODO Refactor or remove the usage of "messae" in this function
	    @Override
	    public void onSelect(SelectEvent event) {
	      String message = "";
	      
	      // May be multiple selections.
	      JsArray<Selection> selections = chart.getSelections();

	      for (int i = 0; i < selections.length(); i++) {
	        // add a new line for each selection
	        message += i == 0 ? "" : "\n";
	        
	        Selection selection = selections.get(i);

	        if (selection.isCell()) {
	          // isCell() returns true if a cell has been selected.
	           
	          // getRow() returns the row number of the selected cell.
	          int row = selection.getRow();
	          // getColumn() returns the column number of the selected cell.
	          int column = selection.getColumn();
	          message += "cell " + row + ":" + column + " selected";
	        } else if (selection.isRow()) {
	          // isRow() returns true if an entire row has been selected.
	           
	          // getRow() returns the row number of the selected row.
	          int row = selection.getRow();
	          message += "row " + row + " selected";
	        } else {
	          // unreachable
	          message += "Pie chart selections should be either row selections or cell selections.";
	          message += "  Other visualizations support column selections as well.";
	        }
	      }
	        
	      }
	  };
	}

  private AbstractDataTable createTable(float nc, float ski, float dki) {
	  DataTable data = DataTable.create();
	  data.addColumn(ColumnType.STRING, "Flash Card Assessment");
	  data.addColumn(ColumnType.NUMBER, "Number of Responses");
	  data.addRows(3);
	  data.setValue(0, 0, "No Clue");
	  data.setValue(0, 1, nc);
	  data.setValue(1, 0, "Sorta Knew It");
	  data.setValue(1, 1, ski);
	  data.setValue(2, 0, "Definitely Knew It");
	  data.setValue(2, 1, dki);
	  return data;
  }
	
  
  public Widget asWidget() {
    return this;
  }
}
