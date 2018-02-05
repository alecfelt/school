#Alec Felt  1430374  allfelt@ucsc.edu

FileReverse: FileReverse.class
	echo Main-class: FileReverse > Manifest
	jar cvfm FileReverse Manifest FileReverse.class
	rm Manifest
	chmod +x FileReverse

FileReverse.class: FileReverse.java
	javac -Xlint FileReverse.java

clean:
	rm FileReverse.class FileReverse

submit:
	submit cmps012b-pt.s16 lab2 FileReverse.java Makefile README

check:
	ls /afs/cats.ucsc.edu/class/cmps012b-pt.s16/lab2/allfelt
