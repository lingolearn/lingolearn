/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.shared;

import java.io.Serializable;

/**
 * @author YPolyanskyy
 * 
 * This class represents a Test. It extends the Session class.
 */
public class Test extends Session implements Serializable {

	private int 	timeLimit;		// Time limit (minutes) allowed to take this test
	private Float	Grade;			// Resulting grade (percent value) for obtained during this session 
}
