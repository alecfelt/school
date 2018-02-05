//-----------------------------------------------------------------------------
// Dictionary.c
// Alec Felt  1430374  allfelt@ucsc.edu
//-----------------------------------------------------------------------------

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<assert.h>
#include"Dictionary.h"

// private types and functions ------------------------------------------------

//global variable for table size
int tableSize = 101; 

// NodeObj
//typedef struct NodeObj{
  // char* key;
  // char* value;
  // struct NodeObj* previous;
  // struct NodeObj* next;
//} NodeObj;

// Node
//typedef NodeObj* Node;


// newNode()
// constructor for private Node type
//Node newNode(char* k, char* v) {
//   Node N = malloc(sizeof(NodeObj));
//   assert(N!=NULL);
//   N->key = k;
//   N->value = v;
//   N->previous = N->next = NULL;
//   return(N);
//}

typedef struct DictionaryObj{//Dictionary object
   int numPairs;
   Node* hashTable;
} DictionaryObj;


unsigned int rotate_left(unsigned int value, int shift) {//function for turning an int into a string
 int sizeInBits = 8*sizeof(unsigned int);
 shift = shift & (sizeInBits - 1);
 if ( shift == 0 )
 return value;
 return (value << shift) | (value >> (sizeInBits - shift));
}

// pre_hash()
// turn a string into an unsigned int
unsigned int pre_hash(char* input) {
 unsigned int result = 0xBAE86554;
 while (*input) {
 result ^= *input++;
 result = rotate_left(result, 5);
 }
 return result;
}

// hash()
// turns a string into an int in the range 0 to tableSize-1
int hash(Dictionary D, char* key){
 return pre_hash(key)%(tableSize);
}


// freeNode()
// destructor for private Node type
//void freeNode(Node* pN){
  // if( pN!=NULL && *pN!=NULL ){
    //  free(*pN);
     // *pN = NULL;
  // }
//}


// findKey()
// returns the Node containing key k in the hash table, or returns
// NULL if no such Node exists
Node findKey(Dictionary R, char* k){
  int index = hash(R, k);
  Node N = R->hashTable[index];
  if(N == NULL) {
    ;
  }else{
    while(N!=NULL){
      if(strcmp(N->key, k)  == 0){
        return N;
      }else{
        N = N->next;
      }
    }
  }
  return NULL;
}


// public functions -----------------------------------------------------------

// newDictionary()
// constructor for the Dictionary type
Dictionary newDictionary(){
   Dictionary D = malloc(sizeof(DictionaryObj));
   assert(D!=NULL);
   D->hashTable = calloc(tableSize, sizeof(Node));
   for(int i = 0; i < tableSize; i++) {
     D->hashTable[i] = NULL;
   }
   D->numPairs = 0;
   return D;
}

// freeDictionary()
// destructor for the Dictionary type
void freeDictionary(Dictionary* pD){
   if( pD!=NULL ){
      makeEmpty(*pD);
      free(((*pD)->hashTable));
      free(*pD);
      *pD = NULL;
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
   return(D->numPairs==0);
}

// size()
// returns the number of (key, value) pairs in D
// pre: none
int size(Dictionary D){
   if( D==NULL ){
      fprintf(stderr,
         "Dictionary Error: calling size() on NULL Dictionary reference\n");
      exit(EXIT_FAILURE);
   }
   return(D->numPairs);
}

// lookup()
// returns the value v such that (k, v) is in D, or returns NULL if no
// such value v exists.
// pre: none
char* lookup(Dictionary D, char* k){
  if( D==NULL ){//checks for null dictionary
     fprintf(stderr,
        "Dictionary Error: calling delete() on NULL Dictionary reference\n");
     exit(EXIT_FAILURE);
   }
   Node N;
   N = findKey(D, k);
   return ( N==NULL ? NULL : N->value );
}

// insert()
// inserts new (key,value) pair into D
// pre: lookup(D, k)==NULL
void insert(Dictionary D, char* k, char* v){
   if( D==NULL ){//checks for null dictionary
     fprintf(stderr,
        "Dictionary Error: calling delete() on NULL Dictionary reference\n");
     exit(EXIT_FAILURE);
   }
   Node H = findKey(D, k);
   if( H != NULL ) {
      fprintf(stderr,
         "Dictionary Error: key collision\n");
      exit(EXIT_FAILURE);
   }
   int index = hash(D, k);
   Node N = newNode(k, v);
   Node K = D->hashTable[index];
   if(K == NULL) {
     D->hashTable[index] = N;
   }else{
     N->next = K;
     K->previous = N;
     D->hashTable[index] = N;
   } 
   D->numPairs++;
}

// delete()
// deletes pair with the key k
// pre: lookup(D, k)!=NULL
void delete(Dictionary D, char* k){
  if( D==NULL ){//checks for null dictionary
     fprintf(stderr,
        "Dictionary Error: calling delete() on NULL Dictionary reference\n");
     exit(EXIT_FAILURE);
   }
  Node N = findKey(D, k);
  if( N==NULL ){//makes sure the node exists
       fprintf(stderr,
          "Dictionary Error: cannot delete() non-existent key: \"%s\"\n", k);
       exit(EXIT_FAILURE);
  }
  int index = hash(D, k); 
  if(N->previous != NULL && N->next != NULL) {
       N->next->previous = N->previous;
       N->previous->next = N->next;
  }else if(N->previous != NULL) {
       N->previous->next = N->next;
  }else if(N->next != NULL) {
       N->next->previous = N->previous;
       D->hashTable[index] = N->next;
  }else{
       D->hashTable[index] = N->next;
  }
  D->numPairs--;
  freeNode(&N);
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

   for(int i = 0; i < tableSize; i++){
     Node N = D->hashTable[i];
     if(N == NULL){
       ;
     }else{
       while(N!=NULL){
         Node H = N;
         N = N->next;
         freeNode(&H);
       }
       D->hashTable[i] = NULL;
     }
   }
   D->numPairs = 0;
}

// printDictionary()
// pre: none
// prints a text representation of D to the file pointed to by out
void printDictionary(FILE* out, Dictionary D){
   if(D == NULL){
     fprintf(stderr,
         "Dictionary Error: calling printDictionary() on NULL"
         " Dictionary reference\n");
      exit(EXIT_FAILURE);
   }
   for(int i = 0; i < tableSize; i++){
     Node N = D->hashTable[i];
     if(N == NULL){
       ;
     }else{
       while(N!=NULL){
         fprintf(out, "%s %s\n", N->key, N->value);
         N = N->next;
       }
     }
   }

}
