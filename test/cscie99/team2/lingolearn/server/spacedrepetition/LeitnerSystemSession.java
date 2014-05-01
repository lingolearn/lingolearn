package cscie99.team2.lingolearn.server.spacedrepetition;

/**
 * This class is used with LeitnerSystemTest to store the expected values for 
 * the test passes.
 */
public class LeitnerSystemSession {
	private String correct;
	
	private String[] session;

	/**
	 * Constructor.
	 * 
	 * @param correct The correct answers.
	 * @param session The expected session results.
	 */
	public LeitnerSystemSession(String correct, String[] session) {
		this.correct = correct;
		this.session = session;
	}
	
	/**
	 * Get the correct answers for this session.
	 * 
	 * @return A string with the correct answers for this session.
	 */
	public String getCorrect() {
		return correct;
	}

	/**
	 * Get the expected session results, this is how the cards should be
	 * positioned at the end of the session.
	 * 
	 * @return An array that has string with where the cards should be.
	 */
	public String[] getSession() {
		return session;
	}
}
