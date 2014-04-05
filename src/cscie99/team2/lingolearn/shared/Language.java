/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.shared;

import java.io.Serializable;

/**
 * @author YPolyanskyy
 *
 * This class contains a list of the ISO 639 letter codes and their corresponding English names
 * It provides ability to lookup and assign the standard letter code 
 */
public class Language implements Serializable {
	private static final long serialVersionUID = -770788580468589881L;
	
	private String langId;	// language name in English
	private String langName;

	public Language () {};
	
	public Language (String id, String lName) {
		this.langId = id;
		this.langName = lName;
	};
	
	public Language( String lName ){
		this( null, lName );
	}
	
	public String getLangId() {
		return langId;
	}

	public void setLangId(String langId) {
		this.langId = langId;
	}

	public String getLangName() {
		return langName;
	}

	public void setLangName(String langName) {
		this.langName = langName;
	}

}
