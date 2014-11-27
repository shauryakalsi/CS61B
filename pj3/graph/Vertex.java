package graph; 
import list.*;

class Vertex {

	public DList edgeList;
	protected int degree;
	protected Object identity;

	public Vertex(Object i) {
		edgeList = new DList();
		degree = 0;
		identity = i;
	}

	//returns the object
	public Object getIdentity(){
		return identity;
	}
	

	//returns the degree of a vertex

	public int getDegree() {
		return degree;
	}


	//increases the degree of a vertex by 1 when an edge is added

	public void increaseDegree(){
		degree += 1;
	}
	//decreases the degree of a vertex by 1 when an edge is removed
	public void decreaseDegree(){
		degree -= 1;
	}

	//inserts an edge to the DList edgeList
	public void insertEdge(Edge edge){
		edgeList.insertBack(edge);
	}

	// //deletes an edge 
	// public void deleteEdge(Edge edge){
	// 	edgeList.remove(edge);
	// }

	//returns Edge edge
	public void getEdge(Edge edge){}

	public String toString(){
		String s = new String();
		s = "[" + identity.toString() + ", " + degree + "]";
		return s;
	}

}




