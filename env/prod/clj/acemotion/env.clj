(ns acemotion.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[acemotion started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[acemotion has shut down successfully]=-"))
   :middleware identity})
