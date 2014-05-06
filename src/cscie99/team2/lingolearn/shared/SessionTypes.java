package cscie99.team2.lingolearn.shared;

public enum SessionTypes {
	Kanji_Translation("Kanji / Translation"), 
	Hiragana_Translation("Hiragana / Translation"),
	Translation_Kanji("Translation / Kanji"),
	Translation_Hiragana("Translation / Hiragana"),
	Kanji_Hiragana("Kanji / Hiragana"),
	Hiragana_Kanji("Hiragana / Kanji");
	
	// The text returned by toString()
	private String text;
	
	/**
	 * Constructor, set the text for toString().
	 */
	private SessionTypes(String value) {
		text = value;
	}
	
	/**
	 * Get the enumerated type that corresponds to either the name or toString value.
	 * 
	 * @param value Value to be checked.
	 * @return The correct session type enumeration.
	 */
	public static SessionTypes getEnum(String value) {
		for (SessionTypes type : SessionTypes.values()) {
			if (type.toString().equalsIgnoreCase(value)) {
				return type;
			}
			if (type.name().equalsIgnoreCase(value)) {
				return type;
			}
		}
		// If we get here, we don't know what was provided
		throw new IllegalArgumentException("The value was not recognized: " + value);
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	public static boolean confuserSupported( SessionTypes sessionType ){
		switch( sessionType ){
			case Kanji_Translation:
			case Hiragana_Translation:
				return false;
				
			default:
				return true;
		}
	}
}
