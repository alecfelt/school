(* $Id: bigint.ml,v 1.5 2014-11-11 15:06:24-08 - - $ *)
(* Alec Felt   1430374   allfelt *)

open Printf

module Bigint = struct

    type sign     = Pos | Neg
    type bigint   = Bigint of sign * int list
    let  radix    = 10
    let  radixlen =  1

    let car       = List.hd
    let cdr       = List.tl
    let map       = List.map
    let reverse   = List.rev
    let strcat    = String.concat
    let strlen    = String.length
    let strsub    = String.sub
    let zero      = Bigint (Pos, [])

    let make_bigint_neg (Bigint (n1, v1)) = Bigint (Neg, v1)

    let charlist_of_string str = 
        let last = strlen str - 1
        in  let rec charlist pos result =
            if pos < 0
            then result
            else charlist (pos - 1) (str.[pos] :: result)
        in  charlist last []

    let bigint_of_string str =
        let len = strlen str
        in  let to_intlist first =
                let substr = strsub str first (len - first) in
                let digit char = int_of_char char - int_of_char '0' in
                map digit (reverse (charlist_of_string substr))
            in  if   len = 0
                then zero
                else if   str.[0] = '_'
                     then Bigint (Neg, to_intlist 1)
                     else Bigint (Pos, to_intlist 0)

    let string_of_bigint (Bigint (sign, value)) =
        match value with
        | []    -> "0"
        | value -> let reversed = reverse value
                   in  strcat ""
                       ((if sign = Pos then "" else "-") ::
                        (map string_of_int reversed))

    let rec cmp v1 v2 =
        if List.length v1 > List.length v2
        then 1
        else if List.length v1 < List.length v2
            then 0
            else if car v1 > car v2
                then 1
                else if car v1 < car v2
                    then 0
                    else cmp (cdr v1) (cdr v2)  


    let rec add' list1 list2 carry = match (list1, list2, carry) with
        | list1, [], 0       -> list1
        | [], list2, 0       -> list2
        | list1, [], carry   -> add' list1 [carry] 0
        | [], list2, carry   -> add' [carry] list2 0
        | car1::cdr1, car2::cdr2, carry ->
          let sum = car1 + car2 + carry
          in  sum mod radix :: add' cdr1 cdr2 (sum / radix)

    let trimzeros list =
    let rec trimzeros' list' = match list' with
        | []       -> []
        | [0]      -> []
        | car::cdr ->
             let cdr' = trimzeros' cdr
             in  match car, cdr' with
                 | 0, [] -> []
                 | car, cdr' -> car::cdr'
    in trimzeros' list

    let rec sub' list1 list2 carry = match (list1, list2, carry) with
        | list1, [], 0       -> trimzeros list1
        | [], list2, 0       -> trimzeros list2
        | list1, [], carry   -> sub' list1 [carry] 0
        | [], list2, carry   -> sub' [carry] list2 0
        | car1::cdr1, car2::cdr2, carry ->
          if (car1 - car2 - carry) < 0
          then let sum = radix + car1 - car2 - carry
               in sum :: sub' cdr1 cdr2 1
          else let sum = car1 - car2 - carry
               in sum :: sub' cdr1 cdr2 0
               

    let add (Bigint (neg1, value1)) (Bigint (neg2, value2)) =
        if neg1 = neg2
        then Bigint (neg1, add' value1 value2 0)
        else if cmp (reverse value1) (reverse value2) = 0
            then Bigint (neg2, sub' value2 value1 0)
            else Bigint (neg1, sub' value1 value2 0)

    let sub (Bigint (n1, v1)) (Bigint (n2, v2)) =
        if (n1 = Pos && n2 = Pos)
        then if cmp (reverse v1) (reverse v2) = 1
            then Bigint (n1, sub' v1 v2 0)
            else Bigint (Neg, sub' v2 v1 0)
        else if (n1 = Neg && n2 = Neg)
            then if (cmp (reverse v1) (reverse v2)) = 1            
                then Bigint (n1, add' v1 v2 0)
                else Bigint (Pos, sub' v2 v1 0)
            else Bigint (n1, add' v1 v2 0)
    
    let double number = number + number

    let rec mul' count total multiplicand =
        if count = [0]
        then total
        else let count' = sub' count [1] 0
            in let total' = add' total multiplicand 0
                in mul' count' total' multiplicand

    let rec divrem' (dividend, powerof2, divisor') =
        if divisor' > dividend
        then 0, dividend
        else let quotient, remainder =
            divrem' (dividend, double powerof2, double divisor')
            in  if remainder < divisor'
                then quotient, remainder
                else quotient + powerof2, remainder - divisor'

    let divrem (dividend, divisor') = divrem' (dividend, 1, divisor')

    let div (dividend, divisor) =
        let quotient, _ = divrem (dividend, divisor)
        in quotient

    let rem (dividend, divisor) =
        let _, remainder = divrem (dividend, divisor)
        in remainder

    let mul (Bigint (n1, v1)) (Bigint (n2, v2)) = 
        if n1 = n2
        then Bigint (Pos, mul' v1 [0] v2)
        else Bigint (Neg, mul' v1 [0] v2)

    let rec div' v1 v2 carry = 
        if (cmp v1 v2) = 0
        then (carry, v1)
        else (div' (sub' v1 v2 0) v2 (add' carry [1] 0))

    let div (Bigint (neg1, value1)) (Bigint (neg2, value2)) =
        if (car value2) <> 0 then 
            if neg1 = neg2
            then Bigint(Pos, fst(div' value1 value2 [0]))
            else Bigint(Neg, fst(div' value1 value2 [0]))
        else (printf "dc: divide by zero\n"; zero)

    let rem (Bigint (n1, v1)) (Bigint (n2, v2)) = 
        if (car v2) <> 0 then 
            if n1 = n2
            then Bigint(n1, snd(div' v1 v2 [0]))
            else Bigint(Neg, snd(div' v1 v2 [0]))
        else (printf "dc: remainder by zero\n"; zero)

    let rec pow' val1 val2 = 
        if (car val2) <> 1
        then mul' val1 [0] (pow' val1 (sub' val2 [1] 0))
        else val1

    let pow (Bigint (neg1, value1)) (Bigint (neg2, value2)) =
        if neg2 = Neg
        then zero 
        else if neg1 = Pos
           then (Bigint (neg1, pow' value1 value2))
           else if rem (Bigint (Pos, value2)) (Bigint (Pos, [2])) = 
                       (Bigint (Pos, [1]))
               then (Bigint (Neg, pow' value1 value2))
               else (Bigint (Pos, pow' value1 value2))

    let sub = sub

    let mul = mul

    let div = div

    let rem = rem

    let pow = pow

end

