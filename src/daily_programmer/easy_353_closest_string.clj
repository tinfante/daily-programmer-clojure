(ns daily-programmer.easy-353-closest-string
  "https://www.reddit.com/r/dailyprogrammer/comments/826coe/20180305_challenge_353_easy_closest_string/"
  )


(defn hamming-distance
  "https://en.wikipedia.org/wiki/Hamming_distance"
  [first-string second-string]
  (if (= (count first-string) (count second-string))
    (let [fs (clojure.string/split first-string #"")
          ss (clojure.string/split second-string #"")]
      (reduce + (map #(if (= (% 0) (% 1)) 0 1) (map vector fs ss)))
      )
    nil
    ))


(defn leave-one-out-combinatorial
  ([string-input]
   (let [input-lines (clojure.string/split string-input #"\n")]
     (let [n-sequences (Integer. (first input-lines))
           sequences (vec (rest input-lines))]
       (if (not= n-sequences (count sequences))
         nil
         (leave-one-out-combinatorial
           sequences
           (rest sequences)
           {(first sequences) (filter #(not= (first sequences) %) sequences)}
           )))))
  ([sequences rest-sequences combinatorial-map]
   (if (empty? rest-sequences)
     combinatorial-map
     (leave-one-out-combinatorial
       sequences
       (rest rest-sequences)
       (conj combinatorial-map 
             {(first rest-sequences)
              (filter #(not= (first rest-sequences) %) sequences)}
             )))))


(defn all-vs-all-distance
  [combinatorial-map distance-func]
  (loop [combs combinatorial-map
         distances {}]
   (if (not (empty? combs))
     (recur
       (rest combs)
       (conj distances
             {((first combs) 0)
              (reduce + (map
                          #(distance-func ((first combs) 0) %)
                          ((first combs) 1)))}
             ))
     distances
     )))


(defn easy-353
  ([summed-cross-distance]
   (let [[sequence- summed-distance] (first summed-cross-distance)]
     (easy-353
       (rest summed-cross-distance)
       summed-distance
       [sequence-]
       )))
  ([rest-summed-cross-distance
    min-summed-cross-distance
    min-distance-sequences]
   (if (empty? rest-summed-cross-distance)
     min-distance-sequences
     (cond
       (< (last (first rest-summed-cross-distance)) min-summed-cross-distance)
         (easy-353 (rest rest-summed-cross-distance)
                   (last (first rest-summed-cross-distance))
                   [(first (first rest-summed-cross-distance))]
                   )
       ; Improvement over requirements specified for challange. This allows
       ; returning several sequences if they all have min cross-distance.
       ; This means return value is a vector, not a sequence string.
       (= (last (first rest-summed-cross-distance)) min-summed-cross-distance)
         (easy-353 (rest rest-summed-cross-distance)
                   min-summed-cross-distance
                   (conj min-distance-sequences (first (first rest-summed-cross-distance)))
                   )
       :default
         (easy-353 (rest rest-summed-cross-distance)
                   min-summed-cross-distance
                   min-distance-sequences
                   )))))


(def challange-input-1
"11
AACACCCTATA
CTTCATCCACA
TTTCAATTTTC
ACAATCAAACC
ATTCTACAACT
ATTCCTTATTC
ACTTCTCTATT
TAAAACTCACC
CTTTTCCCACC
ACCTTTTCTCA
TACCACTACTT"  
)

(def challange-input-2
"21
ACAAAATCCTATCAAAAACTACCATACCAAT
ACTATACTTCTAATATCATTCATTACACTTT
TTAACTCCCATTATATATTATTAATTTACCC
CCAACATACTAAACTTATTTTTTAACTACCA
TTCTAAACATTACTCCTACACCTACATACCT
ATCATCAATTACCTAATAATTCCCAATTTAT
TCCCTAATCATACCATTTTACACTCAAAAAC
AATTCAAACTTTACACACCCCTCTCATCATC
CTCCATCTTATCATATAATAAACCAAATTTA
AAAAATCCATCATTTTTTAATTCCATTCCTT
CCACTCCAAACACAAAATTATTACAATAACA
ATATTTACTCACACAAACAATTACCATCACA
TTCAAATACAAATCTCAAAATCACCTTATTT
TCCTTTAACAACTTCCCTTATCTATCTATTC
CATCCATCCCAAAACTCTCACACATAACAAC
ATTACTTATACAAAATAACTACTCCCCAATA
TATATTTTAACCACTTACCAAAATCTCTACT
TCTTTTATATCCATAAATCCAACAACTCCTA
CTCTCAAACATATATTTCTATAACTCTTATC
ACAAATAATAAAACATCCATTTCATTCATAA
CACCACCAAACCTTATAATCCCCAACCACAC"  
)

(defn main-
  []
  (println (easy-353 (all-vs-all-distance (leave-one-out-combinatorial challange-input-1) hamming-distance)))
  (println (easy-353 (all-vs-all-distance (leave-one-out-combinatorial challange-input-2) hamming-distance)))
  )
