(ns acemotion.core.alerts.services
  (:require [schema.core :as s]
            [acemotion.db.core :as db]))

(defn get-alerts [user-id]
  (db/get-related-alerts {:user-id user-id}))

(defn create-alert! [alert]
  (db/create-alert! alert))
