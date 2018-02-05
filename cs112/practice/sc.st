sc := SortedCollection new.
sc add: 23; add: 498; add: 33; add: 87; add: 10.
sc do: [:x | stdout<<x<<'\n'].
