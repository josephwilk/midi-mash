# Midi MASH

Install midi2csv

```shell
wget http://www.fourmilab.ch/webtools/midicsv/midicsv-1.1.tar.gz && tar -xf midicsv-1.1.tar.gz && cd midicsv-1.1 && make && make install

midi2csv your-midi-file.mid > your-midi.csv
```

```clojure
(use 'midi-mash.core)

(csv->events "your-midi.csv")
;; => {:channel 1, :duration 96, :instrument 0, :pitch 48, :time 48480, :velocity 64} ....
```