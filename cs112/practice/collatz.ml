let collatz n =
	let rec c nn acc = 
	if nn < 1
	then failwith
	else if nn = 1
		then acc
		else if nn mod 2 = 0
			then c (nn / 2) (acc + 1)
			else c (3 * nn + 1) (acc + 1)
	in c n 0;;
