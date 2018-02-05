//Alec Felt  1430374  allfelt@ucsc.edu
//pa4  GraphTest.c

#include "List.h"
#include "Graph.h"

int main(int argc, char* argv[]){

	Graph G = newGraph(5);
	printf("yo\n");
	printf("graph order: %d\n", getOrder(G));
	printf("yo\n");
	for(int i = 1; i <= 5; i++){
		addEdge(G, i, 5);
		addEdge(G, 5, i);
		addArc(G, 4, i);
	}
	printf("yo\n");
	printf("graph size: %d\n", getSize(G));
	printf("yo\n");
	BFS(G, 3);
	printf("yo\n");
	printf("graph source: %d\n", getSource(G));
	printf("yo\n");
	printf("vertex 4 distance: %d\n", getDist(G, 4));
	printf("yo\n");
	List L = newList();
	getPath(L, G, 1);
	printf("shortest path from 1-3:");
	printList(stdout, L);
	printf("\n"); 
	printf("yo\n");
	printGraph(stdout, G);
	printf("yo\n");
	makeNull(G);
	printf("yo\n");
	printGraph(stdout, G);

	return 1;

}
