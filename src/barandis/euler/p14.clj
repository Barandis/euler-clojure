;; Copyright (c) 2021 Thomas J. Otterson
;; 
;; This software is released under the MIT License.
;; https://opensource.org/licenses/MIT

;; Solves Project Euler problem 11:
;;
;; The following iterative sequence is defined for the set of positive integers:
;;
;;             n → n/2 (n is even)
;;             n → 3n + 1 (n is odd)
;;
;; Using the rule above and starting with 13, we generate the following
;; sequence:
;;
;;             13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1
;;
;; It can be seen that this sequence (starting at 13 and finishing at 1)
;; contains 10 terms. Although it has not been proved yet (Collatz Problem), it
;; is thought that all starting numbers finish at 1.
;;
;; Which starting number, under one million, produces the longest chain?
;;
;; NOTE: Once the chain starts the terms are allowed to go above one million.

;; Collatz sequences are, on the individual level, pretty unpredictable. There
;; isn't a good shortcut algorithm. So brute force is used to calculate the
;; length of each Collatz sequence. Memoization is used because it's very common
;; for two Collatz sequences to share the last several values; this cuts the
;; algorithm's runtime by about 60% when the target number is one million.
;;
;; This solution can be run using `clojure -X:p14`. It will default to the 1e6
;; target described in the problem. To run with another target, use `clojure
;; -X:p14 :target 13` or similar.

(ns barandis.euler.p14)

(def prior-results
  "A map of collatz lengths that have already been calculated."
  (atom {}))

(defn- collatz-length
  "Calculates the length of the Collatz sequence for `n`. Uses memoization to
   reduce the number of actual calculations that need to be made."
  [n]
  (loop [current n acc 0]
    (if (contains? @prior-results current)
      (let [result (+ acc (@prior-results current))]
        (swap! prior-results assoc n result)
        result)
      (if (= current 1)
        (let [result (inc acc)]
          (swap! prior-results assoc n result)
          result)
        (if (odd? current)
          (recur (inc (* 3 current)) (inc acc))
          (recur (/ current 2) (inc acc)))))))

(defn- max-collatz-length
  "Calculates the number between 1 and `n` (inclusive) that has the longest
   Collatz sequence."
  [n]
  (loop [current 1 max-start 0 max-length 0]
    (if (> current n)
      max-start
      (let [len (collatz-length current)]
        (if (> len max-length)
          (recur (inc current) current len)
          (recur (inc current) max-start max-length))))))

(defn solve
  "Displays the number between 1 and (:target data) that has the longest
   Collatz sequence. This number defaults to 1e6, which makes the displayed
   value the solution to Project Euler problem 14."
  ([] (solve {}))
  ([data] (-> (get data :target 1e6) max-collatz-length println time)))
