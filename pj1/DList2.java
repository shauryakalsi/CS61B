/* DList2.java */

/**
 *  A DList2 is a mutable doubly-linked list.  Its implementation is
 *  circularly-linked and employs a sentinel (dummy) node at the head
 *  of the list.
 */

public class DList2 {

  /**
   *  head references the sentinel node.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  public DListNode2 head;
  protected long size;

  /* DList2 invariants:
   *  1)  head != null.
   *  2)  For any DListNode2 x in a DList2, x.next != null.
   *  3)  For any DListNode2 x in a DList2, x.prev != null.
   *  4)  For any DListNode2 x in a DList2, if x.next == y, then y.prev == x.
   *  5)  For any DListNode2 x in a DList2, if x.prev == y, then y.next == x.
   *  6)  size is the number of DListNode2s, NOT COUNTING the sentinel
   *      (denoted by "head"), that can be accessed from the sentinel by
   *      a sequence of "next" references.
   */

  /**
   *  DList2() constructor for an empty DList2.
   */
  public DList2() {
    head = new DListNode2();
    head.item = null;
    head.next = head;
    head.prev = head;
    size = 0;
  }

  /**
   *  DList2() constructor for a one-node DList2.
   */
  // public DList2(int a) {
  //   head = new DListNode2();
  //   head.item = Integer.MIN_VALUE;
  //   head.next = new DListNode2();
  //   head.next.item = a;
  //   head.prev = head.next;
  //   head.next.prev = head;
  //   head.prev.next = head;
  //   size = 1;
  // }

  // /**
  //  *  DList2() constructor for a two-node DList2.
  //  */
  // public DList2(int a, int b) {
  //   head = new DListNode2();
  //   head.item = Integer.MIN_VALUE;
  //   head.next = new DListNode2();
  //   head.next.item = a;
  //   head.prev = new DListNode2();
  //   head.prev.item = b;
  //   head.next.prev = head;
  //   head.next.next = head.prev;
  //   head.prev.next = head;
  //   head.prev.prev = head.next;
  //   size = 2;
  // }

  /**
   *  insertFront() inserts an item at the front of a DList2.
   */
  public void insertFront(int[] o) {
    // Your solution here.
    DListNode2 p = head.prev;
    head.item = o;
    DListNode2 d = new DListNode2();
    d.item = null;
    d.next = head;
    head.prev = d;
    d.prev = p;
    p.next = d;
    head = d;
    size++;
  }

  public void removeNode(DListNode2 current) {
    DListNode2 nxt = current.next;
    current.prev.next = current.next;
    current.next.prev = current.prev;
    current = nxt;
    size--;
  }

  public void insertBefore(DListNode2 current, int[] run) {
    DListNode2 d = new DListNode2();
    DListNode2 oldPrev = current.prev;
    current.prev = d;
    d.next = current;
    oldPrev.next = d;
    d.prev = oldPrev;
    d.item = run;
    size++;
  }

  public void insertAfter(DListNode2 current, int[] run) {
    DListNode2 d = new DListNode2();
    DListNode2 oldNext = current.next;
    current.next = d;
    d.prev = current;
    oldNext.prev = d;
    d.next = oldNext;
    d.item = run;
    size++;
  }

  /**
   *  removeFront() removes the first item (and first non-sentinel node) from
   *  a DList2.  If the list is empty, do nothing.
   */
  public void removeFront() {
    // Your solution here.
    DListNode2 f = head.next.next;
    head.next = f;
    f.prev = head;
    if (size != 0) size--;
  }

  /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
   */
  public String toString() {
    String result = "[  ";
    DListNode2 current = head.next;
    while (current != head) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }

 }