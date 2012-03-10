(ns composition-assistant.core
  (use clojure.set))

(def n2c
  {'1 0
   'b2 1
   '2 2
   's2 3
   'b3 3
   '3 4
   'b4 4
   '4 5
   's4 6
   'b5 6
   '5 7
   's5 8
   'b6 8
   '6 9
   'b7 9
   '7 10
   'M7 11})

(def note2position
  {'1 1
   'b2 2
   '2 2
   's2 2
   'b3 3
   '3 3
   'b4 4
   '4 4
   's4 4
   'b5 5
   '5 5
   's5 5
   'b6 6
   '6 6
   'b7 7
   '7 7
   'M7 7})

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

(def interval-to-scale-distance
  {'unity 0
   'minor-2 1
   'major-2 1
   'augmented-2 1
   'minor-3 2
   'major-3 2
   'diminished-4 3
   'perfect-4 3
   'augmented-4 3
   'diminished-5 4
   'perfect-5 4
   'augmented-5 4
   'minor-6 5
   'major-6 5
   'diminished-7 6
   'minor-7 6
   'major-7 6
   'octave 7})

(def interval-to-absolute-distance
  {'unity 0
   'minor-2 1
   'major-2 2
   'augmented-2 3
   'minor-3 3
   'major-3 4
   'diminished-4 4
   'perfect-4 5
   'augmented-4 6
   'diminished-5 6
   'perfect-5 7
   'augmented-5 8
   'minor-6 8
   'major-6 9
   'diminished-7 9
   'minor-7 10
   'major-7 11
   'octave 12})

(defn absolute-distance
  [fst snd]
  (let [fc (n2c fst)
        sc (n2c snd)]
    (if (> 0 (- sc fc))
      (- (+ sc 12) fc)
      (- sc fc))))

(defn scale-distance
  [fst snd]
  (let [fp (note2position fst)
        sp (note2position snd)]
    (if (> 0 (- sp fp))
      (- (+ sp 7) fp)
      (- sp fp))))

(defn interval
  [fst snd]
  (let [ab-dist (absolute-distance fst snd)
        sc-dist (scale-distance fst snd)]
    (if (some #{ab-dist} [3 4 6 8 9])
      ; need special logic for these enharmonic equivalents.
      ; they change based on their function in the scale ("scale distance")
      (cond
       (= ab-dist 3) (if (= sc-dist 1)
                       'augmented-2
                       'minor-3)
       (= ab-dist 4) (if (= sc-dist 2)
                       'major-3
                       'diminished-4)
       (= ab-dist 6) (if (= sc-dist 3)
                       'augmented-4
                       'diminished-5)
       (= ab-dist 8) (if (= sc-dist 5)
                       'minor-6
                       'augmented-5)
       (= ab-dist 9) (if (= sc-dist 5)
                        'major-6
                        'diminished-7))
      (interval-map ab-dist))))

(defn notes-to-chord
  "Converts a vector of notes into a chord. Only handles triads and sevenths currently."
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
      ; TODO This could be improved a whole lot with pattern matching
      (and (= i1 'major-3) (= i2 'minor-3) (= i3 'major-3)) 'major-7th
      (and (= i1 'major-3) (= i2 'major-3) (= i3 'minor-3)) 'augmented-7th
      (and (= i1 'major-3) (= i2 'minor-3) (= i3 'minor-3)) 'dominant-7th
      (and (= i1 'minor-3) (= i2 'major-3) (= i3 'minor-3)) 'minor-7th
      (and (= i1 'minor-3) (= i2 'major-3) (= i3 'major-3)) 'minor-major-7th
      (and (= i1 'minor-3) (= i2 'minor-3) (= i3 'major-3)) 'minor-7th-b5
      (and (= i1 'minor-3) (= i2 'minor-3) (= i3 'minor-3)) 'diminished-7th))))

(defn all-sevenths
  "Given a scale, gives you all the seventh chords in that scale."
  [scale]
  (map #(notes-to-chord (seventh scale %)) (range 1 8)))

(defn all-triads
  "Given a scale, gives you all the triads in that scale."
  [scale]
  (map #(notes-to-chord (triad scale %)) (range 1 8)))