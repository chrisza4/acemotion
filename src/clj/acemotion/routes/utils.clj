(ns acemotion.routes.utils
  (:require [compojure.api.sweet :as compojure-api]
            [schema.core :as s]
            [acemotion.core.utils.utils :as utils]
            [clojure.tools.logging :as log]
            [buddy.auth.accessrules :refer [restrict]]
            [ring.util.http-response :as response]
            [compojure.api.meta :refer [restructure-param]]))

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

(defn json-ok [data]
  (response/ok {:ok true
                :data data}))

(defn console-log [a]
  (log/info a)
  a)

(defn user-id [{user-id :id}]
  (if (uuid? user-id)
      user-id
      (utils/uuid user-id)))

(defn api-response [data-schemas]
  {:ok Boolean
   :data data-schemas
   (s/optional-key :error) s/Str})

(defmacro ProvideAuth []
  '(defmethod restructure-param :current-user
    [_ binding acc]
    (update-in acc [:letks] into [binding `(:identity ~'+compojure-api-request+)]))
   (defmethod restructure-param :auth-rules
     [_ rule acc]
     (update-in acc [:middleware] conj [wrap-restricted rule])))
