(ns acemotion.test.core.alerts.service_test
  (:require [clojure.test :refer :all]
            [acemotion.test.helpers :as test-helpers]
            [acemotion.core.alerts.services :as alerts-services]
            [acemotion.core.utils.utils :as utils]))

(def chris (test-helpers/generate-test-user "chris@user.com"))
(def awa (test-helpers/generate-test-user "awa@user.com"))
(def paul (test-helpers/generate-test-user "paul@user.com"))
(def group-chris-awa (test-helpers/generate-group (:id chris)))

(defn test-setup [f]
  (test-helpers/setup-db!)
  (doseq [user [chris awa paul]]
    (test-helpers/create-user! user))
  (-> group-chris-awa
      (test-helpers/create-group!)
      (:id)
      (test-helpers/add-members-to-group! [(:id chris) (:id awa)]))
  (f))

(use-fixtures :each test-setup)

(deftest get-related-alerts
  (let [alert {:id (utils/uuid)
               :created ""
               :updated ""
               :owner_id (:id awa)
               :group_id (:id group-chris-awa)
               :status 1}]
    (testing "Given group with Awa and Chris, when Awa posting alert"
      (alerts-services/create-alert! alert))
    (testing "Chris should be able to see it"
      (let [alerts (alerts-services/get-alerts (:id chris))
            [fetched-alert] alerts]
        (is (= 1 (count alerts)))
        (is (= (:id alert) (:id fetched-alert)))
        (is (= (:status alert) (:status fetched-alert)))))
    (testing "Other people should not be able to see it"
      (let [alerts (alerts-services/get-alerts (:id paul))]
        (is (= 0 (count alerts)))))))

(deftest create-alert!
  (testing "Given duplicate alert, should throw duplicate exception"
    (let [alert {:id (utils/uuid)
                 :created ""
                 :updated ""
                 :owner_id (:id awa)
                 :group_id (:id group-chris-awa)
                 :status 1}]
      (alerts-services/create-alert! alert)
      (try
        (alerts-services/create-alert! alert)
        (catch Exception e
          (is (= (:cause (ex-data e)) :duplicate-id)))))))
