#!/afs/cats.ucsc.edu/courses/cmps112-wm/usr/racket/bin/mzscheme -qr
;; allfelt 1430373
;; $Id: sbi.scm,v 1.3 2016-09-23 18:23:20-07 - - $
;; ;;
;; ;; NAME
;; ;;    sbi.scm - silly basic interpreter
;; ;;
;; ;; SYNOPSIS
;; ;;    sbi.scm filename.sbir
;; ;;
;; ;; DESCRIPTION
;; ;;    The file mentioned in argv[1] is read and assumed to be an SBIR
;; ;;    program, which is the executed.  Currently it is only printed.

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;        Good Code		;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define *function-table* (make-hash))
(define *label-table* (make-hash))
(define *variable-table* (make-hash))

(define *stderr* (current-error-port))

(define *run-file*
    (let-values
        (((dirpath basepath root?)
            (split-path (find-system-path 'run-file))))
        (path->string basepath))
)

(define (die list)
    (for-each (lambda (item) (display item *stderr*)) list)
    (newline *stderr*)
    (exit 1)
)

(define (usage-exit)
    (die `("Usage: " ,*run-file* " filename"))
)

(for-each
    (lambda (pair)
            (hash-set! *function-table* (car pair) (cadr pair)))
    `(
        (log10_2 0.301029995663981195213738894724493026768189881)
        (sqrt_2  1.414213562373095048801688724209698078569671875)
        (e       2.718281828459045235360287471352662497757247093)
        (pi      3.141592653589793238462643383279502884197169399)
        (div     ,(lambda (x y) (floor (/ x y))))
        (log10   ,(lambda (x) (/ (log x) (log 10.0))))
        (mod     ,(lambda (x y) (- x (* (div x y) y))))
        (quot    ,(lambda (x y) (truncate (/ x y))))
        (rem     ,(lambda (x y) (- x (* (quot x y) y))))
        (<>      ,(lambda (x y) (not (= x y))))
        (+ ,+) (- ,-) (* ,*) (/ ,/) (abs ,abs) 
        (<= ,<=) (>= ,>=) (= ,=) (> ,>) (tan ,tan)
        (< ,<) (^ ,expt) (atan ,atan) (sin ,sin) (cos ,cos)
        (ceil ,ceiling) (exp ,exp) (floor ,floor)
        (asin ,asin) (acos ,acos) (round ,round)
        (log ,log) (sqrt ,sqrt)))

(define (readlist-from-inputfile filename)
    (let ((inputfile (open-input-file filename)))
         (if (not (input-port? inputfile))
             (die `(,*run-file* ": " ,filename ": open failed"))
             (let ((program (read inputfile)))
                  (close-input-port inputfile)
                         program))))


(define length
    (lambda (len)
        (if (not (null? len))
            (+ (length (cdr len)) 1)
            0
        )   
    )       
)


(define (program-eval program line)
    (when (< line (length program))
        (let ((curLine (list-ref program line)))
        (cond
            [(= 3 (length curLine)) (line-eval (caddr curLine) program line)]
            [(and (= 2 (length curLine)) (list? (cadr curLine))) (line-eval (cadr curLine) program line)]
            [else (program-eval program (+ 1 line))]
        )
        )
    )       
)   


(define (line-label-eval line)
    (when(not(null? (cdr line)))
        (cond
            [(pair? (cadr line)) (values)]
            [(and (symbol? (cadr line)) (null? (cddr line))) (hash-set! *label-table* (cadr line) (car line))]
            [(symbol? (cadr line)) (hash-set! *label-table* (cadr line) (car line))]
        )
    )
)


(define (program-label-eval lines)
        (if (null? lines)
                    (values)
                    (begin
                        (line-label-eval (car lines))
                        (program-label-eval (cdr lines))
                    )
        )
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;     Under Construction     ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;


(define (line-eval statement program line)
	(when (not (hash-has-key? *function-table* (car statement)))
		(die '("invalid statement"))
	)
	(cond
		[(eq? (car statement) 'print)
			(if (null? (cdr statement))
				(newline)
				(func-print (cdr statement))
			)
			(program-eval program (+ line 1))
		]
		[(eq? (car statement) 'goto)
			(program-eval (hash-ref *label-table* (cadr statement)))
		]
		[(eq? (car statement) 'if)	
			(if (expr-eval (car (cdr statement)))
				(program-eval program (hash-ref *label-table* (cadr (cdr statement))))
				(program-eval program (+ line 1)))
		]
		[else
			((hash-ref *function-table* (car statement)) (cdr statement))
			(program-eval program (+ line 1))
		]
			
	)
)

(define (func-print expr)
	(map (lambda (c) (display (expr-eval c))) expr)
	(printf "~n")
)

(define (func-let expr)
	(hash-set! *variable-table* (car expr) (expr-eval(cadr expr)))
)

(define (func-dim expr)
	(values)
)

(define (func-input1 expr count)
	(values)
)

(define (func-input expr)
	(hash-set! *variable-table* 'inputcount 0)
	(if (not (null? (car expr)))
		(begin
			(hash-set! *variable-table* 'inputcount (func-input1 expr 0)))
		(hash-set! *variable-table* 'inputcount -1)
	) 
)

(for-each
	(lambda (x)
		(hash-set! *function-table* (car x) (cadr x)))
		`((print, func-print)
			(let, func-let)
			(dim, func-dim)
			(input, func-input)
			(if (void))
			(goto (void))
		)
)

(define (expr-eval expr)
	(cond
		[(number? expr)
			(+ 0.0 expr)]
		[(string? expr)
			expr]
		[(hash-has-key? *function-table* expr)
			(hash-ref *function-table* expr)]
		[(hash-has-key? *variable-table* expr)
			(hash-ref *variable-table* expr)]
		[(list? expr)
			(cond
				[(hash-has-key? *function-table* (car expr))
					(let((start (hash-ref *function-table* (car expr))))
						(cond 
							[(procedure? start)
								(apply start (map (lambda (y) (expr-eval y)) (cdr expr)))]
							[(vector? start)
								(vector-ref start (cadr expr))]
							[(number? start)
								(+ 0.0 start)]
							[else 
								(die '("Error: function table is wrong"))]
						)
					)
				]
				[(not(hash-has-key? *function-table* (car expr)))
					(die '("Error: expr not in function table"))]

			)
		]
	)
)

	
(define (main arglist)
    (if (or (null? arglist) (not (null? (cdr arglist))))
        (usage-exit)
        (let* (
			(sbprogfile (car arglist))
			(program (readlist-from-inputfile sbprogfile))
			  )
			(program-label-eval program)
			(program-eval program 0)
		)
	)
)

(main (vector->list (current-command-line-arguments)))

