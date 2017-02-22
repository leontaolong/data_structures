import java.util.*;

/**
 * A representation of a graph.
 * Assumes that we do not have negative cost edges in the graph.
 */
public class MyGraph implements Graph {
    // you will need some private fields to represent the graph
    // you are also likely to want some private helper methods

    Map<Vertex, Set<Edge>> graphMap = new HashMap<>();

    /**
     * Creates a MyGraph object with the given collection of vertices
     * and the given collection of edges.
     * @param v a collection of the vertices in this graph
     * @param e a collection of the edges in this graph
     */
    public MyGraph(Collection<Vertex> v, Collection<Edge> e) { 	
    	for (Edge edge : e) {
    		Edge newEdge = new Edge(edge.getSource(), 
    								edge.getDestination(), 
    								edge.getWeight());
    		Vertex source = new Vertex(newEdge.getSource().getLabel());
    		if (graphMap.containsKey(source)) 
    			graphMap.get(source).add(newEdge);
    		else {
    			Set<Edge> edgeSet = new HashSet<>();
    			edgeSet.add(newEdge);
    			graphMap.put(source, edgeSet);
    		}
    	}
	

    }

    /** 
     * Return the collection of vertices of this graph
     * @return the vertices as a collection (which is anything iterable)
     */
    public Collection<Vertex> vertices() {
    	Set<Vertex> result = new HashSet<>();
    	for (Vertex v: graphMap.keySet()) {
    		result.add(new Vertex(v.getLabel()));
    	}
    	return result;
    }

    /** 
     * Return the collection of edges of this graph
     * @return the edges as a collection (which is anything iterable)
     */
    public Collection<Edge> edges() {
    	Set<Edge> result = new HashSet<>();
    	for (Set<Edge> edgeSet : graphMap.values()) {
    		for (Edge e: edgeSet) {
    			result.add(new Edge(e.getSource(), 
    								e.getDestination(), 
    								e.getWeight()));
    		}
    	}
    	return result;
    }

    /**
     * Return a collection of vertices adjacent to a given vertex v.
     *   i.e., the set of all vertices w where edges v -> w exist in the graph.
     * Return an empty collection if there are no adjacent vertices.
     * @param v one of the vertices in the graph
     * @return an iterable collection of vertices adjacent to v in the graph
     * @throws IllegalArgumentException if v does not exist.
     */
    public Collection<Vertex> adjacentVertices(Vertex v) {
    	Set<Vertex> result = new HashSet<>();
    	if (!graphMap.containsKey(v)) 
    		throw new IllegalArgumentException();
    	for (Edge e: graphMap.get(v))
    			result.add(e.getDestination());
    	return result;

    }

    /**
     * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed graph.
     * Assumes that we do not have negative cost edges in the graph.
     * @param a one vertex
     * @param b another vertex
     * @return cost of edge if there is a directed edge from a to b in the graph, 
     * return -1 otherwise.
     * @throws IllegalArgumentException if a or b do not exist.
     */
    public int edgeCost(Vertex a, Vertex b) {
    	if (!graphMap.containsKey(a) || !graphMap.containsKey(b))
    		throw new IllegalArgumentException();
    	for (Edge e: graphMap.get(a)) {
    		if (e.getDestination().equals(b))
    			return e.getWeight();
    	}
    	return -1;	
    }

}