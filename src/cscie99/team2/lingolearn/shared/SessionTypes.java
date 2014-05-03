package cscie99.team2.lingolearn.shared;

public enum SessionTypes {
	Kanji_Translation("Kanji / Translation"), 
	Hiragana_Translation("Hiragana / Translation"),
	Translation_Kanji("Translation / Kanji"),
	Translation_Hiragana("Translation / Hiragana"),
	Kanji_Hiragana("Kanji / Hiragana"),
	Hiragana_Kanji("Hiragana / Kanji"),
	Confusor("Confusor");
	
	// The text returned by toString()
	private String text;
	
	/**
	 * Constructor, set the text for toString().
	 */
	private SessionTypes(String value) {
		text = value;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
