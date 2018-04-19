(ns daily-programmer.easy-3
  "https://www.reddit.com/r/dailyprogrammer/comments/pkw2m/2112012_challenge_3_easy/"
  (:require [clojure.string :as str-])
  )

(def alphabet "qwertyuiopasdfghjklzxcvbnm1234567890 ,.!?")

(def message "hola, mundo cruel!")

(defn caesar-shift
  [character amount]
  (let [shifted_index (+ (.indexOf alphabet character) amount)]
    (if (> shifted_index (dec (count alphabet)))
      (get alphabet (- shifted_index (count alphabet)))
      (get alphabet shifted_index)
      )))

(defn caesar-unshift
  [character amount]
  (let [shifted_index (- (.indexOf alphabet character) amount)]
    (if (<= shifted_index 0)
      (get alphabet (+ shifted_index (count alphabet)))
      (get alphabet shifted_index)
      )))

(defn rot13
  [message option]
  (let [chars- (str-/split message #"")]
    (cond (= option :encode) (str-/join "" (map #(caesar-shift % 13) chars-))
          (= option :decode) (str-/join "" (map #(caesar-unshift % 13) chars-))
    )))
    

(defn easy-3
  []
  (let [encoded-msg (rot13 message :encode)]
    (println "message:" message)
    (println "encoded:" encoded-msg)
    (println "decoded:" (rot13 encoded-msg :decode))
    ))
