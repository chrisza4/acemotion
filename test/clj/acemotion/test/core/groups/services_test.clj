(ns acemotion.test.core.groups.services_test
  (:require [acemotion.config :refer [env]]
            [acemotion.db.core :refer [*db*] :as db]
            [acemotion.test.helpers :as test-helpers]
            [acemotion.core.groups.services :as groups-services]
            [clojure.test :refer :all]))

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

(use-fixtures :each test-setup)

(comment (test-setup))

(deftest create-and-get-groups
  (testing "should be able to create group with chris and awa, and both chris and awa should see group"
    (groups-services/create-group! (:id chris) [(:id chris) (:id awa)])
    (let [groups (groups-services/get-related-groups (:id chris))
          [group] groups]
      (is (= (count groups) 1))
      (is (= (:id chris) (:owner_id group))))
    (let [groups (groups-services/get-related-groups (:id awa))
          [group] groups]
      (is (= (count groups) 1))
      (is (= (:id chris) (:owner_id group)))))

  (testing "Given paul and luck create a group, chris should not see that group"
    (groups-services/create-group! (:id paul) [(:id paul) (:id luck)])
    (let [groups (groups-services/get-related-groups (:id chris))
          [group] groups]
      (is (= (count groups) 1))
      (is (= (:id chris) (:owner_id group))))))

(deftest get-groups-members
  (testing "Given group with chris as owner, Chris and Awa as members, both chris and awa should be members"
    (let [group (groups-services/create-group! (:id chris) [(:id chris) (:id awa)])
          members (groups-services/get-group-member-ids (:id chris) (:id group))]
      (is (= 2 (count members)))
      (is (some #(= % (:id chris)) members))))

  (testing "Given group with chris as owner, only Awa as members, Chris should be automatically add as member"
    (let [group (groups-services/create-group! (:id chris) [(:id awa)])
          members (groups-services/get-group-member-ids (:id chris) (:id group))]
      (is (= 2 (count members)))
      (is (some #(= % (:id chris)) members)))))
