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
    diminished-5

Note that the interval function should correctly take into account scale distance (it probably doesn't handle all cases right now, but I'll add them as they come up).

    user> (interval 1 's4)
    augmented-4
    user> (interval 1 'b5)
    diminished-5

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

The coolest part of this is in the composition-assistant.absolute package. (It's also terribly written as of right now.) Given pitch-agnostic vectors of notes and a key, you can convert them. This will do all the correct theory stuff.

    user> (notes-to-pitches ionian 'C)
    (C D E F G A B)
    user> (notes-to-pitches mixo 'G)
    (G A B C D E F)
    user> (notes-to-pitches mixo-b2-b6 'C#)
    (C# D E# F# G# A B)
    user> (notes-to-pitches aeolian 'Bb)
    (Bb C Db Eb F Gb Ab)

Or, if you just want to skip all this and get pitches in a scale + the corresponding 7ths:

    user> (roots-and-chords lydian 'C)
    ([C major-7th (C E G B)] [D dominant-7th (C E G Bb)] [E minor-7th (C Eb G Bb)] 
     [F# minor-7th-b5 (C Eb Gb Bb)] [G major-7th (C E G B)] [A minor-7th (C Eb G Bb)] [B minor-7th (C Eb G Bb)])
    user> (roots-and-chords locrian-b7 'D)
    ([D diminished-7th] [Eb major-7th] [F minor-7th-b5] [G minor-7th] 
     [Ab minor-major-7th] [Bb dominant-7th] [Cb augmented-7th])

The testing is nonexistent (so I wouldn't use it for your homework), but I haven't seen any problems so far.

## License

Copyright (C) 2012 Derek Mansen

Distributed under the Eclipse Public License, the same as Clojure.
