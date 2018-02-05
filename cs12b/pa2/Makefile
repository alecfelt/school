#Alec Felt  1430374  allfelt@ucsc.edu

Search: Search.class
	echo Main-class: Search > Manifest
	jar cvfm Search Manifest Search.class
	rm Manifest
	chmod +x Search

Search.class: Search.java
	javac -Xlint Search.java

clean:
	rm Search Search.class

submit:	
	submit cmps012b-pt.s16 pa2 Search.java Makefile README

check:
	ls /afs/cats.ucsc.edu/class/cmps012b-pt.s16/pa2/allfelt	
