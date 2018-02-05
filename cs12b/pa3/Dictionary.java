//Alec Felt  1430374  allfelt@ucsc.edu

public class Dictionary implements DictionaryInterface {

   // private inner Node class
   private class Node {
      Node previous;
      String key; //doubly linked;
      String value;
      Node next;

      Node(String k, String v){//constructor
         previous = null;
	 key = k;
	 value = v;
         next = null;
      }
   }

   private Node head;    //starting point to find list
   private int numItems; //to make methods easier to implement

   public Dictionary(){ //object constructor
      head = null;
      numItems = 0;
   }

   private Node findKey(String key){//private helper method to help the public methods
      Node N = head;
      int i;
      for(i=1;i<=numItems;i++){//goes through whole list
         int a = key.compareTo(N.key);//string comparison
	 if(a == 0) {
            return N;
	 }else{
	    N = N.next;
	 }
      }
      return null;
   }	   


   public String lookup(String key){ //finds a key and return value
      Node N = findKey(key);         //returns null if suck key is found
      if(N != null) {
         return N.value;
      }else{
         return null;
      }
   }

   public boolean isEmpty(){ //just returns false or true based on the numItems
      return(numItems == 0);
   }

   public int size() { //just returns size of list
      return numItems;
   }

   public void insert(String key, String value) throws DuplicateKeyException {

      Node H = findKey(key);//throws exception key laready exists
      if(H != null){ 
         throw new DuplicateKeyException(
            "Dictionary Error: Key already exist: " + key);
      }else if(numItems >= 1){//if there is more than one item insert at the end
         H = head;
         for(int i=1; i<numItems; i++) {
            H = H.next;
         }
	 Node N = new Node(key, value);//makes sure all link element pointers
	 N.previous = H;               //are up to date
  	 H.next = N;
	 numItems++;
      }else{ //if the list is empty insert at head
	 head = new Node(key, value);
	 numItems++;
      }
   }
    
   public void delete(String key) throws KeyNotFoundException {
      Node H = findKey(key);//throws exception if key isn't found
      if(H == null){
         throw new KeyNotFoundException(
            "Dictionary Error: No key found in dictionary: " + key);
      }else if(H == head && numItems == 1){ //if the element is the head and
	 head = null;                       //there is only one element in the 
         numItems--;                        //list
      }else if(H == head){ //if the element is the head but there are other
	 head = H.next;    //elements as well
         head.previous = null;
	 numItems--;
      }else {//if the element isn't head and there are other elements
	 if(H.next != null){
            H.next.previous = H.previous;
	 }
	 H.previous.next = H.next;
	 numItems--;
      }
   }

   public void makeEmpty(){//resets the dictionary fields to empty object
      head = null;
      numItems = 0;
   }

   public String toString(){//puts all of the elements together with their
      String s = "";        //respective keys into one string
      Node N = head;
      
      for( ; N!=null; N=N.next) s += N.key + " " + N.value + "\n";
      return s;             //returns the string
   }
}
