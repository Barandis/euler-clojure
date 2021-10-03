;; Copyright (c) 2021 Thomas J. Otterson
;; 
;; This software is released under the MIT License.
;; https://opensource.org/licenses/MIT

;; Solves Project Euler problem 9:
;;
;; A Pythagorean triplet is a set of three natural numbers, a < b < c, for which
;;
;;                          a^2 + b^2 = c^2
;;
;; For example, 3^2 + 4^2 = 9 + 16 = 25 = 5^2.
;;
;; There exists exactly one Pythagorean triplet for which a + b + c = 1000. Find
;; the product abc.

;; The method here is Euclid's formula, which still works great after all these
;; years. If m and n are coprime (have a GCD of 1) and m > n > 0, then
;;
;;                        a = m^2 - n^2
;;                        b = 2mn
;;                        c = m^2 + n^2
;;
;; are a Pythagorean triple.
;;
;; This formula produces only primitive triples like 3, 4, 5. Multiples of the
;; primitive triples (like 6, 8, 10) are also triples and must also be
;; generated. To do this, a constant k is multipled by each component to come up
;; with all of the other triples. k, m, and n are chosen so that triples above
;; the target value aren't generated, so no time is wasted checking them.
;;
;; This solution can be run using `clojure -X:p9`. It will default to the 1000
;; target described in the problem. To run with another target, use `clojure
;; -X:p9 :target 150` or similar.

(ns barandis.euler.p9)

(defn- triples
  "Generates a sequence of all Pythagorean triples whose components add up to
   the target value. (This can also be regarded as the sides of Pythagorean
   triangles with the target perimeter.) An empty sequence will be returned if
   no Pythagorean triple's components add up to the target value."
  [target]
  (letfn [(gcd [x y] (if (zero? y) x (recur y (mod x y))))]
    (for [m (range 2 (Math/sqrt (/ target 2)))
          n (range (inc (mod m 2)) m 2)
          :when (= 1 (gcd m n))
          :let [p (* 2 m (+ m n))]
          k (range 1 (inc (quot target p)))
          :let [m2 (* m m)
                n2 (* n n)
                a (* k (- m2 n2))
                b (* 2 k m n)
                c (* k (+ m2 n2))]
          :when (= target (+ a b c))]
      [a b c])))

(defn solve
  "Displays the product of the components of the first Pythagorean triple whose
   components add up to (:target data). :target defaults to 1000, which makes the
   displayed value the solution to Project Euler problem 9."
  ([] (solve {}))
  ([data] (->> (get data :target 1000)
               triples
               first
               (reduce *)
               println
               time)))
