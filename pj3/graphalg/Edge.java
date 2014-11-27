package graphalg;
import list.*;
import dict.*;



public class Edge{

	protected Object object1;
	protected Object object2;
	protected int weight;
	//protected boolean visited;

	public Edge(Object u, Object v, int weight){
		object1 = u;
		object2 = v;
		this.weight = weight;
		//visited = false;
	}

	public Object getObject1(){
		return object1;
	}

	public Object getObject2(){
		return object2;
	}

	public Object getWeight(){
		return new Integer(this.weight);
	}

	public int getNumericWeight(){
		return this.weight;
	}

	// public boolean getVisited(){
	// 	return visited;
	// }

	// public void setVisited(boolean visit){
	// 	visited = visit;
	// }


  public String toString(){
    return "< " + object1.toString() + ", " + object2.toString() + " (" + weight + ") >";
  }
}