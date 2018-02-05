//Alec Felt  1430374  allfelt@ucsc.edu
//lab6
@SuppressWarnings("overrides")
public class List<T> implements ListInterface<T> {

  private class Node<T> {//private class for linked List
    T item;
    Node<T> previous;//pointers to the previous and next nodes
    Node<T> next;

    Node(T x) {//constructor
      item = x;//initialization code
      next = null;
      previous = null;
    }
  }

  private Node<T> head;//reference to the beginning of the List
  private int numItems;//numItems in the List

  public List() {//constructor
    head = null;
    numItems = 0;
  }

  private Node<T> find(int index) {//private helper fuunction that returns a Node at the given indici
    Node<T> N = head;
    for(int i = 1; i < index; i++) {
      N = N.next;
    }
    return N;
  }

  public boolean isEmpty() {//checks if numItems == 0
    return(numItems == 0);
  }

  public int size() {//returns the numItems
    return numItems;
  }

  public T get(int index) throws ListIndexOutOfBoundsException {//returns the item at the corresponding index
    if(index<1 || index > numItems) {
      throw new ListIndexOutOfBoundsException(
        "List Error: get() called on invalid index: " + index);
    }
    Node<T> N = find(index);
    return N.item;
  }

  public void add(int index, T newItem) throws ListIndexOutOfBoundsException {//adds th newItem to the corresponding index
    if(index<1 || index>(numItems+1)) {
      throw new ListIndexOutOfBoundsException(
        "List Error: add() called on invalid index: " + index);
    }
    Node<T> N = new Node<T>(newItem);
    if(numItems == 0){//all of the cases we may encounter
      head = N;
    }else if(index == 1){
      N.next = head;
      head.previous = N;
      head = N;
    }else if(index == (numItems + 1)){
      Node<T> H = find(index-1);
      H.next = N;
      N.previous = H;
    }else{
      Node<T> H = find(index);
      H.previous.next = N;
      N.previous = H.previous;
      H.previous = N;
      N.next = H;
    }
    numItems++;//increment
  }

  public void remove(int index) throws ListIndexOutOfBoundsException {//removes the node at the corresponding index
    if(index<1 || index > numItems) {
      throw new ListIndexOutOfBoundsException(
        "List Error: add() called on invalid index: " + index);
    }

    Node<T> N = find(index);
    if(numItems == 1) {//all of the cases we may encounter
      removeAll();
    }else if(N == head){
      N.next.previous = null;
      head = N.next;
      numItems--;
    }else if(N.next == null){
      N.previous.next = null;
      numItems--;
    }else{
      N.next.previous = N.previous;
      N.previous.next = N.next;
      numItems--;
    }
  }

  public void removeAll() {//sets the head = null and numItems = 0, effectively emptying the list
    numItems = 0;
    head = null;
  }

  public String toString(){//puts a string together of all the items in the list
    StringBuffer sb = new StringBuffer();
    Node<T> N = head;

    for( ; N != null; N = N.next) {
      sb.append(N.item).append(" ");
    }
    return new String(sb);
  }

  //checks to see if the items in a List are the same as another List
  @SuppressWarnings("unchecked")
  public boolean equals(Object rhs) {
    boolean eq = false;
    List<T> R = null;
    Node<T> N = null;
    Node<T> M = null;
   
    if(this.getClass() == rhs.getClass()) {
      R = (List<T>) rhs;
      eq = ( this.numItems == R.numItems );

      N = this.head;
      M = R.head;
      
      while(eq && N!=null) {
        eq = (N.item == M.item);
        N = N.next;
	M = M.next;
      }
    }
    return eq;
  }

}
