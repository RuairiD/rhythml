(ns rhythml.core
  (:use [overtone.live]
		 [overtone.inst.drum]))
  
(defn bd [] (kick))
(defn ch [] (closed-hat))
(defn sn [] (snare 405 1 0.1 0.1 0.25 40 1000))

(def drumPool (overtone.at-at/mk-pool))

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

(def rhyRef (ref rhy))
	
(defn updateRhythm [r]
	(dosync (ref-set rhyRef r)))

(defn playBeat [beat]
	(dorun (for [ins beat]
		(ins))))

(defn playRhythm [rRef]
	(let [time (now), r (deref rRef), beats (get r :beats), interval (get r :interval), length (get r :length)]
		(do 
			(dorun (for [beat beats]
				(apply-at (+ (* interval (get beat :count)) time) playBeat [(get beat :instruments)])))
			(apply-at (+ (* length interval) time) playRhythm [rRef]))))

(defn startRhythm [r] 
	(do
		(updateRhythm r)
		(playRhythm rhyRef)))