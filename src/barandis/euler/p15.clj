;; Copyright (c) 2021 Thomas J. Otterson
;; 
;; This software is released under the MIT License.
;; https://opensource.org/licenses/MIT

;; Solves Project Euler problem 15:
;;
;; Starting in the top left corner of a 2×2 grid, and only being able to move to
;; the right and down, there are exactly 6 routes to the bottom right corner.
;;
;; How many such routes are there through a 20×20 grid?

;; The number of lattice paths in an n x n grid is the same as the number of
;; combinations of n elements from a set of 2n objects, so we simply calculate
;; 2nCn.
;;
;; This solution can be run using `clojure -X:p15`. It will default to the 20
;; grid size described in the problem. To run with another size, use `clojure
;; -X:p15 :size 2` or similar.

(ns barandis.euler.p15)

(defn- combinations
  "Calculates the number of possible combinations of `r` elements from a set of
   `n` objects (nCr)."
  [n r]
  (let [fact #(loop [x % acc 1] (if (<= x 1) acc (recur (dec x) (* x acc))))
        n (bigint n)
        r (bigint r)]
    (/ (fact n) (* (fact r) (fact (- n r))))))

(defn- num-paths
  "Calculates the number of lattice paths in an `n`x`n` grid."
  [n]
  (combinations (* 2 n) n))

(defn solve
  "Displays the number of lattice paths in a (:size data) x (:size data) grid.
   This number defaults to 20, making the displayed value the solution to
   Project Euler problem 15."
  ([] (solve {}))
  ([data] (-> (get data :size 20) num-paths long println time)))
