(ns marvelous.ircc
  (:require [while-let.core :refer [while-let]])
  (:import (java.net Socket)
           (java.io InputStreamReader BufferedReader PrintWriter))
  (:gen-class))

(defn- send-command [output command]
  (println (str ">>> " command))
  (doto output
    (.println (str command "\r"))
    (.flush)))

(defn- send-payload [output properties]
    (send-command output (str "NICK " (:nickname properties)))
    (send-command output (str "USER " (:nickname properties) " 0 * : " (:nickname properties))))

(def log { :matcher #(boolean %) :handler-fn #(println %) })
(def ping { :matcher #(re-find #"PING" %) :handler-fn #(str "PONG" %) })

(defn- start-loop [input output properties]
    (while-let [line (.readLine input)]
      (println (str "<<< " line))
        (if ((:matcher ping) line)
          (send-command output ((:handler-fn ping) line)))))

(defn start-ircc [properties]
  (let [socket (Socket. (:server properties) (:port properties))
        input (BufferedReader. (InputStreamReader. (.getInputStream socket)))
        output (PrintWriter. (.getOutputStream socket))]

    (println " ~~~~~~~~~~~~~~~~~~ Starting Marvelous IRC Bot :p ~~~~~~~~~~~~~~~\n")
    (send-payload output properties)
    (start-loop input output properties)))
