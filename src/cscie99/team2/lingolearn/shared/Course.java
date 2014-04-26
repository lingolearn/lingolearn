package cscie99.team2.lingolearn.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * This class represents a study course created by users of the system.
 */
public class Course implements Serializable {

	private static final long serialVersionUID = 3307093343888121538L;
	
	private Long   courseId;	// Unique course Id
	private String courseDesc,	// Course description
				   courseName;  // Course name
	Date 	       courseStart,	// Course start date
				   courseEnd;	// Course end date
	private User instructor;
	private List<User> students;
	
	public Course() {
		this( null, null, null, null, null, null );
	};
	
	public Course(Long courseId, String courseDesc, String courseName,
			Date courseStart, Date courseEnd) {
		
		this( courseId, courseDesc, courseName, courseStart,
					courseEnd, null );
	}
	
	public Course( Long courseId, String courseDesc, String courseName,
			Date courseStart, Date courseEnd, User instructor ) {
		
		this.courseId = courseId;
		this.courseDesc = courseDesc;
		this.courseName = courseName;
		this.courseStart = courseStart;
		this.courseEnd = courseEnd;
		this.instructor = instructor;
		this.students = new ArrayList<User>();
	}
	
	public void addStudent( User student ){
		HashSet<User> studentSet = new HashSet<User>(this.students);
		if (!studentSet.contains(student)) { 
			students.add(student);
		}
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

	public User getInstructor() {
		return instructor;
	}

	public void setInstructor(User instructor) {
		this.instructor = instructor;
	}

	public List<User> getStudents() {
		return students;
	}

	public void setStudents(List<User> students) {
		this.students = students;
	}	
}
