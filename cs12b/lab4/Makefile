#Alec Felt  1430374  allfelt@ucsc.edu

FLAGS   = -std=c99 -Wall
SOURCES = test.c
OBJECTS = test.o
EXEBIN  = test
SUBMIT  = submit cmps012b-pt.s16 lab4 README Makefile
CHECK   = ls /afs/cats.ucsc.edu/class/cmps012b-pt.s16/lab4/allfelt

all: $(EXEBIN)

$(EXEBIN) : $(OBJECTS)
	gcc -o $(EXEBIN) $(OBJECTS)
	
$(OBJECTS) : $(SOURCES)
	gcc -c $(FLAGS) $(SOURCES)
	
clean:
	rm -f $(EXEBIN) $(OBJECTS)
	
submit: $(SOURCES)
	$(SUBMIT) $(SOURCES)
	
check:
	$(CHECK)
