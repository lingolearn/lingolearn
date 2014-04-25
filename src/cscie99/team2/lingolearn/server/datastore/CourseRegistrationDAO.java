package cscie99.team2.lingolearn.server.datastore;

import static cscie99.team2.lingolearn.server.datastore.OfyService.ofy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cscie99.team2.lingolearn.shared.CourseRegistration;
import cscie99.team2.lingolearn.shared.User;

public class CourseRegistrationDAO {

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class CourseRegistrationDAOHolder { 
		public static final CourseRegistrationDAO INSTANCE = new CourseRegistrationDAO();
	}

	public static CourseRegistrationDAO getInstance() {
		return CourseRegistrationDAOHolder.INSTANCE;
	}   
	
	/**
	 * This method stores the CourseRegistration in the datastore
	 * @param course CourseRegistration to be stored in the datastore
	 * @return stored CourseRegistration for diagnostic purpose
	 */
	public CourseRegistration storeCourseRegistration(CourseRegistration courseRegistration) {
		CourseRegistration existingCourseRegistration = this.findExistingCourseReservation(courseRegistration);
		if (existingCourseRegistration == null) {
			ObjectifyableCourseRegistration oCourseRegistration = new ObjectifyableCourseRegistration(courseRegistration); 
			ofy().save().entity(oCourseRegistration).now();
			ObjectifyableCourseRegistration fetched = ofy().load().entity(oCourseRegistration).now();
			courseRegistration = fetched.getCourseRegistration();
			return courseRegistration;
		}
		return existingCourseRegistration;
	}
	
	/**
	 * Helper method to identify of CourseRegistration is already exists
	 * 
	 * @param courseReg object
	 * @return courseReg if already exists, or null if not found
	 */
	private CourseRegistration findExistingCourseReservation(CourseRegistration courseReg) {
		// lookup the list of stored reservation for the duplicate one
		List<CourseRegistration> allCourseRegistration;
		allCourseRegistration = this.getAllCourseRegistrations();
		if (allCourseRegistration == null) {
			// Not found
			return null;
		} 
		Iterator<CourseRegistration> it = allCourseRegistration.iterator();
		while (it.hasNext()) {
			CourseRegistration currCR = it.next();	
			if ((currCR.getCourseId().toString()+ currCR.getGplusId())
					.equalsIgnoreCase(courseReg.getCourseId().toString()+ courseReg.getGplusId())) {
				return currCR;
			} 
		}
		return null;
	}
	
	/**
	 * Retrieve CourseRegistration by courseRegId the datastore
	 * @param id courseRegId
	 * @return CourseRegistration with the specified courseRegId or null if not found
	 */
	public CourseRegistration getCourseRegistrationById(Long id) {
		if (id == null) {
			return null;
		}
		ObjectifyableCourseRegistration oCR = ofy().load().type(ObjectifyableCourseRegistration.class).id(id).now();
		return (oCR==null) ? null : oCR.getCourseRegistration();
	}
	
	/**
	 * Obtains a list of CourseRegistration by UserId
	 * @param uId gplusId
	 * @return List of CourseRegistrations stored for the userId or null if not found
	 */
	public List<CourseRegistration> getCourseRegistrationByUserId(String gplusId) {
		List<ObjectifyableCourseRegistration> oCR = ofy().load().type(ObjectifyableCourseRegistration.class).filter("gplusId", gplusId).list();
		Iterator<ObjectifyableCourseRegistration> it = oCR.iterator();
		List<CourseRegistration> cReg = new ArrayList<>();
		while (it.hasNext()) {
			cReg.add(it.next().getCourseRegistration());
		}
		return (cReg.size() == 0) ? null : cReg;		
	}
	
	/**
	 * Obtains a list of CourseRegistration by courseId
	 * @param courseId courseId
	 * @return List of CourseRegistrations stored for the courseId or null if not found
	 */
	public List<CourseRegistration> getCourseRegistrationByCourseId(Long courseId) {
		List<ObjectifyableCourseRegistration> oCR = ofy().load().type(ObjectifyableCourseRegistration.class).filter("courseId", courseId).list();
		Iterator<ObjectifyableCourseRegistration> it = oCR.iterator();
		List<CourseRegistration> cReg = new ArrayList<>();
		while (it.hasNext()) {
			cReg.add(it.next().getCourseRegistration());
		}
		return (cReg.size() == 0) ? null : cReg;		
	}
	
	/**
	 * Obtains a list of Users registered to the course with the courseId
	 * @param courseId courseId
	 * @return List of Users stored for the courseId or null if not found
	 */
	public List<User> getUserCourseId(Long courseId) {
		// Obtain a list of course registrations for the specified courseId 
		List<CourseRegistration> cReg = getCourseRegistrationByCourseId(courseId);
		if (cReg == null) {
			return null;
		}
		// Retrieve Users with the userId in the course registration List
		List<User> userList = new ArrayList<>();
		User u;
		UserDAO userAccessor = UserDAO.getInstance();
		Iterator<CourseRegistration> it = cReg.iterator();
		while (it.hasNext()) {
			u = userAccessor.getUserByGplusId(it.next().getGplusId());
			if(u != null) {
				userList.add(u);
			}
		}
		return userList;
	}
	
	/**
	 * Obtain all course registrations stored in datastore
	 */
	public List<CourseRegistration> getAllCourseRegistrations() {
		List<ObjectifyableCourseRegistration> oCRs = ofy().load().type(ObjectifyableCourseRegistration.class).list();
		Iterator<ObjectifyableCourseRegistration> it = oCRs.iterator();
		List<CourseRegistration> courseRegistrations = new ArrayList<>();
		while (it.hasNext()) {
			courseRegistrations.add(it.next().getCourseRegistration());
		}
		return (courseRegistrations.size() == 0) ? null : courseRegistrations;
	}
	
	
	/**
	 * Deletes the CourseRegistration with the specified courseRegId from the datastore
	 * @param courseRegId
	 */
	public void deleteCourseRegistrationById(Long courseRegId) {
		if (courseRegId != null) {
			ofy().delete().type(ObjectifyableCourseRegistration.class).id(courseRegId).now();
		}
	}
}
