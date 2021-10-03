;; Copyright (c) 2021 Thomas J. Otterson
;; 
;; This software is released under the MIT License.
;; https://opensource.org/licenses/MIT

;; Solves Project Euler problem 757:
;;
;; A positive integer is stealthy, if there exist positive integers a, b, c, d 
;; such that ab = cd = N and a + b = c + d + 1. For example, 36 = 4 × 9 =
;; 6 × 6 is stealthy.
;;
;; You are also given that there are 2851 stealthy numbers not exceeding 10^6.
;;
;; How many stealthy numbers are there that don't exceed 10^14?

;; This solution relies on the fact that these "stealthy" numbers are also
;; called *bipronic* numbers and that they also take the form of xy(x + 1)(y +
;; 1) where x and y are positive integers. They are sequence number A072389 in
;;    [OEIS](https://oeis.org/A072389) or the [Sequence
;;    Database](http://sequencedb.net/s/A072389).
;;
;; This solution simply calculates all bipronic numbers with an x and y
;; components between 1 and n^1/4 (since no bipronic could have a component of
;; > n^1/4 and still be < n), where n is the upper limit to the values of the
;; "stealthy" numbers, by looping over all of those integers and inserting them
;; into the above formula. Since this will produce duplicate solutions, the
;; solutions are collected into a vector and deduped before being counted. This
;; is approximately 4 times faster than deduping by inserting the solutions into
;; a set.
;;
;; The final answer is over 75 million, meaning that we have to store a vector
;; with that many elements in order to get a count. This will require tons of
;; heap space. This code should be run with the -Xmx4G JVM option to create
;; enough heap space to not run out before finishing.
;;
;; This solution can be run using `clojure -X:p757`. It will default to the 1e14
;; target described in the problem. To run with another target, use `clojure
;; -X:p757 :target 1e6` or similar.

(ns barandis.euler.p757)

(defn- calc-partial-bipronics
  "Calculates all of the bipronic numbers of form xy(x + 1)(y + 1) that are less
   than `n` for a single value of `x`. `found` is a vector of discovered
   solutions, and that vector is returned."
  [^long x ^long n ^clojure.lang.PersistentVector found]
  (loop [y x found' found]
    (let [v (* x y (inc x) (inc y))]
      (if (> v n)
        found'
        (recur (inc y) (conj found' v))))))

(defn- calc-bipronics
  "Calculates all bipronic numbers up to and potentially including `n`. All
   unique solutions are returned in a sorted vector."
  [^long n]
  (let [limit (Math/pow n 0.25)]
    (loop [x 1 found []]
      (if (> x limit)
        (-> found sort dedupe)
        (recur (inc x) (calc-partial-bipronics x n found))))))

(defn solve
  "Prints out the number of 'stealthy' (bipronic) numbers up to and including
   (:target data). This number defaults to 10^14, which makes the return value
   the solution to Project Euler problem 757."
  ([] (solve {}))
  ([data] (-> (get data :target 1e14) long calc-bipronics count println time)))
