package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Index;

import cscie99.team2.lingolearn.shared.OutsideCourse;

/**
 *
 * This class represents an Objectifyable version of a course outside of the system that a user has taken.
 * This is required for biographical data.
 *
 */
@Embed
@Index
public class ObjectifyableOutsideCourse implements Serializable {
	
	private static final long serialVersionUID = 222765005584422704L;

	private String outsideCourseID;
	private String name;
	private String institution;

	public ObjectifyableOutsideCourse () {};
	
	/**
	 * This method creates Objectifyable Proxy from real object
	 * @param course	OutsideCourse object
	 */
	public ObjectifyableOutsideCourse (OutsideCourse course) {
		this.outsideCourseID = course.getOutsideCourseID();
		this.name = course.getName();
		this.institution = course.getInstitution();
	};
	
	/**
	 * This method reconstructs real object from Objectifyable Proxy
	 * @return OutsideCourse object
	 */
	public OutsideCourse getOutsideCourse () {
		OutsideCourse course = new OutsideCourse();
		course.setOutsideCourseID(this.outsideCourseID);
		course.setName(this.name);
		course.setInstitution(this.institution);
		return course;
	}
}
