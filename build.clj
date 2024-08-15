(ns build
  (:require [clojure.tools.build.api :as bld]))

(def lib 'marrs/debug)
(def version (format "0.1.%s" (bld/git-count-revs nil)))
(def class-dir "target/classes")
(def jar-file (format "target/%s-%s.jar" (name lib) version))

(prn jar-file)

;; delay to defer side effects (artifact downloads)
(def basis (delay (bld/create-basis {:project "deps.edn"})))

(defn clean [_]
  (bld/delete {:path "target"}))

(defn jar [_]
  (bld/write-pom {:class-dir class-dir
                  :lib lib
                  :version version
                  :basis @basis
                  :src-dirs ["src"]})
  (bld/copy-dir {:src-dirs ["src" "resources"]
                 :target-dir class-dir})
  (bld/jar {:class-dir class-dir
            :jar-file jar-file}))
