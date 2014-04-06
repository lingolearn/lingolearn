package cscie99.team2.lingolearn.shared;

import java.io.Serializable;
import java.util.Set;

/**
 * 
 * @author nichols
 *
 * This class represents the registration that a student has made for a course.
 *
 */
public class CourseRegistration implements Serializable{

	private static final long serialVersionUID = -1107489289326201552L;
	
	private String gplusId;				// User's Id
	private Set <Long> courseIdList;	// List of courses that this user is registered to 
	
	public CourseRegistration() {};
	
	public CourseRegistration(String gplusId, Set<Long> courseIdList) {
		this.gplusId = gplusId;
		this.courseIdList = courseIdList;
	}

	public String getGplusId() {
		return gplusId;
	}

	public void setGplusId(String gplusId) {
		this.gplusId = gplusId;
	}

	public Set<Long> getCourseIdList() {
		return courseIdList;
	}

	public void setCourseIdList(Set<Long> courseIdList) {
		this.courseIdList = courseIdList;
	}

}
