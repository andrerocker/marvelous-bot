(ns marvelous.plug
  (:gen-class))

(def log { :matcher (fn[line](boolean true)) :handler-fn #(println "<<<" %) })
(def ping { :matcher #(re-find #"PING" %) :handler-fn (fn[line](str "PONG")) })

(defn core-plugins []
  [log ping])
