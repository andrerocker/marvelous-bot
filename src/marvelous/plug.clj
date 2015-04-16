(ns marvelous.plug
  (:gen-class))

(def log { :matcher (fn[line]true) :handler-fn #(println "<<<" %) })
(def ping { :matcher #(re-find #"PING" %) :handler-fn (fn[line]"PONG") })

(defn core-plugins []
  [log ping])
