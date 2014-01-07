(ns rhythml.core
  (:require [instaparse.core :as insta])
  (:import [rhythml MapGenerator])
  (:use [overtone.live]
		[overtone.inst.drum]))
		
(defn vector-to-array-list [xs]
	(let [result (java.util.ArrayList.) len (count xs)]
		(loop [i 0] (when (< i len)
			(let [x (get xs i)]
				(if (vector? x) (.add result (vector-to-array-list x)) (.add result x)))
			(recur (inc i)))) 
		result))
		
(def grammar "
	p : rhy ;
	rhy : inst <WS> rhy | inst;
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

(defn to-clojure-map [m] {
	:interval (get m "interval")
	:length (get m "length")
	:beats (into [] (map 
		(fn [beat] {
			:count (get beat "count") 
			:instruments (into [] (map 
				(fn [ins] (resolve (symbol ins))) 
				(into [] (get beat "instruments"))))})
		(into [] (get m "beats"))))})

(defn make-rhythm [bpm rml] (to-clojure-map (MapGenerator/getMap bpm (vector-to-array-list (parse-rhythm rml)))))

(def rhy-ref (ref rhy))
	
(defn update-rhythm [r]
	(dosync (ref-set rhy-ref r)))
	
(defn add-rhythm [bpm rml] (update-rhythm (make-rhythm bpm rml)))

(defn play-beat [beat]
	(dorun (for [ins beat]
		(ins))))

(defn play-rhythm [rRef]
	(let [
		time (now)
		r @rRef
		beats (get r :beats)
		interval (get r :interval)
		length (get r :length)]
		(do 
			(dorun (for [beat beats]
				(apply-at (+ (* interval (get beat :count)) time) play-beat [(into [] (get beat :instruments))])))
			(apply-at (+ (* length interval) time) play-rhythm [rRef]))))

(defn start-rhythm [r] 
	(do
		(update-rhythm r)
		(play-rhythm rhy-ref)))