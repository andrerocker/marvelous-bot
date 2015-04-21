(ns marvelous.main-spec
  (:require [speclj.core :refer :all]
            [marvelous.args :refer [parse-args]]
            [marvelous.ircc :refer [start-ircc]]
            [marvelous.main :refer [user-plugins]]
            [marvelous.main :refer [-main]]))

(describe "#main"
  (it "start base process; dirty mode to check of methods call chain"
      (with-redefs [user-plugins 4
                    parse-args (fn[_]4)
                    start-ircc (fn[x,y](* x y))]
          (should= 16 (-main)))))

(run-specs)
