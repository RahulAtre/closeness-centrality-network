import java.util.HashMap;
import java.util.Map; //Used to store -> Closeness Centrality mapping for each node || Adjacency List for each node

import java.util.List; //Used to store a list of the nodes in a graph

import java.util.Set;
import java.util.HashSet; //Used to store the nodes that have been visited during the BFS algorithm traversal

import java.util.Queue; //To look at LinkedList through the lens of the Queue Interface for our BFS algorithm
import java.util.LinkedList; //BFS Queue to store the child nodes that need to be visited to go deeper in our BFS algorithm

/**
 * @author Rahul Atre
 */

public class ClosenessCentrality {
    /**
     * This means if you do not have any connection between two, you can use total number of nodes.
     */
    public static final double Dicconnect_Distance = 1891;

    /**
     * we do not have any static or predefined parameters in Closeness Centrality formula but
     * you can add based on your code if you need it (like size, number of edges)
     */

    ClosenessCentrality() {
    }

    /**
     * Computes the ClosenessCentrality (CC) of each node in a graph.
     *
     * @param graph the Graph to compute CC for
     * @return returns a Map<Integer, Double> mapping each node to its ClosenessCentrality CC
     *
     */
    public Map<Integer, Double> computeClosenessCentrality(Graph graph) {

        List<Integer> graphNodes = graph.getGraphNodes();
        Map<Integer, Double> closenessCentralityNodes = new HashMap<Integer, Double>();

        for(Integer startNode : graphNodes) { //Iterate through all nodes and compute its CC

            double sumOfDistances = breadthFirstSearch(graph, startNode); //Perform BFS algorithm to obtain the total distances sum of that node
            double closenessCentrality = (graphNodes.size() - 1)/sumOfDistances; //Calculate the CC of that Node

            closenessCentralityNodes.put(startNode, closenessCentrality); //Add a key-value pair of the node and its CC value 
        }

        return closenessCentralityNodes;
        
    }    

    /**
     * This method applies the breadhFirstSearch algorithm on a starting node and sums the distance of all other nodes through its traversal process
     * @param graph to get the list of nodes and its adjacent nodes
     * @param rootNode which is the starting node of the BFS algorithm
     * @return returns the shortest path summation to all other nodes (Which is then used in the CC calculation)
     * 
     */
    private int breadthFirstSearch(Graph graph, int rootNode) {
        Map<Integer, List<Integer>> adjacencyList = graph.getGraphEdges();
        int numOfVertices = graph.getGraphNodes().size();

        Queue<Integer> nodeTraversalQueue = new LinkedList<Integer>(); nodeTraversalQueue.add(rootNode); //This Queue will track the child nodes that need to be fully explored; add the rootNode to begin the algorithm traversal process
        Set<Integer> nodesVisited = new HashSet<Integer>(); nodesVisited.add(rootNode); //This HashSet will keep track of nodes that have been visited; mark rootNode as 'visited'
        int sumOfDistances=0; //Stores total distance summation
        int depthLevel=1; //Stores current BFS depth level (By how much will we increase our total sum when we reach a node of greater depth)

    
        while(!nodeTraversalQueue.isEmpty() && (nodesVisited.size() != numOfVertices)) { //Loop keeps running until we have either seen all possible nodes (summation calculation complete) or if the queue is empty (all possible nodes exhausted)
            int adjacencyNodeSize = nodeTraversalQueue.size();    

            while(adjacencyNodeSize != 0) { //Loop keeps running until we have traversed through all nodes of that particular depth
                int currentNode = nodeTraversalQueue.remove();

                /**
                 * //Explore all adjacent vertices of that node, if it is an unexplored node, 
                 * //mark it as explored, insert it to the Queue to search its child nodes,
                 * //and add its depth level to the total summation
                 */
                for(int adjacentNode: adjacencyList.get(currentNode)) { 

                    if(!nodesVisited.contains(adjacentNode)) {
                        nodesVisited.add(adjacentNode);
                        nodeTraversalQueue.add(adjacentNode);

                        sumOfDistances+=depthLevel;
                    }
                }
                adjacencyNodeSize--;
            }
            depthLevel++;

        }

        sumOfDistances += ((Dicconnect_Distance) * (numOfVertices - nodesVisited.size())); //To account for vertices not connected to our rootNode -> Sum the Disconnected distance * # of disconnnected vertices to our current rootNode
        return sumOfDistances;
    }

}