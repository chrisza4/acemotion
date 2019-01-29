(ns acemotion.core.alerts.services
  (:require [schema.core :as s]
            [acemotion.db.core :as db]))

(defn get-alerts [user-id]
  (db/get-related-alerts {:user-id user-id}))

(defn alert-id-exists [id]
  (-> (db/alert-exists {:id id})
      (:count)
      (> 0)))

(defn create-alert! [alert]
  (if-not (alert-id-exists (:id alert))
    (db/create-alert! alert)
    (throw (ex-info "Duplicate id" {:type  :client-error
                                    :cause :duplicate-id}))))
