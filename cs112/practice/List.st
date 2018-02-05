Object subclass: List [
	|car cdr|
	List class >> new [
		^ nil
	]
	List class >> cons: c with: w [
		|result|
		result := super new.
		result setcar: c setcdr: w
		^result
	]
	setcar: ca setcdr: cd [
		car := ca.
		cdr := cd.
	]
	car [
		^ car
	]
	cdr [
		^ cdr
	]
]
