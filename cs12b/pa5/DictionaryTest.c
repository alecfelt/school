//Alec Felt  1430374  allfeltAucsc.edu
//DictionaryTest.c

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"Dictionary.h"


char* cat(char* s1, char* s2) {
	int s1Length = 0, s2Length = 0, heapLength = 0, k;
	for(k = 0; s1[k] != '\0'; k++) {
		s1Length++;
	}
	for(k = 0; s2[k] != '\0'; k++) {
		s2Length++;
	}
	heapLength = (s1Length + s2Length + 1);
	printf("s1Length: %d\ns2Length: %d\nheapLength: %d\n", s1Length, s2Length, heapLength);
	char* heapArray = malloc(heapLength*sizeof(char));
	for(k = 0; k < (heapLength - 1); k++) {
		if(k < s1Length) {
			heapArray[k] = s1[k];
		}else{
			heapArray[k] = s2[k - s1Length];
		}
	}
	heapArray[heapLength - 1] = '\0';
	return heapArray;
}


void sortWords(char** W, int n) {
	int i, j;
	char* temp;
	for(j = 1; j < n; j++) {
		temp = W[j];
		i = j-1;
		while(i >= 0 && strcmp(temp, W[i]) < 0) {
			W[i+1] = W[i];
			i--;
		}
		W[i+1] = temp;
	}
}


typedef struct NodeObj{
	int item;
	struct NodeObj* next;
} NodeObj;

typedef NodeObj* Node;

int sumList(Node H) {
	if(H == NULL){
		return 0;
	}
	return (H->item + sumList(H->next));
}

Node newNode(int x) {
	Node ref = malloc(sizeof(NodeObj));
	ref->item = x;
	ref->next = NULL;
	return ref;
}

void freeNode(Node* pN) {
	free(pN);
	*pN = NULL;
}

void clearList(Node H) {
	if(H == NULL) {
		;
	}else{
		clearList(H->next);
		freeNode(&H);
	}	
}


int main() {

Node H = newNode(1);
H->next = newNode(2);
H->next->next = newNode(3);

clearList(H);


return (EXIT_SUCCESS);

}



