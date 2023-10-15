(ns aoc2015.day06
  (:require [clojure.string :as str]))

(def data (str/split-lines (slurp "resources/day06.data")))

(defn turn-on-point [matrix [x y]]
  (assoc-in matrix [x y] true))

(defn create-base-matrix
  "creates inital 0-999 matrix"
  [initial]
  (zipmap (for [x (range 1000)
                y (range 1000)]
            [x y])
          (repeat initial)))


(defn create-input-matrix [[[x1 y1] [x2 y2]]]
  (for [x (range x1 (inc x2))
        y (range y1 (inc y2))]
    [x y]))


(defn parse-action [action]
  (case action
    "turn on" "on"
    "turn off" "off"
    "toggle"))

(defn parse-line [line]
  (let [[_ action x1 y1 x2 y2] (re-matches #"(\D+)(\d+),(\d+) through (\d+),(\d+)" line)]
    {:action (parse-action (str/trim action))
     :coords [[(parse-long x1) (parse-long y1)]
              [(parse-long x2) (parse-long y2)]]}))


(defn next-val-switch [prev action]
  (case (str/trim action)
    "toggle" (not prev)
    "on" true
    "off" false))


(defn next-val-gauge [prev action]
  (case (str/trim action)
    "toggle" (+ prev 2)
    "on" (inc prev)
    "off" (max 0 (dec prev))))



(defn process-line [matrix {:keys [action coords]} change-fn]
  (let [new-matrix (create-input-matrix coords)]
    (reduce (fn [m [x y]]
              (let [prev (get m [x y])
                    next (change-fn prev action)]
                (assoc m [x y] next)))
            matrix
            new-matrix)))

(defn process-matrix [matrix lines change-fn]
  (let [current-line (atom 1)]
    (reduce (fn [m line]
              (println (str "line: " @current-line " " line))
              (swap! current-line inc)
              (process-line m (parse-line line) change-fn))
            matrix lines)))


(defn p1 []
  (let [base-matrix (create-base-matrix false)]
    (->> (process-matrix base-matrix data next-val-switch)
         (filter (fn [[_ v]] (true? v)))
         count)))

(defn p2 []
  (let [base-matrix (create-base-matrix 0)]
    (->> (process-matrix base-matrix data next-val-gauge)
         vals
         (apply +))))
