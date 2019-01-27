(ns acemotion.core.users.utils
  (:require
    [acemotion.config :refer [env]]
    [buddy.sign.jwt :as jwt]
    [crypto.password.pbkdf2 :as password])
  (:import java.lang.IndexOutOfBoundsException))

(defn hash-password [pwd]
  (password/encrypt pwd 100000))

(defn validate-password [pwd hash]
  (try
    (password/check pwd hash)
    (catch IndexOutOfBoundsException e ; IndexOutOfBoundsException means hash incorrect format
      false)
    (catch Exception e
      false)))

(defn create-jwt-token [user]
  (jwt/sign user (:jwt-secret env)))

(defn verify-jwt-token [token]
  (jwt/unsign token (:jwt-secret env)))

(defn user-to-jwt [user]
  (-> (select-keys user [:id :email])
      (create-jwt-token)))
