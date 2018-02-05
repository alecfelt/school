#Alec Felt  1430374  allfelt@ucsc.edu

Recursion: Recursion.class
	echo Main-class: Recursion > Manifest
	jar cvfm Recursion Manifest Recursion.class
	rm Manifest
	chmod +x Recursion

Recursion.class: Recursion.java
	javac -Xlint Recursion.java

clean: 
	rm Recursion.class Recursion

submit: 
	submit cmps012b-pt.s16 pa1 Recursion.java Makefile README
