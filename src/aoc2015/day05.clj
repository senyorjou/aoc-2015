(ns aoc2015.day05
  (:require [clojure.string :as str]))


(def data (str/split-lines (slurp "resources/day05.data")))


(defn rule-3 [s]
  (not (re-find #"ab|cd|pq|xy" s)))

(defn rule-2 [s]
  (some? (re-find #"(.)\1{1,}" s)))

(defn rule-1 [s]
 (> (count (re-seq #"[aeiou]" s)) 2))


(defn valid-trio? [[a _ c]]
  (= a c))

(defn rule-5 [s]
  (let [trios (partition 3 1 s)]
    (not (empty? (filter valid-trio? trios)))))

(defn p1 []
  (->> data
       (filter rule-3)
       (filter rule-2)
       (filter rule-1)
       count))

(defn rule-4 [[pair & pairs]]
  (and
    pairs
    (or
      (some #{pair} (rest pairs))
      (recur pairs))))

(defn double_pair? [[pair & pairs]]
  (and
    pairs
    (or
      (some #{pair} (rest pairs))
      (recur pairs))))

(defn part2 [pwd]
  (and
    (->> pwd (partition 3 1) (some (fn [[a _ b]] (= a b))))
    (->> pwd (partition 2 1) double_pair?)))


(defn p2 []
  (->> data
       (filter rule-5)
       (partition 2 1)
       (filter rule-4)
       count))
