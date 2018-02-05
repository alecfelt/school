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
    int* discover;
    int* finish;
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
    G->discover = malloc((n+1)*sizeof(int));
    G->finish = malloc((n+1)*sizeof(int));
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
        free((*pG)->discover);
        free((*pG)->finish);
        free(*pG);
        *pG = NULL;
    }
}

/*** Helper Functions ***/


void Visit(Graph G, List S, int x, int* time){
	G->color[x] = GRAY;
    G->discover[x] = ++(*time);

    List L = G->adj[x];

    if(length(L) > 0){
        moveFront(L);
        for(int i = 0; i < length(L); i++){
            int y = get(L);
            if(G->color[y] == WHITE){
                G->parent[y] = x;
				Visit(G, S, y, time);
            }
            moveNext(L);
        }
    }

    G->color[x] = BLACK;
    G->finish[x] = ++(*time);
    prepend(S, x);
}


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



int getDiscover(Graph G, int u){
    if(G == NULL){
        printf("Graph Error: calling getOrder() on NULL Graph reference");
        exit(1);
    }

    if(u >= 1 && u <= G->order){
        return G->discover[u];
    }else{
        fprintf(stderr, "getDist() Error: bad vertex parameter\n");
        exit(1);
    }
}



int getFinish(Graph G, int u) {
    if (G == NULL) {
        printf("Graph Error: calling getOrder() on NULL Graph reference");
        exit(1);
    }

    if (u >= 1 && u <= G->order) {
        return G->finish[u];
    } else {
        fprintf(stderr, "getFinish() Error: bad vertex parameter\n");
        exit(1);
    }
}


/*** Manipulation procedures ***/


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


void DFS(Graph G, List S){
    if(G == NULL){
        printf("Graph Error: calling DFS() on NULL Graph reference");
        exit(1);
    }

    if(length(S) != getOrder(G)){
        printf("DFS Error: input length(S) != getOrder(G)");
        exit(1);
    }

    for(int i = 1; i <= getOrder(G); i++){
        G->color[i] = WHITE;
        G->parent[i] = NIL;
    }

    int time = 0;

    if(length(S) > 0){
		List SS = copyList(S);
        clear(S);
        moveFront(SS);
		for(int i = 0; i < length(SS); i++){
            int x = get(SS);
			if(G->color[x] == WHITE){
				Visit(G, S, x, &time);
			}
            moveNext(SS);
        }
		clear(SS);
    	freeList(&SS);
    }

}




Graph transpose(Graph G){
    if(G == NULL){
        printf("Graph Error: calling transpose() on NULL Graph reference");
        exit(1);
    }

    Graph newG = newGraph(getOrder(G));
    for(int i = 1; i <= getOrder(G); i++){
        List L = G->adj[i];
        if(length(L) > 0){
            moveFront(L);
            for(int j = 0; j < length(L); j++){
                int vert = get(L);
				addArc(newG, vert, i);
                moveNext(L);
            }
        }
    }
    return newG;
}


Graph copyGraph(Graph G){
    if(G == NULL){
        printf("Graph Error: calling copyGraph() on NULL Graph reference");
        exit(1);
    }

    Graph newG = newGraph(getOrder(G));
    
	for(int i = 1; i <= getOrder(G); i++){
		List L = G->adj[i];
        if(length(L) > 0){
            moveFront(L);
            for(int j = 0; j < length(L); j++){
				int Lval = get(L);
                addArc(newG, i, Lval);
				moveNext(L);
            }
        }
    }

    return newG;
}


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
