(ns composition-assistant.scales)

; major type scales

(def ionian
  '[1 2 3 4 5 6 M7])

(def mixo
  '[1 2 3 4 5 6 7])

(def mixo-b6
  '[1 2 3 4 5 b6 7])

(def mixo-b2-b6
  '[1 b2 3 4 5 b6 7])

(def harmonic-major
  '[1 2 3 4 5 b6 M7])

(def lydian
  '[1 2 3 s4 5 6 M7])

(def mixo-s4
  '[1 2 3 s4 5 6 7])

(def mixo-b2
  '[1 b2 3 4 5 6 7])

(def lydian-s2
  '[1 s2 3 s4 5 6 M7])

; Augmented type

(def ionian-s5
  '[1 2 3 4 s5 6 M7])

(def lydian-s5
  '[1 2 3 s4 s5 6 M7])

(def lydian-s2-s5
  '[1 s2 3 s4 s5 6 M7])

; Minor type

(def aeolian
  '[1 2 b3 4 5 b6 7])

(def harmonic-minor
  '[1 2 b3 4 5 b6 M7])

(def melodic-minor
  '[1 2 b3 4 5 6 M7])

(def melodic-minor-s4
  '[1 2 b3 s4 5 6 M7])

(def dorian
  '[1 2 b3 4 5 6 7])

(def dorian-b2
  '[1 b2 b3 4 5 6 7])

(def dorian-s4
  '[1 b2 b3 s4 5 6 7])

(def phrygian
  '[1 b2 b3 4 5 b6 7])

(def phrygian-b4
  '[1 b2 b3 b4 5 b6 7])

; Diminished type

(def locrian
  '[1 b2 b3 4 b5 b6 7])

(def locrian-b4
  '[1 b2 b3 b4 b5 b6 7])

(def locrian-b4-b7
  '[1 b2 b3 b4 b5 b6 b7])

(def locrian-b7
  '[1 b2 b3 4 b5 b6 b7])

(def locrian-6
  '[1 b2 b3 4 b5 6 7])

(def locrian-2
  '[1 2 b3 4 b5 b6 7])

(def locrian-2-6
  '[1 2 b3 4 b5 6 7])
