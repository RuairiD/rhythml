(ns rhythml.sticks
  (:require [instaparse.core :as insta])
  (:use [rhythml.skins]))
		
(def text-grammar "
	p : expr-list ;
	
	expr-list : <WS> expr <WS> expr-list <WS> | <WS> expr <WS>;
	expr : assign-expr | play-expr;
	
	assign-expr : id <WS> rhy-expr;
	rhy-expr : make-expr | concat-expr | merge-expr;
	concat-expr : <'.'> <WS> id-list;
	merge-expr : <'+'> <WS> id-list;
	make-expr : <'$'> <WS> rhy;
	rhy : #'[a-zA-Z:|\\-]*';
	
	play-expr : <'>'> <WS> id;
	id-list : id <WS> id-list | id;
	id : #'[a-zA-Z0-9]+';
	
	WS : #'\\s*' ;")	

(def parse-text
  (insta/parser text-grammar))
	
(defn concat-rhythm-vector
	"Concatenates a vector of rhythm maps together, returning the resulting map."
	[rhy-vect] (if (empty? (rest rhy-vect)) (first rhy-vect) (concat-rhythm (first rhy-vect) (concat-rhythm-vector (rest rhy-vect)))))
	
(defn merge-rhythm-vector
	"Merges a vector of rhythm maps together, returning the resulting map."
	[rhy-vect] (if (empty? (rest rhy-vect)) (first rhy-vect) (merge-rhythm (first rhy-vect) (merge-rhythm-vector (rest rhy-vect)))))
	
(defmulti read-sticks-parse-tree 
	"Read the parse tree returned by Instaparse to create a map of ids and rhythms represented by the input string. Rhythms are merged, concatenated and played as appropriate."
	(fn [parse-tree output-map] (first parse-tree)))

(defmethod read-sticks-parse-tree :p [[_ expr-list] output-map] (read-sticks-parse-tree expr-list output-map))

(defmethod read-sticks-parse-tree :expr-list 
	[[_  expr expr-list] output-map] (if (= expr-list nil) (read-sticks-parse-tree expr output-map) (read-sticks-parse-tree expr-list (read-sticks-parse-tree expr output-map)) ))

(defmethod read-sticks-parse-tree :expr 
	[[_ expr] output-map] (read-sticks-parse-tree expr output-map))

(defmethod read-sticks-parse-tree :assign-expr 
	[[_ id rhy-expr] output-map] (assoc output-map (read-sticks-parse-tree id output-map) (read-sticks-parse-tree rhy-expr output-map)))

(defmethod read-sticks-parse-tree :rhy-expr 
	[[_ expr] output-map] (read-sticks-parse-tree expr output-map))

(defmethod read-sticks-parse-tree :concat-expr 
	[[_ id-list] output-map] (concat-rhythm-vector (read-sticks-parse-tree id-list output-map)))

(defmethod read-sticks-parse-tree :merge-expr 
	[[_ id-list] output-map] (merge-rhythm-vector (read-sticks-parse-tree id-list output-map)))

(defmethod read-sticks-parse-tree :make-expr 
	[[_ rhy] output-map] (read-sticks-parse-tree rhy output-map))

(defmethod read-sticks-parse-tree :rhy 
	[[_ rml] output-map] (make-rhythm 200 rml))

(defmethod read-sticks-parse-tree :play-expr
	[[_ id] output-map] (update-rhythm (output-map (read-sticks-parse-tree id output-map)) ))

(defmethod read-sticks-parse-tree :id-list 
	[[_  id id-list] output-map] (into [] (if (= id-list nil) [(output-map (read-sticks-parse-tree id output-map))] (concat [(output-map (read-sticks-parse-tree id output-map))] (read-sticks-parse-tree id-list output-map)))))

(defmethod read-sticks-parse-tree :id 
	[[_ id] output-map] id)

(defmethod read-sticks-parse-tree :default 
	[[_ & rest] output-map] (map (fn [x] (read-sticks-parse-tree x output-map)) (into [] rest)))
	
(defn read-sticks [s] (read-sticks-parse-tree (parse-text s) {}))

(def assign-ex (parse-text "foo $ B:|o|"))

(def full-ex (parse-text "foo $ B:|o| bar $ SN:|o| fb + foo bar"))
