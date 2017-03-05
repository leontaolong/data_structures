/**
 * Representation of a graph vertex
 */
public class Vertex implements Comparable<Vertex> {
	private final String label;   // label attached to this vertex
	private int cost; // current lowest cost used in finding shortest path
	private Vertex source; // current source vertex used in finding shortest path
	private boolean known; // if it's visited, used in finding shortest path

	/**
	 * Construct a new vertex
	 * @param label the label attached to this vertex
	 */
	public Vertex(String label) {
		if(label == null)
			throw new IllegalArgumentException("null");
		this.label = label;
		cost = Integer.MAX_VALUE;
	}
	
	/**
	 * Get a vertex label
	 * @return the label attached to this vertex
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * A string representation of this object
	 * @return the label attached to this vertex
	 */
	public String toString() {
		return label;
	}

	//auto-generated: hashes on label
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	//auto-generated: compares labels
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Vertex other = (Vertex) obj;
		if (label == null) {
                    return other.label == null;
		} else {
		    return label.equals(other.label);
		}
	}
	
	/**
	 * set the current lowest cost of the vertex
	 * @param the current lowest cost of the Vertex
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	/**
	 * @return the current cost of the vertex
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * set the souce as given
	 * @param a vertex as source
	 */
	public void setSource(Vertex source) {
		if (source != null)
			this.source = new Vertex(source.getLabel());
	}
	
	/**
	 * @return the current source of the vertex
	 * @return null as default if not specified
	 */
	public Vertex getSource() {
		if (source == null)
			return null;
		return new Vertex(source.getLabel());
	}
	
	/**
	 * set known to be true
	 */
	public void setKnown() {
		known = true;
	}
	
	/**
	 * @return true is vertex is known, false otherwise
	 */
	public boolean isKnown() {
		return known;
	}
	
	/**
	 * the compare method for comparing to Vertex based on their current lowest cost
	 */
	@Override
	public int compareTo(Vertex o) {
		return getCost() - o.getCost();
	}
}