(ns marvelous.main-spec
  (:require [speclj.core :refer :all]
            [marvelous.args :refer [parse-args]]
            [marvelous.ircc :refer [start-ircc]]
            [marvelous.main :refer [-main]]))

(describe "#main"
  (it "start base process; dirty mode to check of methods call chain"
      (with-redefs [parse-args (fn[_]1)
                    start-ircc (fn[x](inc x))]
          (should= 2 (-main)))))

(run-specs)
