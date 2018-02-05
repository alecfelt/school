let rec zip l1 l2 =
	match l1, l2 with
		| [], [_] -> Printf.eprintf "there was an error\n" 
		| [_], [] -> Printf.eprintf "there was an error\n"
		| h1::t1, h2::t2 -> (h1, h2) :: zip t1 t2;; 
