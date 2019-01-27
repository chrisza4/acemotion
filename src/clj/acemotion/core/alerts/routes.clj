(ns acemotion.core.alerts.routes
  (:require [compojure.api.sweet :as compojure-api]
            [ring.util.http-response :as response]))

(def my-routes
  (compojure-api/routes
    (compojure-api/GET "/alerts" []
      "Not implemented yet")

    (compojure-api/POST "/alerts" []
      "Not implemented yet")))
