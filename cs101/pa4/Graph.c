//Alec Felt  1430374  allfelt@ucsc.edu
//pa4  Graph.c

#include <stdbool.h>
#include <stdlib.h>
#include "Graph.h"
#include "List.h"


typedef struct GraphObj {
	int order;
	int size;
	int source;
	int* color;
	int* parent;
	int* distance;
	List* adj;	
} GraphObj;



/*** Constructors-Destructors ***/

Graph newGraph(int n){
	Graph G = malloc(sizeof(GraphObj));
	G->adj = malloc((n+1)*sizeof(List));
	for(int i = 0; i <= n; i++){
		G->adj[i] = newList();
	}
	G->order = n;
	G->source = NIL;
	G->color = malloc((n+1)*sizeof(int));
	G->parent = malloc((n+1)*sizeof(int));
	G->distance = malloc((n+1)*sizeof(int));
	return G;
}



void freeGraph(Graph *pG){
	if(pG != NULL && *pG != NULL){
		for(int i = 0; i <= (*pG)->order; i++){
			freeList(&((*pG)->adj[i]));
		}		
		free((*pG)->adj);
		free((*pG)->color);
		free((*pG)->parent);
		free((*pG)->distance);
		free(*pG);
		*pG = NULL;
	}
}



/*** Helper Functions ***/



void addToList(List L, int val){
	int listSize = length(L);
	int inserted = false;	

	if(listSize > 0){
	
		moveFront(L);

		for(int i = 0; i < listSize; i++){

			int listVal = get(L);

			if(val < listVal || val == listVal){
		
				insertBefore(L, val);
				inserted = true;
				break;

			}

			moveNext(L);

		}

	}			

	if(inserted == false){

		append(L, val);

	}

}



/*** Access functions ***/




int getOrder(Graph G){
	if(G == NULL){
		printf("Graph Error: calling getOrder() on NULL Graph reference");
		exit(1);
	}
	return G->order;
}



int getSize(Graph G){
	if(G == NULL){
        printf("Graph Error: calling getOrder() on NULL Graph reference");
        exit(1);
    }
	return G->size;
}



int getSource(Graph G){
	if(G == NULL){
        printf("Graph Error: calling getOrder() on NULL Graph reference");
        exit(1);
    }
	
    return G->source;
}



int getParent(Graph G, int u){
	if(G == NULL){
        printf("Graph Error: calling getOrder() on NULL Graph reference");
        exit(1);
    }
	
	if(u >= 1 && u <= G->order){
        return G->parent[u];
    }else{
        fprintf(stderr, "getParent() Error: bad vertex parameter\n");
        exit(1);
    }
}



int getDist(Graph G, int u){
	if(G == NULL){
        printf("Graph Error: calling getOrder() on NULL Graph reference");
        exit(1);
    }
	
	if(u >= 1 && u <= G->order){
		return G->distance[u];
	}else{
		fprintf(stderr, "getDist() Error: bad vertex parameter\n");
        exit(1);
	}
}



void getPath(List L, Graph G, int u){
	if(G == NULL){
        printf("Graph Error: calling getOrder() on NULL Graph reference");
        exit(1);
    }

	if(G->source == NIL){
		fprintf(stderr, "getPath() Error: bad source vertex\n");
		exit(1);
	}else if(u < 1 || u > G->order){
		fprintf(stderr, "getPath() Error: bad destination vertex\n");
	}else{
		if(u == getSource(G)){
			append(L, u);
		}else if(G->parent[u] == NIL){
			;
		}else{
			getPath(L, G, G->parent[u]);
			append(L, u);
		}
	}
}



/*** Manipulation procedures ***/



void makeNull(Graph G){
	if(G == NULL){
        printf("Graph Error: calling getOrder() on NULL Graph reference");
        exit(1);
    }

	for(int i = 1; i <= G->order; i++){
		clear(G->adj[i]);
	}
}



void addEdge(Graph G, int u, int v){
	if(G == NULL){
        printf("Graph Error: calling getOrder() on NULL Graph reference");
        exit(1);
    }

	if(u >= 1 && u <= G->order && v >= 1 && v <= G->order){
		addToList(G->adj[u], v);
		addToList(G->adj[v], u);
	}else{
		fprintf(stderr, "addEdge() Error: bad vertex parameters");
		exit(1);
	}
}



void addArc(Graph G, int u, int v){
	if(G == NULL){
        printf("Graph Error: calling getOrder() on NULL Graph reference");
        exit(1);
    }

	if(u >= 1 && u <= G->order && v >= 1 && v <= G->order){
        addToList(G->adj[u], v);
    }else{
        fprintf(stderr, "addArc() Error: bad vertex parameters");
        exit(1);
    }
}



void BFS(Graph G, int s){
	if(G == NULL){
        printf("Graph Error: calling getOrder() on NULL Graph reference");
        exit(1);
    }

	G->source = s;

	if(s >= 1 && s <= G->order){
		
		for(int x = 1; x <= G->order; x++){

			if(x != s){
				G->color[x] = WHITE;
				G->distance[x] = INF;
				G->parent[x] = NIL;
			}
	
		}

		G->color[s] = GRAY;
		G->distance[s] = 0;
		G->parent[s] = NIL;

		List Queue = newList();
		prepend(Queue, s);

		while(length(Queue) > 0){

			moveBack(Queue);
			int x = get(Queue);
			deleteBack(Queue);
			
			List Xadj = copyList(G->adj[x]);
			
			if(length(Xadj) > 0){
				
				moveFront(Xadj);

				for(int k = 0; k < length(Xadj); k++){

					int y = get(Xadj);

					if(G->color[y] == WHITE){

						G->color[y] = GRAY;
						G->distance[y] = (G->distance[x] + 1);
						G->parent[y] = x;
						prepend(Queue, y);

					}
				
					moveNext(Xadj);
			
				}

			}

			G->color[x] = BLACK;

			freeList(&Xadj);

		}

		freeList(&Queue);
 
	}
	
}



/*** Other operations ***/

void printGraph(FILE* out, Graph G){
	if(G == NULL){
        printf("Graph Error: calling getOrder() on NULL Graph reference");
        exit(1);
    }

	for(int i = 1; i <= G->order; i++){
		fprintf(out, "%d:", i);
		printList(out, G->adj[i]);
		fprintf(out, "\n");
	}		
}
