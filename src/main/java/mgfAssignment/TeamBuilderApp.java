/**
 * 
 */
package mgfAssignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author Mark.Freeman
 * 
 * Created: 10/8/2017
 *
 */
public class TeamBuilderApp {

	private static Boolean debug = true;
	
	static String configFileName = "config.properties";
	static Integer MIN_PATHS = 2; // Configurable, but must be >= 2
	static Integer MAX_PATHS = 50; // Configurable, but must be <= 50
	
	private static TeamBuilder teamBuilder;

	public TeamBuilderApp() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {

		// If there is an optional properties file, load these values.... may Override MIN_PATHS and/or MAX_PATHS
		loadPropertyValues();
		
		// Get no. of Paths to process, and the list of these paths, from User. Build original Matrix from paths.
		teamBuilder = processInput();
		
		// From original Matrix, perform the Transitive Closure on original Matrix, which gives us the results
		teamBuilder.transitiveClosure();
		
		if (debug) {
			System.out.println(teamBuilder);
		}
		
		// Compute the results and output
		String results = teamBuilder.computeResults();
		System.out.println(results);
	}

	public static TeamBuilder processInput() throws IOException {

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		int numVertices = 0;

		while ( ! (numVertices >= TeamBuilderApp.MIN_PATHS && numVertices <= TeamBuilderApp.MAX_PATHS) ) {
			System.out.print("Enter the number of paths ( >= "+TeamBuilderApp.MIN_PATHS+" AND <= "+TeamBuilderApp.MAX_PATHS+"): ");
			String str = input.readLine();
			numVertices = str.matches("\\d+") ? Integer.parseInt(str) : 0;
		}
		TeamBuilder teamBuilder = new TeamBuilder(numVertices);
		
		String[] allPaths = teamBuilder.getValidUserPathString();
		if (allPaths == null || allPaths.length == 0) {
			System.out.println("** FATAL: Paths list is Empty!");
			return null;
		}
		// Now break the binary strings into a Matrix of size: (numVertices * numVertices)
		int adjacency_matrix[][];
		adjacency_matrix = new int[numVertices][numVertices];

		for (int i=0; i < allPaths.length; i++) { // for (each row)
			
			//System.out.println("**DEBUG** Next Row for Matrix:["+allPaths[i]+"]");
			char[] nextRow = allPaths[i].toCharArray(); // these are the column values
			
			int colNum = 0;
			for (char s: nextRow) {
				adjacency_matrix[i][colNum++] = s - '0';
			}			
		}
		teamBuilder.setAdjacencyMatrix(adjacency_matrix);
		return teamBuilder;
	}

	public static int loadPropertyValues() {
		int rval = 0; // default is NO properties file
		
		InputStream input = TeamBuilderApp.class.getClassLoader().getResourceAsStream(TeamBuilderApp.configFileName);
		if (input == null) {
			;//System.out.println("Warning: No Properties file in ClassPath, using Defaults.");
		}
		else {
			Properties prop = new Properties();
			try {
				prop.load(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			rval = 1; // May or may not be entries for MIN_PATHS and/or MAX_PATHS
			String strMin = prop.getProperty("MIN_PATHS");
			if (strMin != null && strMin.length() > 0)
				TeamBuilderApp.MIN_PATHS = Integer.valueOf(strMin);
			if (TeamBuilderApp.MIN_PATHS < 2) {
				System.out.println("**WARNING: Invalid  Properties file 'MIN_PATHS' entry, Reset to 2");
				TeamBuilderApp.MIN_PATHS = 2;
			}
			
			String strMax = prop.getProperty("MAX_PATHS");
			if (strMax != null && strMax.length() > 0)
				TeamBuilderApp.MAX_PATHS = Integer.valueOf(strMax);
			if (TeamBuilderApp.MAX_PATHS > 50) {
				System.out.println("**WARNING: Invalid  Properties file 'MAX_PATHS' entry, Reset to 50");
				TeamBuilderApp.MAX_PATHS = 50;
			}
			
			if (TeamBuilderApp.MIN_PATHS >= TeamBuilderApp.MAX_PATHS) {
				 System.out.println("**WARNING: Invalid Properties file 'MIN_PATHS' entry, Reset to 2");
				TeamBuilderApp.MIN_PATHS = 2;
			}
		}
		return rval;
	}
	
	public static String getStringFromMainApp() {
		return "Hello-App";
	}
}

