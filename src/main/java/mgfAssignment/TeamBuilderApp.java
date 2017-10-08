/**
 * 
 */
package mgfAssignment;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mark.Freeman
 *
 */
public class TeamBuilderApp {

	private static TeamBuilder teamBuilder;
	/**
	 * 
	 */
	public TeamBuilderApp() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Started TeamBuilder Application");
	 
		teamBuilder = processInput();
		
		teamBuilder.transitiveClosure2();
		
		//System.out.println("The Transitive Closure of the Graph"); 
		//TransitiveClosure transitiveClosure = new TransitiveClosure(numberofvertices);
		//transitiveClosure.transitiveClosure(adjacency_matrix);	
	}

	public static TeamBuilder processInput() {
		Scanner scan = new Scanner(System.in);
		int numVertices = 0;

		while ( ! (numVertices >= 2 && numVertices <= 50) ) {
			System.out.print("Enter the number of paths ( >= 2 AND <= 50 ): ");
			numVertices = scan.nextInt();
		}
		
		System.out.println("Number of Vertices for Matrix:["+numVertices+"]");
		TeamBuilder teamBuilder = new TeamBuilder(numVertices);
		
		String partialRegExp = "([0-1]+)[ \\t\\r\",]+";
		String inputRegExp = "[ \\t\\r\\[\"]*([0-1]+)[ \\t\\r\",]+([0-1]+)[ \\t\\r\",]+";
		
		// Note: this ignores final ']' char, but my assumption is it does not have to be present
		for (int i=2; i < numVertices; i++) {
			inputRegExp = inputRegExp + partialRegExp;
		}
		
		// Final pattern to capture 'numVertices' number of paths from the user
		Pattern p = Pattern.compile(inputRegExp);
		System.out.println("Pattern for matching: ["+inputRegExp+"]\n");
		String inputPaths;
		String[] allPaths = new String[numVertices];

		Boolean validPaths = false;
		while (!validPaths) {

			System.out.print("Enter the String of paths: ");
			inputPaths = scan.nextLine();
			Matcher m = p.matcher(inputPaths);
			
			// Does the line user just entered, match the pattern?
			if (m.find()) { // YES!
				
				System.out.println("**DEBUG** the pattern and capture groups matched the string");

				// Be safe and verify the number of groups matched equals expected/required number of paths
				if (m.groupCount() != numVertices) {
					System.out.println("  ** ERROR: Invalid no. of paths, there must be = "+numVertices + "**\n");
					continue; // back to while()
				}
				
				for (int i = 0; i < numVertices; i++) {
					allPaths[i] = m.group(i+1);
					System.out.println("**DEBUG** Next Path:["+allPaths[i]+"]");
				}
				validPaths = true;
			}
		}
		
		// Now break the binary strings in a Matrix of size: (numVertices * numVertices)
		int adjacency_matrix[][];
		adjacency_matrix = new int[numVertices + 1][numVertices + 1];

		for (int i=0; i < allPaths.length; i++) { // for (each row)
			
			System.out.println("**DEBUG** Next Row for Matrix:["+allPaths[i]+"]");
			char[] nextRow = allPaths[i].toCharArray(); // these are the column values
			
			int colNum = 0;
			for (char s: nextRow) {
				adjacency_matrix[i][colNum++] = s - '0';
			}			
		}
		
		// Print out the Matrix to verify it is correct!
		System.out.println("\n********** Start of Input Matrix before Transitive Closure **********");
		for (int i=0; i < numVertices; i++ ) {
			System.out.println("      ");
			for (int j=0; j < numVertices; j++) {
				System.out.print(adjacency_matrix[i][j]+" ");
			}
		}
		System.out.println("\n\n********** End of Input Matrix before Transitive Closure **********");
		
		teamBuilder.setAdjancyMatrix(adjacency_matrix);
		scan.close();
		return teamBuilder;
	}
	
	public static String getStringFromMainApp() {
		return "Hello-App";
	}
}

