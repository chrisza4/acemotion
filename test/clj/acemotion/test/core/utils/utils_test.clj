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
