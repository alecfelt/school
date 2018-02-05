//-----------------------------------------------------------------------------
// Dictionary.c
// Dictionary ADT
// Alec Felt  1430374  allfelt@ucsc.edu
//-----------------------------------------------------------------------------

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<assert.h>
#include"Dictionary.h"

// private types and functions ------------------------------------------------

// NodeObj
typedef struct NodeObj{
   char* key;
   char* value;
   struct NodeObj* previous;
   struct NodeObj* next;
} NodeObj;

// Node
typedef NodeObj* Node;

// newNode()
// constructor for private Node type
Node newNode(char* k, char* v) {
   Node N = malloc(sizeof(NodeObj));
   assert(N!=NULL);
   N->key = k;
   N->value = v;
   N->next = N->previous = NULL;
   return(N);
}

// freeNode()
// destructor for private Node type
void freeNode(Node* pN){
   if( pN!=NULL && *pN!=NULL ){
      free(*pN);
      *pN = NULL;//sets pointer to null
   }
}

// DictionaryObj
typedef struct DictionaryObj{
   Node head;
   Node tail;
   int numPairs;
} DictionaryObj;


// findKey()
// returns the Node containing key k, or returns
// NULL if no such Node exists
Node findKey(Dictionary R, char* k){
   Node N = R->head;
   while(N != NULL) {
      if(strcmp(N->key, k) == 0) {//string comparison condition
	 return N;
      } else {
         N = N->next;
      }
   }
   return N;//returns node
}
   

// public functions -----------------------------------------------------------

// newDictionary()
// constructor for the Dictionary type
Dictionary newDictionary(){
   Dictionary D = malloc(sizeof(DictionaryObj));
   assert(D!=NULL);
   D->head = NULL;
   D->tail = NULL;
   D->numPairs = 0;
   return D;
}

// freeDictionary()
// destructor for the Dictionary type
void freeDictionary(Dictionary* pD){
   if( pD!=NULL && *pD!=NULL ){
      makeEmpty(*pD);
      free(*pD);
      *pD = NULL;//sets pointer to null
      
   }
}

// isEmpty()
// returns 1 (true) if D is empty, 0 (false) otherwise
// pre: none
int isEmpty(Dictionary D){
   if( D==NULL ){
      fprintf(stderr, 
         "Dictionary Error: calling isEmpty() on NULL Dictionary reference\n");
      exit(EXIT_FAILURE);
   }
   return(D->numPairs==0);//returns condition
}

// size()
// returns the number of (key, value) pairs in D
// pre: none
int size(Dictionary D){
   if( D==NULL ){ //error checking
      fprintf(stderr, 
         "Dictionary Error: calling size() on NULL Dictionary reference\n");
      exit(EXIT_FAILURE);
   }
   return(D->numPairs);//returns size
}

// lookup()
// returns the value v such that (k, v) is in D, or returns NULL if no 
// such value v exists.
// pre: none
char* lookup(Dictionary D, char* k){
   Node N;
   if( D==NULL ){ //error checking
      fprintf(stderr, 
         "Dictionary Error: calling lookup() on NULL Dictionary reference\n");
      exit(EXIT_FAILURE);
   }
   N = findKey(D, k);//finds the node
   return ( N==NULL ? NULL : N->value );
}

// insert()
// inserts new (key,value) pair into the end of D
// pre: lookup(D, k)==NULL
void insert(Dictionary D, char* k, char* v){
   Node N = findKey(D, k); //finds node
   if( D==NULL ){//error checking
      fprintf(stderr, 
         "Dictionary Error: calling insert() on NULL Dictionary reference\n");
      exit(EXIT_FAILURE);
   }
   if( N !=NULL ){//making sure the key doesn't already exist
      fprintf(stderr, 
         "Dictionary Error: cannot insert() duplicate key: \"%s\"\n", k);
      exit(EXIT_FAILURE);
   }
   N = newNode(k, v);//new node
   if(D->numPairs == 0) {//case for empty dictionary
      D->head = N;
      D->tail = N;   
      D->numPairs++;
   } else {
      D->tail->next = N;
      N->previous = D->tail;
      D->tail = N;
      D->numPairs++;
   } 
}

// delete()
// deletes pair with the key k
// pre: lookup(D, k)!=NULL
void delete(Dictionary D, char* k){
   Node N;
   if( D==NULL ){//checks for null dictionary
      fprintf(stderr, 
         "Dictionary Error: calling delete() on NULL Dictionary reference\n");
      exit(EXIT_FAILURE);
   }
   N = findKey(D, k);//finds node
   if( N==NULL ){//makes sure the node exists
      fprintf(stderr, 
         "Dictionary Error: cannot delete() non-existent key: \"%s\"\n", k);
      exit(EXIT_FAILURE);
   }
   if(N == D->head) {//if node is at the beginning
      if(size(D) == 1) {
	 makeEmpty(D);
      } else {
	 N->next->previous = NULL;
	 D->head = N->next;
	 freeNode(&N);
	 D->numPairs--;
      }
   }else if(N == D->tail) {//if node is at tail
      D->tail = N->previous;
      D->tail->next = NULL;
      freeNode(&N);
      D->numPairs--;
   }else{//if node is in the middle somewhere
      N->previous->next = N->next;
      N->next->previous = N->previous;
      freeNode(&N);
      D->numPairs--;
   }
}

// makeEmpty()
// re-sets D to the empty state.
// pre: none
void makeEmpty(Dictionary D){
   if( D==NULL ){//checks for null dictionary
      fprintf(stderr, 
         "Dictionary Error: calling makeEmpty() on NULL Dictionary reference\n");
      exit(EXIT_FAILURE);
   }
   Node N = D->head;//free each node
   while(N != NULL) {
      Node D = N;
      N = N->next;
      freeNode(&D);
   }
   D->head = NULL;//adjust head and numPairs
   D->numPairs = 0;
}

// printDictionary()
// pre: none
// prints a text representation of D to the file pointed to by out
void printDictionary(FILE* out, Dictionary D){
   if( D==NULL ){//checks for null dictionary
      fprintf(stderr, 
         "Dictionary Error: calling printDictionary() on NULL"
         " Dictionary reference\n");
      exit(EXIT_FAILURE);
   }
   Node N = D->head;
   while(N != NULL) {//prints every key value pair starting from the head in order
      fprintf(out, "%s %s\n", N->key, N->value);
      N = N->next;
   }
}
