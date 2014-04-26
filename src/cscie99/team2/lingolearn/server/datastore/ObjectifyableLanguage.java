package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Index;

import cscie99.team2.lingolearn.shared.Language;

/**
 * This class represents an Objectifyable version of the Language class
 */
@Embed
@Index
public class ObjectifyableLanguage implements Serializable {
	
	private static final long serialVersionUID = -2456376848955039648L;
	
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
