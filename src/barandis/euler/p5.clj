;; Copyright (c) 2021 Thomas J. Otterson
;; 
;; This software is released under the MIT License.
;; https://opensource.org/licenses/MIT

;; Solves Project Euler problem 5:
;;
;; 2520 is the smallest number that can be divided by each of the numbers from 1
;; to 10 without any remainder.
;;
;; What is the smallest positive number that is evenly divisible by all of the
;; numbers from 1 to 20?

;; This is an LCM problem without using the term. Solution is an algorithm that
;; uses GCD to calculate the LCM of two numbers, then a `reduce` call applies
;; that algorithm to all numbers in a range.
;;
;; This solution can be run using `clojure -X:p5`. It will default to the 20
;; input described in the problem. To run with another input, use `clojure -X:p5
;; :input 10` or similar.

(ns barandis.euler.p5)

(defn- lcm-all
  "Calculates the least common multiple of all of the args."
  [& args]
  (let [gcd (fn [x y] (if (zero? y) x (recur y (mod x y))))
        lcm (fn [x y] (/ (* x y) (gcd x y)))]
    (reduce lcm args)))

(defn solve
  "Prints out the LCM of all positive integers up to (:max data). This number 
   defaults to 20, which makes the return value the solution to Project Euler 
   problem 5."
  ([] (solve {}))
  ([data] (-> (apply lcm-all (range 1 (inc (get data :max 20))))
              println
              time)))
