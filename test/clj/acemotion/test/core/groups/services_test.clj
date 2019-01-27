(ns acemotion.test.core.groups.services_test
  (:require [acemotion.config :refer [env]]
            [acemotion.db.core :refer [*db*] :as db]
            [acemotion.test.helpers :as test-helpers]
            [acemotion.core.groups.services :as groups-services]
            [clojure.test :refer :all]
            [luminus-migrations.core :as migrations]
            [mount.core :as mount]))

(def chris (test-helpers/generate-test-user "chris@user.com"))
(def awa (test-helpers/generate-test-user "awa@user.com"))
(def paul (test-helpers/generate-test-user "paul@user.com"))
(def mo (test-helpers/generate-test-user "mo@user.com"))
(def faii (test-helpers/generate-test-user "faii@user.com"))
(def luck (test-helpers/generate-test-user "luck@user.com"))

(defn test-setup [f]
  (test-helpers/setup-db!)
  (doseq [user [chris awa paul mo faii luck]]
    (test-helpers/create-user! user))
  (f))

(use-fixtures :once test-setup)

(comment (test-setup))

(deftest groups
  (testing "should be able to create group with chris and awa, and both chris and awa should see group"
    (groups-services/create-group! (:id chris) [(:id chris) (:id awa)])
    (let [groups (groups-services/get-related-groups (:id chris))
          [group] groups]
      (is (= (count groups) 1))
      (is (= (:id chris) (:owner_id group))))
    (let [groups (groups-services/get-related-groups (:id awa))
          [group] groups]
      (is (= (count groups) 1))
      (is (= (:id chris) (:owner_id group))))))
