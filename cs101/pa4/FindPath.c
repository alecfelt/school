//Alec Felt  14303074  allfelt@ucsc.edu
//pa4  FindPath.c

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
 	
 	addEdge(G, atoi(s1), atoi(s2));//adds the edge
 }

 
 FILE* fp1 = fopen(argv[2], "w");//output file
 printGraph(fp1, G);//prints the adj array
 

 while(true){//for loop for BFS
       size_t bufsize = 20;
       char buffer[20];//array for each lines

       char *yes = fgets(buffer, bufsize, fp);//gets the line
       if(yes == NULL){//getLine() error check
           fprintf(stderr, "Error with fgets()");
           exit(EXIT_FAILURE);
       }

       if(buffer[0] == '0'){//checks to see if the lines of BFS input are done
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

 	int source = atoi(s1);//sets the source vertex
 	int destination = atoi(s2);//sets the destination vertex

   	BFS(G, source);//runs BFS

 	List findPath = newList();//gets the shortest path List
 	getPath(findPath, G, destination);
 	int pathLen = length(findPath);//length of shortest path
 
 	if(pathLen == 0){//if there exists no path
 		fprintf(fp1, "\nThe distance from %d to %d is infinity\nNo %d-%d path exists\n", source, destination, source, destination);
 	}else{
 		fprintf(fp1, "\nThe distance from %d to %d is %d\nA shortest %d-%d path is:", source, destination, (pathLen - 1), source, destination);
 		printList(fp1, findPath);
 		fprintf(fp1, "\n");
 	} 
 
 	freeList(&findPath);
 }


 freeGraph(&G);	





 fclose(fp1);//closes files
 fclose(fp);



	return EXIT_SUCCESS;
}



