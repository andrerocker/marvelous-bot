(ns marvelous.args
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def commandline-options
  [["-s" "--server SERVER" "IRC server"
    :default "irc.freenode.net"]
   ["-p" "--port PORT" "IRC server port"
    :default 6667
    :parse-fn #(Integer/parseInt %)]
   ["-n" "--nickname NICKNAME" "User nickname"
    :default "marvelous-bot"]
   ["-c" "--channel CHANNEL" "IRC channel"
    :default "marvelous-development"]
   ["-b" "--banner BANNER" "Marvelous Start Banner :p"
    :default "~~~~~~~~~~~~~~~~~~~~~ Starting Marvelous IRC Bot ~~~~~~~~~~~~~~~~~~~~~" ]
   ["-h", "--help"]])

(defn parse-args [args]
  (:options (parse-opts args commandline-options)))
