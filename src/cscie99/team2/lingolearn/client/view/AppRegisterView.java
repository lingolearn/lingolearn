package cscie99.team2.lingolearn.client.view;


import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AppRegisterView extends Composite {

	private static final String REG_BTN_TXT = "Register";
	
	/* Widgets */
	VerticalPanel holder = new VerticalPanel();
	private FormPanel formPanel = new FormPanel();
	private TextBox gmail = new TextBox();
	private Hidden gplusId = new Hidden();
	private TextBox firstName = new TextBox();
	private TextBox lastName = new TextBox();
	private ListBox yearsExperience = new ListBox();
	private ListBox birthYear = new ListBox();
	private ListBox nativeLanguage = new ListBox();
	private Button register = new Button(REG_BTN_TXT);
	
	private Label emailAddressLabel;
	private Label firstNameLabel;
	private Label lastNameLabel;
	private Label yearsExperienceLabel;
	private Label birthYearLabel;
	private Label nativeLanguageLabel;
	
	public AppRegisterView(){
		this.initWidget(this.holder);
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
	public void addRegistrationHandler( Handler handler ){
		register.addAttachHandler(handler);
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
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
		
		initListBoxes();
		emailAddressLabel = new Label("Email Address");
		firstNameLabel = new Label("First Name");
		lastNameLabel = new Label("Last Name");
		birthYearLabel = new Label("What year were you born?");
		yearsExperienceLabel = new Label("Years Experience");
		nativeLanguageLabel = new Label("Native Language");
		gmail.setReadOnly(true);
		
		holder.add(emailAddressLabel);
		holder.add(gmail);
		holder.add(gplusId);
		holder.add(firstNameLabel);
		holder.add(firstName);
		holder.add(lastNameLabel);
		holder.add(lastName);
		holder.add(yearsExperienceLabel);
		holder.add(yearsExperience);
		holder.add(birthYearLabel);
		holder.add(birthYear);
		holder.add(nativeLanguageLabel);
		holder.add(nativeLanguage);
		holder.add(register);
		
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
		
		// List<String> languages = cardService.getSupportedLanguages();
		/*
		List<String> languages = new ArrayList<String>();
		languages.add("English");
		languages.add("Japanese");
		
		for( int i = 0; i < languages.size(); i++ ){
			nativeLanguage.addItem( languages.get(i) );
		}
		*/
		
	}

	public VerticalPanel getHolder() {
		return holder;
	}

	public void setHolder(VerticalPanel holder) {
		this.holder = holder;
	}

	public FormPanel getFormPanel() {
		return formPanel;
	}

	public void setFormPanel(FormPanel formPanel) {
		this.formPanel = formPanel;
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

	public static String getRegBtnTxt() {
		return REG_BTN_TXT;
	}
	
}
