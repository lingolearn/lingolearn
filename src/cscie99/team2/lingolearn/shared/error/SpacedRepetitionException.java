package cscie99.team2.lingolearn.shared.error;

/**
 * This error is thrown by code that is related to the spaced repetition 
 * algorithm handlers.
 */
@SuppressWarnings("serial")
public class SpacedRepetitionException extends Exception {
	/**
	 * Constructor.
	 * 
	 * @param message The error message to associate with the error.
	 * @param cause The exception that caused the error.
	 */
	public SpacedRepetitionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param message The error message to associate with the error.
	 */
	public SpacedRepetitionException(String message) {
		super(message);
	}
}
