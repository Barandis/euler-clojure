;; Copyright (c) 2021 Thomas J. Otterson
;; 
;; This software is released under the MIT License.
;; https://opensource.org/licenses/MIT

;; Solves Project Euler problem 8:
;;
;; The four adjacent digits in the 1000-digit number that have the greatest
;; product are 9 × 9 × 8 × 9 = 5832.
;;
;;            73167176531330624919225119674426574742355349194934
;;            96983520312774506326239578318016984801869478851843
;;            85861560789112949495459501737958331952853208805511
;;            12540698747158523863050715693290963295227443043557
;;            66896648950445244523161731856403098711121722383113
;;            62229893423380308135336276614282806444486645238749
;;            30358907296290491560440772390713810515859307960866
;;            70172427121883998797908792274921901699720888093776
;;            65727333001053367881220235421809751254540594752243
;;            52584907711670556013604839586446706324415722155397
;;            53697817977846174064955149290862569321978468622482
;;            83972241375657056057490261407972968652414535100474
;;            82166370484403199890008895243450658541227588666881
;;            16427171479924442928230863465674813919123162824586
;;            17866458359124566529476545682848912883142607690042
;;            24219022671055626321111109370544217506941658960408
;;            07198403850962455444362981230987879927244284909188
;;            84580156166097919133875499200524063689912560717606
;;            05886116467109405077541002256983155200055935729725
;;            71636269561882670428252483600823257530420752963450
;;
;; Find the thirteen adjacent digits in the 1000-digit number that have the
;; greatest product. What is the value of this product?

;; To solve this problem, we first create a sequence of all of the possible
;; substrings (consecutive characters) of the desired length that are present in
;; the input. The product of each string in this sequence is then determined by
;; splitting the string into 1-length strings for each character, converting
;; each string into an integer, and multiplying those integers together.
;; Finally, the largest product is displayed.
;;
;; This solution can be run using `clojure -X:p8`. It will default to substrings
;; of length 13 in the rather large number shown above, as described in the
;; problem. To run with another input, use `clojure -X:p7 :input
;; 23257530420752963450 :size 4` or similar. (:input can be either a number or a
;; string.)

(ns barandis.euler.p8)

(def ^:private problem-8-number
  "The 1000-digit number from Project Euler problem 8, as a string."
  (str "73167176531330624919225119674426574742355349194934"
       "96983520312774506326239578318016984801869478851843"
       "85861560789112949495459501737958331952853208805511"
       "12540698747158523863050715693290963295227443043557"
       "66896648950445244523161731856403098711121722383113"
       "62229893423380308135336276614282806444486645238749"
       "30358907296290491560440772390713810515859307960866"
       "70172427121883998797908792274921901699720888093776"
       "65727333001053367881220235421809751254540594752243"
       "52584907711670556013604839586446706324415722155397"
       "53697817977846174064955149290862569321978468622482"
       "83972241375657056057490261407972968652414535100474"
       "82166370484403199890008895243450658541227588666881"
       "16427171479924442928230863465674813919123162824586"
       "17866458359124566529476545682848912883142607690042"
       "24219022671055626321111109370544217506941658960408"
       "07198403850962455444362981230987879927244284909188"
       "84580156166097919133875499200524063689912560717606"
       "05886116467109405077541002256983155200055935729725"
       "71636269561882670428252483600823257530420752963450"))

(defn- substrings
  "Creates a sequence of all of the strings of `size` consecutive characters
   that can be found in the input string `s`."
  [s size]
  (loop [s s acc []]
    (if (< (count s) size)
      acc
      (recur (subs s 1) (conj acc (subs s 0 size))))))

(defn- max-product
  "Calculates the products of the digits of each of the strings in the input
   sequence and returns the largest of them."
  [s size]
  (letfn [(map-reduce [f1 f2 coll] (->> coll (map f1) (reduce f2)))
          (product [s] (map-reduce #(Character/digit % 10) * s))]
    (map-reduce product max (substrings s size))))

(defn solve
  "Displays the maximum product derived from the digits of all possible
   substrings of length (:size data) that can be taken from the input
   (:input data), which can be either a number or a string of digits. :size
   defaults to 13 and :input to the 1000-digit input string shown on the page
   for Project Euler problem 8, which makes the displayed value the solution
   to that problem."
  ([] (solve {}))
  ([data] (-> (max-product (str (get data :input problem-8-number))
                           (get data :size 13))
              println
              time)))
