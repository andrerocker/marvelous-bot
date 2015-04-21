(ns marvelous.plug
  (:gen-class))

(def log { :matcher (fn[line]true) :handler-fn #(println "<<<" %) })
(def ping { :matcher #(re-find #"PING" %) :handler-fn (fn[line]"PONG") })
(def core-plugins [log ping])

(defn hello-handler-fn [line]
  (let [[_ sender _ channel _] (re-matches #":(.*)!(.*)PRIVMSG #(.*) :(.*)" line)]
    (str "PRIVMSG #" channel " :" sender ": iae tru!" )))

(def hello-user { :matcher #(re-find #"hello" %) :handler-fn hello-handler-fn })

