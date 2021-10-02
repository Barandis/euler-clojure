;; Copyright (c) 2021 Thomas J. Otterson
;; 
;; This software is released under the MIT License.
;; https://opensource.org/licenses/MIT

;; Solves Project Euler problem 3:
;;
;; The prime factors of 13195 are 5, 7, 13 and 29.
;;
;; What is the largest prime factor of the number 600851475143 ?

;; This is an extremely naive one-off solution for finding prime factors. It
;; simply loops through every number from 2 to the square root of `n`, and if
;; the number divides evenly into `n`, it's accumulated into the list of prime
;; factors. It would be much more efficient to only have the algorithm use prime
;; numbers insted of -every- number, but that would mean writing a function to
;; produce a sequence of prime numbers and that's not worth it for a one-off
;; problem that gets solved in under a millisecond anyway.
;;
;; The prime numbers are accumulated into a list instead of the usual vector
;; because it's convenient and efficient to have the largest prime factor be
;; first.
;;
;; This solution can be run using `clojure -X:p3`. It will default to the
;; 600851475143 input described in the problem. To run with another input, use
;; `clojure -X:p3 :input 1729` or similar.

(ns barandis.euler.p3)

(defn- prime-factorize
  "Calculates the prime factors of `n`. The output is accumulated into a list
   with the prime factors in descending order."
  [n]
  (loop [n n p 2 acc (list)]
    (if (>= n (* p p))
      (if (zero? (mod n p))
        (recur (/ n p) p (conj acc p))
        (recur n (inc p) acc))
      (conj acc n))))

(defn solve
  "Prints out the largest prime factor of (:input data). This number defaults
   to 600851475143, which makes the return value the solution of Project Euler
   problem 3."
  ([] (solve {}))
  ([data] (-> (get data :input 600851475143)
              prime-factorize
              first
              println
              time)))
