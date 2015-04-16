(ns marvelous.plug-spec
  (:require [speclj.core :refer :all]
            [marvelous.plug :refer :all]))

(describe "#core-plugins"
  (it "return core plugins"
      (let [plugins (core-plugins)]
        (should= [log ping] plugins))))

(run-specs)
