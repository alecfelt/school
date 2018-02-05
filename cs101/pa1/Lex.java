//Alec Felt	allfelt		pa1

import java.io.*; //file io simplicity

public class Lex{

	public static void main(String[] args) {

		int numLines = 0;

		if(args.length != 2) { //command line error check
			System.err.println("Incorrect Usage: two strings needed");
			System.exit(-1);
		}

		try{ //mechanism to catch file io errors
			FileReader in = new FileReader(args[0]);
			LineNumberReader ln = new LineNumberReader(in);
			int size = 

			String tempStr;//temp string
			String str = ""; //important string
			int n = 0;
			while((tempStr = ln.readLine()) != null){//counts numLines and loads str with the characters in those lines
				numLines++;
				str += tempStr + "\n";
				n++;
			}

			in.close();//done with the input file
                        ln.close();

			String input[] = str.split("\n", numLines + 1); //declares and initializes the input array
			
		
			a1.prepend(0);
  	                for(n = 1; n < numLines; n++){//insertion sort logic to alphabetize lines of file input using the List a1  

				a1.moveBack();
				String temp = input[n];
				while((a1.index() != -1) && (temp.compareTo(input[a1.get()]) < 0)){
					a1.movePrev();		
                	        }

        	                if(a1.index() == -1){//sorts indexArray rather than the actual String array
					a1.prepend(n);
				}else{
					a1.insertAfter(n);
				}
	                }

			a1.moveFront();
			PrintWriter writer = new PrintWriter(args[1], "UTF-8");//gets ready to output alphabetic strings to a file
			for(n = 0; n < numLines; n++){
				writer.println(input[a1.get()]);
				a1.moveNext();
			}
			
			writer.close();
			
		}catch( IOException e1 ){
			System.err.println("Error with file operation\n" + e1);//catches any file io exceptions
		}

	}

}
				
