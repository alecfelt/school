let iota n =
	let rec i num l = 
		if num < 1
		then l
		else i (num - 1) (num::l)
	in i n [];;
