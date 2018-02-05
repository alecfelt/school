//-----------------------------------------------------------------------------
// alphaNum.c
// extracts alpha-numeric characters from each line of the input file
// and places them in the output file.
//
// Recall the program FileIO.c from lab3 used fscanf to parse words in
// a file and then process them.  However the function fscanf is not
// appropriate when you want to read an entire line from a file as a
// string.  In this program we use another standard IO function from
// stdio.h called fgets for this purpose.  Its prototype is:
//
//         char* fgets(char* s, int n, FILE* stream);
//
// fgets() reads up to n-1 characters from stream and places them in
// the character array ponted to by s.  Characters are read until either
// a newline or an EOF is read, or until the specified limit is reached.
// After the characters have been read, a null character '\0' is placed
// in the array immediately after the last character read.  A newline
// character in stream will be retained and placed in s.  If successful,
// fgets() returns the string s, and a NULL pointer is returned upon
// failure.  See fgets in section 3c of the unix man pages for more.
//
//-----------------------------------------------------------------------------

#include<stdio.h>
#include<stdlib.h>
#include<ctype.h>
#include<assert.h>

#define MAX_STRING_LENGTH 100
  
int alpha_size; //size of each char array;
int digit_size;
int punct_size;
int space_size;

// function prototype 
void extract_chars(char* s, char* a, char* d, char* p, char* w);

// function main which takes command line arguments 
int main(int argc, char* argv[]){
   FILE* in;        // handle for input file                  
   FILE* out;       // handle for output file                 
   char* line;      // string holding input line              
   char* alpha; // string holding all alphabetic chars 
   char* digit; // string holding all digit chars   
   char* punct; // string holding all punctuation chars   
   char* space; // string holding all space chars   
     if( argc != 3 ){ // check command line for correct number of arguments 
      printf("Usage: %s input-file output-file\n", argv[0]);
      exit(EXIT_FAILURE);
   }

   // open input file for reading 
   if( (in=fopen(argv[1], "r"))==NULL ){
      printf("Unable to read from file %s\n", argv[1]);
      exit(EXIT_FAILURE);
   }

   // open output file for writing 
   if( (out=fopen(argv[2], "w"))==NULL ){
      printf("Unable to write to file %s\n", argv[2]);
      exit(EXIT_FAILURE);
   }

   // allocate strings line, alpha, digit, punct, space on the heap 
   line = calloc(MAX_STRING_LENGTH+1, sizeof(char) );
   alpha = calloc(MAX_STRING_LENGTH+1, sizeof(char) );
   digit = calloc(MAX_STRING_LENGTH+1, sizeof(char) );
   punct = calloc(MAX_STRING_LENGTH+1, sizeof(char) );
   space = calloc(MAX_STRING_LENGTH+1, sizeof(char) );
   assert( line!=NULL && alpha!=NULL && digit!=NULL && punct!=NULL && space!=NULL);

   // read each line in input file, extract alpha-numeric characters 
   while( fgets(line, MAX_STRING_LENGTH, in) != NULL ){
      extract_chars(line, alpha, digit, punct, space);
      fprintf(out, "line 1 contains:\n%d alphabetic character: %s\n" 
      "%d numeric characters: %s\n%d punctuation characters: %s\n"
      "%d whitespace characters: %s\n\n", alpha_size, alpha, digit_size,
      digit, punct_size, punct, space_size, space);
   }

   // free heap memory 
   free(line);
   free(alpha);
   free(digit);
   free(punct);
   free(space);

   // close input and output files 
   fclose(in);
   fclose(out);

   return EXIT_SUCCESS;
}

// function definition 
void extract_chars(char* s, char* a, char* d, char* p, char* w){
   int i=0, aa=0, dd=0, pp=0, ww=0;
   while(s[i]!='\0' && i<MAX_STRING_LENGTH){ //stores each char in s into the corresponding char array
      if( isalpha( (int)s[i]) ) a[aa++] = s[i];//comparisons
      if( isdigit( (int)s[i]) ) d[dd++] = s[i];
      if( ispunct( (int)s[i]) ) p[pp++] = s[i];
      if( isspace( (int)s[i]) ) w[ww++] = s[i];
      i++;
      a[aa] = '\0';//add NULL to the end of each array 
      d[dd] = '\0';
      p[pp] = '\0';
      w[ww] = '\0';

   }
  
   alpha_size = aa;//gets the size of each char array
   digit_size = dd;
   punct_size = pp;
   space_size = ww;

}
