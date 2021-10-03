;; Copyright (c) 2021 Thomas J. Otterson
;; 
;; This software is released under the MIT License.
;; https://opensource.org/licenses/MIT

;; Solves Project Euler problem 10:
;;
;; The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
;;
;; Find the sum of all the primes below two million.

;; The solution is very straightforward. This uses the same lazy infinite
;; sequence of primes as problem 7 (code is duplicated here because I want each
;; solution to stand alone). The primes less than 2 million are taken into their
;; own sequence, and then that is summed.
;;
;; This solution can be run using `clojure -X:p10`. It will default to the 2e6
;; target described in the problem. To run with another target, use `clojure
;; -X:p10 :target 10` or similar.

(ns barandis.euler.p10)

(def ^:private primes
  "Infinite, lazy sequence of prime numbers."
  ((fn sieve [table x]
     (letfn [(reinsert [table prime] (update table (+ prime x) conj prime))]
       (if-let [factors (get table x)]
         (recur (reduce reinsert (dissoc table x) factors) (inc x))
         (lazy-seq (cons x (sieve (assoc table (* x x) (list x)) (inc x)))))))
   {} 2))

(defn- prime-sum
  "Calculates the sum of all primes up to (but not including) `target`."
  [target]
  (->> primes (take-while #(< % target)) (reduce +)))

(defn solve
  "Displays the sum of all primes up to (but not including) (:target data).
   :target defaults to 2e6, which makes the displayed value the solution to
   Project Euler problem 10."
  ([] (solve {}))
  ([data] (-> (get data :target 2e6) prime-sum println time)))
