(ns composition-assistant.core
  (use clojure.set))

(def chromatic
  '[1 2 3 4 5 6 7 8 9 10 11 12])

(def n2c
  {'1 1
   'b2 2
   '2 3
   'b3 4
   '3 5
   '4 6
   's4 7
   'b5 7
   '5 8
   'b6 9
   '6 10
   'b7 11
   '7 12})

(def c2n (map-invert n2c))

(def aeolian
  '[1 2 3 4 5 6 7])

(def mixolydian
  '[1 2 3 4 5 6 b7])

(def mixo-b6
  '[1 2 3 4 5 b6 b7])

(def mixo-b2-b6
  '[1 b2 3 4 5 b6 b7])

(defn chord-to-chromatic
  [c]
  (map n2c c))

(defn chromatic-to-chord
  [c]
  (map c2n c))

(defn triad
  [scale starting-note]
  (let [real-starting (- starting-note 1)
        one (nth scale real-starting)
        two (nth (concat scale scale) (+ 2 real-starting))
        three (nth (concat scale scale) (+ 4 real-starting))]
    [one two three]))

(defn seventh
  [scale starting-note]
  (let [real-starting (- starting-note 1)
        one (nth scale real-starting)
        two (nth (concat scale scale) (+ 2 real-starting))
        three (nth (concat scale scale) (+ 4 real-starting))
        seven (nth (concat scale scale) (+ 6 real-starting))]
    [one two three seven]))
