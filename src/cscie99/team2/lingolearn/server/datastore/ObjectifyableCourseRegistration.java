package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import cscie99.team2.lingolearn.shared.CourseRegistration;

/**
 * 
 * @author nichols
 *
 * This class represents the registration that a student has made for a course.
 *
 */
@Entity(name="ObjectifyableCourseRegistration")
public class ObjectifyableCourseRegistration implements Serializable{

	private static final long serialVersionUID = 8776350460631989672L;
	
	@Id private Long courseRegId;	// CourseRegistration Id 
	@Index private Long courseId;	// Course's Id
	@Index private String gplusId;	// Course's Id
	
	public ObjectifyableCourseRegistration() {};
	
	/**
	 * This method constructor creates Objectifyable Course Registration from real object
	 * @param courseReg		CourseRegistration object
	 */
	public ObjectifyableCourseRegistration (CourseRegistration courseReg) {
		this.courseRegId = courseReg.getCourseRegId();
		this.courseId = courseReg.getCourseId();
		this.gplusId = courseReg.getGplusId();
	}
	
	/**
	 * This method reconstructs real object from Objectifyable Proxy
	 * @return CourseRegistration object
	 */
	public CourseRegistration getCourseRegistration() {
		CourseRegistration cReg = new CourseRegistration();
		cReg.setCourseRegId(this.courseRegId);
		cReg.setCourseId(this.courseId);
		cReg.setGplusId(this.gplusId);
		return cReg;
	}
}
