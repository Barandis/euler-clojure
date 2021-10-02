;; Copyright (c) 2021 Thomas J. Otterson
;; 
;; This software is released under the MIT License.
;; https://opensource.org/licenses/MIT

;; Solves Project Euler problem 4:
;;
;; A palindromic number reads the same both ways. The largest palindrome made
;; from the product of two 2-digit numbers is 9009 = 91 × 99.
;;
;; Find the largest palindrome made from the product of two 3-digit numbers.

;; This solution is very brute-force. It simply multiplies every combination of
;; two numbers that are within certain ranges (defaulting to 100-999; i.e.,
;; three-digit numbers) and then figures out if that product is a palindrome. A
;; running tally of the largest palindrome is found, and that's printed at the
;; end.
;;
;; Palindromic numbers with an even number of digits all have to be multiples of
;; 11, so  at least one of the two numbers multiplies would also have to be a
;; multiple of 11. This could be used to reduce the number of multiplications
;; considerably, but this rule does not apply to palindromic numbers with an odd
;; number of digits. I went into this problem with every reason to believe that
;; the solution would have 6 digits, but I couldn't prove this so I went with
;; the more general solution.
;;
;; This solution can be run using `clojure -X:p4`. It will default a minimum of
;; 100 and a maximum of 999, which reflects the three-digit numbers described in
;; the problem. To run with different mins and maxes, use `clojure -X:p4 :min 10
;; :max 99` or similar.

(ns barandis.euler.p4)

(defn- palindromic?
  "Determines if a number is a palindrome by comparing its string form to its
   string form reversed."
  [n]
  ; `apply str` is needed because `reverse` returns a char seq and not a string
  (= (str n) (->> n str reverse (apply str))))

(defn- find-max-palindrome
  "Finds the largest palindromic number created from the product of two numbers,
   each of which must be between `min` and `max` (inclusive). This is a brute
   force method that simply multiples all of the possible pairs of two numbers
   in the given range and then returns the largest of them that is a 
   palindrome."
  [min max]
  (loop [x min y min acc 0]
    (if (> y max)
      (recur (inc x) min acc)
      (if (> x max)
        acc
        (let [p (* x y)]
          (recur x (inc y) (if (and (palindromic? p) (> p acc)) p acc)))))))

(defn solve
  "Prints out the largest palindromic number that is a multiple of two numbers 
   that are each between (:min data) and (:max data), inclusive. These numbers 
   default to 100 and 999 (i.e., 3-digit numbers), which makes the return value
   the solution to Project Euler problem 4."
  ([] (solve {}))
  ([data] (-> (find-max-palindrome
               (get data :min 100)
               (get data :max 999))
              println
              time)))
