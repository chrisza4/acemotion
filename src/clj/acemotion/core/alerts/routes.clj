(ns acemotion.core.alerts.routes
  (:require [compojure.api.sweet :as compojure-api]
            [compojure.api.meta :refer [restructure-param]]
            [acemotion.core.alerts.services :as alerts-services]
            [acemotion.core.alerts.schemas :as alerts-schemas]
            [acemotion.routes.utils :as routes-utils]
            [acemotion.core.utils.utils :as utils]
            [ring.util.http-response :as response]))

(routes-utils/ProvideAuth)

(def my-routes
  (compojure-api/routes
    (compojure-api/GET "/alerts" []
      :return (routes-utils/api-response [alerts-schemas/alert])
      :current-user user
      (->> (routes-utils/user-id user)
           (alerts-services/get-alerts)
           (routes-utils/json-ok)))

    (compojure-api/POST "/alerts" []
      :body [data alerts-schemas/alert-post]
      :current-user user
      :return (routes-utils/api-response alerts-schemas/alert)
      (->> (routes-utils/user-id user)
           (assoc data :owner_id)
           (utils/fill-id)
           (alerts-services/create-alert!)
           (routes-utils/json-ok)))))
