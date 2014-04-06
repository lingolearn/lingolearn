/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * @author YPolyanskyy
 * 
 * This class represents a study course created by users of the system.
 * 
 */
public class Course implements Serializable {

	private static final long serialVersionUID = 3307093343888121538L;
	
	private Long   courseId;	// Unique course Id
	private String courseDesc,	// Course description
				   courseName;  // Course name
	Date 	       courseStart,	// Course start date
				   courseEnd;	// Course end date
	
	public Course() {};
	
	public Course(Long courseId, String courseDesc, String courseName,
			Date courseStart, Date courseEnd) {
		super();
		this.courseId = courseId;
		this.courseDesc = courseDesc;
		this.courseName = courseName;
		this.courseStart = courseStart;
		this.courseEnd = courseEnd;
	}
	
	
	public void setName(String name) {
		this.courseName = name;
	}
	
	public String getName() {
		return this.courseName;
	}
	
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	
	public Long getCourseId() {
		return courseId;
	}

	public String getCourseDesc() {
		return courseDesc;
	}

	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Date getCourseStart() {
		return courseStart;
	}

	public void setCourseStart(Date courseStart) {
		this.courseStart = courseStart;
	}

	public Date getCourseEnd() {
		return courseEnd;
	}

	public void setCourseEnd(Date courseEnd) {
		this.courseEnd = courseEnd;
	}
				   
}
