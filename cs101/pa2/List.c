//Alec Felt  1430374  allfelt@ucsc.edu
//pa2, List.c
//This file contains the List ADT in c

#include<stdbool.h>
#include<stdio.h>
#include<stdlib.h>
#include "List.h"

// structs ----------------------------------------------------------------------

// private NodeObj type
typedef struct NodeObj{
   int index;
   struct NodeObj* previous;
   struct NodeObj* next;
} NodeObj;

// private Node type
typedef NodeObj* Node;

// private ListObj type
typedef struct ListObj{
   Node front;
   Node back;
   Node cursor;
   int length;
   int index;
} ListObj;


// Constructors-Destructors ---------------------------------------------------


// newNode()
// Returns reference to new Node object. Initializes next and data fields.
// Private.
Node newNode(int node_data){
   Node N = malloc(sizeof(NodeObj));
   N->index = node_data;
   N->next = NULL;
   N->previous = NULL;
  return(N);
}

// freeNode()
// Frees heap memory pointed to by *pN, sets *pN to NULL.
// Private.
void freeNode(Node* pN){
   if( pN!=NULL && *pN!=NULL ){
   	free(*pN);
   	*pN = NULL;
   }	
}

//creates a new List from heap memory
//sets all of the List fields
List newList() {
	List L;
	L = malloc(sizeof(ListObj));
	L->front = NULL;
	L->back = NULL;
	L->cursor = NULL;
	L->length = 0;
	L->index = -1;
	return(L);   
}

//frees heap memory with the list
//public function
void freeList(List *pL){
	if(pL != NULL && *pL != NULL){
		clear(*pL);
		free(*pL);
		*pL = NULL;
	}
}


// Helper Functions -----------------------------------------------------------


//once an item is deleted of inserted the index has of the cursor has to be adjusted
//this code resets the index value 
void setIndex(List L){
	if(L->index != -1){
		if(length(L) == 0 || L->cursor == NULL){
			L->index = -1;
		}
		Node N = L->front;
		int count = 0;
		int set = 0;
		while(N != NULL){
			if(N == L->cursor){
				L->index = count;
				set = 1;
				break;
			}
			count++;
			N = N->next;
		}
		if(set == 0){
			L->index = -1;
		}
	}
}


// Access functions -----------------------------------------------------------


//returns length of List
int length(List L){
	if( L==NULL ){
      		printf("List Error: calling length() on NULL List reference\n");
      		exit(1);
   	}
	if(length(L) != 0){
                return L->length;
        }else{
                printf("No elements in the list or cursor is null");
                exit(-1);
                return -1;
        }
}

//pre: length > 0
//returns the index of the cursor with respect to the List
int index(List L){
	if( L==NULL ){
                printf("List Error: calling index() on NULL List reference\n");
                exit(1);
        }
	if(length(L) != 0){
		return L->index;
	}else{
		printf("No elements in the list or cursor is null");
                exit(-1);
                return -1;
	}
}

//pre: length > 0
int front(List L){//returns the value held at the front of the list
	if( L==NULL ){
                printf("List Error: calling front() on NULL List reference\n");
                exit(1);
        }
	if(length(L) != 0){
                return L->front->index;;
        }else{
                printf("No elements in the list or cursor is null");
                exit(-1);
                return -1;
        }
}


//pre: length != 0
//returns index at the back of the List
int back(List L){
	if( L==NULL ){
                printf("List Error: calling back() on NULL List reference\n");
                exit(1);
        }
	if(L->length != 0){
                        return L->back->index;
        }else{
           	printf("No elements in the list");
                exit(-1);
                return -1;
        }
}


//pre: length != 0 && cursor != NULL
//returns the index at the Node pointed to by the cursor
int get(List L){
	if( L==NULL ){
                printf("List Error: calling get() on NULL List reference\n");
                exit(1);
        }
	if(L->length != 0 && L->cursor != NULL){
                return L->cursor->index;
        }else{
                printf("No elements in the list or cursor is null");
                exit(-1);
                return -1;
        }
}


