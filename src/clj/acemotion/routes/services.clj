(ns acemotion.routes.services
  (:require [ring.util.http-response :as response]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [compojure.api.meta :refer [restructure-param]]
            [acemotion.core.users.services :as user-service]
            [buddy.auth.accessrules :refer [restrict]]
            [buddy.auth :refer [authenticated?]]))

(defn access-error [_ _]
  (response/unauthorized {:error "unauthorized"}))

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

    (GET "/whoami" []
         :auth-rules authenticated?
         :current-user user
         :header-params [authorization :- s/Str]
         (response/ok user))

    (POST "/login" []
      :return      api-response
      :body-params [username :- s/Str, password :- s/Str]
      :summary "Login and get authentication token"
      (let [token (user-service/login-get-token username password)]
        {:body
         (if (= token nil)
           {:ok false :response "Error authentication"}
           {:ok true  :response {:token token}})}))))
