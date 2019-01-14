(ns acemotion.users.utils
  (:require
    [acemotion.config :refer [env]]
    [buddy.sign.jwt :as jwt]
    [crypto.password.pbkdf2 :as password]))

(defn hash-password [pwd]
  (password/encrypt pwd 100000))

(defn validate-password [pwd hash]
  (password/check pwd hash))

(defn create-jwt-token [user]
  (jwt/sign user (:jwt-secret env)))

(defn verify-jwt-token [token]
  (jwt/unsign token (:jwt-secret env)))
