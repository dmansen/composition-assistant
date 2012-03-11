(ns composition-assistant.server
  (:require [noir.server :as server]))

(server/load-views "src/composition_assistant/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'composition-assistant})))

