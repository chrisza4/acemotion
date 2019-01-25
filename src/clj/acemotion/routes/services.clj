(ns acemotion.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [compojure.api.meta :refer [restructure-param]]
            [acemotion.users.services :as user-service]
            [buddy.auth.accessrules :refer [restrict]]
            [buddy.auth :refer [authenticated?]]))

(defn access-error [_ _]
  (unauthorized {:error "unauthorized"}))

(defn wrap-restricted [handler rule]
  (restrict handler {:handler  rule
                     :on-error access-error}))

(defmethod restructure-param :auth-rules
  [_ rule acc]
  (update-in acc [:middleware] conj [wrap-restricted rule]))

(defmethod restructure-param :current-user
  [_ binding acc]
  (update-in acc [:letks] into [binding `(:identity ~'+compojure-api-request+)]))

(s/defschema api-response
  {:ok Boolean
   :response s/Any})



(def service-routes
  (api
    {:swagger {:ui "/swagger-ui"
               :spec "/swagger.json"
               :data {:info {:version "1.0.0"
                             :title "Sample API"
                             :description "Sample Services"}}}}

    (GET "/authenticated" []
         :auth-rules authenticated?
         :current-user user
         (ok {:user user}))

    (POST "/login" []
      :return      api-response
      :body-params [username :- String, password :- String]
      :summary "Login and get authentication token"
      (let [token (user-service/login-get-token username password)]
        {:body
         (if (= token nil)
           {:ok false :response "error authentication"}
           {:ok true  :response {:token token}})}))))
