edge(a,b).
edge(b,c).
edge(c,d).
edge(d,a).
edge(a,c).

adjacent(X, Y) :- 
	edge(X, Y), !.
adjacent(X, Y) :-
	edge(Y, X), !.
