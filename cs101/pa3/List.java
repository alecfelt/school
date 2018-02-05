//List.java  allfelt@ucsc.edu 
//Alec Felt     allfelt         pa3


public class List{

//private inner Node class

	private class Node{

		Node previous;
		Node next;
		Object entry;

		Node(Object i){
			previous = null;
			next = null;
			entry = i;
		}

	}

//List fields

	private Node front; //Node at the front of the list
	private Node back; //Node at the back of the list
	private Node cursor; //Node that the cursor is pointing to
	private	int length; //# of Nodes in the list
	private int index; //index of the Node pointed to by cursor

//List constructor

	List(){
	
		front = null;
		back = null;
		cursor = null;
		length = 0;
		index = -1;

	}

//helper finctions

	private void setIndex(){ //sets the index field with whatever index the cursor is on
		
		if(index != -1){
			if(length > 0){
				Node N = front; 
				int counter = 0;
				while(cursor != null){
					if(N == cursor){
						index = counter;
						break;
					}
				
					counter++;
					N = N.next;
				}
				if(cursor == null){
					index = -1;
				}
			}
		}
	}

	

//access functions

	int length(){ //returns length of List

		return length;

	}

	int index(){ //returns index of cursor

		return index;

	}

	Object front(){ //returns element of the front node of the list

		if(length != 0){
			return front.entry;
		}else{
			System.err.println("No elements in the List");
			System.exit(-1);
			return -1;
		}			
		
	}

	Object back(){ //returns the element at the back of the list

		if(length != 0){
			return back.entry;
		}else{
			System.err.println("No elements in the list");
			System.exit(-1);
			return -1;
		}
	}
	
	Object get(){ //returns the element of the node pointed to by the cursor

                if(length != 0 && cursor != null){
                        return cursor.entry;
                }else{
                        System.err.println("No elements in the list or cursor is null");
                        System.exit(-1);
                        return -1;
                }

        }

        public boolean equals(List L){ //checks to see if the current list is equal to list L

                if(L.length != this.length){ //checks the lengths of each List
                        return false;
                }

                if(L.length > 0){ //checks each element of the list and compares
                        this.moveFront(); //to the other list
                        L.moveFront();
                        while(cursor != null){ //comparisons
                                Object thisEntry = this.get();
                                Object LEntry = L.get();
                                if(thisEntry.equals(LEntry) == false){
				        return false;
                                }
                                L.moveNext(); //incrementing both lists
                                this.moveNext();
                        }
                }

                return true; //if the program makes it through all the above code the lists are equal

	}

	void clear(){ //sets List back to original state

                front = back = cursor = null;
                index = -1;
                length = 0;

        }

        void moveFront(){ //moves the cursor to the front node

                if(length > 0){
                        cursor = front;
                        index = 0;
                }else{
                        System.err.println("No elements in the List");
                        System.exit(-1);
                }

        }

        void moveBack(){ //moves the cursor to the back node

                if(length > 0){
                        cursor = back;
                        index = length - 1;
                }else{
                        System.err.println("No elements in the List");
                        System.exit(-1);
                }
        }

        void movePrev(){ //moves cursor to the previous node if the cursor is defined

                if(cursor == null){
                        ;
                }else if(cursor == front){
                        cursor = null;
                        index = -1;
                }else{
                        cursor = cursor.previous;
                        index--;
                }

        }

        void moveNext(){ //moves the cursor to the next node if the cursor is defined

                if(cursor == null){
                        ;
                }else if(cursor == back){
                        cursor = null;
                        index = -1;
                }else{
                        cursor = cursor.next;
                        index++;
                }

        }

        void prepend(Object O){ //inserts data at the front of the list

                Node N = new Node(O);

                if(length == 0){
                        front = back = N;
                }else{
                        front.previous = N;
                        N.next = front;
                        front = N;
                        setIndex();
                }

                length++;

        }
     
        void append(Object O){ //inserts data at the end of the list

                Node N = new Node(O);

                if(length == 0){
                        front = back = N;
                }else{
                        back.next = N;
                        N.previous = back;
                        back = N;
                }

                length++;

        }

        void insertBefore(Object O){ //inserts data before the cursor node

                if(length != 0 && index != -1){

                        if(cursor == front){
                                 prepend(O);
                        }else{
                                Node N = new Node(O);
                                cursor.previous.next = N;
                                N.previous = cursor.previous;
                                cursor.previous = N;
                                N.next = cursor;
                        	setIndex();
                        	length++;
			}

                }else{
                        System.err.println("No elements in the List and/or cursor is undefined");
                        System.exit(-1);
                }

        }

        void insertAfter(Object O){ //inserts data after the cursor node

                if(length != 0 && index != -1){

                        if(cursor == back){
                                append(O);
                        }else{
                                Node N = new Node(O);
                                cursor.next.previous = N;
                                N.next = cursor.next;
                                cursor.next = N;
                                N.previous = cursor;
                        	setIndex();
                        	length++;
			}
                }else{
                        System.err.println("No elements in the List and/or the cursor is undefined");
                        System.exit(-1);
                }

        }

        void deleteFront(){ //inserts data before the cursor node

                if(length != 0){

                        front = front.next;
                        front.previous = null;
                        length--;
                        if(cursor == front){
                                index = -1;
                        }
                        setIndex();

                }else{
                        System.err.println("No elements in the List");
                        System.exit(-1);
                }

        }

        void deleteBack(){ //deletes the back node if length is > 0

                if(length != 0){

                        back = back.previous;
                        back.next = null;
                        length--;
                        if(cursor == back){
                                index = -1;
                        }
                        setIndex();

                }else{
                        System.err.println("No elements in the List");
                        System.exit(-1);
                }

        }

        void delete(){ //deletes the cursor node if the cursor is defined

                if(length != 0 && cursor != null){

                        if(length == 1){
                                front = back = cursor = null;
                                index = -1;
                        }else if(cursor == front){
                                cursor = null;
                                index = -1;
                                front = front.next;
                                front.previous = null;
                        }else if(cursor == back){
                                cursor = null;
                                index = -1;
                                back = back.previous;
                                back.next = null;
                        }else{
                                cursor.previous.next = cursor.next;
                                cursor.next.previous = cursor.previous;
                                cursor = null;
                                index = -1;
                        }

                        length--;

                }else{
                        System.err.println("No elements in the List and/or cursor is undefined");
                        System.exit(-1);
                }

        }

//Other methods

        public String toString(){ //overrides toString method and returns the elements of the list

                String s = "";

                if(length != 0){

                        Node N = front;
                        while(N != null){
                                s += (N.entry.toString());
                                N = N.next;
                        }

                }

                return s;

        }


}
