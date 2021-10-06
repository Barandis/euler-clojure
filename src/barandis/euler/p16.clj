;; Copyright (c) 2021 Thomas J. Otterson
;; 
;; This software is released under the MIT License.
;; https://opensource.org/licenses/MIT

;; Solves Project Euler problem 16:
;;
;; 2^15 = 32768 and the sum of its digits is 3 + 2 + 7 + 6 + 8 = 26.
;;
;; What is the sum of the digits of the number 2^1000?

;; Converting the number to a string and then mapping over the characters (to
;; change them to digits) and reducing the result with + is an easy way to get
;; the sum. There's probably more effort in creating the BigInteger 2^1000.
;;
;; This solution can be run using `clojure -X:p16`. It will default to the
;; 2e1000 target described in the problem. To run with another target, use
;; `clojure -X:p16 :target 2e15` or similar.

(ns barandis.euler.p16)

(defn- sum-of-digits
  "Calculates the sum of the digits of the number `n`."
  [n]
  (->> n str (map #(Character/digit % 10)) (reduce +)))

(defn solve
  "Displays the sum of the digits of the number (:target data). This number
   defaults to 2e1000 (as a BigInteger for full precision), making the displayed
   value the solution to Project Euler problem 16."
  ([] (solve {}))
  ([data] (-> (get data :target (.pow (biginteger 2) 1000))
              sum-of-digits
              println
              time)))
