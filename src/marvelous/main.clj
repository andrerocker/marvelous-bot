(ns marvelous.main
  (:require [marvelous.args :refer [parse-args]]
            [marvelous.ircc :refer [start-ircc]])
  (:import (java.net Socket)
           (java.io InputStreamReader BufferedReader PrintWriter))
  (:gen-class))

(defn -main [& args]
  (start-ircc (parse-args args)))
