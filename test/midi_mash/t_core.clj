(ns midi-mash.t-core
  (:use midje.sweet
        midi-mash.core))

(fact "it converts csv files into event maps"
  (count (csv->events "test/fixtures/one_summers_day.csv" )) => 937)