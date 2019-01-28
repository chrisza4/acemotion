(ns acemotion.test.helpers
  (:require [acemotion.db.core :refer [*db*] :as db]
            [acemotion.config :refer [env]]
            [acemotion.core.utils.utils :as utils]
            [acemotion.core.users.utils :as user-utils]
            [mount.core :as mount]
            [luminus-migrations.core :as migrations]))

(defn- fixed-length-password
  ([] (fixed-length-password 8))
  ([n]
   (let [chars (map char (range 33 127))
         password (take n (repeatedly #(rand-nth chars)))]
       (reduce str password))))

(defn generate-test-user [email]
  {:id (utils/uuid)
   :first_name "Testing"
   :last_name "Testing Purpose"
   :email email
   :pass (user-utils/hash-password (fixed-length-password))
   :salt ""
   :is_active true})

(defn generate-group [owner-id]
  {:id (utils/uuid)
   :owner_id owner-id})

(defn create-group! [group]
  (db/create-group! group)
  group)

(defn add-members-to-group! [group-id user-ids]
  (doseq [user-id user-ids]
    (db/add-group-member! {:id (utils/uuid)
                           :group_id group-id
                           :user_id user-id})))

(defn create-user! [user]
  (db/create-user! user))

(defn delete-test-users! []
  (db/delete-user-by-firstname! {:first_name "Testing"}))

(defn clean-db! []
  (db/test-delete-all-alerts)
  (db/test-delete-all-groups!)
  (db/delete-all-users!)
  true)

(defn setup-db! []
  (mount/start
    #'acemotion.config/env
    #'acemotion.db.core/*db*)
  (migrations/migrate ["migrate"] (select-keys env [:database-url]))
  (clean-db!))
