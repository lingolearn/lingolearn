package cscie99.team2.lingolearn.shared;

import java.io.Serializable;

/**
 * A single card within a Deck of cards.
 */
public class Card implements Serializable {

	private static final long serialVersionUID = -2630264168091602483L;
	
	private Long cardId;			// Unique card Id
	private String desc;			// Card's Description
	private String kanji;			// Kanji Unicode
	private	String hiragana;		// Hiragana Unicode
	private	String katakana;		// Katakana Unicode
	private String translation; 	// Translation
	private String nativeLanguage;	// Native language of the translation, example "en-us"
	private Image image;			// Image
	private Sound sound;			// Sound

	/**
	 * Constructor.
	 */
	public Card () {};
	
	/**
	 * Constructor.
	 */
	public Card(String kanji, String hiragana, String katakana,
			String translation, String nativeLanguage, String description) {
		this.kanji = kanji;
		this.hiragana = hiragana;
		this.katakana = katakana;
		this.translation = translation;
		this.nativeLanguage = nativeLanguage;
		this.desc = description;
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
}