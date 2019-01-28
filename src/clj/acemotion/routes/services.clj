(ns acemotion.routes.services
  (:require [ring.util.http-response :as response]
            [compojure.api.sweet :as compojure-api]
            [schema.core :as s]
            [acemotion.core.users.services :as user-service]
            [acemotion.core.alerts.routes :as alerts-routes]
            [acemotion.routes.utils :as routes-utils]
            [buddy.auth :refer [authenticated?]]))

(routes-utils/ProvideAuth)

(def service-routes
  (compojure-api/api
    {:swagger {:ui "/swagger-ui"
               :spec "/swagger.json"
               :data {:info {:version "1.0.0"
                             :title "Sample API"
                             :description "Sample Services"}}}}
    (compojure-api/POST "/login" []
      :return      (routes-utils/api-response s/Any)
      :body-params [username :- s/Str, password :- s/Str]
      :summary "Login and get authentication token"
      (let [token (user-service/login-get-token username password)]
        {:body
         (if (= token nil)
           {:ok false :error "Error authentication"}
           {:ok true  :data {:token token}})}))

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
