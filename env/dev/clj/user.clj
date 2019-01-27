(ns user
  (:require [acemotion.config :refer [env]]
            [clojure.spec.alpha :as s]
            [expound.alpha :as expound]
            [mount.core :as mount]
            [acemotion.figwheel :refer [start-fw stop-fw cljs]]
            [acemotion.core :refer [start-app]]
            [acemotion.db.core]
            [conman.core :as conman]
            [luminus-migrations.core :as migrations]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(defn start []
  (mount/start-without #'acemotion.core/repl-server))

(defn stop []
  (mount/stop-except #'acemotion.core/repl-server))

(defn restart []
  (stop)
  (start))

(defn restart-db []
  (mount/stop #'acemotion.db.core/*db*)
  (mount/start #'acemotion.db.core/*db*)
  (binding [*ns* 'acemotion.db.core]
    (conman/bind-connection acemotion.db.core/*db* "sql/queries.sql" "sql/group-queries.sql")))

(defn reset-db []
  (migrations/migrate ["reset"] (select-keys env [:database-url])))

(defn migrate []
  (migrations/migrate ["migrate"] (select-keys env [:database-url])))

(defn rollback []
  (migrations/migrate ["rollback"] (select-keys env [:database-url])))

(defn create-migration [name]
  (migrations/create name (select-keys env [:database-url])))

(comment
  (restart) (migrate) (rollback) (restart-db)
  (create-migration "owner-relationships"))
