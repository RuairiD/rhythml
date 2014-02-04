(ns rhythml.core
  (:use [rhythml.skins] [rhythml.sticks] [org.httpkit.server]))
  
(defn read-rhythm [s] (read-sticks s))

(defn receive-rhythm [data]
	(do
		(read-rhythm data)))

(defn handler [req]
  (with-channel req channel              ; get the channel
    ;; communicate with client using method defined above
    (on-close channel (fn [status]
                        (println "Rhythm Channel Closed.")))
    (if (websocket? channel)
      (println "WebSocket channel opened.")
      (println "HTTP channel opened."))
    (on-receive channel (fn [data]
                          (receive-rhythm data))))) 
						  
(defn start-server [options]
	(let [port (options :port)]
		(do
			(run-server handler options)
			(println "Sticks server started on port " port "."))))

(start-server {:port 8080})
(start-rhythm)