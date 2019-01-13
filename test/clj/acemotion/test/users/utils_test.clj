(ns acemotion.test.users.utils_test
  (:require
    [acemotion.users.utils :refer :all]
    [clojure.test :refer :all]))

(deftest test-hash-paassord
  (testing "hashed password should not equal original password"
    (let [hashed-pwd (hash-password "my-pwd")]
      (is (not= hashed-pwd "my-pwd"))))

  (testing "hashed password should be able to validate back"
    (let [hashed-pwd (hash-password "another-pwd")]
      (is (validate-password "another-pwd" hashed-pwd))))

  (testing "hashed password should be able to validate back"
    (let [hashed-pwd (hash-password "another-pwd")]
      (is (not (validate-password "another-pwd2" hashed-pwd))))))
