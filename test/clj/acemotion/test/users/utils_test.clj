(ns acemotion.test.users.utils_test
  (:require
    [acemotion.users.utils :refer :all]
    [acemotion.utils.utils :as utils]
    [clojure.test :refer :all]))

(deftest test-hash-password
  (testing "hashed password should not equal original password"
    (let [hashed-pwd (hash-password "my-pwd")]
      (is (not= hashed-pwd "my-pwd"))))

  (testing "hashed password should be able to validate back"
    (let [hashed-pwd (hash-password "another-pwd")]
      (is (validate-password "another-pwd" hashed-pwd))))

  (testing "hashed password should be able to validate back"
    (let [hashed-pwd (hash-password "another-pwd")]
      (is (not (validate-password "another-pwd2" hashed-pwd))))))

(deftest test-jwt-token
  (testing "should be able to create and verify token"
    (let [user {:id (str utils/uuid) :email "test@test.com"}
          user-id (:id user)
          token (create-jwt-token user)
          verified-data (verify-jwt-token token)]
      (do
        (is (= (:email verified-data) "test@test.com"))
        (is (= (:id verified-data) user-id))))))
