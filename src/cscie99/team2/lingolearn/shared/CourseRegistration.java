package cscie99.team2.lingolearn.shared;

import java.io.Serializable;

/**
 * 
 * @author nichols
 *
 * This class represents the registration that a student has made for a course.
 *
 */
public class CourseRegistration implements Serializable{

	private static final long serialVersionUID = -1107489289326201552L;

	private Long courseRegId;	// CourseRegistration Id 
	private String gplusId;		// User's Id
	private Long courseId;		// Course's Id
	
	public CourseRegistration() {}

	public CourseRegistration(Long courseRegId, String gplusId, Long courseId) {
		this.courseRegId = courseRegId;
		this.gplusId = gplusId;
		this.courseId = courseId;
	}
	
	public Long getCourseRegId() {
		return courseRegId;
	}

	public void setCourseRegId(Long courseRegId) {
		this.courseRegId = courseRegId;
	}

	public String getGplusId() {
		return gplusId;
	}

	public void setGplusId(String gplusId) {
		this.gplusId = gplusId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	};
	
}
