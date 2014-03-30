/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.shared;

import java.io.Serializable;

/**
 * @author YPolyanskyy
 * 
 * This class represents a Quiz. It extends the Session class.
 */
public class Quiz extends Session implements Serializable {
	
	private String mode;		// Defines if the quiz should use confuser algorithm
}
