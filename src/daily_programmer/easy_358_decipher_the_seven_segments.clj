(ns daily-programmer.easy-358-decipher-the-seven-segments
  "https://www.reddit.com/r/dailyprogrammer/comments/8eger3/20180423_challenge_358_easy_decipher_the_seven/"
  (:require [clojure.string :as s])
  )


(def test-inputs [
"
    _  _     _  _  _  _  _ 
  | _| _||_||_ |_   ||_||_|
  ||_  _|  | _||_|  ||_| _|",

"
    _  _  _  _  _  _  _  _ 
|_| _| _||_|| ||_ |_| _||_ 
  | _| _||_||_| _||_||_  _|",

"
 _  _  _  _  _  _  _  _  _ 
|_  _||_ |_| _|  ||_ | ||_|
 _||_ |_||_| _|  ||_||_||_|",

"
 _  _        _  _  _  _  _ 
|_||_ |_|  || ||_ |_ |_| _|
 _| _|  |  ||_| _| _| _||_ "
                  ])


(defn take-chars
  [n string]
  [(s/join "" (take n string)) (take-last (- (count string) n) string)])


(defn read-num
  [cl1 cl2 cl3]
  (if (= cl1 "   ")
    (if (= cl2 "  |") 1 4)
    (cond
      (= cl2 "| |") 0
      (= cl2 " _|") (if (= cl3 "|_ ") 2 3)
      (= cl2 "|_ ") (if (= cl3 " _|") 5 6)
      (= cl2 "  |") 7
      (= cl2 "|_|") (if (= cl3 "|_|") 8 9)
      )))


(defn read-nums
  [string]
  (loop [numbers []
        [l1 l2 l3] (filter (complement empty?) (s/split-lines string))]
    (if (empty? l1)
      numbers
      (let [[cl1 rl1] (take-chars 3 l1)
            [cl2 rl2] (take-chars 3 l2)
            [cl3 rl3] (take-chars 3 l3)]
        (recur (conj numbers (read-num cl1 cl2 cl3)) [rl1 rl2 rl3])
        ))))


(defn easy-358
  []
  (doseq [t test-inputs] (println (read-nums t))))
