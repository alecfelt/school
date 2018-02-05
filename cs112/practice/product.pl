product(1, []).
product(N, [H|T]) :- product(X, T), N is X * H.
	
