(ns acemotion.test.users.service_test
  (:require
    [acemotion.core.users.services :refer :all]
    [acemotion.db.core :refer [*db*] :as db]
    [acemotion.core.utils.utils :as utils]
    [acemotion.core.users.utils :as user-utils]
    [acemotion.core.users.entity :as user-entity]
    [clojure.test :refer :all]))

(def test-user {:id (utils/uuid)
                :first_name "Chakrit"
                :last_name "Likitkhajorn"
                :email "chakrit@gmail.com"
                :pass (user-utils/hash-password "1234")
                :salt ""
                :is_active true})

(defn test-setup [f]
  (db/create-user! test-user)
  (f)
  (user-entity/test-delete-all-users!))

(use-fixtures :once test-setup)

(deftest test-login
  (testing "should be able to login with correct username and password"
    (let [user (login "chakrit@gmail.com" "1234")]
      (is (= (:email user) "chakrit@gmail.com"))))

  (testing "should not be able to login with correct username and incorrect password"
    (let [user (login "chakrit@gmail.com" "123x")]
      (is (= user nil))))

  (testing "should not be able to login with incorrect username and incorrect password"
    (let [user (login "chakritx@gmail.com" "1234")]
      (is (= user nil)))))

(deftest test-login-get-token
  (testing "should return token that only contain email address and id"
    (let [jwt-user (->  (login-get-token "chakrit@gmail.com" "1234")
                        (user-utils/verify-jwt-token))]
      (is (contains? jwt-user :id))
      (is (contains? jwt-user :email))
      (is (not (contains? jwt-user :pass))))))
