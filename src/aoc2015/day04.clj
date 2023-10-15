(ns aoc2015.day04
  (:require [clojure.string :as str]
            [clj-commons.digest :as digest])
  (:import [java.security MessageDigest]))


(def seed "iwrupvqb")
(def algo (MessageDigest/getInstance "MD5"))


(defn md5 [^String s]
    (let [algorithm (MessageDigest/getInstance "MD5")
          raw (.digest algorithm (.getBytes s))]
      (format "%032x" (BigInteger. 1 raw))))

(defn mymd5 [^String s]
    (let [raw (.digest algo (.getBytes s))]
      (format "%032x" (BigInteger. 1 raw))))

(defn has-5-zeroes? [s]
  ;; (= (subs s 0 5) "00000"))
  (some? (re-find #"^00000" s)))


(defn has-5-bytes? [s]
  (when (= (first s) \0)
    (when (= (second s) \0)
      (= (subs s 0 5) "00000"))))


(defn has-6-zeroes? [s]
  (= (subs s 0 6) "000000"))
  ;; (some? (re-find #"^000000" s)))

(defn compose-number [seed n]
  (format "%s%d" seed n))


(defn p11 []
  (first (filter has-6-zeroes? (map #(digest/md5 (compose-number seed %)) (range)))))

(defn p1 []
  (loop [i 0
         hash (md5 (compose-number seed i))]

    (if (has-5-zeroes? hash)
      i
      (recur (inc i) (md5 (compose-number seed (inc i)))))))

(defn p2 []
  (loop [i 0
         hash (md5 (compose-number seed i))]

    (if (has-6-zeroes? hash)
      i
      (recur (inc i) (md5 (compose-number seed (inc i)))))))


(has-5-zeroes? (md5 "abcdef609043"))
