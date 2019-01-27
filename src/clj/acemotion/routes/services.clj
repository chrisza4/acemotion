(ns acemotion.routes.services
  (:require [ring.util.http-response :as response]
            [compojure.api.sweet :as compojure-api]
            [schema.core :as s]
            [compojure.api.meta :refer [restructure-param]]
            [acemotion.core.users.services :as user-service]
            [acemotion.core.alerts.routes :as alerts-routes]
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
  (compojure-api/api
    {:swagger {:ui "/swagger-ui"
               :spec "/swagger.json"
               :data {:info {:version "1.0.0"
                             :title "Sample API"
                             :description "Sample Services"}}}}
    (compojure-api/POST "/login" []
      :return      api-response
      :body-params [username :- s/Str, password :- s/Str]
      :summary "Login and get authentication token"
      (let [token (user-service/login-get-token username password)]
        {:body
         (if (= token nil)
           {:ok false :response "Error authentication"}
           {:ok true  :response {:token token}})}))

    (compojure-api/context "/api" []
      :auth-rules authenticated?
      :header-params [authorization :- s/Str]
      :tags ["private"]

      (compojure-api/GET "/sanity" []
           (response/ok "I am sane"))

      #'alerts-routes/my-routes

      (compojure-api/GET "/whoami" []
           :current-user user
           (response/ok user)))))
