package mgfAssignment;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;


public class TestApp1 {

	@Test
	public void test() {
		
		assertEquals(TeamBuilderApp.getStringFromMainApp(),"Hello-App");

		if (TeamBuilderApp.loadPropertyValues() == 0) {
			System.out.println("** Warning: No Properties file on ClassPath, Using default settings.");
		}
		else {
			System.out.println("** Information: Properties file on ClassPath, values may Override default settings.");
		}
		System.out.println("** DEBUG** Valid No. of Paths Range from "+TeamBuilderApp.MIN_PATHS+" to "+TeamBuilderApp.MAX_PATHS);

		int numPaths = testUserInput(System.in,System.out);
		assertTrue(TeamBuilderApp.MIN_PATHS <= numPaths && numPaths <= TeamBuilderApp.MAX_PATHS);
		
		System.out.println("User-entered Number of Vertices for Matrix:["+numPaths+"]");
		TeamBuilder teamBuilder = new TeamBuilder(numPaths);
		
		Scanner scan = new Scanner(System.in);
		String[] allPaths = teamBuilder.getValidUserPathString(scan);
		
		assertEquals(numPaths,allPaths.length);
	}

	
	
	public static int testUserInput(InputStream in,PrintStream out) {
		Scanner keyboard = new Scanner(in);
		//out.print("Give a number between 1 and 10: ");
		int input = 0;//keyboard.nextInt();
		while (input < TeamBuilderApp.MIN_PATHS || input > TeamBuilderApp.MAX_PATHS) {
			out.print("Enter the number of paths ( >= "+TeamBuilderApp.MIN_PATHS+" AND <= "+TeamBuilderApp.MAX_PATHS+"): ");
			input = keyboard.nextInt();
			if (input < TeamBuilderApp.MIN_PATHS || input > TeamBuilderApp.MAX_PATHS)
				out.println("Error: Invalid Input, try again.");			
		} 
		return input;
	}
}
