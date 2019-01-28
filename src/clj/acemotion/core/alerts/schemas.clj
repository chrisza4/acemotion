(ns acemotion.core.alerts.schemas
  (:require [schema.core :as s]
            [acemotion.core.utils.utils :as utils]))

(def alert-type
  {:warning 0
   :danger 1})

(def alert
  {:id s/Uuid
   :created s/Any
   :updated s/Any
   :owner_id s/Uuid
   :group_id s/Uuid
   :status (apply s/enum (vals alert-type))})
