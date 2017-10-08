/**
 * 
 */
package mgfAssignment;

/**
 * @author Mark.Freeman
 * 
 * This performs a transitive closure on the Input Matrix, using the Floyd Warshall Algorithm.
 * This is a standard Graph algorithm
 *         F.W. Algorithm runs in O(n3) ... Note that there is a O(n2) for larger matrices
 *         
 * Once the closure matrix is produced, we can easily compute:
 *   - shortest distance between nodes
 *   - the nodes which are reachable from all other nodes
 *   - nodes that are able to reach all other nodes
 *
 */
public class TeamBuilder {

    private int adjacency_matrix[][];
    private int numberofVertices;
    
    // This is the Transitive Clouse of the input Adjacency Matrix 
    private int transitiveMatrix[][];
    
	/**
	 * 
	 */
	public TeamBuilder() {
		// TODO Auto-generated constructor stub
	}
	public TeamBuilder(int numVertices) {
		this.numberofVertices = numVertices;
	}

	public void setnumberofVertices(int numVerts) {
		this.numberofVertices = numVerts;
	}
	public int getnumberofVertices() {
		return this.numberofVertices;
	}
	
	public void setAdjancyMatrix(int a_matrix[][]) {
		this.adjacency_matrix = a_matrix;
	}
	public int[][] getAdjancyMatrix() {
		return this.adjacency_matrix;
	}

	public int[] specialLocations(String[] paths) {
		int[] rvals = null;		
		return rvals;
	}

	public void transitiveClosure2() {
		   /* reach[][] will be the output matrix that will finally have the 
	       shortest distances between every pair of vertices */
		int V = numberofVertices;
		int reach[][];
		reach = new int[numberofVertices + 1][numberofVertices + 1];
	    int i, j, k;
	 
	    /* Initialize the solution matrix same as input graph matrix. Or
	       we can say the initial values of shortest distances are based
	       on shortest paths considering no intermediate vertex. */
	    for (i = 0; i < V; i++) {
	        for (j = 0; j < V; j++) {
	        	if (i == j)
	        		reach[i][j] = 1;
	        	else
	        		reach[i][j] = adjacency_matrix[i][j];//graph[i][j];
	        }
	    }
	    /* Add all vertices one by one to the set of intermediate vertices.
	      ---> Before start of a iteration, we have reachability values for
	           all pairs of vertices such that the reachability values 
	           consider only the vertices in set {0, 1, 2, .. k-1} as 
	           intermediate vertices.
	      ----> After the end of a iteration, vertex no. k is added to the 
	            set of intermediate vertices and the set becomes {0, 1, .. k} */
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
	                // then make sure that the value of reach[i][j] is 1
	                reach[i][j] = reach[i][j] | (reach[i][k] & reach[k][j]);
	            }
	        }
	    }
		System.out.println();
		for (int source = 0; source < numberofVertices; source++) {
			//System.out.print(source + "\t");
			for (int destination = 0; destination < numberofVertices; destination++) {
				System.out.print(reach[source][destination] + "\t");
			}
			System.out.println();
		}
		System.out.println("\n\n********** End of Transitive-Closure Matrix **********");
 		
		
	}

	// Method for producing a transitive closure of a DAG, using Floyd Warshall Algorithm
	public void transitiveClosure() {
	 
		// Same size matrix as original
		transitiveMatrix= new int[numberofVertices + 1][numberofVertices + 1];

		// Copy original matrix 1st
		for (int source = 0; source < numberofVertices; source++) {
	            for (int destination = 0; destination < numberofVertices; destination++)
	            {
	                transitiveMatrix[source][destination] = adjacency_matrix[source][destination];
	                if (source == destination) // every element on Diagonal is '1' in this algorithm
	                	transitiveMatrix[source][destination] = 1;
	            }
		}
	 
		for (int intermediate = 0; intermediate < numberofVertices; intermediate++) {
	            for (int source = 0; source < numberofVertices; source++)
	            {
	                for (int destination = 0; destination < numberofVertices; destination++)
	                {
	                    if (transitiveMatrix[source][intermediate] + transitiveMatrix[intermediate][destination]
	                               < transitiveMatrix[source][destination])
	                        transitiveMatrix[source][destination] = transitiveMatrix[source][intermediate] 
	                                + transitiveMatrix[intermediate][destination];
	                }
	            }
		}
	 
		System.out.println("\n********** Start of Transitive-Closure Matrix **********");

//		for (int source = 1; source <= numberofVertices; source++)
//			System.out.print("\t" + source);
	 
		System.out.println();
		for (int source = 0; source < numberofVertices; source++) {
			System.out.print(source + "\t");
			for (int destination = 0; destination < numberofVertices; destination++) {
				System.out.print(transitiveMatrix[source][destination] + "\t");
			}
			System.out.println();
		}
		System.out.println("\n\n********** End of Transitive-Closure Matrix **********");
	}
}
