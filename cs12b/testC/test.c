#include<stdio.h>
#include<stdlib.h>
#include<string.h>

char* cat(char* s1, char* s2) {
	int s1Length = 0, s2Length = 0, heapLength = 0, k;
	for(k = 0; s1[k] != '\0'; k++) {
		s1Length++;
	}
	printf("YO\n");
	for(k = 0; s2[k] != '\0'; k++) {
		s2Length++;
	}
	heapLength = (s1Length + s2Length + 1);
	char* heapArray = malloc(heapLength*sizeof(char));
	for(k = 0; k < (heapLength - 1); k++) {
		if(k < s1Length) {
			heapArray[k] = s1[k];
		}else{
			heapArray[k] = s2[k];
		}
	}
	heapArray[heapLength - 1] = '\0';
	return heapArray;
}


int main() {
	printf("aerg");
//	char str1[] = "yolo ";
//	char str2[] = "# %ty 3pl";
//	fprintf(stdout, "%s\n", str1);
//	char* newStr = cat(str1, str2);
//	printf("%s\n%s\n%s\n", str1, str2, newStr);
	return (EXIT_SUCCESS);
}



