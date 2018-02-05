after(H, [H|T], T).
after(N, [H|T], X) :- after(N, T, X).
after(N, [], []).
