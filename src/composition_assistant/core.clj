(ns composition-assistant.core
  (use clojure.set))

(def chromatic
  '[1 2 3 4 5 6 7 8 9 10 11 12])

(def n2c
  {'1 1
   'b2 2
   '2 3
   's2 4
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

(def ionian
  '[1 2 3 4 5 6 7])

(def mixo
  '[1 2 3 4 5 6 b7])

(def mixo-b6
  '[1 2 3 4 5 b6 b7])

(def mixo-b2-b6
  '[1 b2 3 4 5 b6 b7])

(def harmonic-major
  '[1 2 3 4 5 b6 7])

(def lydian
  '[1 2 3 s4 5 6 7])

(def mixo-s4
  '[1 2 3 s4 5 6 b7])

(def mixo-b2
  '[1 b2 3 4 5 6 b7])

(def lydian-s2
  '[1 s2 3 s4 5 6 7])

                                        ; Augmented type
(def ionian-s5
  '[1 2 3 4 s5 6 7])

(def lydian-s5
  '[1 2 3 s4 s5 6 7])

(def lydian-s2-s5
  '[1 s2 3 s4 s5 6 7])

                                        ; Minor type

(def aeolian
  '[1 2 b3 4 5 b6 b7])

(def harmonic-minor
  '[1 2 b3 4 5 b6 7])

(def melodic-minor
  '[1 2 b3 4 5 6 7])

(def melodic-minor-s4
  '[1 2 b3 s4 5 6 7])

(def dorian
  '[1 2 b3 4 5 6 b7])

(def dorian-b2
  '[1 b2 b3 4 5 6 b7])

(def dorian-s4
  '[1 b2 b3 s4 5 6 b7])

(def phrygian
  '[1 b2 b3 4 5 b6 b7])

(def phrygian-b4
  '[1 b2 b3 b4 5 b6 b7])

                                        ; diminished type scales

(def locrian
  '[1 b2 b3 4 b5 b6 b7])

(def locrian-b4
  '[1 b2 b3 b4 b5 b6 b7])

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
