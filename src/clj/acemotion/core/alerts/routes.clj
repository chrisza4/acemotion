(ns acemotion.core.alerts.routes
  (:require [compojure.api.sweet :as compojure-api]
            [compojure.api.meta :refer [restructure-param]]
            [acemotion.core.alerts.services :as alerts-services]
            [acemotion.routes.utils :as routes-utils]
            [acemotion.core.utils.utils :as utils]
            [ring.util.http-response :as response]))

(routes-utils/ProvideAuth)

(def my-routes
  (compojure-api/routes
    (compojure-api/GET "/alerts" []
      :current-user user
      (response/ok (alerts-services/get-alerts (utils/uuid (:id user)))))

    (compojure-api/POST "/alerts" []
      "Not implemented yet")))
