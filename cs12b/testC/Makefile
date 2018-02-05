#------------------------------------------------------------------------------
# Makefile for DictionaryClient.c with macros
# Alec Felt  1430374  allfelt@ucsc.edu
#------------------------------------------------------------------------------

FLAGS   = -std=c99 -Wall
SOURCES = test.c
OBJECTS = test.o
EXEBIN  = test

all: $(EXEBIN)

$(EXEBIN) : $(OBJECTS)
	gcc -o $(EXEBIN) $(OBJECTS)

$(OBJECTS) : $(SOURCES)
	gcc -c $(FLAGS) $(SOURCES)

clean :
	rm -f $(EXEBIN) $(OBJECTS)

check:
	valgrind --leak-check=full $(EXEBIN) 
