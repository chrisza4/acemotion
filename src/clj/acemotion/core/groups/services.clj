(ns acemotion.core.groups.services
  (:require [acemotion.db.core :as db]
            [acemotion.core.utils.utils :as utils]))

(defn generate-group [owner-id]
  {:id (utils/uuid)
   :owner_id owner-id})

(defn create-group! [owner-id user-ids]
  (let [group (generate-group owner-id)]
    (do
      (db/create-group! group)
      (doseq [user-id user-ids]
        (db/add-group-member! {:id (utils/uuid)
                               :group_id (:id group)
                               :user_id user-id})))))

(defn get-related-groups [user-id]
  (db/get-related-groups {:user_id user-id}))
