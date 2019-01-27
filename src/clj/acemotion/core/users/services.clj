(ns acemotion.core.users.services
  (:require
    [acemotion.core.users.entity :refer :all]
    [acemotion.core.users.utils :refer :all]))

(defn- add-token [user-hash]
  (assoc user-hash :token (create-jwt-token user-hash)))

(defn login [email password]
  (if-let [user (get-by-email email)]
    (when (validate-password password (:pass user))
          user)))

(defn login-get-token [email password]
  (if-let [user (login email password)]
    (user-to-jwt user)))
