(ns marvelous.args-spec
  (:require [speclj.core :refer :all]
            [marvelous.args :refer [parse-args]]))

(describe "#parse-args"
  (it "can populate a default properties"
      (let [props (parse-args [])]
        (should= 6667 (:port props))
        (should= "marvelous-bot" (:nickname props))
        (should= "irc.freenode.net" (:server props))
        (should= "marvelous-development" (:channel props))))

  (it "can populate specified values"
      (let [props (parse-args ["-s" "irc.baconetwork.net"
                               "-p" "1337"
                               "-n" "baconz"
                               "-c" "deathstar"])]
        (should= 1337 (:port props))
        (should= "baconz" (:nickname props))
        (should= "deathstar" (:channel props))
        (should= "irc.baconetwork.net" (:server props)))))

(run-specs)
