(ns rhythml.core
  (:use [overtone.live]
		 [overtone.inst.drum]))
  
(defn bd [] (overtone.inst.drum/kick))
(defn ch [] (overtone.inst.drum/closed-hat))
(defn sn [] (overtone.inst.drum/snare 405 1 0.1 0.1 0.25 40 1000))

(def drumPool (overtone.at-at/mk-pool))

(def rhy {
	:interval 200, 
	:beats [
		{:count 0, :instruments [bd ch]}
		{:count 1, :instruments [ch]}
		{:count 2, :instruments [ch sn]}
		{:count 3, :instruments [ch]}
	],
	:length 4
})

(defn playBeat [beat]
	(for [ins beat]
		(ins)
	)
)

(defn playRhythm [r]
	(let [time (now), beats (get r :beats), interval (get r :interval)]
		(for [beat beats]
			(apply-at (+ (* interval (get beat :count)) time) playBeat [(get beat :instruments)])
		)
	)
)

(defn loopRhythm [r] 
    (overtone.at-at/every (* (get r :length) (get r :interval)) #(playRhythm r) drumPool))