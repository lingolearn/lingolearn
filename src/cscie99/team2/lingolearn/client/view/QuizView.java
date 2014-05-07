package cscie99.team2.lingolearn.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizView extends Composite {
  interface Binder extends UiBinder<Widget, QuizView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField FlowPanel card;
  @UiField FlowPanel stemContainer;
  @UiField FlowPanel answerContainer;
  @UiField Button submitButton;
  @UiField Button nextButton;
  @UiField FlowPanel responseArea;
  @UiField FlowPanel successOverlay;
  
  ArrayList<RadioButton> answerNodes;
  
  public QuizView() {
	  initWidget(binder.createAndBindUi(this));
	  answerNodes = new ArrayList<RadioButton>();
  }
  
  public void enableSubmitButton() {
	  submitButton.setEnabled(true);
  }
  
  public void disableSubmitButton() {
	  submitButton.setEnabled(false);
  }
  
  public void hideButtons() {
	  submitButton.setVisible(false);
	  nextButton.setVisible(false);	
  }
  
  public void showSubmitButton() {
	  submitButton.setVisible(true);
	  nextButton.setVisible(false);
  }

  public HasClickHandlers getSubmitButton() {
	  return submitButton;
  }
  
  public void showNextButton() {
	  submitButton.setVisible(false);
	  nextButton.setVisible(true);
  }
  
  public HasClickHandlers getNextButton() {
	  return nextButton;
  }
  
  public void clearQuiz() {
	  clearResponseArea();
	  disableSubmitButton();
	  showSubmitButton();
	  stemContainer.clear();
	  answerContainer.clear();
	  answerNodes = new ArrayList<RadioButton>();
	  disableSubmitButton();
  }
  
  public void addToStem(String stemText) {
	  InlineHTML stem = new InlineHTML();
	  stem.setHTML(stemText);
	  stemContainer.add(stem);
  }
  
  public void addAnswer(String answerText) {
	  RadioButton r = new RadioButton("answersGroup");
	  r.setStyleName("quiz-choice");
	  r.setHTML(answerText);
	  answerNodes.add(r);
	  answerContainer.add(r);
	  r.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			checkIfAnswerSelected();
		} 
	  });
	  shuffleAnswerNodes();
  }
  
  public void clearResponseArea() {
	  responseArea.clear();
  }
  
  public void showCorrect() {
	  successOverlay.addStyleName("card-overlay-forefront");
	  successOverlay.addStyleName("card-overlay-show");
	  
	  // has to be done in two seperate steps to avoid covering up the form elements
	  Timer overlay_timer = new Timer() {
	      public void run() {
	    	  successOverlay.removeStyleName("card-overlay-show");
	      }
	  };
	  
	  Timer forefront_timer = new Timer() {
	      public void run() {
	    	  successOverlay.removeStyleName("card-overlay-forefront");
	      }
	  };
	  
	  overlay_timer.schedule(1000);
	  forefront_timer.schedule(1350);
  }
  
  public void showIncorrect(String correctAnswer) {
	  card.addStyleName("shake");
	  
	  Timer shake_timer = new Timer() {
	      public void run() {
	    	  card.removeStyleName("shake");
	      }
	  };
	  
	  shake_timer.schedule(700);
	  
	  for (RadioButton element : answerNodes) {
		  element.setEnabled(false);
		  if (element.getHTML().equals(correctAnswer))
			  element.addStyleName("text-success");
	  }
	  
	  showNextButton();
  }
  
  public String getSelectedAnswer() {
	  String res = "";
	  for (int i=0;i<answerNodes.size();i++) {
		  RadioButton r = answerNodes.get(i);
		  if (r.getValue()) {
			  res = r.getHTML();
		  }
	  }
	  return res;
  }
  
  private void checkIfAnswerSelected() {
	  for (int i=0;i<answerNodes.size();i++) {
		  RadioButton r = answerNodes.get(i);
		  if (r.getValue()) {
			  enableSubmitButton();
		  }
	  }
  }
  
  private void shuffleAnswerNodes() {
	  //Shuffle.  Note that Collections.shuffle() not supported in GWT
	  Random random = new Random();  
	  for(int i=0;i<answerNodes.size();i++) {  
	      Collections.swap(answerNodes,i,i+random.nextInt(answerNodes.size()-i));  
	  }
	  
	  //Remove and re-add
	  for (int i=0;i<answerNodes.size();i++) {
		  answerContainer.remove(answerNodes.get(i));
	  }
	  for (int i=0;i<answerNodes.size();i++) {
		  answerContainer.add(answerNodes.get(i));
	  }
  }
  
  public Widget asWidget() {
    return this;
  }
}
