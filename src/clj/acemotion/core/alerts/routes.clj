(ns acemotion.core.alerts.routes
  (:require [compojure.api.sweet :as compojure-api]
            [ring.util.http-response :as response]))

(def my-routes
  (compojure-api/routes
    (compojure-api/GET "/alert-sanity" [])
    (response/ok "hi")

    (compojure-api/GET "/alerts" []
     (response/ok "Not implemented yet"))))
