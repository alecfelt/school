(define (rev lst)
	(define (r l1 l2)
		(if (null? l1) l2
			(r (cdr l1) (append (list (car l1)) l2))
		)
	)
	(r lst '())
)
