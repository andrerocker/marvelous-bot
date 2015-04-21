(ns marvelous.plug.hello
  (:gen-class))

(defn- hello-handler-fn [line]
  (let [[_ sender _ channel _] (re-matches #":(.*)!(.*)PRIVMSG #(.*) :(.*)" line)]
    (if sender
      (str "PRIVMSG #" channel " :" sender ": iae tru!" ))))

(def hello-user { :matcher #(re-find #"hello" %) :handler-fn hello-handler-fn })
