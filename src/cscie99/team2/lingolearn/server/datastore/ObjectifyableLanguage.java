/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Embed;

import cscie99.team2.lingolearn.shared.Language;

/**
 * @author YPolyanskyy
 *
 * This class contains a list of the ISO 639 letter codes and their corresponding English names
 * It provides ability to lookup and assign the standard letter code 
 */
@Embed
public class ObjectifyableLanguage implements Serializable {
	private static final long serialVersionUID = -770788580468589881L;

	private String langId;	// language name in English
	private String langName;

	public ObjectifyableLanguage() {}
	
	/**
	 * This method creates Objectifyable Proxy from real object
	 * @param lang		Language object
	 */
	public ObjectifyableLanguage(Language lang) {
		this.langId = lang.getLangId();
		this.langName = lang.getLangName();
	}
	
	/**
	 * This method reconstructs real object from Objectifyable Proxy
	 * @return Language object
	 */
	public Language getLanguage() {
		Language lang = new Language();
		lang.setLangId(this.langId);
		lang.setLangName(this.langName);
		return lang;
	}
	
}
