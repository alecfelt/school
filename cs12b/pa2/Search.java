//Alec Felt  1430374  allfelt@ucsc.edu

import java.io.*;
import java.util.Scanner;

class Search {

	public static void main(String[] args) throws IOException {
		
		if(args.length < 1) {
			System.err.println("Usage: LC file");
			System.exit(1);
		}

		Scanner in1 = new Scanner(new File(args[0]));
		int lineCount = 0;
		while(in1.hasNextLine()) {
			in1.nextLine();
			lineCount++;
		}
		in1.close();

		int i, j;
		int[] lineNumber = new int[lineCount];

		String[] words = new String[lineCount];
		Scanner in2 = new Scanner(new File(args[0]));
		String line = null;	

		for (i = 0; i < lineCount; i++) {
			line = in2.nextLine().trim();
			words[i] = line;
			lineNumber[i] = i + 1;
		}
		
		in2.close();

		mergeSort(words, lineNumber, 0, words.length - 1);
		
		for (i = 1; i < args.length; i++) {
			int line1 = binarySearch(words, lineNumber, 0, words.length - 1, args[i]);
			if(line1 >= 0) {
				System.out.println(args[i] + " was found on " + line1);
			} else {
				System.out.println(args[i] + " wasn't found");
			} 
		}
	}


	public static void mergeSort(String[] A, int[] lineNumber, int p, int r) {
		int q;
		if(p < r) {
			q = (p+r)/2;
			mergeSort(A, lineNumber, p, q);
			mergeSort(A, lineNumber, q+1, r);
			merge(A, lineNumber, p, q, r);
		}
	}

	public static void merge(String[] A, int[] lineNumber, int p, int q, int r) {
		int n1 = q-p+1;
		int n2 = r-q;
		String[] L = new String[n1];
		String[] R = new String[n2];
		int[] L1 = new int[n1];
		int[] R1 = new int[n2];

		int i, j, k;

		for(i=0; i<n1; i++) L[i] = A[p+i];
		for(j=0; j<n2; j++) R[j] = A[q+j+1];
		for(i=0; i<n1; i++) L1[i] = lineNumber[p+i];
		for(j=0; j<n2; j++) R1[j] = lineNumber[q+j+1];
		i = 0;
		j = 0;
		for(k=p; k<=r; k++){
			if(i<n1 && j<n2) {
				if(L[i].compareTo(R[j]) < 0) {
					A[k] = L[i];
					lineNumber[k] = L1[i];
					i++;
				} else {
					A[k] = R[j];
					lineNumber[k] = R1[j];
					j++;
				}
			} else if(i<n1) {
				A[k] = L[i];
				lineNumber[k] = L1[i];
				i++;
			} else { 
				A[k] = R[j];
				lineNumber[k] = R1[j];
				j++;
			}
		}
	}

	public static int binarySearch(String[] A, int[] lineNumber, int p, int r, String target) {
		int q;
		if(p > r) {
			return -1;
		} else {
			q = (p+r)/2;
			if(target.compareTo(A[q]) == 0) {
				return lineNumber[q];
			} else if(target.compareTo(A[q]) < 0) {
				return binarySearch(A, lineNumber, p, q - 1, target);
			} else {
				return binarySearch(A, lineNumber, q + 1, r, target);
			}
		}
	}


	



}
