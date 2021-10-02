;; Copyright (c) 2021 Thomas J. Otterson
;; 
;; This software is released under the MIT License.
;; https://opensource.org/licenses/MIT

;; Solves Project Euler problem 7:
;;
;; By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see
;; that the 6th prime is 13.
;;
;; What is the 10 001st prime number?

;; The solution is trivial once you have a lazy sequence of all prime numbers.
;; I adapted the Haskell implementation of just such a sequence from page 6 of
;; https://www.cs.hmc.edu/~oneill/papers/Sieve-JFP.pdf. 
;;
;; Once you have the lazy sequence, you can find any nth prime number by
;; indexing the sequence at n - 1.

(ns barandis.euler.p7)

(def primes
  "Infinite, lazy sequence of prime numbers."
  ((fn sieve [table x]
     (let [reinsert (fn [table prime] (update table (+ prime x) conj prime))]
       (if-let [factors (get table x)]
         (recur (reduce reinsert (dissoc table x) factors) (inc x))
         (lazy-seq (cons x (sieve (assoc table (* x x) (list x)) (inc x)))))))
   {} 2))

(defn- nth-prime
  "Calculates the `n`th prime number, where 2 is the first."
  [n]
  (nth primes (dec n)))

(defn solve
  "Displays the (:input data)th prime number. :input defaults to 10001, which
   makes the displayed value the solution to Project Euler problem 7."
  ([] (solve {}))
  ([data] (-> (get data :input 10001)
              nth-prime
              println
              time)))
