(ns sub-log
  (:gen-class)
  (:require
   [clojure.java.io :as io]
   [hello :refer [file-path]]
   [lumber.core :as lumber]))

(defn mylog []
  (when (.isFile (io/file file-path))
    (io/delete-file file-path))

  (let [stopper
        (lumber/init!
         {:service     "snowflake"
          :environment "env"
          :deployment  "deployment"}

         {:file {:filename file-path}})]

    (lumber/log ::from-subprocess {:message "From the subprocess"})
    (stopper)
    (assert (.isFile (io/file file-path))
            "Log file not found")))
