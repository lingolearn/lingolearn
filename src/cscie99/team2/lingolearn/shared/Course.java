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
	
	private String courseId,	// Unique course Id
				   courseDesc,	// Course description
				   courseName;  // Course name
	Date 	       courseStart,	// Course start date
				   courseEnd;	// Course end date
	
	public void setName(String name) {
		this.courseName = name;
	}
	
	public String getName() {
		return this.courseName;
	}
	
	public String getCourseId() {
		return courseId;
	}
				   
}
