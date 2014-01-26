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
	
(defmulti read-parse-tree 
	"Read the parse tree returned by Instaparse to create a map of ids and rhythms represented by the input string. Rhythms are merged, concatenated and played as appropriate."
	(fn [parse-tree map] (first parse-tree)))

(defmethod read-parse-tree :p [[_ expr-list] map] (read-parse-tree expr-list map))

(defmethod read-parse-tree :expr-list 
	[[_  expr expr-list] map] (if (= expr-list nil) (read-parse-tree expr map) (read-parse-tree expr-list (read-parse-tree expr map)) ))

(defmethod read-parse-tree :expr 
	[[_ expr] map] (read-parse-tree expr map))

(defmethod read-parse-tree :assign-expr 
	[[_ id rhy-expr] map] (assoc map (read-parse-tree id map) (read-parse-tree rhy-expr map)))

(defmethod read-parse-tree :rhy-expr 
	[[_ expr] map] (read-parse-tree expr map))

(defmethod read-parse-tree :concat-expr 
	[[_ id-list] map] (concat-rhythm-vector (read-parse-tree id-list map)))

(defmethod read-parse-tree :merge-expr 
	[[_ id-list] map] (merge-rhythm-vector (read-parse-tree id-list map)))

(defmethod read-parse-tree :make-expr 
	[[_ rhy] map] (read-parse-tree rhy map))

(defmethod read-parse-tree :rhy 
	[[_ rml] map] (make-rhythm 200 rml))

(defmethod read-parse-tree :play-expr
	[[_ id] map] (update-rhythm (map (read-parse-tree id map)) ))

(defmethod read-parse-tree :id-list 
	[[_  id id-list] map] (into [] (if (= id-list nil) [(map (read-parse-tree id map))] (concat [(map (read-parse-tree id map))] (read-parse-tree id-list map)))))

(defmethod read-parse-tree :id 
	[[_ id] map] id)

(defmethod read-parse-tree :default 
	[[_ & rest] map] (map (fn [x] (read-parse-tree x map)) (into [] rest)))
	
(defn read-text [s] (read-parse-tree (parse-text s) {}))

(def assign-ex (parse-text "foo $ B:|o|"))

(def full-ex (parse-text "foo $ B:|o| bar $ SN:|o| fb + foo bar"))
