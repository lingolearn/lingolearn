package cscie99.team2.lingolearn.shared;

public enum SessionTypes {
	Kanji_Translation("Kanji / Translation", false), 
	Katakana_Translation("Katakana / Translation", false),
	Hiragana_Translation("Hiragana / Translation", false),
	Translation_Kanji("Translation / Kanji", true),
	Translation_Katakana("Translation / Katakana", true),
	Translation_Hiragana("Translation / Hiragana", true),
	Kanji_Hiragana("Kanji / Hiragana", true),
	Hiragana_Kanji("Hiragana / Kanji", true);
	
	// Flag to indicate if confusers are supported by this type
	private boolean confuserSupported;
	
	// The text returned by toString()
	private String text;
	
	/**
	 * Constructor.
	 */
	private SessionTypes(String value, boolean confuserSupported) {
		text = value;
		this.confuserSupported = confuserSupported;
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
	
	/**
	 * Check to see if confusers are supported by this session type.
	 * 
	 * @return True if they are supported, false otherwise.
	 */
	public boolean isConfuserSupported() {
		return confuserSupported;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
