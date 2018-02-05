//Alec Felt  1430374  allfelt@ucsc.edu
//lab7
//Dictionary.java

public class Dictionary implements DictionaryInterface{

	private class Node {//private class, what's in the BST
		String key;
		String value;
		Node left;
		Node right;

		Node(String k, String v) {//private class constructot
			key = k;
			value = v;
			left = null;
			right = null;
		}

	}

	

	Node root;//Dictionary class fields
	int numItems;

	public Dictionary() {//constructor
		root = null;
		numItems = 0;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////
// Private Helper Functions /////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
	//helper function to find any key in my bST and return the node
	//most important function
	private Node findKey(Node R, String k) {
		if(R == null || ((R.key).compareTo(k) == 0)) {//string comparisons in the conditional statements
			return R;
		}else if((R.key).compareTo(k) < 0) {
			return findKey(R.right, k);
		}else{
			return findKey(R.left, k);
		}
	}

	//finds the parent of a particular node N rooted at R
	private Node findParent(Node N, Node R) {
		Node P = null;
		if(N!=R) {
			P = R;
			while(P.left != N && P.right != N) {
				if((N.key).compareTo(R.key) < 0) {
					P = P.left;
				}else{
					P = P.right;
				}
			}
		}
		return P;
	}

	//returns the node farthest to the left of the subtree rooted ar R
	private Node findLeftmost(Node R) {
		Node L = R;
		if(L!=null) {
			for( ; L.left!=null; L = L.left ){
				;
			}
		}
		return L;
	}

	//helper function for toString
	//creates a string with the keys being in ascending order
	private void printInOrder(Node R, StringBuilder S) {
		if(R != null) {
			printInOrder(R.left, S);
			S.append(R.key + " " + R.value + "\n");
			printInOrder(R.right, S);

		}
	}



////////////////////////////////////////////////////////////////////////////////////////////////////
// ADT Operations //////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

	//returns true or false based on size of BST
	public boolean isEmpty() {
		return (numItems == 0);
	}

	//returns size of BST
	public int size() {
		return numItems;
	}

	//returns value of a specific key if it exists
	public String lookup(String key) {
		Node N = findKey(root, key);
		if(N == null) {
			return null;
		}
		return N.value;	
			
	}

	//inserts a key,value pair into the subtree
	public void insert(String key, String value) throws DuplicateKeyException {
		Node N, A, B;
		if(findKey(root, key) != null) {//making sure it doesnt exist
			throw new DuplicateKeyException(
				"Dictionary Error: Key already exists: " + key);
		}
		N = new Node(key, value);//new Node
		B = null;
		A = root;
		while(A!=null) {//gets the parent node of the new node
			B = A;
			if(key.compareTo(A.key) < 0) {
				A = A.left;
			}else{
				A = A.right;
			}
		}
		if(B == null) {//assigns the new node to either left or right of parent node
			root = N;
		}else if(key.compareTo(B.key) < 0) {
			B.left = N;
		}else{
			B.right = N;
		}
		numItems++;
		
	}

	//deletes a node from the BST
	public void delete(String key) throws KeyNotFoundException {
		Node N, P, S;
		N = findKey(root, key);
		if(N == null) {//making sure it exists
			throw new KeyNotFoundException(
				"Dictionary Error: No key found in dictionary: " + key);
		}
		if(N.left == null && N.right == null) {//if the node has no children
			if(N == root) {
				root = null;
			}else{
				P = findParent(N, root);
				if(P.right == N) {
					P.right = null;
				}else{
					P.left = null;
				}
				N = null;
			}
		}else if(N.right == null) {//if the node has no right child
			if(N == root) {
				root = N.left;
				N = null;
			}else{
				P = findParent(N, root);
				if(P.right == N) {
					P.right = N.left;
				}else{
					P.left = N.left;
				}
				N = null;
			}
		}else if(N.left == null) {//if the node has no left child
			if(N == root) {
				root = N.right;
				N = null;
			}else{
				P = findParent(N, root);
				if(P.right == N) {
					P.right = N.right;
				}else{
					P.left = N.left;
				}
				N = null;
			}
		}else{//if the node has 2 children
			S = findLeftmost(N.right);
			N.key = S.key;
			N.value = S.value;
			P = findParent(S, N);
			if(P.right == S) {
				P.right = S.right;
			}else{
				P.left = S.right;
			}
			S = null;
		}
		numItems--;
	}

	//resets the dictionary
	public void makeEmpty() {
		root = null;
		numItems = 0;
	}

	//prepares a string to be printed out of the keys in ascending order
	public String toString() {
		StringBuilder S = new StringBuilder ();
		printInOrder(root, S);
		return S.toString();
	}


}

	
