(ns acemotion.core.users.entity
  (:require
    [acemotion.db.core :as db]
    [acemotion.core.utils.utils :as utils]
    [acemotion.core.users.utils :as user-utils]))

(defn create-seed-users! []
  (db/create-user! {:id (utils/uuid)
                    :first_name "Chakrit"
                    :last_name "Likitkhajorn"
                    :email "chakrit.lj@gmail.com"
                    :pass (user-utils/hash-password "1234")
                    :salt ""
                    :is_active true})
  (db/create-user! {:id (utils/uuid)
                    :first_name "Plawtiwa"
                    :last_name "Niyomrat"
                    :email "visal58000@gmail.com"
                    :pass (user-utils/hash-password "1234")
                    :salt ""
                    :is_active true}))

(defn get-by-id [user-id]
  (db/get-user user-id))

(defn get-by-email [email]
  (db/get-user-by-email {:email email}))

(defn test-delete-all-users! []
  (db/delete-all-users!))
