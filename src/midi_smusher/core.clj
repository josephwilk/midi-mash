(ns midi-smusher.core
  "Takes a csv file generate from a midi (via http://www.fourmilab.ch/webtools/midicsv/)
   and turns it into a list of event maps"
  (:require
   [clojure.string :as string]
   [clojure.data.csv :as csv]
   [clojure.java.io  :as io]))

(defn pitch-of [row] (string/trim (nth row 4)))
(defn type-of  [row] (string/trim (nth row 2)))
(defn instrument-of [row] (string/trim (nth row 4)))

(defn durations [[k v ]]
  (reduce (fn [r [on off]]
            (if (and (= "Note_on_c"  (:type on))
                     (= "Note_off_c" (:type off)))
              (conj r (-> on (assoc :duration (- (:time off) (:time on)))
                          (dissoc :type)))
              (do
                (println "on off mismatch" on off)
                r)))
          []
          (partition 2 v)))

(def current-instrument (atom "0"))

(defn row->map [[track time type channel pitch velocity :as row]]
  {:time       (Integer/parseInt (string/trim time))
   :type       (string/trim type)
   :channel    (Integer/parseInt (string/trim channel))
   :pitch      (Integer/parseInt (string/trim pitch))
   :velocity   (Integer/parseInt (string/trim (or velocity 0)))
   :instrument (Integer/parseInt @current-instrument)})

(defn row->pitch-event [pitch-map row]
  (when (= (type-of row) "Program_c") (reset! current-instrument (instrument-of row)))
  (if (> (count row) 4)
    (let [pitch (pitch-of row)]
      (assoc pitch-map pitch (conj (or (pitch-map pitch) [])
                                   (row->map row))))
    pitch-map))

(defn csv->events [file]
  (with-open [in-file (io/reader file)]
    (mapcat durations
     (reduce row->pitch-event {} (csv/read-csv in-file)))))

(comment (csv->events "midnight.csv"))

