#------------------------------------------------------------------------------
#  Makefile
#  Alec Felt  1430374  allfelt@ucsc.edu
#------------------------------------------------------------------------------

JAVAC      = javac 
MAINCLASS  = QueueTest
JAVASRC    = $(wildcard *.java)
SOURCES    = $(JAVASRC) makefile README
CLASSES    = $(patsubst %.java, %.class, $(JAVASRC))
JARCLASSES = $(patsubst %.class, %*.class, $(CLASSES))
JARFILE    = $(MAINCLASS) 
SUBMIT     = submit cmps012b-pt.s16 pa4 Queue.java QueueTest.java README Makefile Simulation.java QueueInterface.java QueueEmptyException.java Job.java

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(JARCLASSES)
	chmod +x $(JARFILE)
	rm Manifest

%.class: %.java
	$(JAVAC) $<

clean:
	rm -f *.class $(JARFILE)

submit: 
	$(SUBMIT)
