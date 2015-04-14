(ns marvelous.ircc
  (:require [while-let.core :refer [while-let]])
  (:import (java.net Socket)
           (java.io InputStreamReader BufferedReader PrintWriter))
  (:gen-class))

(defn send-payload [output properties]
  (println "Starting Marvelous IRC Bot :p")
  (doto output
    (.println (str "NICK " (:nickname properties) "\r"))
    (.println (str "USER " (:nickname properties) " 0 * : " (:nickname properties) "\r"))
    (.flush)))

(defn start-ircc [properties]
  (let [socket (Socket. (:server properties) (:port properties))
        input (BufferedReader. (InputStreamReader. (.getInputStream socket)))
        output (PrintWriter. (.getOutputStream socket))]

    (send-payload output properties)
    (while-let [line (.readLine input)]
               (println line))))