//pre: both List pointers aren't NULL
//retursn true or false and compares 2 Lists together to see if they are the same
int equals(List A, List B){
	if( A==NULL || B==NULL){
                printf("List Error: calling equals() on NULL List reference\n");
                exit(1);
        }
	if(length(A) != length(B)){
		return false;
	}
	if(length(A) > 0){
		moveFront(A);
		moveFront(B);
		while(A->cursor != NULL){
			int Aindex = get(A);
			int Bindex = get(B);
			if(Aindex != Bindex){
				return false;
			}
			moveNext(A);
			moveNext(B);
		}
	}
	return true;
}


// Manipulation procedures ----------------------------------------------------


//clears the List of all its Nodes
//pre: length > 0
void clear(List L){
	if( L==NULL ){
                printf("List Error: calling clear() on NULL List reference\n");
                exit(1);
        }
	while(length(L) > 0){
		moveFront(L);
		printf("cursor: %d\nlength: %d\n", get(L), length(L));
		delete(L);
	}
}

//pre: length > 0
//points the cursor to the front Node on the List
void moveFront(List L){
	if( L==NULL ){
                printf("List Error: calling moveFront() on NULL List reference\n");
                exit(1);
        }
	if(length(L) > 0){
		L->cursor = L->front;
		L->index = 0;
	}else{
		printf("No elements in the List");
                exit(-1);
	}
}

//pre: length > 0
//points the cursor to the back Node on the list
void moveBack(List L){
	if( L==NULL ){
                printf("List Error: calling moveBack() on NULL List reference\n");
                exit(1);
        }
	if(length(L) > 0){
		L->cursor = L->back;
		L->index = (L->length - 1);
        }else{
                printf("No elements in the List");
                exit(-1);
        }
}

//pre: length > 0, cursor != NULL
//points the cursor one Node closer to the front 
void movePrev(List L){
	if( L==NULL ){
                printf("List Error: calling movePrev() on NULL List reference\n");
                exit(1);
        }
	if(L->cursor == NULL){
		;
	}else{
		L->cursor = L->cursor->previous;
		if(L->cursor == NULL){
			L->index = -1;
		}else{
			setIndex(L);
		}
	}
}

//pre: length > 0, cursor != NULL
//points the cursor one Node closer to the back
void moveNext(List L){
	if( L==NULL ){
                printf("List Error: calling moveNext() on NULL List reference\n");
                exit(1);
        }
	if(L->cursor == NULL){
                ;
        }else{
                L->cursor = L->cursor->next;
                if(L->cursor == NULL){
                        L->index = -1;
                }else{
                        setIndex(L);
                }
        }
}

//inserts a Node before the front Node on the list
void prepend(List L, int data){
	if( L==NULL ){
                printf("List Error: calling prepend() on NULL List reference\n");
                exit(1);
        }
	Node N = newNode(data);
	if(length(L) == 0){
		L->front = L->back = N;
	}else{
		L->front->previous = N;
		N->next = L->front;
		L->front = N;
		if(index(L) != -1){
			L->index++;
		}
	}
	L->length++;
}

//inserts a Node after the back Node on the List
void append(List L, int data){
	if( L==NULL ){
                printf("List Error: calling append() on NULL List reference\n");
                exit(1);
        }
	Node N = newNode(data);
        if(length(L) == 0){
                L->front = L->back = N;
        }else{
                L->back->next = N;
                N->previous = L->back;
                L->back = N;
        }
        L->length++;
}

//pre: length > 0, cursor != null
//inserts a Node before the Node pointed to by the cursor
void insertBefore(List L, int data){
	if( L==NULL ){
                printf("List Error: calling insertBefore() on NULL List reference\n");
                exit(1);
        }
	if(length(L) != 0 && L->cursor != NULL){
               	if(L->cursor == L->front){
  	                 prepend(L, data);
                }else{
       	                 Node N = newNode(data);
                         L->cursor->previous->next = N;
                         N->previous = L->cursor->previous;
                         L->cursor->previous = N;
                         N->next = L->cursor;
			L->length++;
                }

                setIndex(L);

         }else{
                printf("No elements in the List and/or cursor is undefined");
                exit(-1);
         }
}

