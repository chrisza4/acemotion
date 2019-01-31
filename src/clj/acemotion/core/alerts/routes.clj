(ns acemotion.core.alerts.routes
  (:require [compojure.api.sweet :as compojure-api]
            [compojure.api.meta :refer [restructure-param]]
            [ring.util.http-response :as response]
            [buddy.auth.accessrules :refer [restrict]]
            [acemotion.core.alerts.services :as alerts-services]
            [acemotion.core.alerts.schemas :as alerts-schemas]
            [acemotion.routes.utils :as routes-utils]
            [acemotion.core.utils.utils :as utils]))

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

(defn- get-alert-handler [user]
  (->> (routes-utils/user-id user)
       (alerts-services/get-alerts)
       (routes-utils/json-ok)))

(defn- post-alert-handler [user data]
  (->> (routes-utils/user-id user)
       (assoc data :owner_id)
       (utils/fill-id)
       (alerts-services/create-alert!)
       (routes-utils/json-ok)))

(def my-routes
  (compojure-api/routes
    (compojure-api/GET "/alerts" []
      :return (routes-utils/api-response [alerts-schemas/alert])
      :current-user user
      (get-alert-handler user))

    (compojure-api/POST "/alerts" []
      :body [data alerts-schemas/alert-post]
      :current-user user
      :return (routes-utils/api-response alerts-schemas/alert)
      (routes-utils/console-log data)
      (post-alert-handler user data))))
