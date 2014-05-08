package cscie99.team2.lingolearn.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;





import cscie99.team2.lingolearn.shared.Gender;
import cscie99.team2.lingolearn.shared.Language;

public class User implements Serializable {

	private static final long serialVersionUID = 4690764038062275542L;

	Long userId;
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
		this( null, null, null, null, null, null, null );
	}
	
	public User(  String gplusId, String gmail, String fname,
							String lname, Gender gender, Language nativ ){
		this( null, gplusId, gmail, fname, lname, gender, nativ );
	}
	
	public User( Long userId, String gplusId, String gmail, String fname,
							String lname, Gender gender, Language nativ ){
		
		this.languages = new HashSet<Language>();
		this.textbooks = new HashSet<Textbook>();
		this.outsideCourses = new HashSet<OutsideCourse>();
		
		this.userId = userId;
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

	public boolean addLanguages (Language language) {
		return languages.add(language);
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
	
	/**
	 * HashCode method takes the gmail, gplusid, and userid into 
	 * consideration.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gmail == null) ? 0 : gmail.hashCode());
		result = prime * result + ((gplusId == null) ? 0 : gplusId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/**
	 * Equals method to determine equality between two User objects.
	 */
	@Override
	public boolean equals(Object other){
		if (other == null) {
			return false;
		}
		if (!(other instanceof User)) {
			return false;
		}
		User otherUser = (User) other;
		if (!otherUser.getUserId().equals(this.getUserId())) {
			return false;
		}
		if (!otherUser.getGmail().equals(this.getGmail())) {
			return false;
		}
		return true;
	}	
	
/*	@Override
	public String toString() {
		String result = null;
		result  = "User Id   : " + this.getGplusId() + "\n";
		result += "First Name: " + this.getFirstName() + "\n";
		result += "Last Name : " + this.getLastName() + "\n";
		result += "Gender    : " + this.getGender() + "\n";
		result += "Reg. Date : " + this.getUserRegistrationTime() + "\n";
		result += "     Native Language: " + this.getNativeLanguage().getLangName() + "\n";
		for (Language lang: this.getLanguages()) {
			result += "     Other Language : " + lang.getLangName() + "\n";
		}
		for (Textbook tb: this.getTextbooks()) {
			result += "     Texbooks used  : " + tb.getName() + "\n" ;
		}
		for (OutsideCourse oc: this.getOutsideCourses()) {
			result += "     Other Courses  : " + oc.getInstitution() + "\n";
		}
		return result;	
	}*/
	
	public String getFullName(){
	 	return firstName + " " + lastName;
	 }
}
