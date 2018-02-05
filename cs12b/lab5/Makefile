#Alec Felt  1430374  allfelt@ucsc.edu

FLAGS   = -std=c99 -Wall
SOURCES = Dictionary.c DictionaryTest.c
OBJECTS = Dictionary.o DictionaryTest.o
HEADERS = Dictionary.h
EXEBIN  = Dictionary
CHECK   = valgrind --leak-check=full $(EXEBIN)
SUBMIT  = submit cmps012b-pt.s16 lab5 README Makefile Dictionary.h Dictionary.c DictionaryTest.c DictionaryClient.c

all: $(EXEBIN)

$(EXEBIN) : $(OBJECTS) $(HEADERS)
	gcc -o $(EXEBIN) $(OBJECTS)
	
$(OBJECTS) : $(SOURCES) $(HEADERS)
	gcc -c $(FLAGS) $(SOURCES)
	
clean:
	rm -f $(EXEBIN) $(OBJECTS)
	
submit: $(SOURCES)
	$(SUBMIT) $(SOURCES)
	
check:
	$(CHECK)

submit: 
	$(SUBMIT)
