(define (iota n)
	(define (i nn lst)
		(if (<= nn 0) lst
			(i (- nn 1) (cons nn lst))
		)
	)
	(i n '())
)
