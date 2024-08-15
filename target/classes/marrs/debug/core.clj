(ns marrs.debug.core
  (:require [clojure.pprint :refer [pprint]]
            [clojure.string :as st]
            )
  (:use marrs.debug.util))

(defn shout! [x] (println "*****" x "*****"))

(defn inspect
  "Pretty print the value of any expression at the command line
  and carry on running"
  ([x]
   (pprint x) x)
  ([msg x]
   (shout! msg)
   (pprint x) x))

(defn p-inspect
  "Compiles a prepared statement and puts it to the console.
  Quite buggy!  Should use JDBC's own compiler.
  Returns original arg."
  ([p]
   (p-inspect nil p) p)
  ([msg p]
   (let [result (-> (knit (vec (st/split (first p) #"\?"))
                          (vec (mapv #(str %) (rest p))))
                    (->> (st/join ""))
                    (st/replace #"\s+" " "))]
     (if msg
       (inspect msg result)
       (inspect result)))
   p))

(defn log
  "Log a message

  For now, we log to stdout. Deprecated by
  Timbre."
  [x]
  (pprint x) x)

(defn tap
  "Executes arg against f and returns arg"
  [f arg]
  (f arg)
  arg)

(defn tap-inspect
  "Pretty print the value of (f arg) and returns arg"
  ([f arg]
   (inspect (f arg))
   arg)
  ([msg f arg]
   (inspect msg (f arg))
   arg))
