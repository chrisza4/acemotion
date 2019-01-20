(ns acemotion.users.services
  (:require
    [acemotion.users.entity :refer :all]
    [acemotion.users.utils :refer :all]))

(defn- add-token [user-hash]
  (assoc user-hash :token (create-jwt-token user-hash)))

(defn login [email password]
  (if-let [user (get-by-email email)]
    (if (validate-password password (:pass user)) user nil)))

(defn login-get-token [email password]
  (if-let [user (login email password)]
    (create-jwt-token user)))
