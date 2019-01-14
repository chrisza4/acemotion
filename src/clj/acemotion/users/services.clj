(ns acemotion.users.services
  (:require
    [acemotion.users.entity :refer :all]
    [acemotion.users.utils :refer :all]))

(defn login [email password]
  (if-let [user (get-by-email email)]
    (if (validate-password password (:pass user))
      user
      nil)))
