package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import cscie99.team2.lingolearn.shared.Gender;
import cscie99.team2.lingolearn.shared.Language;
import cscie99.team2.lingolearn.shared.OutsideCourse;
import cscie99.team2.lingolearn.shared.Textbook;
import cscie99.team2.lingolearn.shared.User;



@Entity(name="ObjectifyableUser")
public class ObjectifyableUser implements Serializable {

	private static final long serialVersionUID = 4690764038062275542L;

	@Id String gplusId;					// id from google +		
	@Index String gmail;
	String firstName;					// not indexed, no search by this field
	String lastName;					// not indexed, no search by this field
	@Index Gender gender;
	@Index ObjectifyableLanguage nativeLanguage;
	@Index Set<ObjectifyableLanguage> languages;
	@Index Set<ObjectifyableTextbook> textbooks;
	@Index Set<ObjectifyableOutsideCourse> outsideCourses;
	Date userRegistrationTime;			// not indexed, no search by this field
	
	public ObjectifyableUser(){}
	
	/**
	 * This method creates Objectifyable Proxy from real object
	 * @param user		User object
	 */
	public ObjectifyableUser( User user ){
		this.gplusId = user.getGplusId();
		this.gmail = user.getGmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.gender = user.getGender();
		this.nativeLanguage = new ObjectifyableLanguage(user.getNativeLanguage());
		languages = new HashSet<ObjectifyableLanguage>();
		for (Language lang: user.getLanguages()) {
			this.languages.add(new ObjectifyableLanguage(lang));
		}
		textbooks = new HashSet<ObjectifyableTextbook>();
		for (Textbook book: user.getTextbooks()) {
			this.textbooks.add(new ObjectifyableTextbook(book));
		}
		outsideCourses = new HashSet<ObjectifyableOutsideCourse>();
		for (OutsideCourse course: user.getOutsideCourses()) {
			this.outsideCourses.add(new ObjectifyableOutsideCourse(course));
		}
		this.userRegistrationTime = user.getUserRegistrationTime();
	}

	/**
	 * This method reconstructs real object from Objectifyable Proxy
	 * @return User object
	 */
	public User getUser() {
		User u = new User ();
		u.setGplusId(this.gplusId);
		u.setGmail(this.gmail);
		u.setFirstName(this.firstName);
		u.setLastName(this.lastName);
		u.setGender(this.gender);
		u.setNativeLanguage(this.nativeLanguage.getLanguage());
		for (ObjectifyableLanguage oLang: languages) {
			u.addLanguage(oLang.getLanguage());
		}		
		for (ObjectifyableTextbook oBook: textbooks) {
			u.addTextbook(oBook.getTextbook());
		}
		for (ObjectifyableOutsideCourse oCourse: outsideCourses) {
			u.addOutsideCourse(oCourse.getOutsideCourse());
		}
		u.setUserRegistrationTime(this.userRegistrationTime);
		return u;
	}
	
	
}
