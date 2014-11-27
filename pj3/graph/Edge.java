package graph;
import list.*;

class Edge {

	protected Edge partner;
	protected VertexPair edge;
	protected int weight;
	protected DListNode myNode;
	protected DListNode parent1;
	protected DListNode parent2;

	public Edge(Object u, Object v, int weight) {
		
		edge = new VertexPair(u, v);
		this.weight = weight;
	}

	public Edge getPartner(){
		return partner;
	}
	public void setPartner(Edge partner) {
		this.partner = partner;
	}

	public void setWeight(int weight){
		this.weight = weight;
	}
	public int getWeight() {
		return weight;
	}

	public DListNode getParent1(){
		return parent1;
	}

	public DListNode getParent2(){
		return parent2;
	}

	public DListNode getMyNode(){
		return myNode;
	}

	public String toString(){
		return "< " + ((Vertex)parent1.item).getIdentity() + ", " + ((Vertex)parent2.item).getIdentity() + " (" + weight + ") " + " >";
	}

}