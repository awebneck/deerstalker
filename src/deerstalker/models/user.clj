(ns deerstalker.models.user
  (:require [monger.collection :as mc])
  (:import java.security.MessageDigest
           java.util.Random))

(defn find-by-id [id]
  (mc/find-one-as-map "users" {:_id id}))

(defn generate-salt []
  (let [r (Random.)
        b (byte-array 20)]
    (.nextBytes r b)
    (String. b)))

(defn hash-password [password salt]
  (let [digest (MessageDigest/getInstance "SHA-512")]
    (loop [n 100
           h (.getBytes (str password salt) "UTF-8")]
      (if (= n 0)
        (String. h)
        (recur (dec n) (do (.reset digest) (.digest digest h)))))))

(defn authenticate [email password]
  (when-let [user (mc/find-one-as-map "users" {:email email})]
    (when (= (hash-password password (user :salt)) (user :crypted_password))
      user)))
