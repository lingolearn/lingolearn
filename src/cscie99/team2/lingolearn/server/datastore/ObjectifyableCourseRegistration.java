package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

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
	
	@Id private String gplusId;
	private Set <Long> courseIdList;
	
	public ObjectifyableCourseRegistration() {
		courseIdList = new HashSet<Long>();
	};
	
	/**
	 * This method constructor creates Objectifyable Course Registration from real object
	 * @param courseReg		CourseRegistration object
	 */
	public ObjectifyableCourseRegistration (CourseRegistration courseReg) {
		this.gplusId = courseReg.getGplusId();
		courseIdList = new HashSet<Long>();
		for (Long course: courseReg.getCourseIdList() ) {
			this.courseIdList.add(course);
		}
	}
	
	/**
	 * This method reconstructs real object from Objectifyable Proxy
	 * @return CourseRegistration object
	 */
	public CourseRegistration getCourseRegistration() {
		CourseRegistration cReg = new CourseRegistration();
		cReg.setGplusId(this.gplusId);
		cReg.setCourseIdList(this.courseIdList);
		return cReg;
	}
}
