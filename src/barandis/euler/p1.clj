;; Copyright (c) 2021 Thomas J. Otterson
;; 
;; This software is released under the MIT License.
;; https://opensource.org/licenses/MIT

;; Solves Project Euler problem 1:
;;
;; If we list all the natural numbers below 10 that are multiples of 3 or 5, we 
;; get 3, 5, 6 and 9. The sum of these multiples is 23.
;;
;; Find the sum of all the multiples of 3 or 5 below 1000.

;; Solved by using the formula n(n + 1) / 2, which is the sum of all integers
;; between 1 and n inclusive. To accomodate for multiples, the number of terms
;; is divided by the multiple and the resulting sum is multiplied by the same
;; multiple. Also, `n` has one subtracted from it at the beginning because the
;; problem says "*below* 1000".
;;
;; Once we have the sums of the multiples of 3 and 5, we simply add those sums.
;; However, that new sum will include each number that is a multiple of both 3
;; and 5 *twice*, once for each series, so we also subtract the sum of those
;; multiples (represented as the sum of multiples of 15) one time to reach the
;; final answer.
;;
;; This solution can be run using `clojure -X:p1`. It will default to the 1000
;; target described in the problem. To run with another target, use `clojure
;; -X:p1 :target 100` or similar.

(ns barandis.euler.p1)

(defn- series-sum
  "Calculates the sum of all positive integers that are multiples of `m` up to 
   `n` inclusive. Uses the formula n(n + 1) / 2 but takes the multiple into 
   account by dividing `n` by it and multiplying the sum by it."
  [n m]
  (let [l (Math/floor (/ n m))]
    (int (* 0.5 m l (inc l)))))

(defn solve
  "Prints out the sum of all multiples of 3 and 5 up to and including 
   (:target data). This number defaults to 1000, which makes the return value the
   solution to Project Euler problem 1."
  ([] (solve {}))
  ([data] (-> (let [limit (dec (get data :target 1000))
                    sum-3 (series-sum limit 3)
                    sum-5 (series-sum limit 5)
                    sum-15 (series-sum limit 15)]
                (- (+ sum-3 sum-5) sum-15))
              println
              time)))
