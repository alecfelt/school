let merge op list1 list2 =
	let rec m operator l1 l2 l =
		match (l1, l2) with
		| ([], []) -> l
		| ([], h::t) -> m operator l1 t h::l::[]
		| (hh::tt, []) -> m operator tt l2 hh::l::[]
		| (h1::t1, h2::t2) -> if operator h1 h2
									then m operator t1 l2 h1::l::[]
									else m operator l1 t2 h2::l::[]
	in m op list1 list2 [];;
