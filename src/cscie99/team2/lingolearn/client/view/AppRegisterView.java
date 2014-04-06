package cscie99.team2.lingolearn.client.view;


import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import cscie99.team2.lingolearn.shared.Gender;
import cscie99.team2.lingolearn.shared.LanguageTypes;

public class AppRegisterView extends Composite {
	interface Binder extends UiBinder<Widget, AppRegisterView> { }
	private static final Binder binder = GWT.create(Binder.class);
	
	/* Widgets */
	@UiField FormPanel form;
	@UiField TextBox gmail;
	@UiField Hidden gplusId;
	@UiField TextBox firstName;
	@UiField TextBox lastName;
	@UiField ListBox yearsExperience;
	@UiField ListBox birthYear;
	@UiField ListBox nativeLanguage;
	@UiField ListBox gender;
	@UiField Button register;
	
	public AppRegisterView(){
		initWidget(binder.createAndBindUi(this));
		
		initForm();
	}
	
	public Widget asWidget() {
		    return this;
	 }
	
	/**
	 * Add the click / submit event for the registration button.
	 * This should be done by the presenter / controller.
	 * @param handler The Handler that will execute when the
	 * register button is clicked.
	 */
	public void addRegistrationHandler( ClickHandler handler ){
		register.addClickHandler( handler );
	}
	
	/**
	 * Set the form read only value of the register user's
	 * gmail address.  This will be set by the presenter.
	 * Users must have logged into gmail / gplus to see this view.
	 * @param gmailAddress the gmail address of the user.
	 */
	public void setRegistrationGmail( String gmailAddress ){
		gmail.setValue( gmailAddress );
	}
	
	/**
	 * Set the form hidden value of the register user's
	 * gplus unique id.  This will be set by the presenter.
	 * Users must have logged into gmail / gplus to see this view.
	 * @param gplus the gplus id of the user.
	 */
	public void setRegistrationGplusId( String gplus ){
		gplusId.setValue( gplus );
	}
	
	public void setSupportedLanguges( List<String> langs ){
		
		Iterator<String> langItr = langs.iterator();
		while( langItr.hasNext() ){
			String supportedLanguage = langItr.next();
			nativeLanguage.addItem( supportedLanguage );
		}
	}
	
	/*
	 * Helper method to initialize form elements 
	 */
	private void initForm(){
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		
		initListBoxes();
		gmail.setReadOnly(true);
	}
	
	
	/*
	 * Helper method to initialize listbox elements 
	 */
	private void initListBoxes(){
		// init years experience select box
		for( int i = 0; i < 100; i++ ){
			yearsExperience.addItem( String.valueOf(i) );
		}
		
		// init birth year
		int thisYear = 2014;//new DateTimeFormat("Y").;
		for( int i = thisYear - 110; i < thisYear; i++ ){
			birthYear.addItem( String.valueOf(i) );
		}
		
		for( LanguageTypes lang : LanguageTypes.values() )
			nativeLanguage.addItem( lang.name() );
		
		for( Gender g : Gender.values() )
			gender.addItem(g.name());
		
	}

	public FormPanel getFormPanel() {
		return form;
	}

	public void setFormPanel(FormPanel formPanel) {
		this.form = formPanel;
	}

	public TextBox getGmail() {
		return gmail;
	}

	public void setGmail(TextBox gmail) {
		this.gmail = gmail;
	}

	public Hidden getGplusId() {
		return gplusId;
	}

	public void setGplusId(Hidden gplusId) {
		this.gplusId = gplusId;
	}

	public TextBox getFirstName() {
		return firstName;
	}

	public void setFirstName(TextBox firstName) {
		this.firstName = firstName;
	}

	public TextBox getLastName() {
		return lastName;
	}

	public void setLastName(TextBox lastName) {
		this.lastName = lastName;
	}

	public ListBox getYearsExperience() {
		return yearsExperience;
	}

	public void setYearsExperience(ListBox yearsExperience) {
		this.yearsExperience = yearsExperience;
	}

	public ListBox getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(ListBox birthYear) {
		this.birthYear = birthYear;
	}

	public ListBox getNativeLanguage() {
		return nativeLanguage;
	}

	public void setNativeLanguage(ListBox nativeLanguage) {
		this.nativeLanguage = nativeLanguage;
	}

	public Button getRegister() {
		return register;
	}

	public void setRegister(Button register) {
		this.register = register;
	}

	public ListBox getGender() {
		return gender;
	}

	public void setGender(ListBox gender) {
		this.gender = gender;
	}
	
	
}
