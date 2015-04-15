(ns marvelous.ircc
  (:require [while-let.core :refer [while-let]]
            [marvelous.plug :refer [core-plugins]])
  (:import (java.net Socket)
           (java.io PrintWriter)
           (java.util.concurrent Executors))
  (:gen-class))

(defn- send-command [output command]
  (println (str ">>> " command))
  (doto output
    (.println (str command "\r"))
    (.flush)))

(defn- send-payload [output properties]
  (let [channel (:channel properties)
        nickname (:nickname properties)]
    (doto output
      (send-command (str "USER " nickname " 0 * : " nickname))
      (send-command (str "NICK " nickname))
      (send-command (str "JOIN #" channel)))))

(defn- select-plugins [line]
  (filter #((:matcher %) line) (core-plugins)))

(defn- execute-plugin [output plugin line]
  (if-let [response ((:handler-fn plugin) line)]
    (send-command output response)))

(defn- start-loop [executor input output properties]
  (while-let [line (.readLine input)]
    (doseq [current (select-plugins line)]
      (.submit executor #(execute-plugin output current line)))))

(defn start-ircc [properties]
  (println " ~~~~~~~~~~~~~~~~~~ Starting Marvelous IRC Bot :p ~~~~~~~~~~~~~~~\n")
  (let [socket (Socket. (:server properties) (:port properties))
        input (clojure.java.io/reader (.getInputStream socket))
        output (PrintWriter. (.getOutputStream socket))
        executor (Executors/newCachedThreadPool)]

    (send-payload output properties)
    (start-loop executor input output properties)))
