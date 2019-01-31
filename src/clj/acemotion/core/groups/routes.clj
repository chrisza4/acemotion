(ns acemotion.core.groups.routes
  (:require [compojure.api.sweet :as compojure-api]
            [compojure.api.meta :refer [restructure-param]]
            [acemotion.core.groups.services :as groups-services]
            [acemotion.core.groups.schemas :as groups-schemas]
            [acemotion.routes.utils :as routes-utils]
            [acemotion.core.utils.utils :as utils]))

; (routes-utils/ProvideAuth)

(defn- get-groups-handler [user]
  (->> (routes-utils/user-id user)
       (groups-services/get-related-groups)
       (routes-utils/json-ok)))

(def my-routes
  (compojure-api/routes
    (compojure-api/GET "/groups" []
      :return (routes-utils/api-response [groups-schemas/group])
      :current-user user
      (get-groups-handler user))))
