(ns aoc2015.day03
  (:require [clojure.string :as str]
            [clojure.set :refer [union]]))


(def data (str/trim (slurp "resources/day03.data")))

(def conv-table
  {\^ [0 1]
   \> [1 0]
   \< [-1 0]
   \v [0 -1]})


(defn sum-pairs [a b]
  (map + a b))


(defn p1 []
  (let [initial [0 0]]
    (->> data
         (map conv-table)
         (reductions sum-pairs initial)
         set
         count)))


(defn p2 [data]
  (let [stream        (map conv-table data)
        santa-stream  (take-nth 2 stream)
        santa-pairs   (reductions sum-pairs santa-stream)
        robo-stream   (take-nth 2 (drop 1 stream))
        robo-pairs    (reductions sum-pairs robo-stream)]
    (count (set (union santa-pairs robo-pairs)))))
