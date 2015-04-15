(ns marvelous.main
  (:require [marvelous.args :refer [parse-args]]
            [marvelous.ircc :refer [start-ircc]])
  (:gen-class))

(defn -main [& args]
  (start-ircc (parse-args args)))
