#include <string.h>

void BubbleSort(char* A[], int n) {
int i, j;
for(j = n-1; j>0; j--) {
for(i=1; i<=j; i++) {
if(strcmp(A[i], A[i-1]) > 0) {
printf("y\n");
}
}
}
}

int main() {
char* B[] = {"Hi", "yo", "ayy", "ska"};
BubbleSort(B, 4);
return 0;
}
