;; Copyright (c) 2021 Thomas J. Otterson
;; 
;; This software is released under the MIT License.
;; https://opensource.org/licenses/MIT

;; Solves Project Euler problem 6:
;;
;; The sum of the squares of the first ten natural numbers is
;;
;;                    1^2 + 2^2 + ... + 10^2 = 385
;;
;; The square of the sum of the first ten natural numbers is
;;
;;                 (1 + 2 + ... + 10)^2 = 55^2 = 3025
;;
;; Hence the difference between the sum of the squares of the first ten natural
;; numbers and the square of the sum is 3025 - 385 = 2640.
;;
;; Find the difference between the sum of the squares of the first one hundred
;; natural numbers and the square of the sum.

;; This solution is very straightforward. A range from 1 to `n` (inclusive) is
;; created, and reduction is done to come up with each of the sum of the squares
;; of the numbers in that range and the square of the sum of the numbers in that
;; range. The difference is taken and displayed.
;;
;; This solution can be run using `clojure -X:p6`. It will default to the 100
;; input described in the problem. To run with another input, use `clojure -X:p6
;; :input 10` or similar.

(ns barandis.euler.p6)

(defn- difference
  "Calculates the difference between the sum of the squares and the square of
   the sum of the first `n` natural numbers."
  [n]
  (let [nums (range 1 (inc n))
        sum #(reduce + %)
        sq #(* % %)]
    (- (sq (sum nums)) (sum (map sq nums)))))

(defn solve
  "Displays the difference between the sum of the squares and the square of the
   sum of the first (:input data) natural numbers. This number defaults to 100,
   which makes the return value the solution of Project Euler problem 6."
  ([] (solve {}))
  ([data] (-> (get data :input 100)
              difference
              println
              time)))
