(defproject midi-mash "0.1.0-SNAPSHOT"
  :description "Extract data out of midi files"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.csv "0.1.2"]]
  :profiles {:dev {:dependencies [[midje "1.5.1"]]
                   :plugins [[lein-kibit "0.0.8"]
                             [lein-midje "3.0.0"]]}})
