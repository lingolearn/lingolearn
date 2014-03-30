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
	
	private String gplusId;
	private String courseId;
	public String getGplusId() {
		return gplusId;
	}
	public void setGplusId(String gplusId) {
		this.gplusId = gplusId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

}
