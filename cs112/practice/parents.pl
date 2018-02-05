parents(henry_vii,elizabeth_of_york,henry_viii).
parents(henry_viii,catherine_of_aragon,mary_i).
parents(henry_viii,anne_boleyn,elizabeth_i).
parents(henry_viii,jane_seymour,edward_vi).

father(X, Y) :- parents(X, _, Y).
mother(X, Y) :- parents(_, X, Y).
