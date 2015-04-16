(defproject marvelous "0.0.1-SNAPSHOT"
  :description "Marvelous - A IRC super bot :p"
  :url "http://github.com/andrerocker/marvelous-bot"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.cli "0.3.1"]
                 [while-let "0.2.0"]]
  :profiles {:dev {:dependencies [[speclj "3.2.0"]]}}
  :plugins [[speclj "3.2.0"]]
  :test-paths ["spec"]
  :target-path "target/%s"
  :main marvelous.main)
