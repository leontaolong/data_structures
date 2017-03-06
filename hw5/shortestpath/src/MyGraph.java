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
	 * @throw IllegalArgumentException if v and e are null, source or
	 *        destination are not in the collection of vertex, any edge weight
	 *        is negative, or the directed edge with different weight exists
	 */
	public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
		if (v == null || e == null)
			throw new IllegalArgumentException();
		graphMap = new HashMap<>();

		for (Vertex vertex : v)
			graphMap.put(new Vertex(vertex.getLabel()), new HashSet<Edge>());
		for (Edge edge : e) {
			if (!graphMap.keySet().contains(edge.getSource()) || !graphMap.keySet().contains(edge.getDestination())
					|| edge.getWeight() < 0)
				throw new IllegalArgumentException();
			Vertex source = edge.getSource();
			for (Edge adjEdge : graphMap.get(source)) {
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

	/**
	 * Returns the shortest path from a to b in the graph, or null if there is
	 * no such path. Assumes all edge weights are nonnegative. Uses Dijkstra's
	 * algorithm.
	 * 
	 * @param a
	 *            the starting vertex
	 * @param b
	 *            the destination vertex
	 * @return a Path where the vertices indicate the path from a to b in order
	 *         and contains a (first) and b (last) and the cost is the cost of
	 *         the path. Returns null if b is not reachable from a.
	 * @throws IllegalArgumentException
	 *             if a or b does not exist.
	 */
	public Path shortestPath(Vertex a, Vertex b) {
		if (!graphMap.keySet().contains(a) || !graphMap.keySet().contains(b))
			throw new IllegalArgumentException();
		List<Vertex> pathList = new ArrayList<>();
		Vertex source = new Vertex(a.getLabel());
		Vertex dest = new Vertex(b.getLabel());
		if (source.equals(dest)) {
			pathList.add(source);
			return new Path(pathList, 0);
		}
		return Dijkstra(source, dest);
	}

	/**
	 * uses Dijkstra's algorithms to find the shortest path (helper method)
	 * 
	 * @param a
	 *            the starting vertex
	 * @param b
	 *            the destination vertex
	 * @return a Path where the vertices indicate the path from a to b in order
	 *         and contains a (first) and b (last) and the cost is the cost of
	 *         the path. Returns null if b is not reachable from a.
	 */
	private Path Dijkstra(Vertex source, Vertex dest) {
		Map<String, Vertex> vMap = new HashMap<>(); // manage all vertices
		Queue<Vertex> prioQ = new PriorityQueue<>(); // get vertex of lowest
														// cost
		Vertex destFound = null;
		// put all vertices in the vertex map and priority queue, set start's
		// cost to 0
		for (Vertex v : graphMap.keySet()) {
			Vertex newV = new Vertex(v.getLabel());
			if (newV.equals(source))
				newV.setCost(0);
			prioQ.add(newV);
			vMap.put(newV.getLabel(), newV);
		}
		Vertex known;
		while (!prioQ.isEmpty()) {
			known = prioQ.poll();
			known.setKnown();
			// if the polled out vertex equals destination
			// and it's been visited from starting point,
			// meaning the cost has been updated, result is found and break the
			// searching loop
			if (known.equals(dest) && known.getCost() != Integer.MAX_VALUE) {
				destFound = known;
				break;
			}
			// update each adjacent vertices if necessary in the vertex Map and
			// priority queue
			for (Edge e : graphMap.get(known)) {
				Vertex adjVertex = vMap.get(e.getDestination().getLabel());
				if (!adjVertex.isKnown() && adjVertex.getCost() > known.getCost() + e.getWeight()) {
					prioQ.remove(adjVertex);
					vMap.remove(adjVertex);
					adjVertex.setCost(known.getCost() + e.getWeight());
					adjVertex.setSource(known);
					vMap.put(adjVertex.getLabel(), adjVertex);
					prioQ.add(adjVertex);
				}
			}
		}
		if (destFound == null) {
			return null;
		}
		return new Path(makePathList(vMap, destFound), destFound.getCost());
	}

	/**
	 * make a list of vertices which marks each vertex along the path way in the
	 * order of from starting to end point
	 * 
	 * @param vMap
	 *            the current collection of Vertices with updated source and
	 *            cost info
	 * @param destFound
	 *            the end point Vertex with updated source and cost info
	 * @return a List of Vertices marking the path of the shortest path (in the
	 *         order of from starting to end)
	 */
	private List<Vertex> makePathList(Map<String, Vertex> vMap, Vertex destFound) {
		List<Vertex> result = new ArrayList<>();
		Vertex back = destFound;
		while (back != null) {
			result.add(back);
			if (back.getSource() == null)
				back = null;
			else {
				Vertex sourceV = vMap.get(back.getLabel()).getSource();
				back = vMap.get(sourceV.getLabel());
			}
		}
		Collections.reverse(result); // reverse the order so it's from start to
										// end
		return result;
	}
}