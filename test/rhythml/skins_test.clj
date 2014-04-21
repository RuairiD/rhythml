(ns rhythml.skins-test
  (:require [clojure.test :refer :all]
			[org.senatehouse.expect-call :refer :all]
            [rhythml.skins :refer :all]))
	
(deftest make-rhythm-test
	(let [bpm 200 sounds {} rml "BD:|o---|" result (make-rhythm bpm sounds rml)]
		(is (map? result))
		(is (not (nil? (get result :interval))))
		(is (vector? (get result :beats)))
		(is (= (count (get result :beats)) (get result :length)))))

(def rhy1 {
	:interval 200,
	:length 4,
	:beats [
		{:count 0, :instruments [bd]}
		{:count 1, :instruments [sn]}
		{:count 2, :instruments [sn]}
		{:count 3, :instruments [sn]}]})

(def rhy2 {
	:interval 200,
	:length 4,
	:beats [
		{:count 0, :instruments [ch]}
		{:count 1, :instruments [ch]}
		{:count 2, :instruments [ch]}
		{:count 3, :instruments [ch]}]})
		
(deftest update-rhythm-test
	(let [update (update-rhythm rhy1)]
		(is (= rhy1 @rhy-ref))))
		
(deftest add-rhythm-test
	(let [bpm 200 rml "BD:|o---|"]
		(expect-call [(make-rhythm [bpm rml]) (update-rhythm [_])]
			(add-rhythm bpm rml))))

(def rhy1-rhy2-concat {
	:interval 200,
	:length 8,
	:beats [
		{:count 0, :instruments [bd]}
		{:count 1, :instruments [sn]}
		{:count 2, :instruments [sn]}
		{:count 3, :instruments [sn]}
		{:count 4, :instruments [ch]}
		{:count 5, :instruments [ch]}
		{:count 6, :instruments [ch]}
		{:count 7, :instruments [ch]}]})
			
(deftest concat-rhythm-test
	(let [result (concat-rhythm rhy1 rhy2)]
		(is (= result rhy1-rhy2-concat))))

(def rhy1-rhy2-merge {
	:interval 200,
	:length 4,
	:beats [
		{:count 0, :instruments [bd ch]}
		{:count 1, :instruments [sn ch]}
		{:count 2, :instruments [sn ch ]}
		{:count 3, :instruments [sn ch]}]})
			
(deftest merge-rhythm-test
	(let [result (merge-rhythm rhy1 rhy2)]
		(is (= result rhy1-rhy2-merge))))
		
(def rhy1-edit {
	:interval 200,
	:length 4,
	:beats [
		{:count 0, :instruments [bd]}
		{:count 1, :instruments [sn]}
		{:count 2, :instruments [sn bd]}
		{:count 3, :instruments [sn]}]})
		
(deftest edit-rhythm-test
	(let [result (edit-rhythm rhy1 "BD:|o-o-|")]
		(is (= rhy1-edit result))))
		
(def a (fn [] nil))

(def b (fn [] nil))
		
(deftest play-beat-test
	(expect-call [(a []) (b [])]
		(play-beat [a b])))
		