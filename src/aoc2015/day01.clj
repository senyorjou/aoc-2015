(ns aoc2015.day01)


(def data (slurp "resources/day01.data"))


(defn parse-paren [paren]
  (case paren
      \( 1
      \) -1
      0))

(defn match-floor
  [coll val match]
  (when (= val match ))
  )


(defn count-parens
  [input]
  (->> input
       (map parse-paren)
       (reductions +)))


(defn p1 [data]
  (last (count-parens data)))

(defn p2 [data]
  (let [matching-floor -1]
    (->> data
         count-parens
         (keep-indexed #(when (= %2 matching-floor) %1))
         first
         inc)))
