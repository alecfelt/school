//Alec Felt  14030374  allfelt@ucsc.edu
//Sparse.java  pa3

import java.io.*; //file io simplicity

public class Sparse{

	public static void main(String[] args){
	
		if(args.length != 2) { //command line error check
                        System.err.println("Incorrect Usage: two strings needed");
                        System.exit(-1);
                }

		try{ //mechanism to catch file io errors
                        FileReader in = new FileReader(args[0]);
                        LineNumberReader ln = new LineNumberReader(in);
                        List a1 = new List();//list created to store indicis of string array
                        
			String[] str = (ln.readLine()).split(" ");//specifying line
			int size = Integer.parseInt(str[0]);//sets variables from first line fo input file
			int aNNZ = Integer.parseInt(str[1]);
			int bNNZ = Integer.parseInt(str[2]);
		
			ln.readLine();//skip a line


			Matrix A = new Matrix(size);//matrix declaration
			for(int i = 0; i < aNNZ; i++){//read and create Entrys from input
				String[] S = (ln.readLine()).split(" ");
				int row = Integer.parseInt(S[0]);
                        	int col = Integer.parseInt(S[1]);
                        	double val = Double.parseDouble(S[2]);
				A.changeEntry(row, col, val);	
			}

			
			ln.readLine();//skip a line

			
			Matrix B = new Matrix(size);
			for(int i = 0; i < bNNZ; i++){//read and create Entrys from other matrix
				String[] S = (ln.readLine()).split(" ");
				int row = Integer.parseInt(S[0]);
                        	int col = Integer.parseInt(S[1]);
                        	double val = Double.parseDouble(S[2]);
				B.changeEntry(row, col, val);
			}


			in.close();//done with the input file
                        ln.close();//done with linereader

			PrintWriter writer = new PrintWriter(args[1], "UTF-8");//output 

			writer.println("A has " + aNNZ + " non-zero entries:");//using the matrix methods for
			writer.println(A);	                               //computation between the two matrices
			writer.println("B has " + bNNZ + " non-zero entries:");
			writer.println(B);
			writer.println("(1.5)*A =");
			writer.println(A.scalarMult(1.5));
			writer.println("A+B =");
			writer.println(A.add(B));
			writer.println("A+A =");
			writer.println(A.add(A));
			writer.println("B-A =");
			writer.println(B.sub(A));
			writer.println("A-A =");
			writer.println(A.sub(A));
			writer.println("Transpose(A) =");
			writer.println(A.transpose());
			writer.println("A*B =");
			writer.println(A.mult(B));
			writer.println("B*B =");
			writer.println(B.mult(B));

	
                        writer.close();//done with the output file

                }catch( IOException e1 ){
                        System.err.println("Error with file operation\n" + e1);//catches any file io exceptions
                }

        }

}
