//Alec Felt  1430374  allfelt@ucsc.edu
//pa5 GraphTest.c

#include "Graph.h"
#include "List.h"

int main(int argc, char* argv[]){

    Graph G = newGraph(5);
    printf("graph order: %d\n", getOrder(G));
    addEdge(G, 1, 2);
	addEdge(G, 1, 4);
	addEdge(G, 1, 5);
	addEdge(G, 2, 3);
	addEdge(G, 2, 4);
	addEdge(G, 2, 5);
	addEdge(G, 3, 5);
	addEdge(G, 4, 5);
	printf("graph size: %d\n", getSize(G));
    List S = newList();
	for(int i = 1; i <= getOrder(G); i++){
		append(S, i);
	}
	printf("\nStart DFS\n");
	DFS(G, S);
	printf("End DFS\n\n");
    printGraph(stdout, G);	


	Graph C = copyGraph(G);
	printGraph(stdout, C);

	freeList(&S);
	freeGraph(&G);
	freeGraph(&C);

	printf("GraphTest done\n");
    return 1;

}
