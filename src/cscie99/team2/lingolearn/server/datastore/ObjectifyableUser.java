package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

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
	@Index String firstName;
	@Index String lastName;
	Gender gender;
	@Serialize Language nativeLanguage;
	@Serialize Set<Language> languages;
	@Serialize Set<Textbook> textbooks;
	@Serialize Set<OutsideCourse> outsideCourses;
	Date userRegistrationTime;
	
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
		this.nativeLanguage = user.getNativeLanguage();
		this.languages = user.getLanguages();
		this.textbooks = user.getTextbooks();
		this.outsideCourses = user.getOutsideCourses();
		this.userRegistrationTime = user.getUserRegistrationTime();
	}

	/**
	 * This method reconstructs real object from Objectifyable Proxy
	 * @return Card object
	 */
	public User getUser() {
		User u = new User ();
		u.setGplusId(this.gplusId);
		u.setGmail(this.gmail);
		u.setFirstName(this.firstName);
		u.setLastName(this.lastName);
		u.setGender(this.gender);
		u.setNativeLanguage(this.nativeLanguage);
		u.setLanguages(this.languages);
		u.setTextbooks(this.textbooks);
		u.setOutsideCourses(this.outsideCourses);
		u.setUserRegistrationTime(this.userRegistrationTime);
		return u;
	}
	
	
}