//pre: length > 0, cursor != NULL
//inserts a Node after the Node pointed to by the cursor
void insertAfter(List L, int data){
	if( L==NULL ){
                printf("List Error: calling insertAfter() on NULL List reference\n");
                exit(1);
        }
	if(length(L) != 0 && L->cursor != NULL){
                if(L->cursor == L->back){
                         append(L, data);
                }else{
                         Node N = newNode(data);
                         L->cursor->next->previous = N;
                         N->next = L->cursor->next;
                         L->cursor->next = N;
                         N->previous = L->cursor;
			 L->length++;
                }

                setIndex(L);

         }else{
                printf("No elements in the List and/or cursor is undefined");
                exit(-1);
         }
}

//pre: length > 0
//deletes the Node in the List pointed to by the front
void deleteFront(List L){
	if( L==NULL ){
                printf("List Error: calling deleteFront() on NULL List reference\n");
                exit(1);
        }
	if(length(L) != 0){
		Node N;
		if(length(L) == 1){
			N = L->front;
			L->front = L->back = L->cursor = NULL;
			L->index = -1;
		}else{	
                	L->front = L->front->next;
                	N = L->front->previous;
			L->front->previous = NULL;
		}
		freeNode(&N);
                L->length--;
                setIndex(L);
		if(length(L) == 1){
			L->back = L->front;
		}
        }else{
                printf("No elements in the List");
                exit(-1);
        }
}

//pre: length > 0
//deletes the Node in the List pointed to by back
void deleteBack(List L){
	if( L==NULL ){
                printf("List Error: calling deleteBack() on NULL List reference\n");
                exit(1);
        }
	if(length(L) != 0){
        	Node N;
                if(length(L) == 1){
                        N = L->front;
                        L->front = L->back = L->cursor = NULL;
                        L->index = -1;
                }else{
                        L->back = L->back->previous;
                        N = L->back->next;
                        L->back->next = NULL;
                }
                freeNode(&N);
                L->length--;
                setIndex(L);
                if(length(L) == 1){
                        L->front = L->back;
                }
	}else{
                printf("No elements in the List");
                exit(-1);
        }
}

//pre: length > 0, cursor != NULL
//deletes the Node pointed to by the cursor
void delete(List L){
	if( L==NULL ){
                printf("List Error: calling delete() on NULL List reference\n");
                exit(1);
        }
	if(length(L)!= 0 && L->cursor != NULL){

      	       if(length(L) == 1){
 	           freeNode(&(L->cursor));
		   L->front = L->back = L->cursor = NULL;
                   L->index = -1;
               }else if(L->cursor == L->front){
                   L->index = -1;
                   L->front = L->front->next;
                   L->front->previous = NULL;
		   freeNode(&(L->cursor));
                   L->cursor = NULL;
               }else if(L->cursor == L->back){
                   L->index = -1;
                   L->back = L->back->previous;
                   L->back->next = NULL;
		   freeNode(&(L->cursor));
                   L->cursor = NULL;
               }else{
                   L->cursor->previous->next = L->cursor->next;
                   L->cursor->next->previous = L->cursor->previous;
                   L->index = -1;
		   freeNode(&(L->cursor));
                   L->cursor = NULL;
               }

	       if(length(L) == 1){
			moveFront(L);
			L->front = L->back = L->cursor;
			L->cursor = NULL;
		}

               L->length--;

        }else{
               printf("No elements in the List and/or cursor is undefined");
               exit(-1);
        }
}


// Other operations -----------------------------------------------------------

//prints the Node indexes of the List in order to out
void printList(FILE* out, List L){
	if( L==NULL ){
                printf("List Error: calling printList() on NULL List reference\n");
                exit(1);
        }
	moveFront(L);
	while(L->cursor != NULL){
		fprintf(out, "%d ", get(L));
		moveNext(L);
	}
}

//copys all the Nodes in one List to another List
List copyList(List L){
	if( L==NULL ){
                printf("List Error: calling copyList() on NULL List reference\n");
                exit(1);
        }
	List LL = newList();
	if(length(L) > 0){
		moveFront(L);
		while(L->cursor != NULL){
			append(LL, get(L));
			moveNext(L);
		}
	}
	return LL;
}




