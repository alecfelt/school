filter(_, [], X).
filter(F, [H|T], [H|X]) :- call(F,H), filter(F, T, X).
filter(F, [H|T], X) :- filter(F, T, X).
