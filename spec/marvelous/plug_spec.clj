(ns marvelous.plug-spec
  (:require [speclj.core :refer :all]
            [marvelous.plug :refer :all]))

(describe "#core-plugins"
  (it "return core plugins"
      (let [plugins (core-plugins)]
        (should= [log ping] plugins))))

(describe "#log"
  (it "match all lines"
      (let [matcher (:matcher log)]
        (should= true (matcher false))
        (should= true (matcher true))
        (should= true (matcher nil))
        (should= true (matcher ""))))

  (it "log line and return nil"
      (should-invoke println {:with ["<<<" "bacon"] :return nil}
        (let [result ((:handler-fn log) "bacon")]
          (should-be-nil result)))))

(describe "#ping"
  (it "matches ping command"
      (let [matcher (:matcher ping)]
        (should-not-be-nil (matcher "blabla PING blabla"))
        (should-be-nil (matcher "lalala XXXX ldsllsa"))))

  (it "says PONG"
      (let [handler-fn (:handler-fn ping)]
        (should= "PONG" (handler-fn "kdoaskd kdoas sakdo")))))

(run-specs)
