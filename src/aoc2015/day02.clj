(ns aoc2015.day02
  (:require [clojure.string :as str]))


(def data (str/split-lines (slurp "resources/day02.data")))


(defn calculate-wrap [input]
  (let [[x y z] (map #(Integer/parseInt %) (str/split input #"x"))
        [a b]   (sort [x y z])
        area    (+ (* 2 x y) (* 2 x z) (* 2 y z))
        total   (+ area (* a b))]
    total))

(defn ribbonize [input]
  (let [[x y z] (map #(Integer/parseInt %) (str/split input #"x"))
        [a b]   (sort [x y z])
        wrap    (+ a a b b)
        bow     (* x y z)]
    (+ wrap bow)))

(defn p1 [data]
  (->> data
       (map calculate-wrap)
       (reduce +)))

(defn p2 [data]
  (->> data
       (map ribbonize)
       (reduce +)))
