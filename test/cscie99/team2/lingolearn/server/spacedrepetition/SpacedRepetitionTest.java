package cscie99.team2.lingolearn.server.spacedrepetition;

import static org.hamcrest.CoreMatchers.instanceOf;

import static org.junit.Assert.*;

import org.junit.Test;

import cscie99.team2.lingolearn.shared.error.SpacedRepetitionException;

public class SpacedRepetitionTest {
	/**
	 * Test to ensure that object creation is working correctly.
	 */
	@Test
	public void testObjectCreation() throws SpacedRepetitionException {
		String name = BasicRandomization.class.getCanonicalName();
		SpacedRepetition object = SpacedRepetitionFactory.getSpacedRepetition(name);
		assertThat(object, instanceOf(BasicRandomization.class));
	}
}
