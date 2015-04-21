(ns marvelous.plug.hello
  (:require [marvelous.plug.helper :refer [extract-chan-message, send-chan-user-message]])
  (:gen-class))

(defn- hello-handler-fn [line]
  (let [props (extract-chan-message line)]
    (send-chan-user-message props "iae tru")))

(def hello-user { :matcher #(re-find #"hello" %) :handler-fn hello-handler-fn })
