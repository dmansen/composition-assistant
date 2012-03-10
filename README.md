# composition-assistant

A program for helping you figure out scales and chords.

## Usage

Play with it at the REPL. I use a jazz notation for scale tones, which consists of:

    1  tonic
    b2 minor 2nd
    2  major 2nd
    s2 sharp 2nd
    b3 minor 3rd
    3  major 3rd
    4  perfect 4th
    s4 augmented 4th
    b5 diminished 5th
    5  perfect 5th
    b6 minor 6th
    6  major 6th
    b7 diminished 7th
    7  minor 7th
    M7 major 7th

Form chords using vectors of these. For example, a major 7th chord built on the tonic looks like: '[1 3 5 M7]

All of the scales I know are enumerated in composition-assistant.scales. This allows you to do things like:

    user> (triad aeolian 3)
    [b3 5 7]

    user> (seventh ionian 5)
    [5 M7 2 4]

Or you can get a bit fancier and find, for example, all 7th chords in the major scale.

    user> (map #(seventh ionian %) (range 1 8))
    ([1 3 5 M7] [2 4 6 1] [3 5 M7 2] [4 6 1 3] [5 M7 2 4] [6 1 3 5] [M7 2 4 6])

There are also ways of finding intervals:

    user> (interval 1 'b3)
    minor-3
    user> (interval 2 7)
    minor-6
    user> (interval 'M7 4)
    augmented-4

Converting a vector of scale tones into chords:

    user> (seventh melodic-minor 1)
    [1 b3 5 M7]
    user> (notes-to-chord (seventh melodic-minor 1))
    minor-major-7th

    user> (seventh ionian 5)
    [5 M7 2 4]
    user> (notes-to-chord (seventh ionian 5))
    dominant-7th

    user> (map #(notes-to-chord (seventh dorian %)) (range 1 8))
    (minor-7th minor-7th major-7th dominant-7th minor-7th minor-7th-b5 major-7th)

## TODO

Convert these into actual notes on a piano. Might need to do this stupidly at first (it probably won't give you the correct enharmonic version of a pitch, but will be good enough).

## License

Copyright (C) 2012 Derek Mansen

Distributed under the Eclipse Public License, the same as Clojure.
