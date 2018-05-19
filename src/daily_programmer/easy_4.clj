(ns daily-programmer.easy-4
  "https://www.reddit.com/r/dailyprogrammer/comments/pm6oj/2122012_challenge_4_easy/"
  )

(defn easy-4
  []
  (println "Generate how many passwords?") (def n (Integer. (read-line)))
  (println "With what length?") (def l (Integer. (read-line)))
  (loop [n- n]
    (if (= n- 0)
      (println n-)
      (recur (dec (n-)))
  )))
