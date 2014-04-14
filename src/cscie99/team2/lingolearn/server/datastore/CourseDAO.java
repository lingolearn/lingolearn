package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.User;

public class CourseDAO {

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 * *****
	 */
	private static class CourseDAOHolder { 
		public static final CourseDAO INSTANCE = new CourseDAO();
	}

	public static CourseDAO getInstance() {
		return CourseDAOHolder.INSTANCE;
	}   
	
	/**
	 * This method stores the Course in the datastore
	 * @param course Course to be stored in the datastore
	 * @return stored Course for diagnostic purpose
	 */
	public Course storeCourse( Course course ) {
		ObjectifyableCourse oCourse = new ObjectifyableCourse(course);
		Long instructorId = course.getInstructor().getUserId();
		ObjectifyableUser storedInstructor = ofy().
				load().type(ObjectifyableUser.class).id(instructorId).now();
		oCourse.instructor = Ref.create(storedInstructor);
		for( User student : course.getStudents() ){
			Ref<ObjectifyableUser> studentRef = Ref.create( new ObjectifyableUser(student) );
			oCourse.students.add( studentRef );
		}
		ofy().save().entity(oCourse).now();
		//ObjectifyableCourse fetched = ofy().load().entity(oCourse).now();
		course = oCourse.getCourse();
		ObjectifyableUser retreivedInstructor = oCourse.instructor.get();
		User instructor = retreivedInstructor.getUser();
		course.setInstructor(instructor);
		return course;
	}
	
	/**
	 * Obtains Course by CourseId
	 * @param courseId courseId
	 * @return Course stored with the courseId or null if not found
	 */
	public Course getCourseById(Long courseId) {
		if (courseId == null) {
			return null;
		}
		ObjectifyableCourse oCourse = ofy().load().type(ObjectifyableCourse.class).id(courseId).now();
		if (oCourse != null) {
			Course course = oCourse.getCourse();
			return course;
		}
		return null;
	}
	
	/**
	 * Obtains Course by CourseName
	 * @param courseName courseName
	 * @return Course stored with the courseName or null if not found
	 */
	public Course getCourseByName(String courseName) {
		ObjectifyableCourse oCourse = ofy().load().type(ObjectifyableCourse.class).filter("courseName", courseName).first().now();
		if (oCourse != null) {
			Course course = oCourse.getCourse();
			return course;
		}
		return null;
	}
	
	/**
	 * Obtains Course by CourseName
	 * @param courseDesc courseDesc
	 * @return Course stored with the courseDesc or null if not found
	 */
	public Course getCourseByDesc(String courseDesc) {
		ObjectifyableCourse oCourse = ofy().load().type(ObjectifyableCourse.class).filter("courseDesc", courseDesc).first().now();
		if (oCourse != null) {
			Course course = oCourse.getCourse();
			return course;
		}
		return null;
	}
	
	/**
	 * Obtains a list of all available Courses in the datastore
	 * @return list of all Courses or Null if no Courses in the datastore
	 */
	public List<Course> getAllCourses(){
		List<ObjectifyableCourse> oUsers = ofy().load().type(ObjectifyableCourse.class).list();
		Iterator<ObjectifyableCourse> it = oUsers.iterator();
		List<Course> courses = new ArrayList<>();
		while (it.hasNext()) {
			courses.add(it.next().getCourse());
		}
		if (courses.size() == 0) {
			return null;
		} else {
			return courses;
		}
	}
	
	public List<Course> getAvailableCourses(User student){
		//List<ObjectifyableCourse> returendCourses = 
		Query<ObjectifyableCourse> query = ofy().load().type(ObjectifyableCourse.class);
		List<ObjectifyableUser> enrolledStudents = 
													new ArrayList<ObjectifyableUser>();
		ObjectifyableUser storedStudent = new ObjectifyableUser( student );
		enrolledStudents.add( storedStudent );

		query = query.filter("instructor", storedStudent);
		//query = query.filter("students in", enrolledStudents);
		List<ObjectifyableCourse> unAddableCourses = query.list();
		Set<ObjectifyableCourse> unAddableSet = 
							new HashSet<ObjectifyableCourse>(unAddableCourses);
		List<ObjectifyableCourse> allCourses = 
								ofy().load().type(ObjectifyableCourse.class).list();
		
		
		List<Course> availableCourses = new ArrayList<Course>();
		for( ObjectifyableCourse oc : allCourses ){
			/*
			if( unAddableSet.contains(oc) ){
				System.out.println("unaddable course: " + oc.courseName);
			}else{
				ObjectifyableUser objectifiedInstructor = oc.instructor.get();
				User instructorUser = objectifiedInstructor.getUser();
				Course course = oc.getCourse();
				course.setInstructor(instructorUser);
				availableCourses.add( oc.getCourse() );
			}
			*/
			if( oc.instructor == null )
				continue;
			
			ObjectifyableUser objectifiedInstructor = oc.instructor.get();
			User instructorUser = objectifiedInstructor.getUser();
			Course course = oc.getCourse();
			course.setInstructor(instructorUser);
			availableCourses.add( oc.getCourse() );
		}
		
		return availableCourses;
		
	}
	
	/**
	 * Deletes the course with the specified CourseId from the datastore
	 * @param courseId
	 */
	public void deleteCourseById(Long courseId) {
		if (courseId != null) {
			ofy().delete().type(ObjectifyableCourse.class).id(courseId).now();
		}
	}
}
