(ns marvelous.args
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:import (java.net InetAddress))
  (:gen-class))

(def commandline-options
  [["-s" "--server SERVER" "IRC server"
    :default (InetAddress/getByName "irc.freenode.net")
    :parse-fn #(InetAddress/getByName %)]
   ["-p" "--port PORT" "IRC server port"
    :default 6667
    :parse-fn #(Integer/parseInt %)]
   ["-n" "--nickname NICKNAME" "User nickname"
    :default "marvelous-bot"]
   ["-h", "--help"]])

(defn parse-args [args]
  (:options (parse-opts args commandline-options)))
