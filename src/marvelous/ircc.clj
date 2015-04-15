(ns marvelous.ircc
  (:require [while-let.core :refer [while-let]]
            [marvelous.plug :refer [core-plugins]])
  (:import (java.net Socket)
           (java.io InputStreamReader BufferedReader PrintWriter))
  (:gen-class))

(defn- send-command [output command]
  (println (str ">>> " command))
  (doto output
    (.println (str command "\r"))
    (.flush)))

(defn- send-payload [output properties]
  (doto output
    (send-command (str "USER " (:nickname properties) " 0 * : " (:nickname properties)))
    (send-command (str "NICK " (:nickname properties)))
    (send-command (str "JOIN #" (:channel properties)))))

(defn- select-plugins [line]
  (filter #((:matcher %) line) (core-plugins)))

(defn- execute-plugin [output plugin line]
  (if-let [response ((:handler-fn plugin) line)]
    (send-command output response)))

(defn- start-loop [input output properties]
  (while-let [line (.readLine input)]
    (doseq [current (select-plugins line)]
      (execute-plugin output current line))))

(defn start-ircc [properties]
  (println " ~~~~~~~~~~~~~~~~~~ Starting Marvelous IRC Bot :p ~~~~~~~~~~~~~~~\n")
  (let [socket (Socket. (:server properties) (:port properties))
        input (clojure.java.io/reader (.getInputStream socket))
        output (PrintWriter. (.getOutputStream socket))]

    (send-payload output properties)
    (start-loop input output properties)))
