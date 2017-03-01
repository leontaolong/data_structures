import java.util.*;

// This class is a client implementation of MyGraph class. It manages the flight routes
// among Seattle, San Franciscom, and Los Angelas. It also tests the MyGraph using some edge cases
public class MyClient {
	public static void main(String[] args) {
		List<Vertex> vList = new ArrayList<>();
		List<Edge> eList = new ArrayList<>();
		
      Vertex seattle = new Vertex("SEA");
      Vertex sanfran = new Vertex("SFO");
      Vertex la = new Vertex("LAX");
      
		vList.add(seattle);
		vList.add(sanfran); 
		vList.add(la);
		
		eList.add(new Edge(seattle, sanfran, 679));
      // test duplicates
      eList.add(new Edge(seattle, sanfran, 679));
      
      // text negative weight
      // eList.add(new Edge(seattle, sanfran, -679));
      
      // test overiding existing edge with different weight
      // eList.add(new Edge(seattle, sanfran, 99999));
      
      eList.add(new Edge(sanfran, seattle, 679));
		eList.add(new Edge(seattle, la, 954));
		eList.add(new Edge(sanfran, la, 338));
		
		MyGraph graph = new MyGraph(vList, eList);
      
      // test edge case: null vertex, null edge
      // MyGraph graph = new MyGraph(null, null);
         
		System.out.println(graph.vertices().toString());
      
      // test immutability
      sanfran = null;
      System.out.println(graph.vertices().toString());
      
      System.out.println(graph.edges().toString());
      System.out.println("Seattle's adjacents: " + graph.adjacentVertices(seattle).toString());
      System.out.println("Edge cost between " + seattle + " and " + la + ": " + graph.edgeCost(seattle, la));
	}
}
