#------------------------------------------------------------------------------
#Makefile for CMPS 101 Programming Assignment 2
#	Alec Felt  allfelt@ucsc.edu  1430374
#       make                   makes Lex
#       make ListClient        makes ListClient
#------------------------------------------------------------------------------

Lex : Lex.o List.o
	gcc -o Lex Lex.o List.o

Lex.o : Lex.c List.h
	gcc -c -std=c99 -Wall Lex.c

ListClient: ListClient.o List.o
	gcc -o ListClient ListClient.o List.o

ListClient.o : ListClient.c List.h
	gcc -c -std=c99 -Wall ListClient.c

List.o : List.c List.h
	gcc -c -std=c99 -Wall List.c

clean :
	rm -f Lex ListClient Lex.o ListClient.o List.o
check:
	valgrind --leak-check=full Lex in out
submit:
	submit cmps101-pt.u16 pa2 Lex.c List.c List.h ListClient.c Makefile README
