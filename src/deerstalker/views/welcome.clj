(ns deerstalker.views.welcome
  (:require [deerstalker.views.common :as common]
            [noir.content.getting-started]
            [noir.session :as session])
  (:use [noir.core :only [defpage]])
  (:import [org.bson.types ObjectId]))

(defpage "/" []
         (common/layout [:h1 "Welcome to Deerstalker!"]))
