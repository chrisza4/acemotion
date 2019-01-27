(ns acemotion.users.entity
  (:require
    [acemotion.db.core :refer :all]
    [acemotion.utils.utils :as utils]
    [acemotion.users.utils :as user-utils]))

(defn create-seed-users! []
  (create-user! {:id (utils/uuid)
                  :first_name "Chakrit"
                  :last_name "Likitkhajorn"
                  :email "chakrit.lj@gmail.com"
                  :pass (user-utils/hash-password "1234")
                  :salt ""
                  :is_active true})
  (create-user! {:id (utils/uuid)
                  :first_name "Plawtiwa"
                  :last_name "Niyomrat"
                  :email "visal58000@gmail.com"
                  :pass (user-utils/hash-password "1234")
                  :salt ""
                  :is_active true}))

(defn get-by-id [user-id]
  (get-user user-id))

(defn get-by-email [email]
  (get-user-by-email {:email email}))

(defn test-delete-all-users! []
  (delete-all-users!))

(comment (create-seed-users!))
