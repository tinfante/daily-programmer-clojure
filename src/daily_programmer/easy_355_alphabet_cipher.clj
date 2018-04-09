(ns daily-programmer.easy-355-alphabet-cipher
  "https://www.reddit.com/r/dailyprogrammer/comments/879u8b/20180326_challenge_355_easy_alphabet_cipher/"
  )


(defn split-str-chart [& lines] (vec (map #(clojure.string/split % #"") lines)))

(def substitution-chart (split-str-chart 
            "abcdefghijklmnopqrstuvwxyz"
            "bcdefghijklmnopqrstuvwxyza"
            "cdefghijklmnopqrstuvwxyzab"
            "defghijklmnopqrstuvwxyzabc"
            "efghijklmnopqrstuvwxyzabcd"
            "fghijklmnopqrstuvwxyzabcde"
            "ghijklmnopqrstuvwxyzabcdef"
            "hijklmnopqrstuvwxyzabcdefg"
            "ijklmnopqrstuvwxyzabcdefgh"
            "jklmnopqrstuvwxyzabcdefghi"
            "klmnopqrstuvwxyzabcdefghij"
            "lmnopqrstuvwxyzabcdefghijk"
            "mnopqrstuvwxyzabcdefghijkl"
            "nopqrstuvwxyzabcdefghijklm"
            "opqrstuvwxyzabcdefghijklmn"
            "pqrstuvwxyzabcdefghijklmno"
            "qrstuvwxyzabcdefghijklmnop"
            "rstuvwxyzabcdefghijklmnopq"
            "stuvwxyzabcdefghijklmnopqr"
            "tuvwxyzabcdefghijklmnopqrs"
            "uvwxyzabcdefghijklmnopqrst"
            "vwxyzabcdefghijklmnopqrstu"
            "wxyzabcdefghijklmnopqrstuv"
            "xyzabcdefghijklmnopqrstuvw"
            "yzabcdefghijklmnopqrstuvwx"
            "zabcdefghijklmnopqrstuvwxy"
            )
    )

(def message "thepackagehasbeendelivered")

(def secret-key "snitch")

(defn resize-secret
  ([message secret]
  (if (> (count message) (count secret))
    (resize-secret message secret secret)
    (clojure.string/join (take (count message) secret))
    )
   )
  ([message secret resized-secret]
   (if (> (- (count message) (count resized-secret)) (count secret))
     (resize-secret message secret (str resized-secret secret))
     (str
       resized-secret
       (clojure.string/join
         (take (- (count message) (count resized-secret)) secret)))
     )
   )
  )

(defn get-index
  ([letter]
   (let [alphabet (first substitution-chart)]
     (if (= letter (first alphabet))
       0
       (get-index letter 1 (rest alphabet))
       )
     )
   )
  ([letter index rest-alphabet]
   (if (= letter (first rest-alphabet))
     index
     (get-index letter (inc index) (rest rest-alphabet))
     )
   )
  )

(def memo-get-index (memoize get-index))

(defn encode-message
  ([message secret-key]
   (let [m (clojure.string/split message #"")
         s (clojure.string/split (resize-secret message secret-key) #"")]
     (encode-message
       (rest m)
       (rest s)
       (vector ((substitution-chart (memo-get-index (first m))) (memo-get-index (first s)))))
     )
   )
  ([rmsg rsec encoded]
   (if (empty? rmsg)
     (clojure.string/join encoded)
     (encode-message
       (rest rmsg)
       (rest rsec)
       (conj encoded ((substitution-chart (memo-get-index (first rmsg))) (memo-get-index (first rsec))))
       )
     )
   )
  )

(def challange-inputs
  [
   {:secret "bond" :message "theredfoxtrotsquietlyatmidnight"}
   {:secret "train" :message "murderontheorientexpress"}
   {:secret "garden" :message "themolessnuckintothegardenlastnight"}
   ]
  )

(defn easy-355
  []
  (doseq [x challange-inputs]
    (println "secret:" (x :secret))
    (println "message:" (x :message))
    (println "encoded:" (encode-message (x :message) (x :secret)))
    (println)
    )
  )
