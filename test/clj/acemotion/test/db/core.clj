(ns acemotion.test.db.core
  (:require [acemotion.db.core :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [acemotion.config :refer [env]]
            [acemotion.core.utils.utils :as utils]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'acemotion.config/env
      #'acemotion.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

; (deftest test-users
;   (jdbc/with-db-transaction [t-conn *db*]
;     (jdbc/db-set-rollback-only! t-conn)
;     (let [userid (utils/uuid)]
;       (is (= 1 (db/create-user!
;                 t-conn
;                 {:id          userid
;                   :first_name "Sam"
;                   :last_name  "Smith"
;                   :email      "sam.smith@example.com"
;                   :pass       "pass"
;                   :salt       "salt"})))
;       (is (= {:id         userid
;               :first_name "Sam"
;               :last_name  "Smith"
;               :email      "sam.smith@example.com"
;               :pass       "pass"
;               :salt       "salt"
;               :last_login nil
;               :is_active  nil}
;             (db/get-user t-conn {:id userid}))))))
