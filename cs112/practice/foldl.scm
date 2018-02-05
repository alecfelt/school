(define (plus x y) (+ x y))

(define (foldl func un lst)
	(if (null? lst) un
		(foldl func (func un (car lst)) (cdr lst))
	)
)

(define (len lst)
	(foldl (lambda (un _) (+ un 1)) 0 lst)
)
