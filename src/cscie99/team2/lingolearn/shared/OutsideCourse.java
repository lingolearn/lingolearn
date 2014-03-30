package cscie99.team2.lingolearn.shared;

import java.io.Serializable;

/**
 * 
 * @author nichols
 *
 *This class represents a course outside of the system that a user has taken. This is required for biographical data.
 *
 */
public class OutsideCourse implements Serializable {

	private static final long serialVersionUID = 7153828329464294076L;

	private String outsideCourseID;
	private String name;
	private String institution;
	public String getOutsideCourseID() {
		return outsideCourseID;
	}
	public void setOutsideCourseID(String outsideCourseID) {
		this.outsideCourseID = outsideCourseID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public OutsideCourse (String o, String n, String i) {
		outsideCourseID = o;
		name = n;
		institution = i;
	}

}
