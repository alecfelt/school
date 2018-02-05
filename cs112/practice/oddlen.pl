oddlen([T]), !.
oddlen([H|T]) :- odd(T).
odd([H|T]) :- oddlen(T).
