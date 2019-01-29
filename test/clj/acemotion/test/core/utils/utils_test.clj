(ns acemotion.test.core.utils.utils_test
  (:require
    [acemotion.core.utils.utils :as utils]
    [clojure.test :refer :all]))

(deftest union-vector
  (testing "in case member exists, should not add into vector"
    (let [actual (utils/union-vector [1 2] 1)]
      (is (= actual [1 2]))))

  (testing "in case not exists, should add into vector"
    (let [actual (utils/union-vector [1 2] 3)]
      (is (= [1 2 3] actual))))

  (testing "should also work with uuid"
    (let [[uuid1 uuid2 uuid3] [(utils/uuid) (utils/uuid) (utils/uuid)]
          actual (utils/union-vector [uuid1] uuid1)]
      (is (= [uuid1] actual)))))

(deftest fill-id
  (testing "if input map do not have id field, provide new id as uuid"
    (let [actual (utils/fill-id {:a 1})]
      (is (uuid? (:id actual)))))

  (testing "if input map already have id field, use old id"
    (let [actual (utils/fill-id {:id 1})]
      (is (= 1 (:id actual))))))
