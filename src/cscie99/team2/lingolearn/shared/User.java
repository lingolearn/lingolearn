package cscie99.team2.lingolearn.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;




import cscie99.team2.lingolearn.shared.Gender;
import cscie99.team2.lingolearn.shared.Language;



public class User implements Serializable {

	private static final long serialVersionUID = 4690764038062275542L;

	String gplusId;					// id from google +
			
	String gmail;
	String firstName;
	String lastName;
	Gender gender;
	Language nativeLanguage;
	Set<Language> languages;
	Set<Textbook> textbooks;
	Set<OutsideCourse> outsideCourses;
	Date userRegistrationTime;
	
	public User(){
		this.languages = new HashSet<Language>();
		this.textbooks = new HashSet<Textbook>();
		this.outsideCourses = new HashSet<OutsideCourse>();
	}
	
	public User( String gplusId, String gmail, String fname,
							String lname, Gender gender, Language nativ ){
		this.gplusId = gplusId;
		this.gmail = gmail;
		this.firstName = fname;
		this.lastName = lname;
		this.gender = gender;
		this.nativeLanguage = nativ;
		this.languages = new HashSet<Language>();
		this.textbooks = new HashSet<Textbook>();
		this.outsideCourses = new HashSet<OutsideCourse>();
		this.userRegistrationTime = new Date();
		
		addLanguage(nativ);
	}
	
	public boolean addLanguage( Language lang ){
		return languages.add(lang);
	}
	
	public boolean addTextbook (Textbook textbook) {
		return textbooks.add(textbook);
	}
	
	public boolean addOutsideCourse (OutsideCourse outsideCourse) {
		return outsideCourses.add(outsideCourse);
	}

	public String getGplusId() {
		return gplusId;
	}

	public void setGplusId(String gplusId) {
		this.gplusId = gplusId;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Language getNativeLanguage() {
		return nativeLanguage;
	}

	public void setNativeLanguage(Language nativeLanguage) {
		this.nativeLanguage = nativeLanguage;
	}

	public Set<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(Set<Language> languages) {
		this.languages = languages;
	}
	
	public Set<Textbook> getTextbooks() {
		return textbooks;
	}
	
	public void setTextbooks(Set<Textbook> textbooks) {
		this.textbooks = textbooks;
	}
	
	public Set<OutsideCourse> getOutsideCourses() {
		return outsideCourses;
	}
	
	public void setOutsideCourses (Set<OutsideCourse> outsideCourses) {
		this.outsideCourses = outsideCourses;
	}

	public Date getUserRegistrationTime() {
		return userRegistrationTime;
	}

	public void setUserRegistrationTime(Date userRegistrationTime) {
		this.userRegistrationTime = userRegistrationTime;
	}
	
	
}
