(ns acemotion.users.utils
  (:require
    [crypto.password.pbkdf2 :as password]))

(defn hash-password [pwd]
  (password/encrypt pwd 100000))

(defn validate-password [pwd hash]
  (password/check pwd hash))
