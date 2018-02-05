let rec map op l = 
	match l with
	| [] -> []
	| h::t -> op h :: map op t;;
