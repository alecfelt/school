let rec evenlen lst =
	match lst with
	| [] -> true
	| [t] -> false
	| h::e::t -> evenlen t;; 
