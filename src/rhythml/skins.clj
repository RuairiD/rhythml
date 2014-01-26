(ns rhythml.skins
  (:require [instaparse.core :as insta])
  (:import [rhythml MapGenerator])
  (:use [overtone.live] [overtone.inst.drum]))
		
(defn vector-to-array-list 
	"Converts a Clojure vector to a Java ArrayList. If the vector contains vectors as elements, those vectors will be recursively converted to Java ArrayLists."
	[xs]
	(let [result (java.util.ArrayList.) len (count xs)]
		(loop [i 0] (when (< i len)
			(let [x (get xs i)]
				(if (vector? x) (.add result (vector-to-array-list x)) (.add result x)))
			(recur (inc i)))) 
		result))
		
(def grammar "
	p : rhy ;
	rhy : <WS> inst <WS> rhy <WS> | inst;
	inst : Id <WS> ':' <WS> bar;
	Id : 'B' | 'SN' | 'HT' | 'LT' | 'FT' | 'HH' | 'Rd' | 'CC' ;
	bar : '|' beats '|' ;
	beats : Beat beats | Beat ;
	Beat : 'x' | 'X' | 'o' | 'O' | '-' ;
	WS : #'\\s*' ;")	

(def parse-rhythm
  (insta/parser grammar))
  
(defn bd [] (kick))
(defn ch [] (closed-hat))
(defn sn [] (snare 405 1 0.1 0.1 0.25 40 1000))
	
(def rhy-ref (ref {}))

(def rhy {
	:interval 200,
	:length 4, 
	:beats [
		{:count 0, :instruments [bd ch]}
		{:count 1, :instruments [ch]}
		{:count 2, :instruments [ch sn]}
		{:count 3, :instruments [ch]}]})

(def rhy2 {
	:interval 200,
	:length 4, 
	:beats [
		{:count 0, :instruments [bd ch]}
		{:count 1, :instruments [bd]}
		{:count 2, :instruments []}
		{:count 3, :instruments [bd]}]})

(def rhy3 {
	:interval 100,
	:length 8,
	:beats [
		{:count 0, :instruments [bd ch]}
		{:count 1, :instruments [ch]}
		{:count 2, :instruments [ch]}
		{:count 3, :instruments [ch]}
		{:count 4, :instruments [ch sn]}
		{:count 5, :instruments [ch]}
		{:count 6, :instruments [ch]}
		{:count 7, :instruments [ch]}
		{:count 8, :instruments [ch]}]})

(defn to-clojure-map 
	"Convert Java HashMap to native Clojure map. Both maps must match the specification for a rhythm map."
	[hash-map] {
		:interval (get hash-map "interval")
		:length (get hash-map "length")
		:beats (into [] (map 
			(fn [beat] {
				:count (get beat "count") 
				:instruments (into [] (map 
					(fn [ins] (ns-resolve 'rhythml.skins (symbol ins))) 
					(into [] (get beat "instruments"))))})
			(into [] (get hash-map "beats"))))})

(defn make-rhythm 
	"Compiles a string of RhythML code and the tempo of the rhythm and returns a map representing the rhythm. The rhythm is not played by this function but can be played back using update-rhythm."
	[bpm rml] 
	(to-clojure-map (MapGenerator/getMap bpm (vector-to-array-list (parse-rhythm rml)))))
	
(defn update-rhythm 
	"Updates the rhythm currently being played to a map representation of a rhythm passed as an argument."
	[r]
	(dosync (ref-set rhy-ref r)))
	
(defn add-rhythm 
	"Compiles a string of RhythML code and the tempo of the rhythm and plays the rhythm immediately."
	[bpm rml] 
	(update-rhythm (make-rhythm bpm rml)))
		
(defn concat-rhythm
	"Concatenates two or more rhythm maps and returns the resulting map. Rhythms are concatenated in order from left to right. In the case that the rhythms have different invervals, the interval of the result will match that of the first argument."
	([map1 map2] {
		:interval (get map1 :interval)
		:length (+ (get map1 :length) (get map2 :length))
		:beats (into [] 
			(concat 
				(get map1 :beats) 
				(map 
					(fn [beat] {
						:count (+ (get beat :count) (get map1 :length)) 
						:instruments (get beat :instruments)}) 
					(get map2 :beats))))})
	([map1 map2 & maps] (apply concat-rhythm (into [] (concat [(concat-rhythm map1 map2)] maps)))))
	
(defn merge-rhythm
	"Merges two or more rhythm maps and returns the resulting map. In the case that the rhythms have different lengths, the length of the result will match that of the shortest rhythm."
	([map1 map2] {
		:interval (get map1 :interval)
		:length (min (get map1 :length) (get map2 :length))
		:beats (into [] 
			(map
				(fn [beat1 beat2] {
					:count (get beat1 :count)
					:instruments (into [] (concat (get beat1 :instruments) (get beat2 :instruments)))})
				(get map1 :beats) (get map2 :beats)))})
	([map1 map2 & maps] 
		(apply merge-rhythm 
			(into [] 
				(concat [(merge-rhythm map1 map2)] maps)))))
	
(defn get-instruments
	"Get the set of instruments used in a rhythm."
	[map1]
	(set (apply concat
		(map
			(fn [beat] (get beat :instruments))
			(get map1 :beats)))))
	
(defn remove-insts
	"Removes all instances of instruments from a rhythm map and returns the map."
	[map1 ins-vector] {
		:interval (get map1 :interval)
		:length (get map1 :length)
		:beats (into [] 
			(map
				(fn [beat] {
					:count (get beat :count)
					:instruments (into [] (filter (fn [x] (not (contains? ins-vector x))) (get beat :instruments)))})
				(get map1 :beats)))})
				
(defn edit-rhythm
	"Replaces drum patterns in an existing rhythm with updated patterns represented by RhythML."
	[map1 rml] 
	(merge-rhythm (remove-insts map1 (get-instruments (make-rhythm 1000 rml))) (make-rhythm 1000 rml)))
	
(defn edit-live-rhythm
	"Replaces drum patterns in the currently playing rhythm with updated patterns represented by RhythML."
	[rml]
	(update-rhythm (merge-rhythm (remove-insts @rhy-ref (get-instruments (make-rhythm 1000 rml))) (make-rhythm 1000 rml))))

(defn play-beat 
	"Plays a vector of instruments immediately and simultaneously."
	[beat]
	(dorun (for [ins beat]
		(ins))))

(defn play-rhythm 
	"Plays the rhythm stored in r-ref in a continuous loop. If the rhythm stored in r-ref changes, the new rhythm will be played at the start of the next loop."
	[r-ref]
	(let [time (now), r (deref r-ref), beats (get r :beats), interval (get r :interval), length (get r :length)]
		(do 
			(dorun (for [beat beats]
				(apply-at (+ (* interval (get beat :count)) time) play-beat [(into [] (get beat :instruments))])))
			(apply-at (+ (* length interval) time) play-rhythm [r-ref]))))

(defn start-rhythm 
	"Starts a rhythm session by setting the global rhythm ref and calling play-rhythm to start the rhythm loop."
	[] 
	(do
		(update-rhythm {
			:interval 10,
			:length 1,
			:beats [
				{:count 0, :instruments []}]})
		(play-rhythm rhy-ref)
		(println "Rhythm session started.")))