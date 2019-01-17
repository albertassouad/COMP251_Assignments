package A2;
import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){

        /* Fill this method (The statement return null is here only to compile) */
    	
    	// create a disjoint sets with  size nb_nodes in the input graph
    	DisjointSets p = new DisjointSets(g.getNbNodes());
    	
    	// create a new graph to hold the MST
    	WGraph mst = new WGraph();
    	// list of sorted edges
    	ArrayList<Edge> sortedEdgesList = g.listOfEdgesSorted();
    	for ( int i = 0 ; i < sortedEdgesList.size() ; i ++) {
    		if(IsSafe(p,sortedEdgesList.get(i))) {
    			mst.addEdge(sortedEdgesList.get(i));
    			
    		}
    	}
    	
    			
        return mst;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){

        /* Fill this method (The statement return 0 is here only to compile) */
    	
    	boolean isSafe = false;
    	//create two variables to hold the value of representative of two ends of the edge
    	int v1,v2;
    	v1 = e.nodes[0];
    	v2 = e.nodes[1];
    	if(p.find(v1) != p.find(v2)) { // if two vertices have the different rep ---> not in same sets
    		p.union(v1, v2);
    		isSafe = true;
    	}
    	return isSafe;
      }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
