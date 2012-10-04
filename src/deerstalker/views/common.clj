(ns deerstalker.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css include-js html5]]
        [hiccup.element :only [javascript-tag]])
  (:require [noir.session :as session]
            [deerstalker.models.user :as user]))

(defn current-user []
  (when-let [user-id (session/get :user-id)]
    (user/find-by-id user-id)))

(defpartial nav []
            [:div.navbar
              [:div.navbar-inner
                [:a.brand {:href "/"} "Deerstalker"]
                [:ul.nav
                  (if (current-user)
                    [:li [:a {:href "#"} "Logout"]]
                    [:li [:a {:href "/login"} "Login"]])
                  [:li [:a {:href "#"} "Link 2"]]]]])

(defpartial layout [& content]
            (html5
              [:head
                [:title "Deerstalker"]
                (include-css "/bootstrap/css/bootstrap.min.css")]
              [:body
                [:div.container (nav)
                                content]
                (include-js "/js/jquery.min.js")
                (include-js "/bootstrap/js/bootstrap.min.js")
                (javascript-tag "var CLOSURE_NO_DEPS=true;")
                (include-js "/js/main.js")]))
