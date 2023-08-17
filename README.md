# Closeness Centrality Network
An application that computes the closeness centrality score for each node in the network based on the sum of its shortest paths and relative distance, identifying the most influential nodes within the network

## Execute Program

Run from command line as below:

```
javac *.java && java GraphParserUtil
```

To run performance test (algorithm output):

```
javac *.java && java TestClosenessCentrality
```

**File Structure:**

- `~/code/` directory contains Closeness Centrality Algorithm
- `~/dataset/` directory contains the graph network files to test the program
- `~/output/` directory contains the output files

## Table of Contents

- [Closeness Centrality Network](#closeness-centrality-network)
- [Execute Program](#execute-program)
- [Table of Contents](#table-of-contents)
- [Main Program](#main-program)
  - [Algorithm Explanation](#algorithm-explanation)
  - [Components](#components)
- [Performance](#performance)


## Main Program

In graph theory and network analysis, indicators of centrality identify the most important vertices within a graph. Applications include: 
- Identifying the most influential people in a social network
- Key infrastructure nodes in internet networks
- The infectious spread of a harmful virus

This algorithm scores each node based on their closeness to all other nodes in the network. It calculates the shortest paths between all nodes and assigns each node a score based on its sum of shortest paths. It is calculated as the average of the shortest path length from the node to every other node in the network. The more central a node is, the lower its total distance to all other nodes.

The closeness is defined as the inverse of the farness. We can standardize the closeness by multiplying the value by to (N-1), representing the equation as:

![image](https://github.com/RahulAtre/closeness-centrality-network/assets/88638204/ecb6ee00-4a4d-4f6a-b5ed-7167bcf3035c)

## Algorithm Explanation

ClosenessCentrality.java contains the main algorithm for this program. It imports several files from the JVM library (HashMap, HashSet, Queue, LinkedList) and contains two methods: computeClosenessCentrality and breadthFirstSearch. 
- computeClosenessCentrality: This high-level method brings together all of the data (graph, nodes, summation distance) and formulates them into the closeness centrality(CC) value for a particular node. The method requires an initial parameter of type Graph to compute the CC for all of the graphs nodes. It returns a HashMap for the key-value pairs for each node in the graph and its corresponding CC value.
- breadthFirstSearch: This private method runs the breadth first search (BFS) algorithm on a starting node in the graph. The BFS algorithm has been modified to obtain the CC calculation. First, the algorithm will traverse the shortest distance to all other nodes, sum all those values one at a time, and return the total shortest summation distance to computeClosenessCentrality, the higher-level method. It requires initial parameters of type Graph, and an integer rootNode, which will represent the starting node of the graph.
  
Graph.java stores the list of nodes and the adjacency list for every node in the graph. Its corresponding utility class assists in reading the graph (parsing and storing data), as well as printing each node and its adjacency list. TestClosenessCentrality computes the 10 most influential nodes in the graph network, and visualizes it in the terminal. 

## Components
1. computeClosenessCentrality
   1. Obtain an integer list of all the graphs nodes by doing graph.getGraphNodes()
   2. Create a HashMap that will store the graphs node and a closeness centrality value as a key-value pair
   3. Iterate through all nodes using for-loop block and call the BFS algorithm on current node
   4. Obtain the sum of the shortest paths between that node and all other nodes
   5. Store the adjacency list and the number of vertices of the graph, along with variables to store the total distance and current depth level of BFS traversal
   6. NodeTraversalQueue keeps track of the child nodes that need to be explored | nodesVisited HashSet keeps track of nodes that are visited during BFS search
2. breadthFirstSearch
   1. The first while-loop keeps running unless the BFS algorithm has traversed all possible nodes or if the queue keeping track of the unexplored child nodes has been emptied. These two parameters will indicate if the summation process has been completed
   2. The second while-loop keeps running until the BFS algorithm has traversed nodes of a particular depth. Since the iterate is level-by-level, continue once all nodes of that depth have been explored. Hence, first take a ‘snapshot’ of the current queue size to indicate the number of nodes of that depth
   3. Decrement the ‘snapshot’ size until it reaches 0, increment the depth by 1, and repeat the process. Inside this block, perform a standard BFS search to explore all adjacent nodes
   4. If the adjacent node is unexplored (not in HashSet), mark it as ‘explored’ by adding it to HashSet, add it to the queue (to explore its child nodes), and add its depth level (distance to rootNode) to the total shortest path summation
   5. Account for nodes that were unvisited (not connected to rootNode). To compensate, add the disconnect distance * (number of vertices - nodes visited)
   6. Finally, return the summation to the higher-level method and calculate the CC using the formula N-1 / Summation. Then store it in the HashMap and repeat for all nodes in the graph

### Performance

The theoretical performance of this algorithm is O(n)*O(|V|+ |E|) = O(n^2), where n is the number of vertices in the graph.
