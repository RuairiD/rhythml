(ns rhythml.core
  (:use [rhythml.skins] [rhythml.sticks]))
  
(defn read-rhythm [s] (read-text s))

(start-rhythm)