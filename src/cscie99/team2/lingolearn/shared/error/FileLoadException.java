package cscie99.team2.lingolearn.shared.error;

/**
 * This exception is thrown in from the CardFileLoader when an error occurs.
 */
@SuppressWarnings("serial")
public class FileLoadException extends Exception {
	// The line that caused the reported problem
	private int lineNumber = -1;
	
	/**
	 * Constructor, note the message and line number.
	 * 
	 * @param message The message about the exception.
	 * @param lineNumber The line in the file the exception occurred at.
	 */
	public FileLoadException(String message, int lineNumber) {
		super(message);
		this.lineNumber = lineNumber;
	}

	/**
	 * Constructor, note the message and inner exception.
	 * 
	 * @param message The message about the exception.
	 * @param cause	The inner exception that that caused this one.
	 */
	public FileLoadException (String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * The line number of the error, or -1 if no lines were involved.
	 * @return
	 */
	public int getLineNum() {
		return lineNumber;
	}
}