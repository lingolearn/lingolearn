/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import cscie99.team2.lingolearn.shared.Course;

/**
 * This class represents Proxy for Course 
 */
@Entity(name="ObjectifyableCourse")
public class ObjectifyableCourse implements Serializable {

	private static final long serialVersionUID = 3307093343888121538L;
	
	@Id private Long   courseId;		// Unique course Id
	@Index private String courseDesc;	// Course description
	@Index private String courseName;	// Course name
	Date 	       courseStart,			// Course start date
				   courseEnd;			// Course end date
	
	public ObjectifyableCourse() {};
	
	/**
	 * This method constructor creates Objectifyable Course from real object
	 * @param card		Course object
	 */
	public ObjectifyableCourse (Course course) {
		this.courseId = course.getCourseId();
		this.courseDesc = course.getCourseDesc();
		this.courseName = course.getCourseName();
		this.courseStart = course.getCourseStart();
		this.courseEnd = course.getCourseEnd();
	}   
	
	/**
	 * This method reconstructs real object from Objectifyable Proxy
	 * @return Course object
	 */
	public Course getCourse() {
		Course c = new Course();
		c.setCourseId(this.courseId);
		c.setCourseDesc(this.courseDesc);
		c.setCourseName(this.courseName);
		c.setCourseStart(this.courseStart);
		c.setCourseEnd(this.courseEnd);
		return c;
	}
	
}
