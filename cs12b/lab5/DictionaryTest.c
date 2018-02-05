//Alec Felt  1430374  allfelt@ucsc.edu
#include<stdio.h>
#include<stdlib.h>
#include"Dictionary.h"
int main(int argc, char* argv[]) {
	
	Dictionary D = newDictionary();//new dictionary
	printf("Size: %d\n", size(D));//print the size of the dict
	insert(D, "yes", "no");//insert some key value pairs
	insert(D, "no", "yes");
	insert(D, "noyes", "yesno");
	insert(D, "yesno", "noyes");
	printf("empty: %d\n", isEmpty(D));//see if the dict is empty
	printf("key: noyes value: %s\n", lookup(D, "noyes"));//tests lookup
	delete(D, "noyes");//deletes a key value pair
	char *p = lookup(D, "noyes");//tests the error catching of lookup
	printf("key: noyes value: %s\n", p);//prints results of lookup error catch
	printDictionary(stdout, D);//prints whole dict
	freeDictionary(&D);
	printDictionary(stdout, D);
	return 1;
}
