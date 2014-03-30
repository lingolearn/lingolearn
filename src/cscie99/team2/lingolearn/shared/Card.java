/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.shared;


import java.io.Serializable;

/**
 * @author YPolyanskyy
 *
 */
public class Card implements Serializable {

	public Card(String enTranslation) {
		super();
		this.enTranslation = enTranslation;
	}

	private static final long serialVersionUID = -2630264168091602483L;
	
	private Long cardId;			// Unique card Id
	private String desc;			// Card's Description
	private String kanji;			// Kanji Unicode
	private	String hiragana;		// Hiragana Unicode
	private	String katakana;		// Katakana Unicode
	private String translation; 	// Translation
	private String enTranslation; 	// EnTranslation
	private String nativeLanguage;	// Native language of the translation, example "en-us"
	private Image image;			// Image
	private Sound sound;			// Sound
	//private List<Confuser>	confuserList;	// List of "confusers"

	public Card () {};
	
	public Card(String kanji, String translation) {
		super();
		this.kanji = kanji;
		this.enTranslation = translation;
	}

	public Long getId() {
		return cardId;
	}

	public void setId(Long cardId) {
		this.cardId = cardId;
	}

	public String getKanji() {
		return kanji;
	}

	public void setKanji(String kanji) {
		this.kanji = kanji;
	}

	public String getHiragana() {
		return hiragana;
	}

	public void setHiragana(String hiragana) {
		this.hiragana = hiragana;
	}

	public String getKatakana() {
		return katakana;
	}

	public void setKatakana(String katakana) {
		this.katakana = katakana;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public String getNativeLanguage() {
		return nativeLanguage;
	}

	public void setNativeLanguage(String nativeLanguage) {
		this.nativeLanguage = nativeLanguage;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getEnTranslation() {
		return enTranslation;
	}

	public void setEnTranslation(String enTranslation) {
		this.enTranslation = enTranslation;
	}

	/*
	public List<Confuser> getConfuserList() {
		return confuserList;
	}

	public void setConfuserList(List<Confuser> confuserList) {
		this.confuserList = confuserList;
	}
	*/
	
}