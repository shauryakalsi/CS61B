/* WUGraph.java */

package graph;
import java.util.Arrays;
import list.*;
import dict.*;
/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {

  protected HashTableChained vertexHashTable;
  protected HashTableChained edgeHashTable;
  protected DList vertexList;
  protected int numberOfEdges;




  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph() {
    vertexHashTable = new HashTableChained(19);
    edgeHashTable = new HashTableChained(19);
    vertexList = new DList();
    numberOfEdges = 0;
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  

  

  public int vertexCount() {
    return vertexList.length();
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount() {
    return numberOfEdges;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
    Object[] vertices = new Object[this.vertexCount()];
    DListNode front = vertexList.front();
    int i = 0;
    while (front != null && front.item != null) {
      vertices[i] = ((Vertex) front.item).getIdentity();
      front = front.next();
      i++;
    }
    return vertices;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex) {
    if (vertexHashTable.find(vertex) != null) {
      System.out.println("Vertex already exists");
      return;
    }
    vertexList.insertBack(new Vertex(vertex));
    DListNode back = vertexList.back();
    vertexHashTable.insert(vertex, back);
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
    Entry vertexToRemove = vertexHashTable.find(vertex);
    if( vertexToRemove == null){ // O(1)
      System.out.println("Vertex does not exist"); 
      return;
    }
    else{

      DListNode toDel = (DListNode)vertexToRemove.value();
      DList edgeList = ((Vertex)toDel.item).edgeList;
      DListNode front = edgeList.front();
      while(front != null && front.item != null){ //front.next().item? 
        this.removeEdge(((Vertex)((DListNode)((Edge)front.item).getParent1()).item).getIdentity(), ((Vertex)((DListNode)((Edge)front.item).getParent2()).item).getIdentity());
        front = front.next();
      }
      vertexList.remove(toDel);
      vertexHashTable.remove(vertex);
    }
    
    // go inside this node to the vertex and delete adjacent edges from the edge Dlist and the edge hash table
    // go to other vertices who share this edge and delete that
    // after all edges are deleted

    
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex){
    if(vertexHashTable.find(vertex) != null){
      return true;
    }
    return false;
  }

  // /**
  //  * degree() returns the degree of a vertex.  Self-edges add only one to the
  //  * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
  //  * of the graph, zero is returned.
  //  *
  //  * Running time:  O(1).
  //  */
  public int degree(Object vertex){
    if(isVertex(vertex) == false){
      return 0;
    }
    return ((Vertex)((DListNode)vertexHashTable.find(vertex).value()).item).getDegree(); // hashtable -> find returns an entry object -> value points to dlistnode -> that is vertex which holds a degree.
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex){
    Entry result = vertexHashTable.find(vertex);
    int degree = ((Vertex)((DListNode)result.value()).item).getDegree();
    if(result == null || degree == 0){
      return null;
    }
    
      Neighbors neighbor = new Neighbors();
      neighbor.neighborList = new Object[degree];
      neighbor.weightList = new int[degree];
      DListNode front = ((Vertex)((DListNode)result.value()).item).edgeList.front();
       
      for(int i = 0; i<degree; i++){
         Object v1 = ((Vertex)((DListNode)((Edge)front.item).getParent1()).item).getIdentity();
        Object v2 = ((Vertex)((DListNode)((Edge)front.item).getParent2()).item).getIdentity();
    
        neighbor.weightList[i] = ((Edge)front.item).getWeight();
        if(v1 == vertex && v2 == vertex){
          neighbor.neighborList[i] = v2;
        }
        else{
          if(v1 == vertex){
            neighbor.neighborList[i] = v2;
          }
          else{
             neighbor.neighborList[i] = v1;
          }
         
        }

        front = front.next();
      }
    
    return neighbor;
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  

  public void addEdge(Object u, Object v, int weight){
    Edge edge1 = null;
    Edge edge2 = null;
    Entry result = edgeHashTable.find(new VertexPair(u, v));
    if(result != null){
      if(u != v){
        ((Edge)((DListNode)result.value()).item).getPartner().setWeight(weight);
        
      }
      
      ((Edge)((DListNode)result.value()).item).setWeight(weight);
      return;
    }
    
    Entry node1 = vertexHashTable.find(u);
    Entry node2 = vertexHashTable.find(v);
    if(u == v){
      if(node1 != null && node2!= null){ ///

      edge1 = new Edge(u, v, weight);
      edge1.setPartner(edge1);
      
      ((Vertex)((DListNode)node1.value()).item).edgeList.insertBack(edge1);
      edge1.myNode = ((Vertex)((DListNode)node1.value()).item).edgeList.back();
      edge1.parent1 = (DListNode)node1.value();
      edge1.parent2 = (DListNode)node1.value();
     }

    }
    else{
      if(node1 == null || node2 == null){
      return;
    }
      else{
        edge1 = new Edge(u, v, weight);
        edge2 = new Edge(u, v, weight);
        edge1.setPartner(edge2);
        edge2.setPartner(edge1);

        ((Vertex)((DListNode)node1.value()).item).edgeList.insertBack(edge1);
        edge1.myNode = ((Vertex)((DListNode)node1.value()).item).edgeList.back();
        edge1.parent1 = (DListNode)node1.value();
        edge1.parent2 = (DListNode)node2.value();

        ((Vertex)((DListNode)node2.value()).item).edgeList.insertBack(edge2);
        edge2.myNode = ((Vertex)((DListNode)node2.value()).item).edgeList.back();
        edge2.parent1 = (DListNode)node2.value();
        edge2.parent2 = (DListNode)node1.value();
        
        ((Vertex)((DListNode)node2.value()).item).increaseDegree();
      }
    }
    ((Vertex)((DListNode)node1.value()).item).increaseDegree();
    edgeHashTable.insert(edge1.edge, edge1.myNode);

    numberOfEdges++;

  }


  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v){
    
    Entry resultEdge = edgeHashTable.find(new VertexPair(u, v));
    Entry resultVertex1 = vertexHashTable.find(u);
    Entry resultVertex2 = vertexHashTable.find(v);
    
    if(resultVertex1 == null || resultVertex2 == null){
      return;
    }
    if(resultEdge == null){
      return;
    }
    else{
      DListNode edgeNode = (DListNode)resultEdge.value();
       Vertex vertex1 = (Vertex)((DListNode)resultVertex1.value()).item;
       Vertex vertex2 = (Vertex)((DListNode)resultVertex2.value()).item;
      if(u != v){
        vertex2.edgeList.remove(((Edge)edgeNode.item).getPartner().myNode);
        vertex2.decreaseDegree();
      }
      
        vertex1.edgeList.remove(edgeNode);
        vertex1.decreaseDegree();
        
       
        edgeHashTable.remove(new VertexPair(u, v));

    }
    numberOfEdges--;
  }

  // /**
  //  * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
  //  * if (u, v) is not an edge (including the case where either of the
  //  * parameters u and v does not represent a vertex of the graph).
  //  *
  //  * Running time:  O(1).
  //  */
  public boolean isEdge(Object u, Object v){
    if(vertexHashTable.find(u) == null || vertexHashTable.find(v) == null){
      return false;
    }
    if(edgeHashTable.find(new VertexPair(u, v)) != null) { // assuming that edge is vertex pair.
      return true; // this function might take more than O(1)
    }
    return false; // assuming that find() finds the vertex, and if its not in the table then it is not an edge
  }       // and also that it doesnt represent a vertex of the graph.
    

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v){
    if(vertexHashTable.find(u) == null || vertexHashTable.find(v) == null){
      return 0;
    }
    Entry result = edgeHashTable.find(new VertexPair(u, v));
    if(result == null){
      return 0;
    }
    else{
      return ((Edge)((DListNode)result.value()).item).getWeight();
    }
    

  }

  public void printString(){
    DListNode next = vertexList.front();

    while(next != null && next.item!= null){
      System.out.print(((Vertex)next.item).getIdentity() + " ----> ");
      System.out.print(((Vertex)next.item).edgeList);
      System.out.println();
      if(next.next() != null){
        next = next.next();
      }else {
        break;
      }
    }
  }
  public static void main(String[] args){
    //numbers in the range of 0-100
    
    // WUGraph graph = new WUGraph();
    // for(int i = 0; i< 60; i++){
    //   // System.out.println("Entry number: " + i); 
    //   // System.out.println(graph.vertexHashTable); 
    //   graph.addVertex(new Integer((int)(Math.random()*100)));
    // }

    // // System.out.println("Just before resizing");

    // // System.out.println(graph.vertexHashTable); 
    
    // // graph.addVertex(new Integer((int)(Math.random()*100)));

    // // System.out.println("Should resize");
    // // System.out.println(graph.vertexHashTable);
    // // System.out.println(graph.vertexHashTable.getSize());

    // System.out.println(graph.edgeHashTable);
    // DListNode front = graph.vertexList.front();
    
    // while(front.next().item != null){
    //   graph.addEdge(((Vertex)front.item).getIdentity(), ((Vertex)front.next().item).getIdentity(), (int)(Math.random()*10));
    //   System.out.println("Number of edges:" + graph.numberOfEdges);
    //   front = front.next();

    // }

    // System.out.println(graph.edgeHashTable);


    //Tests for degree?

    //Tests for weight?

    //Tests for neighbors?

    //Tests for removeEdge???????

  //}
    Integer[] randomArray = new Integer[60];
    WUGraph graph = new WUGraph();

    //test for addVertex
    for(int i = 0; i< 60; i++){
      // System.out.println("Entry number: " + i); 
      // System.out.println(graph.vertexHashTable); 
      Integer integer = new Integer(i*100);
      //System.out.println("Adding " + integer);
      graph.addVertex(integer);
      
        randomArray[i] = integer;
      
      
      //System.out.println(graph.vertexHashTable);
    }
    //tests for vertexCount()
    // System.out.println("VertexCount should be 60: " + graph.vertexCount());
    // graph.removeVertex(new Integer(7000));
    // System.out.println("VertexCount should be 60: " + graph.vertexCount());

    //creates edge between Integer(100) and Integer(400)
    graph.addEdge(randomArray[1], randomArray[4], 3);
    graph.addEdge(randomArray[1], randomArray[2], 4);
    graph.addEdge(randomArray[1], randomArray[15], 1);
    graph.addEdge(randomArray[1], randomArray[1], 10);


    System.out.println(((Vertex)((DListNode)graph.vertexHashTable.find(randomArray[1]).value()).item).edgeList);
    // System.out.println(graph.edgeHashTable);
    // System.out.println("Edgecount Should be 4: " + graph.edgeCount());
    // System.out.println(((Vertex)((DListNode) graph.vertexHashTable.find(randomArray[1]).value()).item).edgeList);
    // System.out.println(((Vertex)((DListNode) graph.vertexHashTable.find(randomArray[4]).value()).item).edgeList);
    // System.out.println(((Vertex)((DListNode) graph.vertexHashTable.find(randomArray[2]).value()).item).edgeList);
    // System.out.println(((Vertex)((DListNode) graph.vertexHashTable.find(randomArray[15]).value()).item).edgeList);

    // System.out.println("Should remove edge(100,400)");
    // graph.removeEdge(randomArray[1], randomArray[4]);
    // System.out.println(((Vertex)((DListNode) graph.vertexHashTable.find(randomArray[1]).value()).item).edgeList);
    // System.out.println(((Vertex)((DListNode) graph.vertexHashTable.find(randomArray[4]).value()).item).edgeList);

    // System.out.println("Edgecount should be 3:" + graph.edgeCount());
    //test for removeVertex



    //tests for weight
    // System.out.println("Weight for edge (100, 400) should be 3: " + graph.weight(randomArray[1], randomArray[4]));
    // System.out.println("Weight for edge (100, 400) should be 3: " + graph.weight(new Integer(100), new Integer(400)));
    //  System.out.println("Weight for edge (400, 400) (not in graph)  should be 0: " + graph.weight(randomArray[4], randomArray[4]));
    //  System.out.println("Weight for edge (200, 8000) (not in graph) should be 0: " + graph.weight(randomArray[2], new Integer(8000)));


    //tests for degree
    // System.out.println("Degree for vertex 100 should be 4: " + graph.degree(new Integer(100)));
    //  System.out.println("Degree for vertex 1500 should be 1: " + graph.degree(new Integer(1500)));
    
    // System.out.println("Degree for vertex 8000 (not in graph) should be 0:" + graph.degree(new Integer(8000)));


    // System.out.println(graph.vertexHashTable);
    // for(int i = 0; i<60; i++){
    //   System.out.println("Removing " + randomArray[i]);
    //   graph.removeVertex(randomArray[i]);
    //   System.out.println(graph.vertexHashTable);
    // }
    //Test for getVertices
    // Object[] vertArray = graph.getVertices();
    // for(int i = 0; i<vertArray.length; i++){
    //   System.out.println(vertArray[i]);
    // }
    //System.out.println(graph.vertexHashTable);

    //Tests for isEdge and isVertex
    // System.out.println("isEdge(100, 400): (should be true) " + graph.isEdge(new Integer(100), new Integer(400)));
    // System.out.println("isEdge(100, 500): (should be false) " + graph.isEdge(new Integer(100), new Integer(500)));
    // System.out.println("isEdge(300, 5000): (should be false) " + graph.isEdge(new Integer(300), new Integer(5000)));



    // System.out.println("isVertex(100): (should be true) " + graph.isVertex(new Integer(100)));
    // System.out.println("isVertex(-100): (should be false) " + graph.isVertex(new Integer(-100)));
    // System.out.println("isVertex(10000): (should be false) " + graph.isVertex(new Integer(10000)));
    
    //Tests for Neighbors
    // Neighbors neigh = graph.getNeighbors(randomArray[1]);
    // for(int i = 0; i<neigh.neighborList.length; i++){
    //   System.out.println("Neighbor is: " + neigh.neighborList[i] + " Weight is: " + neigh.weightList[i] );
    // }
    // System.out.println("Should be [400, 3], [200, 4], [1500, 1], [100, 10]");

  }














}
