(ns marrs.debug.util)

(defn ^:private knitter [acc coll]
  (if (< 0 (count (first coll)))
    (knitter (reduce #(conj %1 (first %2)) acc coll)
             (reduce #(conj %1 (rest %2)) [] coll))
    acc))

(defn knit
  "Given a list of collections (c1, c2, ...cn), return single collection
  (c1 0) (c2 0) ...  (cn 0)
  (c1 1) (c2 1) ...  (cn 1)
  ...
  (c1 N) (c2 N) ... (cn N))"
  [& colls]
  (knitter [] colls))

