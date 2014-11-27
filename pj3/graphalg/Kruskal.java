/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import list.*;
import dict.*;


/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g){
  	WUGraph t = new WUGraph();
    HashTableChained vertexHashtable = new HashTableChained(g.vertexCount());
    DisjointSets vertexSet = new DisjointSets(g.vertexCount());
  	LinkedQueue edgeQueue = new LinkedQueue();
  	Object[] vertices = g.getVertices();
  	for(int i = 0; i<vertices.length; i++){//run time?
  		t.addVertex(vertices[i]);
      vertexHashtable.insert(vertices[i], new Integer(i));
  		Neighbors neighbor = g.getNeighbors(vertices[i]);
      // System.out.println("Reached before loop");
      // System.out.println(neighbor);
      if(neighbor!= null){
        for(int x = 0; x<neighbor.neighborList.length; x++){
        //System.out.println("Inside loop");
        edgeQueue.enqueue(new Edge(vertices[i], neighbor.neighborList[x], neighbor.weightList[x]));
        //System.out.println("edge pair is : (" + vertices[i] + ", " + neighbor.neighborList[x] + ")");
      }
      }
  		
  	}
  	//System.out.println(edgeQueue);
  	ListSorts.mergeSort(edgeQueue);//sorts the queue using mergeSort
  	//System.out.println(edgeQueue);

  	//kruskals algorithm

   
    //loop through sorted queue of edges
    SListNode front = null;
    try{
      front = edgeQueue.frontNode();
    }
    catch(QueueEmptyException e){
      System.out.println(e);
    }
    
    int j = 0;
    while(j!=edgeQueue.size()){//iterate through the queue of edges
      if(((Edge)front.item).getObject1() != ((Edge)front.item).getObject2()){ //not self edges
        if(t.isEdge(((Edge)front.item).getObject1(), ((Edge)front.item).getObject2())){ //if that edge exists in the graph, do nothing
          
        }
        else{// if the edge does not exist in graph
          if(vertexSet.find(((Integer)((Entry)vertexHashtable.find(((Edge)front.item).getObject1())).value()).intValue()) == 
            vertexSet.find(((Integer)((Entry)vertexHashtable.find(((Edge)front.item).getObject2())).value()).intValue())){//if they have the same parent, path already exists

          }
          else{//the 2 objects do not have the same parent, add the edge to the graph
            t.addEdge(((Edge)front.item).getObject1(), ((Edge)front.item).getObject2(), ((Edge)front.item).getNumericWeight());
            vertexSet.union(vertexSet.find(((Integer)((Entry)vertexHashtable.find(((Edge)front.item).getObject1())).value()).intValue()),
             vertexSet.find(((Integer)((Entry)vertexHashtable.find(((Edge)front.item).getObject2())).value()).intValue()));
          }
         
        }
      }
      else{ // if 2 objects are equal, i.e. self edges
        if(t.isEdge(((Edge)front.item).getObject1(), ((Edge)front.item).getObject2())){//if self edge exists in graph, do nothing

        }
        else{//if the self edge does not exist in graph
          t.addEdge(((Edge)front.item).getObject1(), ((Edge)front.item).getObject2(), ((Edge)front.item).getNumericWeight());
         
        }
      }

      front = front.next;
      j++;
    }
    return t;


  }

  public static void main(String[] args){

  	WUGraph g = new WUGraph();
  	for(int i = 0; i<20; i++){
  		g.addVertex(new Integer(i));
  	}
    //System.out.println("The vertexHashtable is: " + g.vertexHashtable);


  	g.addEdge(new Integer(1), new Integer(4), 5);
    g.addEdge(new Integer(13), new Integer(13), 3);
    g.addEdge(new Integer(18), new Integer(18), 17);
  	g.addEdge(new Integer(1), new Integer(8), 4);
  	g.addEdge(new Integer(2), new Integer(9), 1);
  	g.addEdge(new Integer(5), new Integer(4), 5);
  	g.addEdge(new Integer(9), new Integer(2), 9);
  	g.addEdge(new Integer(7), new Integer(9), 20);
  	g.addEdge(new Integer(8), new Integer(11), 15);
    g.printString();
  	WUGraph t = minSpanTree(g);
    t.printString();






  }

}
