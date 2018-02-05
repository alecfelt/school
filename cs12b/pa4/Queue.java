//Alec Felt  1430374  allfelt@ucsc.edu
//Queue.java

public class Queue implements QueueInterface {

	
	Node front;//public variables or Queue
	Node back;
	int numItems;

	
	public void Queue() {//constructor
		front = null;
		back = null;
		numItems = 0;
	}

	
	private class Node {//private class for linked list functionality

		Node next;//doubly linked
		Node previous;
		Object item;
		Node(Object newItem) {//constructor
			item = newItem;
			next = null;
			previous = null;
		}

	}
	
	
	public boolean isEmpty() {//is the queue empty?
		return(numItems == 0);
	}

	
	public int length() {//how many items does the queue contain?
		return numItems;
	}

	
	public void enqueue(Object newItem) {//puts an object onto the back of the linked list
		Node N = new Node(newItem);
		if(isEmpty()) {
			front = N;
			back = front;
			numItems++;
		} else {
			back.next = N;
			N.previous = back;
			back = N;
			numItems++;
		}
	}

	
	public Object dequeue() throws QueueEmptyException {//deletes and returns the object at the front of the queue
		Node N;
		if(isEmpty()) {
			throw new QueueEmptyException("cannot dequeue() empty queue");
		} else if(length() == 1) {
			N = front;
			back = front = null;	
		} else {
			N = front;
			front.next.previous = null;
			front = front.next;
		}
		numItems--;
		return N.item;
	}

	
	public Object peek() throws QueueEmptyException {//returns the item in the first node at the front of the queue
		if(isEmpty()) {
			throw new QueueEmptyException("cannot peek() empty queue");
		} else {
			return front.item;
		}
	}

	
	public void dequeueAll() throws QueueEmptyException {//dequeues the whole queue
		if(isEmpty()) {
			throw new QueueEmptyException("cannot dequeueAll() empty queue");
		} else {
			back = front = null;
			numItems = 0;
		}
	}	
	
	
	public String toString() {//returns a formatted string of all of the items in each node in the queue
		String S = "";
		if(isEmpty()) {
			;
		} else {
			Node N = front;
			while(N != null) {
				S += N.item.toString() + " ";
				N = N.next;
			}
		}
		return S;
	}





}
