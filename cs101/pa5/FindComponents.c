//Alec Felt  1430374  allfelt@ucsc.edu
//pa5  FindComponents.c


#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include "Graph.h"
#include "List.h"


int main(int argc, char* argv[]){


	if(argc != 3){
        fprintf(stderr, "Incorrect usage: two string needed");
        exit(1);
    }

 FILE *fp = fopen(argv[1], "r");//file opening
 if(fp == NULL){//file input error check
       fprintf(stderr, "File IO error");
       exit(EXIT_FAILURE);
   }

 
 char str[10];//string for the first line
 fgets(str, 10, fp);
 int graphOrder = atoi(str);//order of the graph
 Graph G = newGraph(graphOrder);

 while(true){//for loop for filling up the adj List array
 	size_t bufsize = 20;
 	char buffer[20];//array for each lines
 	
 	char *yes = fgets(buffer, bufsize, fp);//gets the line
 	if(yes == NULL){//getLine() error check
 		fprintf(stderr, "Error with fgets()");
 		exit(EXIT_FAILURE);
 	}		

 	if(buffer[0] == '0'){//checks to see if the line sof adjacencies are done
 		break;
 	}
 
 
 	char s1[10];//first vertex
 	char s2[10];//second vertex
 	for(int h = 0; h < 10; h++){//iniitializes the vertex arrays
 		s1[h] = '\n';
 		s2[h] = '\n';
 	}

 	int k;
	for(k = 0; buffer[k] != ' '; k++){//fills the first vertex array
        s1[k] = buffer[k];
    }

    for(int j = (k+1); buffer[j] != '\n'; j++){//fills the second vertex array
        s2[j - (k+1)] = buffer[j];
    }

    addArc(G, atoi(s1), atoi(s2));//adds the arc
 }


 FILE* fp1 = fopen(argv[2], "w");//output file
 	fprintf(fp1, "Adjacency list representation of G:\n");
	printGraph(fp1, G);//prints the adj array
	fprintf(fp1, "\n");

	List S = newList();//List for DFS
	for(int i = 1; i <= getOrder(G); i++){//S is in ascending order
		append(S, i);
	}

	DFS(G, S);//first DFS call
	Graph T = transpose(G);//transpose of G

	DFS(T, S);//Second DFS call on transpose graph

	int numSCC = 0;//number of SCC's
	for(int i = 1; i <= getOrder(T); i++){
		if(getParent(T, i) == NIL){
			numSCC++;
		}
	}
	fprintf(fp1, "G contains %d strongly connected components:\n", numSCC);


	if(length(S) > 0){//printing the SCC's of T
		moveBack(S);
		for(int i = 1; i <= numSCC; i++){
			List SCC = newList();//List for each SCC
			while(true){
				int vertex = get(S);
				prepend(SCC, vertex);
				movePrev(S);
				if(getParent(T, vertex) == NIL){
					fprintf(fp1, "Component %d:", i);
					printList(fp1, SCC);
					fprintf(fp1, "\n");
					break;
				}
			}
			freeList(&SCC);
		}
	}

	freeList(&S);
	freeGraph(&G);
	freeGraph(&T);

	fclose(fp);//closing write and read files
	fclose(fp1);

}
			
