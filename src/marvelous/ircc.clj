(ns marvelous.ircc
  (:require [while-let.core :refer [while-let]])
  (:import (java.net Socket)
           (java.io InputStreamReader BufferedReader PrintWriter))
  (:gen-class))

(defn- send-command [output command]
  (doto output
    (.println (str command "\r"))
    (.flush)))

(defn- send-payload [output properties]
    (send-command output (str "NICK " (:nickname properties)))
    (send-command output (str "USER " (:nickname properties) " 0 * : " (:nickname properties))))

(defn- start-loop [input output properties]
    (while-let [line (.readLine input)]
      (println line)))

(defn start-ircc [properties]
  (let [socket (Socket. (:server properties) (:port properties))
        input (BufferedReader. (InputStreamReader. (.getInputStream socket)))
        output (PrintWriter. (.getOutputStream socket))]
  
    (send-payload output properties)
    (println "Starting Marvelous IRC Bot :p")
    (start-loop input output properties)))
