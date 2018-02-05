//Alec Felt  1430374  allfelt@ucsc.edu
//MatrixTest.java  pa3

public class MatrixTest {

	public static void main(String[] args) {

		Matrix M = new Matrix(3);
		Matrix MM = new Matrix(3);
		
		M.changeEntry(1, 2, 3.0);
		M.changeEntry(2, 1, 5.0);
		M.changeEntry(3, 1, 4.0);
		M.changeEntry(2, 2, 4.0);
		MM.changeEntry(1, 2, 3.0);
                MM.changeEntry(2, 3, 5.0);
                MM.changeEntry(3, 1, 7.0);
                MM.changeEntry(2, 2, 4.0);
		
		System.out.print(M.toString());//print statements for all the methods
		System.out.print(MM.toString());	
		System.out.print((M.sub(MM)).toString());
		System.out.print((M.add(MM)).toString());
		System.out.print((M.mult(MM)).toString());
		System.out.print((M.copy()).toString());
		System.out.print((M.transpose()).toString());
	
		System.out.println("size: " + M.getSize());	
		System.out.println("NNZ: " + M.getNNZ());	
		System.out.println("equals: " + M.equals(MM));	
	}
	

// Matrix mult(Matrix M){
// if(getSize() != M.getSize()){
//     System.err.println("add: the Matrices are different sizes");
//     System.exit(1);
// }
// Matrix newM = new Matrix(getSize());
// for(int i = 0; i < getSize(); i++){//iterates through rows
//
//     List col = new List();
//
//     for(int j = 0; j < getSize(); j++){
//         List L = M.matrix[i];
//         if(L.length() > 0){
//             L.moveFront();
//             for(int y = 0; y < L.length(); y++){
//                 Entry E = ((Entry)L.get());
//                 if(E.getCol() == (i+1)){
//                     L.append(E);
//                     break;
//                 }else if(E.getCol() < (i+1)){
//                     L.moveNext();
//                 }else{
//                     break;
//                 }
//             }
//         }
//     }
//
//     for( int k = 0; k < getSize(); k++){//iterates through columns      
//
//         List row = matrix[k];
//
//         double value = dotProduct(row, col);
//         if(value != 0){
//             newM.changeEntry((k+1), (i+1), value);
//         }
//
//     }
//
// }
//
// return newM;
//
// }
}


