;; Copyright (c) 2021 Thomas J. Otterson
;; 
;; This software is released under the MIT License.
;; https://opensource.org/licenses/MIT

;; Solves Project Euler problem 17:
;;
;; If the numbers 1 to 5 are written out in words: one, two, three, four, five,
;; then there are 3 + 3 + 5 + 4 + 4 = 19 letters used in total.
;;
;; If all the numbers from 1 to 1000 (one thousand) inclusive were written out
;; in words, how many letters would be used?
;;
;; NOTE: Do not count spaces or hyphens. For example, 342 (three hundred and
;; forty-two) contains 23 letters and 115 (one hundred and fifteen) contains 20
;; letters. The use of "and" when writing out numbers is in compliance with
;; British usage.

;; Don't like this problem. If I wanted to deal with grammar I'd probably not
;; have become a computer programmer.
;;
;; No words are constructed; rather, the number of letters in the words are what
;; is considered. There's nothing fancy here, it's all straightforward working
;; out how many letters are represented by one digit in each number and then
;; adding them all together.
;;
;; I did not want to spend any more time on this problem than necessary, so it
;; works up to the Project Euler target number (1000) and no higher. This is
;; not a general solution, which is the first time I've done that.
;;
;; This solution can be run using `clojure -X:p17`. It will default to the 1000
;; target described in the problem. To run with another target, use `clojure
;; -X:p17 :target 115` or similar. Be aware that this program will report
;; incorrect solutions for anything over 1000.

(ns barandis.euler.p17)

(defn- ones
  "Calculates the number of letters in the word for digit character `n`. Returns
   0 for character 0 because 'zero' isn't written in numbers anyway."
  [n]
  (case n \0 0 \1 3 \2 3 \3 5 \4 4 \5 4 \6 3 \7 5 \8 5 \9 4 0))

(defn- teens
  "Calculates the number of letters in the word for the number between 10 and
   19 -ending- with the digit character `n`."
  [n]
  (case n \0 3 \1 6 \2 6 \3 8 \4 8 \5 7 \6 7 \7 9 \8 8 \9 8 0))

(defn- tens
  "Calculates the number of letters in the word for the digit character `n` when
   it's multiplied by 10."
  [n]
  (case n \0 0 \1 3 \2 6 \3 6 \4 5 \5 5 \6 5 \7 7 \8 6 \9 6 0))

(defn- hundreds
  "Calculates the number of letters in the word for the digit character `n` when
   it's multiplied by 100."
  [n]
  (+ (ones n) 7))

(defn- num-letters
  "Calculates the number of letters in a number of up to three digits. Will
   also work correctly when `n` is 1000, but no higher. Conforms to British
   English standards by accounting for the unnecessary 'and' after the hundreds
   place in any number that isn't an even hundred."
  [n]
  (let [numstr (str n)]
    (case (count numstr)
      4 11  ; "one thousand"
      3 (let [base (+ (hundreds (first numstr))
                      (if (= \1 (second numstr))
                        (teens (last numstr))
                        (+ (tens (second numstr)) (ones (last numstr)))))]
          (if (= \0 (second numstr) (last numstr))
            base
            (+ base 3))) ; because "and", whatever
      2 (if (= \1 (first numstr))
          (teens (second numstr))
          (+ (tens (first numstr)) (ones (second numstr))))
      1 (ones (first numstr))
      0)))

(defn- total-letters
  "Calculates the total number of letters in all of the numbers from 1 to `n`
   inclusive. Only works up to `n` = 1000."
  [n]
  (->> n inc (range 1) (reduce #(+ %1 (num-letters %2)) 0)))

(defn solve
  "Displays the total number of letters in all of the numbers from 1 to
   (:target data) inclusive. This number defaults to 1000, which is also the
   maximum number for which this works, and which makes the displayed value the
   solution to Project Euler problem 17."
  ([] (solve {}))
  ([data] (-> (get data :target 1000) total-letters println time)))

