package cscie99.team2.lingolearn.shared;

import java.io.Serializable;

/**
 * 
 * @author nichols
 *
 *This class represents a textbook a user has read.
 */
public class Textbook implements Serializable{

	private static final long serialVersionUID = 4537611207426774788L;

	private String textbookID;
	private String name;
	private int year;
	public String getTextbookID() {
		return textbookID;
	}
	public void setTextbookID(String textbookID) {
		this.textbookID = textbookID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Textbook (String t, String n, int y) {
		textbookID = t;
		name = n;
		year = y;
	}
			

}
