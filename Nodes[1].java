package fordFulkerson;

class Nodes {
	String Name;
	int Weight;
	boolean visited;

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public int getweight() {
		return Weight;
	}

	public void setweight(int weight) {
		this.Weight = weight;
	}

		
	public Nodes(String Name) {
		this.Name = Name;
		visited = false;
	}

	public Nodes(String Name, int weight) {
		this.Name = Name;
		Weight = weight;
		visited = false;
	}

	
}