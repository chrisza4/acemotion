(ns acemotion.core.alerts.routes
  (:require [compojure.api.sweet :as compojure-api]
            [ring.util.http-response :as response]))

(def routes
  (compojure-api/GET "/alert-sanity" []
    (response/ok "hi")))
