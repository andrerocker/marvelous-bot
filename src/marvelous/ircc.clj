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

(def log { :matcher (fn[line](boolean true)) :handler-fn #(println "<<<" %) })
(def ping { :matcher #(re-find #"PING" %) :handler-fn #(str "PONG " %) })

(defn- select-plugins [plugins line]
  (filter #((:matcher %) line) plugins))

(defn- execute-plugin [output plugin line]
  (let [response ((:handler-fn plugin) line)]
    (if response
      (send-command output response))))

(defn- start-loop [input output properties]
  (while-let [line (.readLine input)]
    (doseq [current (select-plugins #{log ping} line)]
      (execute-plugin output current line))))

(defn start-ircc [properties]
  (println " ~~~~~~~~~~~~~~~~~~ Starting Marvelous IRC Bot :p ~~~~~~~~~~~~~~~\n")
  (let [socket (Socket. (:server properties) (:port properties))
        input (clojure.java.io/reader (.getInputStream socket))
        output (PrintWriter. (.getOutputStream socket))]

    (send-payload output properties)
    (start-loop input output properties)))
