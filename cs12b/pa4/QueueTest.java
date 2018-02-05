//QueueTest.java
//Alec Felt  1430374  allfelt@ucsc.edu
public class QueueTest {
	
	public static void main(String[] args) {
		if(false) {

		Queue Q = new Queue();
		Object J = new Job(2, 4);
		Object G = new Job(3, 5);
		Q.enqueue(J);
		Q.enqueue(G);
		System.out.println(Q.toString());
		System.out.println(Q.peek());
		System.out.println(Q.toString());
		Q.dequeue();
		System.out.println(Q.toString());
		System.out.println(Q.isEmpty());
		}
		String[] str = {"yo", "skate", "brah", "cha"};
		for(int i = 0; i < str.length; i++){
			System.out.println(str[i]);
		}
		sortWords(str);
		for(int i = 0; i < str.length; i++){
			System.out.println(str[i]);
		}

		

	//	int[] keys = {34, 25, 79, 56, 6};
	//	probe(keys, 11);

	}

	static void sortWords(String[] W) {
		int i, j;
		String temp;
		for(j = 1; j < W.length; j++) {
			temp = W[j];
			i = j-1;
			while(i >= 0 && W[i].compareTo(temp) > 0) {
				W[i+1] = W[i];
				i--;
			}
			W[i+1] = temp;
		}
	}


	static void probe(int[] W, int n) {
//		int[] table = new int[n];
		for(int k = 0; k < W.length && k < n; k++) {
			for(int i = 0; i < n; i++) {
				int indice = ((((W[k])%n) + i*(1+(W[k]%(n-1)))) % n);
				System.out.println("h(" + W[k] + ", " + i + "): " + indice);
			}
		}		
	}
//		if(table[indici] == 0) {
//					table[indici] = W[k];
//					System.out.println("h(" + W[k] + ", " + i + ") = " + indici);
//					break;
//				}
//			System.out.println("h(" + W[k] + ", " + i + ") = " + indici);	
//			}
//		}
//	}



}
