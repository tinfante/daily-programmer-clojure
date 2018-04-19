(ns daily-programmer.easy-3
  "https://www.reddit.com/r/dailyprogrammer/comments/pkw2m/2112012_challenge_3_easy/"
  (:require [clojure.string :as str-])
  )

(def alphabet "qwertyuiopasdfghjklzxcvbnm1234567890 ,.!?")

(def message "hola, mundo cruel!")

(defn caesar-cipher
  [character amount add-sub-fn]
  (let [shifted_index (add-sub-fn (.indexOf alphabet character) amount)]
    (cond (>= shifted_index (count alphabet))
          (get alphabet (- shifted_index (count alphabet)))
          (<= shifted_index 0)
          (get alphabet (+ shifted_index (count alphabet)))
          :default (get alphabet shifted_index)
          )))

(defn rot13
  [message option]
  (let [chars- (str-/split message #"")]
    (cond (= option :encode)
          (str-/join "" (map #(caesar-cipher % 13 +) chars-))
          (= option :decode)
          (str-/join "" (map #(caesar-cipher % 13 -) chars-))
    )))

(defn easy-3
  []
  (let [encoded-msg (rot13 message :encode)]
    (println "message:" message)
    (println "encoded:" encoded-msg)
    (println "decoded:" (rot13 encoded-msg :decode))
    ))
