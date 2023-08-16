import java.io.IOException;
import java.util.*;

/**
 * @author Rahul Atre
 */

public class TestClosenessCentrality {

    public static void main(String[] args) throws IOException {
        int topN = 10;				// topN top page-ranked nodes to display

        String[] edgeFilenames = {"email-dnc.edges"}; // edge file names

        ClosenessCentrality CC = new ClosenessCentrality();

        for(int i = 0; i < edgeFilenames.length; i++){
            Graph graph = GraphParserUtil.readGraph(edgeFilenames[i]);

            Map<Integer, Double> closenesscentrality = CC.computeClosenessCentrality(graph);
            closenesscentrality = sortClosenessCentrality(closenesscentrality);

            System.out.println("\n\n\nTesting "+edgeFilenames[i]);

            // print topN nodes, CCs, and their adjacency lists in order of decreasing CC values
            int rank = 1;
            System.out.println("\nNumber of nodes in the Graph: " + closenesscentrality.size() + "\n");

            for(Integer node : closenesscentrality.keySet()) {
                System.out.println("Rank:" + rank + "\t" + "Node number: " + node + "\t" + "Node CC: " + closenesscentrality.get(node));

                System.out.print("\nAdjacent Nodes: ");
                if (graph.getGraphEdges().containsKey(node)) {
                    for(Integer edge : graph.getGraphEdges().get(node)) {
                        System.out.print(edge + " ");
                    }
                }
                System.out.println("\n------------------------------------\n");

                if(++rank > topN)
                    break;
            }
        }

    }


    private static Map<Integer, Double> sortClosenessCentrality(Map<Integer, Double> closenesscentrality){
        // Create a list from entries of HashMap
        List<Map.Entry<Integer, Double> > list = new ArrayList<Map.Entry<Integer, Double> >(closenesscentrality.entrySet());

        // Sort the list in decreasing order of CCs
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double> >() {
            public int compare(Map.Entry<Integer, Double> cc1, Map.Entry<Integer, Double> cc2)
            {
                return (cc2.getValue()).compareTo(cc1.getValue());
            }
        });

        // transfer the entries from sorted list to a LinkedHashMap that preserves the insertion order
        HashMap<Integer, Double> temp = new LinkedHashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> cc : list) {
            temp.put(cc.getKey(), cc.getValue());
        }
        return temp;
    }
}
