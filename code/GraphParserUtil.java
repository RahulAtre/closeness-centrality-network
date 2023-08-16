import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rahul Atre
 */

public class GraphParserUtil {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String edgesFilename = "email-dnc.edges";
        Graph graph = readGraph(edgesFilename);
        List<Integer> nodes = graph.getGraphNodes();
        Map<Integer, List<Integer>> edges = graph.getGraphEdges();

        System.out.println("Number of nodes in the Graph: " + nodes.size());

        for(Integer node : nodes) {
            System.out.println("Node number: " + node);
            System.out.print("Adjacent Nodes: ");
            if (edges.containsKey(node)) {
                for(Integer edge : edges.get(node)) {
                    System.out.print(edge + " ");
                }
            }
            System.out.println();
            System.out.println("------------------------------------");
        }

        ClosenessCentrality c1 = new ClosenessCentrality();
        c1.computeClosenessCentrality(graph);

    }

    public static Graph readGraph(String edgesFilename) throws FileNotFoundException, IOException {
        String myPath = System.getProperty("user.dir");
        //URL edgesPath = GraphParserUtil.class.getResource(edgesFilename); //I am not using this URL, and instead creating my own path for the csvReader
        BufferedReader csvReader = new BufferedReader(new FileReader(myPath + "\\" + edgesFilename)); //I changed this line because the URL path wasn't being recognized by my computer
        
        /**
         * Approach 2 for reading the file without interfering with the path manually -> 
         * 
         * BufferedReader csvReader = new BufferedReader(new FileReader(new File(edgesFilename)));
         * 
         * The above line will also generate the appropriate file Reader for the edgesFile
         */


        String row;
        List<Integer>               nodes = new ArrayList<Integer>();
        Map<Integer, List<Integer>> edges = new HashMap<Integer, List<Integer>>();

        boolean first = false;
        while ((row = csvReader.readLine()) != null) {
            if (!first) {
                first = true;
                continue;
            }

            String[] data = row.split(",");

            Integer u = Integer.parseInt(data[0]);
            Integer v = Integer.parseInt(data[1]);

            if (!nodes.contains(u)) {
                nodes.add(u);
            }
            if (!nodes.contains(v)) {
                nodes.add(v);
            }

            /**
             * For this if-else clause, I changed the ordering of the else-statements for the duplicate edges so that the file->graph conversion is always an undirected graph
             */
            if (!edges.containsKey(u)) {
                // Create a new list of adjacent nodes for the new node u
                List<Integer> l = new ArrayList<Integer>();
                l.add(v);
                edges.put(u, l);

            } else { 
                // don't add duplicate edge between u and v
                if (!edges.get(u).contains(v))
                    edges.get(u).add(v);
            }


            if (!edges.containsKey(v)) {
                // Create a new list of adjacent nodes for the new node v
                List<Integer> l2 = new ArrayList<Integer>();
                l2.add(u);
                edges.put(v, l2);

            } else {
                // don't add duplicate edge between u and v
                if (!edges.get(v).contains(u))
                    edges.get(v).add(u);
            }
        }

        for (Integer node : nodes) {
            if (!edges.containsKey(node)) {
                edges.put(node, new ArrayList<Integer>());
            }
        }

        csvReader.close();
        return new Graph(nodes, edges);
    }
}
