RhythML
=======

RhythML is a domain-specific language for representing drum rhythms as part of my Part II project at Cambridge University, entitled 'A Domain Specific Language for Live Coding Drum Rhythms'.

RhythML can be used to create drum rhythms live using Clojure, Leiningen and the live-coding toolkit Overtone.

Setup
-----
1. Install [Leiningen] [1] and create a new project.

2. Add the following dependencies to your `project.clj`:

        [org.clojure/clojure "1.5.1"]
        [overtone "0.9.1"]
        [instaparse "1.2.14"]
        
3. Clone the RhythML repository and copy `src/rhythml` into the Leiningen project `src` directory.

4. Pull in dependencies (`lein deps`) start a REPL in the project (`lein repl`).

5. Load the RhythML source file: `(use 'rhythml.core)`.

The Language
------------
RhythML takes inspiration from drum tablature, so drummers should be able to understand the code fairly quickly. Here's some example code for a simple 4/4 rhythm:

````
B :|o---|
SN:|--o-|
HH:|xxxx|
````

Every rhythm is split into individual instrument lines. An instrument line is of the following form:

````
<id> : |<beats>|
````

Three instruments are available at present and have the following IDs:

<table>
  <tr>
    <th>ID</th><th>Instrument</th>
  </tr>
  <tr>
    <td>B</td><td>Bass/Kick drum</td>
  </tr>
  <tr>
    <td>SN</td><td>Snare drum</td>
  </tr>
  <tr>
    <td>HH</td><td>Closed hi-hat</td>
  </tr>
</table>

The beats of each instrument are represented of character strings, where each character represents a beat. Different characters make different sounds (or no sound at all):

<table>
  <tr>
    <th>Char</th><th>Effect</th>
  </tr>
  <tr>
    <td>o</td><td>Play drum</td>
  </tr>
  <tr>
    <td>x</td><td>Play cymbal</td>
  </tr>
  <tr>
    <td>-</td><td>Rest</td>
  </tr>
</table>

Whitespace is ignored except for new lines at the start of a rhythm and whitespace in the beats string for an instrument.

Making Rhythms
--------------
To start using RhythML in Overtone, call `(start-rhythm)`. Nothing will play back when it's started, so we'd better make some rhythms.

Three functions are used to create rhythms, although they do not all need to be used at once.

* `(add-rhythm bpm rml)`, where `bpm` is an integer representing the number of beats per minute the rhythm should be played at and `rml` is a string of RhythML code, compiles the rhythm and plays it straight away.
* `(make-rhythm bpm rml)`, where `bpm` is an integer representing the number of beats per minute the rhythm should be played at and `rml` is a string of RhythML code, compiles the rhythm and returns it as a Clojure map, but does not play it back.
* `(update-rhythm map)`, where `map` is Clojure map returned by `(make-rhythm)`, plays back the rhythm represented by `map`.

Rhythms can also be concatenated/appended and merged using:

* `(concat-rhythm a b c...)`, where all arguments (which can be of any number) are valid rhythm maps, returns a rhythm equivalent to the arguments appended together. For example: the result of `(concat-rhythm (make-rhythm 200 "B:|o-") (make-rhythm 200 "SN:|o-|"))` is the same as `(make-rhythm 200 "B:|o---| SN:|---o|")`.
* `(merge-rhythm a b c...)`, where all arguments (which can be of any number) are valid rhythm maps, returns a rhythm equivalent to the arguments merged together and of the smallest length of all arguments. For example: the result of `(merge-rhythm (make-rhythm 200 "B:|o-") (make-rhythm 200 "SN:|-o|"))` is the same as `(make-rhythm 200 "B:|o-| SN:|-o|")`.

Editing Rhythms
---------------

Rhythms can be edited after creation by replacing instrument lines individually using `(edit-rhythm)`. For example, the following function returns a rhythm map of two instruments (kick and snare):

`(def foo (make-rhythm 200 "B:|o---| SN:|--o-|"))`

Instrument lines can be changed individually by providing new RhythML code for that instrument:

`(edit-rhythm foo "B:|o-o-|")`

This returns a new map containing the same information as `foo` but with an amended bass drum. 

To update a rhythm already playing, `(edit-live-rhythm)` can be used  without passing a map as an argument. For example:

`(edit-live-rhythm "B:|o-o-|")`


  [1]: https://github.com/technomancy/leiningen        "Leiningen"
