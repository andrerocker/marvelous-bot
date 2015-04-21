(ns marvelous.main
  (:require [marvelous.args :refer [parse-args]]
            [marvelous.ircc :refer [start-ircc]]
            [marvelous.plug :refer [hello-user]])
  (:gen-class))

(def user-plugins [hello-user])

(defn -main [& args]
  (start-ircc (parse-args args) user-plugins))
