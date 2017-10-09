package mgfAssignment;

import static org.junit.Assert.*;
import org.junit.Test;


public class TestApp1 {

	@Test
	public void test() {
		
		assertEquals(TeamBuilderApp.getStringFromMainApp(),"Hello-App");

		int numPaths = 3;
		String pathString = "[\"010\",\"000\",\"110\"]";
		TeamBuilder teamBuilder = new TeamBuilder(numPaths);
		assertEquals(numPaths, teamBuilder.getnumberofVertices());

		String[] extractedStrings = teamBuilder.extractMatchedPatternString(numPaths,pathString);
		assertEquals(numPaths,extractedStrings.length);
		System.out.println("Tests DONE");
	}

}
