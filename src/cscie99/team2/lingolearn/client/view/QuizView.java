package cscie99.team2.lingolearn.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import cscie99.team2.lingolearn.client.presenter.CardPresenter;
import cscie99.team2.lingolearn.client.view.CardView.Binder;
import cscie99.team2.lingolearn.shared.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizView extends Composite {
  interface Binder extends UiBinder<Widget, QuizView> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField FlowPanel stemContainer;
  @UiField VerticalPanel answerContainer;
  @UiField Button submitButton;
  @UiField Button nextButton;
  @UiField FlowPanel responseArea;
  
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
  
  public void showSubmitButton() {
	  submitButton.setVisible(true);
  }
  
  public void hideSubmitButton() {
	  submitButton.setVisible(false);
  }
  
  public HasClickHandlers getSubmitButton() {
	  return submitButton;
  }
  
  public void showNextButton() {
	  nextButton.setVisible(true);
  }
  
  public void hideNextButton() {
	  nextButton.setVisible(false);
  }
  
  public HasClickHandlers getNextButton() {
	  return nextButton;
  }
  
  public void clearQuiz() {
	  clearResponseArea();
	  disableSubmitButton();
	  showSubmitButton();
	  hideNextButton();
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
	  InlineHTML h = new InlineHTML();
	  h.setHTML("Correct");
	  responseArea.add(h);
  }
  
  public void showIncorrect() {
	  InlineHTML h = new InlineHTML();
	  h.setHTML("Incorrect");
	  responseArea.add(h);
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
