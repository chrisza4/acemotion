(ns acemotion.core.groups.schemas
  (:require [schema.core :as s]
            [acemotion.core.utils.utils :as utils]))

(def alert-type
  {:warning 0
   :danger 1})

(def group
  {:id s/Uuid
   :created s/Any
   :updated s/Any
   :owner_id s/Uuid})
