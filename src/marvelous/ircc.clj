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
        nickname (:nickname properties)
        send-send #(send-command output %)]
    (mapv send-send [(str "USER " nickname " 0 * : " nickname)
                     (str "NICK " nickname)
                     (str "JOIN #" channel)])))

(defn- select-plugins [plugins line]
  (filter #((:matcher %) line) plugins))

(defn- execute-plugin [output plugin line]
  (if-let [response ((:handler-fn plugin) line)]
    (send-command output response)))

(defn- start-loop [executor input output properties user-plugins]
  (while-let [line (.readLine input)]
    (let [plugins (concat core-plugins user-plugins)]
      (doseq [current (select-plugins plugins line)]
        (.submit executor #(execute-plugin output current line))))))

(defn start-ircc [properties user-plugins]
  (println (:banner properties) "\n")
  (let [socket (Socket. (:server properties) (:port properties))
        input (clojure.java.io/reader (.getInputStream socket))
        output (PrintWriter. (.getOutputStream socket))
        executor (Executors/newCachedThreadPool)]

    (send-payload output properties)
    (start-loop executor input output properties user-plugins)))
