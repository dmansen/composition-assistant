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
   'b4 5
   '4 6
   's4 7
   'b5 7
   '5 8
   's5 9
   'b6 9
   '6 10
   'b7 10
   '7 11
   'M7 12})

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
  {1 'unity
   2 'minor-2
   3 'major-2
   4 'minor-3
   5 'major-3
   6 'perfect-4
   7 'augmented-4
   8 'perfect-5
   9 'minor-6
   10 'major-6
   11 'minor-7
   12 'major-7
   13 'octave})

(defn absolute-distance
  [fst snd]
  (let [fc (n2c fst)
        sc (n2c snd)]
    (+ 1 (if (> 0 (- sc fc))
           (- (+ sc 12) fc)
           (- sc fc)))))

(defn scale-distance
  [fst snd]
  (let [fp (note2position fst)
        sp (note2position snd)]
    (+ 1 (if (> 0 (- sp fp))
           (- (+ sp 7) fp)
           (- sp fp)))))

(defn interval
  [fst snd]
  (let [ab-dist (absolute-distance fst snd)
        sc-dist (scale-distance fst snd)]
    (if (some #{ab-dist} [4 5 7 9 10])
      ; need special logic for these
      (cond
       (= ab-dist 4) (if (= sc-dist 2)
                       'augmented-2
                       'minor-3)
       (= ab-dist 5) (if (= sc-dist 3)
                       'major-3
                       'diminished-4)
       (= ab-dist 7) (if (= sc-dist 4)
                       'augmented-4
                       'diminished-5)
       (= ab-dist 9) (if (= sc-dist 6)
                       'minor-6
                       'augmented-5)
       (= ab-dist 10) (if (= sc-dist 6)
                        'major-6
                        'diminished-7))
      (interval-map ab-dist))))

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