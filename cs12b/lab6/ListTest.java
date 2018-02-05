//Alec Felt  1430374  allfelt@ucsc.edu
//ListTest.java

public class ListTest{

  public static void main(String[] args){
    List<List<Integer>> A = new List<List<Integer>>();
    List<List<Integer>> B = new List<List<Integer>>();
    List<Integer> AA = new List<Integer>();
    List<String> BB = new List<String>();
    List<Integer> AAA = new List<Integer>();
    AA.add(1, 2);
    AA.add(1, 1);
    AAA.add(1, 2);
    AAA.add(1, 1);
    BB.add(1, "two");
    BB.add(1, "one");
    A.add(1, AA);
    //A.add(2, AAA);
    B.add(1, AA);
    //B.add(2, AAA);

    System.out.println("A: "+A);
    System.out.println("B: "+B);
  

    System.out.println("A: "+A);
    System.out.println("B: "+B);
  
    System.out.println(A.get(1));
    System.out.println(B.get(1));
    //System.out.println(B.get(2)); //throws exception

    System.out.println("A.size(): " + A.size());
    System.out.println("B.size(): " + B.size());
    //A.removeAll();
    //B.removeAll();
    System.out.println("A isEmpty(): " + A.isEmpty());
    System.out.println("B isEmpty(): " + B.isEmpty());

    System.out.println("equal? " + AA.equals(AAA));
  }
}
