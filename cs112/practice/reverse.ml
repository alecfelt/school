let reverse l = 
	let rec r l1 l2 =
	match l1 with
	| [] -> []
	| h::t -> r t h::l2
	in r l [];;
