(ns composition-assistant.absolute
  (use composition-assistant.core))

(def pitch-to-chromatic
  {'C 0
   'B# 0
   'Dbb 0
   'C# 1
   'Db 1
   'B## 1
   'D 2
   'C## 2
   'Ebb 2
   'D# 3
   'Eb 3
   'Fbb 3
   'E 4
   'Fb 4
   'D## 4
   'F 5
   'E# 5
   'Gbb 5
   'F# 6
   'Gb 6
   'E## 6
   'G 7
   'F## 7
   'Abb 7
   'G# 8
   'Ab 8
   'A 9
   'G## 9
   'Bbb 9
   'A# 10
   'Bb 10
   'Cbb 10
   'B 11
   'Cb 11
   'A## 11})

(def associated-pitch
  {'C 'C
   'B# 'B
   'Dbb 'D
   'C# 'C
   'Db 'D
   'B## 'B
   'D 'D
   'C## 'C
   'Ebb 'E
   'D# 'D
   'Eb 'E
   'Fbb 'F
   'E 'E
   'Fb 'F
   'D## 'D
   'F 'F
   'E# 'E
   'Gbb 'G
   'F# 'F
   'Gb 'G
   'E## 'E
   'G 'G
   'F## 'F
   'Abb 'A
   'G# 'G
   'Ab 'A
   'A 'A
   'G## 'G
   'Bbb 'B
   'A# 'A
   'Bb 'B
   'Cbb 'C
   'B 'B
   'Cb 'C
   'A## 'A})

(def chromatic-to-pitches
  {0 '{C C B B# D Dbb}
   1 '{C C# D Db B B##}
   2 '{D D C C## E Ebb}
   3 '{D D# E Eb F Fbb}
   4 '{E E F Fb D D##}
   5 '{F F E E# G Gbb}
   6 '{F F# G Gb E E##}
   7 '{F F## A Abb G G}
   8 '{G G# A Ab}
   9 '{A A G G## B Bbb}
   10 '{A A# B Bb C Cbb}
   11 '{B B C Cb A A##}})

(def note2c
  {'1 'C
   'b2 'Db
   '2 'D
   's2 'D#
   'b3 'Eb
   '3 'E
   'b4 'Fb
   '4 'F
   's4 'F#
   'b5 'Gb
   '5 'G
   's5 'G#
   'b6 'Ab
   '6 'A
   'b7 'Bbb
   '7 'Bb
   'M7 'B})

(def sharp
  {'C 'C#
   'B# 'B##
   'Dbb 'Db
   'C# 'C##
   'Db 'D
   'D 'D#
   'Ebb 'Eb
   'D# 'D##
   'Eb 'E
   'Fbb 'Fb
   'E 'E#
   'Fb 'F
   'F 'F#
   'E# 'E##
   'Gbb 'Gb
   'F# 'F##
   'Gb 'G
   'G 'G#
   'Abb 'Ab
   'G# 'G##
   'Ab 'A
   'A 'A#
   'Bbb 'Bb
   'A# 'A##
   'Bb 'B
   'Cbb 'Cb
   'B 'B#
   'Cb 'C})

(def flat
  {'C 'Cb
   'B# 'B
   'C# 'C
   'Db 'Dbb
   'B## 'B#
   'D 'Db
   'C## 'C#
   'D# 'D
   'Eb 'Ebb
   'E 'Eb
   'Fb 'Fbb
   'D## 'D#
   'F 'Fb
   'E# 'E
   'F# 'F
   'Gb 'Gbb
   'E## 'E#
   'G 'Gb
   'F## 'F#
   'G# 'G
   'Ab 'Abb
   'A 'Ab
   'G## 'G#
   'A# 'A
   'Bb 'Bbb
   'B 'Bb
   'Cb 'Cbb
   'A## 'A#})

(defn rotate-until-first
  [elem coll]
  (if (= elem (first coll))
    coll
    (recur elem (concat (rest coll) [(first coll)]))))

(defn base-pitches
  [start]
  (rotate-until-first start '[C D E F G A B]))

(defn each-pair
  [f coll]
  (loop [accum []
         c coll]
    (if (or (empty? c) (= 1 (count c)))
     accum
     (recur (conj accum (f (first c) (second c)))
            (rest c)))))

(defn intervals
  [scale]
  (each-pair interval scale))

(defn add-interval
  [pitch interval]
  (let [absolute-distance (interval-to-absolute-distance interval)
        base-pitches (base-pitches (associated-pitch pitch))
        scale-distance (interval-to-scale-distance interval)
        added-base-pitch (nth base-pitches scale-distance)
        added-chromatic (mod (+ (pitch-to-chromatic pitch) absolute-distance) 12)]
    ((chromatic-to-pitches added-chromatic) added-base-pitch)))

(defn scale-to-pitches-iter
  [accum intervals]
  (if (empty? intervals)
    accum
    [(concat accum [(add-interval (last accum) (first intervals))])
     (rest intervals)]))

(defn notes-to-pitches
  [notes starting-note]
  (loop [accum [starting-note]
         intervals (intervals notes)]
    (if (empty? intervals)
      accum
      (let [[a i] (scale-to-pitches-iter accum intervals)]
        (recur a i)))))

(defn roots-and-chords
  [scale starting-note]
  (map (fn [x y] [x y]) (notes-to-pitches scale starting-note) (all-sevenths scale)))