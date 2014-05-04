package cscie99.team2.lingolearn.shared;

import static org.junit.Assert.*;

import org.junit.Test;

public class SessionTypesTest {

	/**
	 * Test to make sure we can get an enum back on the basis of the value and
	 * of the name of it.
	 */
	@Test
	public void testGetEnum() {
		for (SessionTypes type : SessionTypes.values()) {
			// Check to make sure the value from toString is parsed correctly
			SessionTypes value = SessionTypes.getEnum(type.toString());
			assertEquals(type, value);
			// Check to make sure the value actual name of the enum is parsed correctly
			value = SessionTypes.getEnum(type.name());
			assertEquals(type, value);
		}
	}
}
