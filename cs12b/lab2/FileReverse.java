// FileReverse.java
// Reverse strings from one file to another
// Alec Felt  1430374  allfelt@ucsc.edu
import java.io.*;
import java.util.Scanner;
class FileReverse{	

	public static String stringReverse(char[] temp, String s, int n) {
		
		if (n>0) {
			temp[n-1] = s.charAt(s.length() - n);
			stringReverse(temp, s, n-1);
		}
		String sk = new String(temp);
		return sk;
	}

	public static void main(String[] args) throws IOException{
 
		Scanner in = null;
		PrintWriter out = null;
		String line = null;
		String[] token = null;
		int i, n, lineNumber = 0;
	
		// check number of command line arguments is at least 2
		if(args.length < 2){
			System.out.println("Usage: FileCopy <input file> <output file>");
			System.exit(1);
		}
	
		// open files
		in = new Scanner(new File(args[0]));
		out = new PrintWriter(new FileWriter(args[1]));
	
		// read lines from in, extract and print tokens from each line
		while( in.hasNextLine() ){
			lineNumber++;
	
			// trim leading and trailing spaces, then add one trailing space so
			// split works on blank lines
			line = in.nextLine().trim() + " ";
 
			// split line around white space
			token = line.split("\\s+");
 
			// print out tokens
			n = token.length;
			char[] temp;
			for(i=0; i<n; i++){
				temp = new char[token[i].length()]; 
				out.println(""+stringReverse(temp, token[i], token[i].length()));
			}
		}
		// close files
		in.close();
		out.close();
	}
}
