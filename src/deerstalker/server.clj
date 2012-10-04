(ns deerstalker.server
  (:require [noir.server :as server]
            [monger.core :as mg]))

(server/load-views-ns 'deerstalker.views)

(mg/connect!)
(mg/set-db! (mg/get-db "deerstalker-development"))

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8008"))]
    (server/start port {:mode mode
                        :ns 'deerstalker})))
