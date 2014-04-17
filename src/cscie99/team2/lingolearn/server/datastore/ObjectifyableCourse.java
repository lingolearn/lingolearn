/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.User;

/**
 * This class represents Proxy for Course 
 */
@Entity(name="ObjectifyableCourse")
public class ObjectifyableCourse implements Serializable {

	private static final long serialVersionUID = -4995077387409263724L;
	
	@Id Long   courseId;		// Unique course Id
	@Index String courseDesc;	// Course description
	@Index String courseName;	// Course name
	Date 	       courseStart,			// Course start date
				   courseEnd;			// Course end date
	
	@Index
	@Load
	Ref<ObjectifyableUser> instructor;
	
	@Index
	@Load
	List<Ref<ObjectifyableUser>> students;
	
	public ObjectifyableCourse() {
		this.students = new ArrayList<Ref<ObjectifyableUser>>();
	};
	
	/**
	 * This method constructor creates Objectifyable Course from real object
	 * @param course		Course object
	 */
	public ObjectifyableCourse (Course course) {
		ObjectifyableUser instructor = 
							new ObjectifyableUser(course.getInstructor());
		this.instructor = Ref.create(instructor); 
		this.students = new ArrayList<Ref<ObjectifyableUser>>();
		for( User student : course.getStudents() ){
			ObjectifyableUser storableStudent = new ObjectifyableUser(student);
			Ref<ObjectifyableUser> studentRef = Ref.create(storableStudent);
			this.students.add(studentRef);
		}
		
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
		ObjectifyableUser storedInstructor = this.instructor.get();
		User instructor = storedInstructor.getUser();
		c.setInstructor(instructor);
		//c.setInstructor(this.instructor.get().getUser());
		
		for( Ref<ObjectifyableUser> studentRef : this.students ){
				ObjectifyableUser storedStudent = studentRef.get();
				User student = storedStudent.getUser();
				c.addStudent(student);
		}
		
		return c;
	}
	
	
}
