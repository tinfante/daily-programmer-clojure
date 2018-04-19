(ns daily-programmer.easy-1
  "https://www.reddit.com/r/dailyprogrammer/comments/pih8x/easy_challenge_1/"
  (:require [clojure.java.io :as io])
  )

(defn easy-1
  []
  (println "What's your name?") (def name- (read-line))
  (println "What's your age?") (def age (read-line))
  (println "What's your username?") (def uname (read-line))
  (println (str "\nYour name is " name- ","
                " you are " age " years old,"
                " and your username is " uname "."))

  ; bonus 
  (if (not (.isDirectory (io/file "src/daily_programmer/output")))
    (.mkdir (io/file "src/daily_programmer/output")))
  (spit
    "src/daily_programmer/output/easy-1.txt"
    (str "name: " name- "\nage: " age "\nusername: " uname )
    ))
