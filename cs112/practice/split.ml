let split pred lst =
	let rec s p l1 l2 l = match l with
	| [] -> l1@l2
	| h::t -> if p h
				then s p l1@(h::[]) l2 t
				else s p l1 l2@[h] t
	in s pred [] [] lst;;
