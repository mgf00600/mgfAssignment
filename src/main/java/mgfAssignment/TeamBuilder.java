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
 * This performs a transitive closure on the Input Matrix, using the Floyd Warshall Algorithm.
 * This is a standard Graph algorithm
 *         F.W. Algorithm runs in O(n3) ... Note that there is a O(n2) for larger matrices
 *         
 * Once the closure matrix is produced, we can easily compute:
 *   - shortest distance between nodes
 *   - the nodes which are transitiveMatrixable from all other nodes
 *   - nodes that are able to transitiveMatrix all other nodes
 *
 */
public class TeamBuilder {
  
	// Program works on a n*n matrix only
	private int numberofVertices;

	// The input Matrix provided by User
    private int adjacencyMatrix[][];
    
    // This is the Transitive Closure of the input Adjacency Matrix 
    private int transitiveMatrix[][];
    
	/**
	 * Constructors
	 */
	public TeamBuilder() {
		// TODO Auto-generated constructor stub
	}
	public TeamBuilder(int numVertices) {
		this.numberofVertices = numVertices;
	}

	// Setters/Getters
	public void setnumberofVertices(int numVerts) {
		this.numberofVertices = numVerts;
	}
	public int getnumberofVertices() {
		return this.numberofVertices;
	}	
	public void setAdjacencyMatrix(int a_matrix[][]) {
		this.adjacencyMatrix = a_matrix;
	}
	public int[][] getAdjacencyMatrix() {
		return this.adjacencyMatrix;
	}
	public void setTransitiveMatrix(int a_matrix[][]) {
		this.transitiveMatrix = a_matrix;
	}
	public int[][] getTransativeMatrix() {
		return this.transitiveMatrix;
	}

/*	public int[] specialLocations(String[] paths) {
		int[] rvals = null;		
		return rvals;
	}
*/
	public String computeResults() {
		
		// Compute 1st count => the nodes that can reach all other nodes
		int count1 = 0;
		for (int i=0; i < numberofVertices; i++) {
			// check all columns for this row, if all '1', increment count1
			Boolean all_one = true;
			for (int j=0; j < numberofVertices; j++) {
				if (transitiveMatrix[i][j] == 0) {
					all_one = false;
					break;
				}
			}
			if (all_one) count1++;
		}
		// Compute 2nd count => the number of nodes that all other nodes can reach
		int count2 = 0;
		for (int j=0; j < numberofVertices; j++) {
			// check all rows for this column, if all '1', increment count2
			Boolean all_one = true;
			for (int i=0; i < numberofVertices; i++) {
				if (transitiveMatrix[i][j] == 0) {
					all_one = false;
					break;
				}
			}
			if (all_one) count2++;
		}
		String result = String.format("[%d, %d]", count1,count2);	
		return result;
	}
	
	public String[] getValidUserPathString(Scanner scan) {
	 
		String strNum = Integer.toString(numberofVertices);
		
		/* The pattern is dynamic since it needs to be based on User-selected no. of vertices for the matrix.
		 * Note that the pattern is constrained by number of column entries, to ensure there are exactly n*n
		 * entries entered by user. Also, comma's must separate each set of row values. But no other constraint
		 * on pattern, e.g., this pattern is ok for a 3*3 matrix: 010,"000",100 
		 */
		String partialRegExp = "([0-1]{"+strNum+"})[ \\t\\r\",]+";
		String inputRegExp = "[ \\t\\r\\[\"]*([0-1]{"+strNum+"})[ \\t\\r\",]+([0-1]{"+strNum+"})[ \\t\\r\",]+";
	
		// Pattern built based on no. of vertices
		for (int i=2; i < numberofVertices; i++) {
			inputRegExp = inputRegExp + partialRegExp;
		}
	
		// Final pattern to capture 'numVertices' number of paths from the user
		Pattern p = Pattern.compile(inputRegExp);
		//System.out.println("** DEBUG; Pattern for matching: ["+inputRegExp+"]\n");
		String inputPaths;
		String[] allPaths = new String[numberofVertices];

		Boolean validPaths = false;
		while (!validPaths) {
			
			System.out.print("Enter the String of paths: ");
			inputPaths = scan.nextLine();
			Matcher m = p.matcher(inputPaths);
		
			// Does the line user just entered, match the pattern?
			if (m.find()) { // YES!
			
				// Be safe and verify the number of groups matched equals expected/required number of paths
				if (m.groupCount() != numberofVertices) {
					System.out.println("  ** ERROR: Invalid no. of paths, there must be = "+numberofVertices + "**\n");
					continue; // back to while()
				}
				
				for (int i = 0; i < numberofVertices; i++) {
					allPaths[i] = m.group(i+1);
				}
				validPaths = true;
			}
		}
		return allPaths;
	}

