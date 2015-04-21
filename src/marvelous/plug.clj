(ns marvelous.plug
  (:gen-class))

(def log { :matcher (fn[line]true) :handler-fn #(println "<<<" %) })
(def ping { :matcher #(re-find #"PING" %) :handler-fn (fn[line]"PONG") })
(def core-plugins [log ping])
