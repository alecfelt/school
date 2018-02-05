
(define (f n)
	(define (fib nn num1 num2)
		(if (< nn 1) num1
			(fib (- nn 1) num2 (+ num1 num2))
		)
	)
	(fib n 0 1)
)

