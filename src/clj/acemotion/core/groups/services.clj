(ns acemotion.core.groups.services
  (:require [acemotion.db.core :as db]
            [acemotion.core.utils.utils :as utils]))

(defn generate-group [owner-id]
  {:id (utils/uuid)
   :owner_id owner-id})

(defn create-group! [owner-id user-ids]
  (let [group (generate-group owner-id)]
    (db/create-group! group)
    (doseq [user-id (utils/union-vector user-ids owner-id)]
      (db/add-group-member! {:id (utils/uuid)
                             :group_id (:id group)
                             :user_id user-id}))
    group))

(defn get-related-groups [user-id]
  (db/get-related-groups {:user_id user-id}))

(defn get-group-member-ids [user-id group-id]
  (->> (db/get-group-member-ids {:group_id group-id})
       (map vals)
       (flatten)))
