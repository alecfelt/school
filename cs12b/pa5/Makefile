#------------------------------------------------------------------------------
# Makefile for DictionaryClient.c with macros
# Alec Felt  1430374  allfelt@ucsc.edu
#------------------------------------------------------------------------------

FLAGS   = -std=c99 -Wall
SOURCES = Dictionary.c DictionaryTest.c
OBJECTS = Dictionary.o DictionaryTest.o
HEADERS = Dictionary.h
EXEBIN  = DictionaryTest
SUBMIT  = submit cmps012b-pt.s16 pa5 README Dictionary.c Dictionary.h DictionaryTest.c DictionaryClient.c Makefile

all: $(EXEBIN)

$(EXEBIN) : $(OBJECTS) $(HEADERS)
	gcc -o $(EXEBIN) $(OBJECTS)

$(OBJECTS) : $(SOURCES) $(HEADERS)
	gcc -c $(FLAGS) $(SOURCES)

clean :
	rm -f $(EXEBIN) $(OBJECTS)

check:
	valgrind --leak-check=full $(EXEBIN) 

submit:
	$(SUBMIT)
