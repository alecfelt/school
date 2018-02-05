drop(_, [], X) :- X is [].
drop(Z, [A | Y], X) :- 
	(A = Z
	-> X is [Y]
	; drop(Z, Y, X)
	).
	
