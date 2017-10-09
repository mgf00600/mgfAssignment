
-----------------------------------
Date: 10/9/2016
Author: Mark G Freeman
-----------------------------------

-----------------------------------
 Code/Build/Test/Github
-----------------------------------

  Code:
    ../mgfAssignment/pom.xml, README.txt
    ../mgfAssignment/src/main/java/TeamBuilderApp.java, TeamBuilder.java
    ../mgfAssignment/src/test/java/TestApp1.java
    ../mgfAssignment/src/main/resources/config.properties
  
  Build: mvn package (will run test phase)
  Test:  mvn test
  Github: https://github.com/mgf00600/mgfAssignment.git

  
-----------------------------------
 Collaboration: GitHub public
-----------------------------------
 
  Location for collaboration: https://github.com/mgf00600/mgfAssignment.git
  clone: to get a local copy, or clone of the public repo., which includes everything (for later push requests)
  fork: will create a copy in your Github account (pull requests)
  
  Command to Clone: git clone https://github.com/mgf00600/mgfAssignment.git


-----------------------------------
 Running/Command Line Arguments
-----------------------------------
 Example: (assumes you are running from GitHub downloaded project-directory ../mgfAssignment/)
	
	java -cp target/mgfAssignment-0.0.1-SNAPSHOT.jar mgfAssignment.TeamBuilderApp

	(Input 1 prompt:) Enter the number of paths ( >= 2 AND <= 50): 5
	(Input 2 prompt:) Enter the String of paths: ["01000","00100","00010","00001","10000"]
	
	(Output:) [5, 5]

    This output means that: 1st '5': there are 5 nodes that can reach all other nodes
    						2nd '5': all the other nodes can reach 5 other nodes
 
-----------------------------------
 Problem/Design/Decisions
-----------------------------------
 The problem is to count 2 types of paths within a user-entered sequence of 0's & 1's.
 The sequence is comma-separated, and each comma-sep. element represents a row of a N*N Matrix.

 For example, ["010","000","110"] is a 3*3 Matrix (3 comma-sep. entries, each with 3 0's/1's). In the 1st element
 "010", the '1' represents the entry in row=1, column=2, which means there is a path between node=1 and node=2.

 See the "Problem Description" below for a more detailed description.

 The solution program solves the problem by representing the sequence in a directed Graph.
 From this, it computes the "Transitive Closure" of the Matrix, which shows whether 2 nodes are reachable.
 Note that the most common use of this computation is to calculate "Shortest Path" between any 2 locations.
 However in this case, I'm using it to ignore the transient nodes, and instead use a '1' to mean there does
 exist a reachable path between any 2 nodes, and a 0 to mean that there is NO reachable path between 2 nodes.
 Then, when a row for node=k has all 1's, it means that node k can reach ALL other nodes in the graph.
 And when a column node=k contains all 1's, it means that ALL the other nodes can reach node k.
 To produce the final 2 counts, it simply counts the number of all rows which have 1's, and all columns of 1's,
 and returns these 2 counts.
 
-----------------------------------
 Core Algorithm
----------------------------------- 

 Uses the Floyd Warshall Algorithm to compute the Transitive Closure of a Directed Graph, represented as a N*N Matrix.
 
 The algorithm complexity is: O(n*n*n).
 For very large matrices, there is an optimized O(n*n*log(n)) algorithm
 
-----------------------------------
 Configuration
----------------------------------- 
 There is an optional properties file that the program looks for at startup: "config.properties"
 To be found at startup, it needs to be available on the Classpath.
 
 Two optional configuration var's: (these are also the default min & max values).
 If invalid entries are present, they are ignored and set back to these defaults.
 
	MIN_PATHS=2
	MAX_PATHS=50

 
-----------------------------------
 Original Problem Statement
----------------------------------- 

Problem Statement You are arranging a weird game for a team building exercise. In this game there are certain locations that people can stand at, and from each location there are paths that lead to other locations, but there are not necessarily paths that lead directly back. You have everything set up, but you need to know two important numbers. There might be some locations from which every other location can be reached. There might also be locations that can be reached from every other location. You need to know how many of each of these there are.

Create a class TeamBuilder with a method specialLocations that takes a String[] paths that describes the way the locations have been connected, and returns a int[] with exactly two elements, the first one is the number of locations that can reach all other locations, and the second one is the number of locations that are reachable by all other locations. Each element of paths will be a String containing as many characters as there are elements in paths. The i-th element of paths (beginning with the 0-th element) will contain a '1' (all quotes are for clarity only) in position j if there is a path that leads directly from i to j, and a '0' if there is not a
path that leads directly from i to j.


Definition

Class:  TeamBuilder Method: specialLocations Parameters:
String[] Returns:  int[] Method signature: int[]
specialLocations(String[] paths) (be sure your method is
public)


Constraints
    - paths will contain between 2 and 50 elements, inclusive.
    - Each element of paths will contain N characters, where N is the number of elements of paths.
    - Each element of paths will contain only the characters '0' and '1'.
    - The i-th element of paths will contain a zero in the i-th position.  So {"01","00"} is valid.


Examples

0) {"010","000","110"}

Returns: { 1,  1 }

Locations 0 and 2 can both reach location 1, and location
2 can reach both of the other locations, so we return
{1,1}.

1) {"0010","1000","1100","1000"}

Returns: { 1,  3 }

Only location 3 is able to reach all of the other
locations, but it must take more than one path to reach
locations 1 and 2. Locations 0, 1, and 2 are reachable by
all other locations. The method returns {1,3}.

2) {"01000","00100","00010","00001","10000"}

Returns: { 5,  5 }

Each location can reach one other, and the last one can
reach the first, so all of them can reach all of the
others.

3) {"0110000","1000100","0000001","0010000","0110000","1000010","0001000"}

Returns: { 1,  3 }
