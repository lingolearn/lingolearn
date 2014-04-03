package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Index;

import cscie99.team2.lingolearn.shared.Textbook;

/**
 *
 * This class represents an Objectifyable version of a textbook a user has read.
 */
@Embed
@Index
public class ObjectifyableTextbook implements Serializable {
	
	private static final long serialVersionUID = -8118136197048807492L;

	private String textbookID;
	private String name;
	private int year;
	
	public ObjectifyableTextbook () {}
	
	/**
	 * This method creates Objectifyable Proxy from real object
	 * @param book		Textbook object
	 */
	public ObjectifyableTextbook (Textbook book) {
		this.textbookID = book.getTextbookID();
		this.name = book.getName();
		this.year = book.getYear();
	}
	
	/**
	 * This method reconstructs real object from Objectifyable Proxy
	 * @return Textbook object
	 */
	public Textbook getTextbook() {
		Textbook book = new Textbook();
		book.setTextbookID(this.textbookID);
		book.setName(this.name);
		book.setYear(this.year);
		return book;
	}
	
}
