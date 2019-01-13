(ns acemotion.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [acemotion.core-test]))

(doo-tests 'acemotion.core-test)