	public void transitiveClosure() {
		   /* transitiveMatrix[][] will be the output matrix that will finally have the 
	       shortest distances between every pair of vertices */
		int V = numberofVertices;
		int transitiveMatrix[][];
		transitiveMatrix = new int[numberofVertices][numberofVertices];
	    int i, j, k;
	 
	    /* Initialize the solution matrix same as input graph matrix. Or
	       we can say the initial values of shortest distances are based
	       on if a path exists.
	    */
	    for (i = 0; i < V; i++) {
	        for (j = 0; j < V; j++) {
	        	if (i == j)
	        		transitiveMatrix[i][j] = 1;
	        	else
	        		transitiveMatrix[i][j] = adjacencyMatrix[i][j];
	        }
	    }
	    /* Set all vertices one by one to the set of intermediate vertices.
	      --- Before start of a iteration, we have transitiveMatrixability values for
	           all pairs of vertices such that the transitiveMatrixability values 
	           consider only the vertices in set {0, 1, 2, .. k-1} as 
	           intermediate vertices.
	      ---  After the end of a iteration, vertex no. k is added to the 
	           set of intermediate vertices and the set becomes {0, 1, .. k}
	    */
	    for (k = 0; k < V; k++)
	    {
	        // Pick all vertices as source one by one
	        for (i = 0; i < V; i++)
	        {
	            // Pick all vertices as destination for the
	            // above picked source
	            for (j = 0; j < V; j++)
	            {
	                // If vertex k is on a path from i to j,
	                // then make sure that the value of transitiveMatrix[i][j] is 1
	                transitiveMatrix[i][j] = transitiveMatrix[i][j] | (transitiveMatrix[i][k] & transitiveMatrix[k][j]);
	            }
	        }
	    }
		setTransitiveMatrix(transitiveMatrix);
	}
	
	 
    public String toString() {
    	StringBuffer rval = new StringBuffer();

    	rval.append("\nStart TeamBuilder Instance ******\n");
    	rval.append("\tNumber of Matrix Vertices: ["+numberofVertices+"]\n");
    	rval.append("\t** Input Matrix **\n");
    	//System.out.println("SIZE of TransMatrix:["+adjacencyMatrix.length+"]");
    	for (int i=0; i < adjacencyMatrix.length; i++) {
    		for (int j=0; j < adjacencyMatrix[i].length; j++) {
    			if (j==0)
    				rval.append("\t\t");
    			rval.append(adjacencyMatrix[i][j] + "\t");
    		}
    		rval.append("\n");
    	}
    	
    	rval.append("\t** Transitive Closure Matrix **\n");
    	//System.out.println("SIZE of TransMatrix:["+transitiveMatrix.length+"]");
    	for (int i=0; i < transitiveMatrix.length; i++) {
    		for (int j=0; j < transitiveMatrix[i].length; j++) {
    			if (j==0)
    				rval.append("\t\t");
    			rval.append(transitiveMatrix[i][j] + "\t");
    		}
    		rval.append("\n");
    	}
    	rval.append("End TeamBuilder Instance ******\n");

    	return rval.toString();
	}

}
