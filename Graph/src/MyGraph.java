import java.util.*;

/**
 * A representation of a graph. Assumes that we do not have negative cost edges
 * in the graph.
 */
public class MyGraph implements Graph {
	// you will need some private fields to represent the graph
	// you are also likely to want some private helper methods

	private Map<Vertex, Set<Edge>> graphMap;
	/**
	 * Creates a MyGraph object with the given collection of vertices and the
	 * given collection of edges.
	 * 
	 * @param v
	 *            a collection of the vertices in this graph
	 * @param e
	 *            a collection of the edges in this graph
	 * @throw IllegalArgumentException if v and e are null,
	 * 		source or destination are not in the collection of vertex,
	 * 		any edge weight is negative,
	 * 		or the directed edge with different weight exists 
	 */
	public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
		if (v == null || e == null)
			throw new IllegalArgumentException();
		graphMap = new HashMap<>();
		
		for (Vertex vertex : v)
			graphMap.put(new Vertex(vertex.getLabel()), new HashSet<Edge>());
		for (Edge edge: e) {
			if (!graphMap.keySet().contains(edge.getSource()) || !graphMap.keySet().contains(edge.getDestination()) || edge.getWeight() < 0)
				throw new IllegalArgumentException();
			Vertex source = edge.getSource();
			for (Edge adjEdge: graphMap.get(source)) {
				if (adjEdge.getDestination().equals(edge.getDestination()) && adjEdge.getWeight() != edge.getWeight())
					throw new IllegalArgumentException();
			}
			graphMap.get(source).add(new Edge(edge.getSource(), edge.getDestination(), edge.getWeight()));
		}
	}

	/**
	 * Return the collection of vertices of this graph
	 * 
	 * @return the vertices as a collection (which is anything iterable)
	 */
	public Collection<Vertex> vertices() {
		Set<Vertex> result = new HashSet<>();
		for (Vertex v : graphMap.keySet()) {
			result.add(new Vertex(v.getLabel()));
		}
		return result;
	}
	/**
	 * Return the collection of edges of this graph
	 * 
	 * @return the edges as a collection (which is anything iterable)
	 */
	public Collection<Edge> edges() {
		Set<Edge> result = new HashSet<>();
		for (Set<Edge> edgeSet : graphMap.values()) {
			for (Edge e : edgeSet)
				result.add(new Edge(e.getSource(), e.getDestination(), e.getWeight()));
		}
		return result;
	}

	/**
	 * Return a collection of vertices adjacent to a given vertex v. i.e., the
	 * set of all vertices w where edges v -> w exist in the graph. Return an
	 * empty collection if there are no adjacent vertices.
	 * 
	 * @param v
	 *            one of the vertices in the graph
	 * @return an iterable collection of vertices adjacent to v in the graph
	 * @throws IllegalArgumentException
	 *             if v is null or does not exist
	 */
	public Collection<Vertex> adjacentVertices(Vertex v) {
		if (v == null || !graphMap.containsKey(v))
			throw new IllegalArgumentException();
		Set<Vertex> result = new HashSet<>();
		for (Edge e : graphMap.get(v))
			result.add(new Vertex(e.getDestination().getLabel()));
		return result;
	}

	/**
	 * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed
	 * graph. Assumes that we do not have negative cost edges in the graph.
	 * 
	 * @param a
	 *            one vertex
	 * @param b
	 *            another vertex
	 * @return cost of edge if there is a directed edge from a to b in the
	 *         graph, return -1 otherwise.
	 * @throws IllegalArgumentException
	 *             if either a or b is null and does not exist.
	 */
	public int edgeCost(Vertex a, Vertex b) {
		if (!graphMap.containsKey(a) || !graphMap.containsKey(b))
			throw new IllegalArgumentException();
		for (Edge e : graphMap.get(a)) {
			if (e.getDestination().equals(b))
				return e.getWeight();
		}
		return -1;
	}
}