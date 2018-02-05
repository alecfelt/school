exclude(N, L, L) :- N < 0.
exclude(0, L, L).
exclude(N, [H|T], U) :- M is N-1, exclude(M, T, U).
