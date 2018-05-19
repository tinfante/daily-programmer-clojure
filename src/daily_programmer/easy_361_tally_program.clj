(ns daily-programmer.easy-361-tally-program
  "https://www.reddit.com/r/dailyprogrammer/comments/8jcffg/20180514_challenge_361_easy_tally_program/"
  )


(defn tally
  [string]
  (loop [freqs (frequencies (filter #(Character/isLowerCase %) string))
         upper (frequencies (filter #(Character/isUpperCase %) string))]
    (if (empty? upper)
      freqs
      (recur (update freqs
                     (Character/toLowerCase (first (first upper)))
                     #(- (or % 0) (second (first upper)))
                     )
             (rest upper)
             ))))


(defn easy-361
  []
  (println (tally "abcde"))
  (println (tally "dbbaCEDbdAacCEAadcB"))
  (println (tally "EbAAdbBEaBaaBBdAccbeebaec"))
  )
