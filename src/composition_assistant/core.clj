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
   'b7 10
   '7 11
   'M7 12})

(def c2n (map-invert n2c))

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

(def interval-map
  {0 'unity
   1 'minor-2
   2 'major-2
   3 'minor-3
   4 'major-3
   5 'perfect-4
   6 'augmented-4
   7 'perfect-5
   8 'minor-6
   9 'major-6
   10 'minor-7
   11 'major-7
   12 'octave})

(defn interval
  [fst snd]
  (let [fc (n2c fst)
        sc (n2c snd)
        dist (if (> 0 (- sc fc))
               (- (+ sc 12) fc)
               (- sc fc))]
    (interval-map dist)))

(defn notes-to-chord
  [chord]
  (cond
   (= 3 (count chord)) 
   (let [[one two three] chord
         i1 (interval one two)
         i2 (interval two three)]
     (cond (and (= i1 'major-3) (= i2 'minor-3)) 'major
           (and (= i1 'minor-3) (= i2 'major-3)) 'minor
           (and (= i1 'minor-3) (= i2 'minor-3)) 'diminished
           (and (= i1 'major-3) (= i2 'major-3)) 'augmented))
   (= 4 (count chord))
   (let [[one two three seventh] chord
         i1 (interval one two)
         i2 (interval two three)
         i3 (interval three seventh)]
     (cond
      (and (= i1 'major-3) (= i2 'minor-3) (= i3 'major-3)) 'major-7th
      (and (= i1 'major-3) (= i2 'major-3) (= i3 'minor-3)) 'augmented-7th
      (and (= i1 'major-3) (= i2 'minor-3) (= i3 'minor-3)) 'dominant-7th
      (and (= i1 'minor-3) (= i2 'major-3) (= i3 'minor-3)) 'minor-7th
      (and (= i1 'minor-3) (= i2 'major-3) (= i3 'major-3)) 'minor-major-7th
      (and (= i1 'minor-3) (= i2 'minor-3) (= i3 'major-3)) 'minor-7th-b5
      (and (= i1 'minor-3) (= i2 'minor-3) (= i3 'minor-3)) 'diminished-7th))))