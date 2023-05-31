(ns hello
  (:gen-class)
  (:require
   [clojure.java.io :as io]
   [clojure.java.shell :as sh]
   [lumber.core :as lumber]))

(def file-path "/tmp/lumber.log")

(defn main [& _args]
  (println "Checking checking")
  (when (.isFile (io/file file-path))
    (io/delete-file file-path))

  (let [stop-fn
        (lumber/init!
         {:service     "snowflake"
          :environment "env"
          :deployment  "deployment"}

         {:file {:filename file-path}})]

    (lumber/log ::from-main {:message "logging from the main file as well"})
    (println (sh/sh "clj" "-X" "sub-log/main"))
    (stop-fn)
    (assert (.isFile (io/file file-path))
            "Log file not found")))
