//-----------------------------------------------------------------------------
//// Alec Felt  1430374  allfelt@unix.ucsc.edu
// List.c
//// Source code for List ADT
////----------------------------------------------------------------------------

#define _GNU_SOURCE
#include<stdio.h>
#include<stdbool.h>
#include<stdlib.h>
#include "List.h"

//in place of strcmp because I can't include <string.h> because of a conflict with index()
//compares 2 strings and returns negative, positive, or zero
int stringCompare(char str1[],char str2[]){
	int i = 0;
	while(str1[i] == str2[i] && str1[i] != '\0' && str2[i] != '\0'){
		i++;
	}
	if(str1[i] > str2[i]){
		return 1;
	}else if(str1[i] < str2[i]){
		return -1;
	}else{
		return 0;
	}
}

int main(int argc, char* argv[]){
	//checks command line input error
	if(argc != 3){
		fprintf(stderr, "Incorrect usage: two string needed");
		exit(-1);
	}
	
	FILE* fp = fopen(argv[1], "r");//opening of input file
	if(fp == NULL){//file input error check
		fprintf(stderr, "File IO error");
		exit(EXIT_FAILURE);
	}
	
	int numLines = 0;//number of lines in input
	char ch = getc(fp);//temp char storage
	int longestLine = 0;//number with respect to the longest line
	int counter = 0;//incrementer for longestLine
	while(ch != EOF){//checks for end of file
		counter++;//increments character count on that line
		if(ch == '\n'){//checks to see if character signals end of line
			numLines++;//increments the number of lines
			counter++;//increments the number of characters on that line
			if(counter > longestLine){//replaces longestLine with counter if counter is greater
				longestLine = counter;
			}
			counter = 0;//resets counter
		}
		ch = getc(fp);//gets next character
	}
	rewind(fp);//resets the reading of the input file
	char strArray[numLines][longestLine];//string array for line storage
	for(int k = 0; k < numLines; k++){//reading lines into the array
		fgets(strArray[k], longestLine, fp);
	}
	
	fclose(fp);//closes input file
	List L = newList();//creates new List
	prepend(L, 0);//puts one index value 0 into the List
	for(int n = 1; n < numLines; n++){//for loop for the elements in the string array
		moveBack(L);//moves cursor back to beginning Node of List
		char* temp = strArray[n];//temp string storage for later comparison
		while((index(L) != -1) && (stringCompare(temp, strArray[get(L)]) < 0)){//makes sure the cursor is on the List and temp string is less than the input string 
			movePrev(L);//if true move the cursor forward once
		}
		if(index(L) == -1){//if new index is -1 your off the list
			prepend(L, n);
		}else{//still on the list
			insertAfter(L, n);
		}
	}
	
	moveFront(L);//moves cursor to front of the List
	fp = fopen(argv[2], "w");//opens out put file for writing
	if(fp == NULL){//file IO error check
                fprintf(stderr, "File IO error");
                exit(EXIT_FAILURE);
	}
	for(int n = 0; n < numLines; n++){//for loop for all string array elements
		fprintf(fp, strArray[get(L)]);//print string array based on cursor value to the output file
		moveNext(L);//move the cursor one to the back
	}

	fclose(fp);//close output file
	freeList(&L);//free heap memory associated with List and Nodes

	return(1);//main is of return type int









}
