(ns deerstalker.views.authentication
  (:use [noir.core :only [defpage defpartial pre-route]]
        [hiccup.form :only [form-to label text-field password-field check-box]])
  (:require [deerstalker.views.common :as common]
            [deerstalker.models.user :as user]
            [noir.session :as session]
            [noir.response :as response]))

(defpartial login-form [& [email remember-me]]
            (form-to {:class "form-horizontal"} [:post "/login"]
                     [:div.control-group
                       (label {:class "control-label"} "email" "Email:")
                       [:div.controls (text-field {:id "email" :placeholder "Email"} "email" email)]]
                     [:div.control-group
                       (label {:class "control-label"} "password" "Password:")
                       [:div.controls (password-field {:id "password" :placeholder "Password"} "password")]]
                     [:div.control-group
                       [:div.controls
                         (label {:class "checkbox"} "remember-me" (list (check-box "remember-me" remember-me 1)
                                                                        "Remember me"))
                         [:button.btn {:type "submit"} "Login!"]]]))

(defpartial registration-form [& [{:keys [email first-name last-name]}]]
            (form-to {:class "form-horizontal"} [:post "/login"]
                     [:div.control-group
                       (label {:class "control-label"} "first-name" "First Name:")
                       [:div.controls (text-field {:id "first-name" :placeholder "First Name"} "first-name" first-name)]]
                     [:div.control-group
                       (label {:class "control-label"} "last-name" "Last Name:")
                       [:div.controls (text-field {:id "last-name" :placeholder "Last Name"} "last-name" last-name)]]
                     [:div.control-group
                       (label {:class "control-label"} "email" "Email:")
                       [:div.controls (text-field {:id "email" :placeholder "Email"} "email" email)]]
                     [:div.control-group
                       (label {:class "control-label"} "password" "Password:")
                       [:div.controls (password-field {:id "password" :placeholder "Password"} "password")]]
                     [:div.control-group
                       (label {:class "control-label"} "password-confirmation" "Confirm Password:")
                       [:div.controls (password-field {:id "password-confirmation" :placeholder "Confirm Password"} "password-confirmation")]]
                     [:div.control-group
                       [:div.controls
                         [:button.btn {:type "submit"} "Sign up!"]]]))

(pre-route "/login" []
           (when (common/current-user) (response/redirect "/")))
(pre-route "/signup" []
           (when (common/current-user) (response/redirect "/")))

(defpage [:get "/signup"] []
         (common/layout (registration-form)))

(defpage [:get "/login"] []
         (common/layout (login-form)))

(defpage [:post "/login"] [{:keys [email password remember-me]}]
         (if-let [user (user/authenticate email password)]
           (do (session/put! (user :_id)) (response/redirect "/"))
           (login-form email (= remember-me "1"))))
