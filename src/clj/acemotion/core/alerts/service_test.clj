(ns acemotion.core.alerts.service_test
  (:require [clojure.test :refer :all]
            [acemotion.db.core :refer [*db*] :as db]
            [acemotion.core.users.utils :as user-utils]
            [acemotion.core.users.entity :as user-entity]
            [acemotion.core.utils.utils :as utils]))

(def test-user {:id (utils/uuid)
                :first_name "Chakrit"
                :last_name "Likitkhajorn"
                :email "chakrit@gmail.com"
                :pass (user-utils/hash-password "1234")
                :salt ""
                :is_active true})

(def test-user2 {:id (utils/uuid)
                 :first_name "Chakrit"
                 :last_name "Likitkhajorn"
                 :email "chakrit@gmail.com"
                 :pass (user-utils/hash-password "1234")
                 :salt ""
                 :is_active true})

(defn test-setup [f]
  (db/create-user! test-user)
  (db/create-user! test-user2)
  (f)
  (user-entity/test-delete-all-users!))

(use-fixtures :once test-setup)

(deftest create-alert
  (testing "Should be able to create alert"
    (is true)))
