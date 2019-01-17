import java.io.*;
import java.util.*;




public class FordFulkerson {

	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		boolean[] visitedNode = new boolean[graph.getNbNodes()];
		Stack.add(0, source);
		int next = source;
		int sizeOfDFS = 0;
		do {
			sizeOfDFS++;
			visitedNode[next] = true;
			boolean neighborFound = false;
			for (Edge edge: graph.getEdges()) {
				if((next == edge.nodes[0]) && (!visitedNode[edge.nodes[1]]) && (edge.weight > 0)) {
					Stack.add(sizeOfDFS, edge.nodes[1]);
					neighborFound = true;
					break;
				}
			}
			if (!neighborFound) {
				sizeOfDFS--;
				Stack.remove(sizeOfDFS);
				if(sizeOfDFS == 0) {
					return new ArrayList<Integer>();
				}
				sizeOfDFS--;
			}
			next = Stack.get(sizeOfDFS);

		} while (next != destination);
		return Stack;
	}
	
	
	
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260717124"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
		
		WGraph residualGraph = new WGraph(graph);
		WGraph capacityGraph = new WGraph(graph);

		for (Edge e: graph.getEdges()) {
			e.weight = 0;
		}
		
		while (!pathDFS(source, destination, residualGraph).isEmpty()) {
			ArrayList<Integer> pathVertices = pathDFS(source, destination, residualGraph);
			ArrayList<Edge> pathEdges = new ArrayList<Edge>();
			int bottleNeck = residualGraph.getEdge(pathVertices.get(0), pathVertices.get(1)).weight;
			for (int i = 0; i < pathVertices.size() - 1; i++) {
				Integer currentNode = pathVertices.get(i);
				if (currentNode != destination) {
					Edge edge = residualGraph.getEdge(currentNode, pathVertices.get(i+1));
					pathEdges.add(edge);
					if (edge.weight < bottleNeck) {
						bottleNeck = edge.weight;
					}

				}
			}

			for (Edge edge: pathEdges) {
				Edge edgeGraph = graph.getEdge(edge.nodes[0], edge.nodes[1]);
				if(edgeGraph !=null) {
					edgeGraph.weight += bottleNeck;
				}
				else {
					graph.getEdge(edge.nodes[1], edge.nodes[0]).weight -= bottleNeck;
				}
			}

			for (Edge e: graph.getEdges()) {
				int max_capacity = capacityGraph.getEdge(e.nodes[0], e.nodes[1]).weight;
				int edge_weight = e.weight;

				if(residualGraph.getEdge(e.nodes[1], e.nodes[0]) == null) {
					Edge backwardsEdge = new Edge(e.nodes[1], e.nodes[0], edge_weight);
					residualGraph.addEdge(backwardsEdge);
				}

				residualGraph.getEdge(e.nodes[0], e.nodes[1]).weight = max_capacity - edge_weight;
				residualGraph.getEdge(e.nodes[1], e.nodes[0]).weight = edge_weight;
			}

			maxFlow += bottleNeck;
		}

		
		
		
		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}
	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
