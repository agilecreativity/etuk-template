(ns {{name}}.example.login-github
  (:require [easy-config.core :as ecf]
            [etuk.browser-utils :as but :refer [start-chrome-session]]
            [etuk.core-navigator :as cnv :refer :all]
            [etuk.core-wait :as cwt :refer :all]
            [webica.expected-conditions :as ec]
            [webica.remote-web-driver :as browser]
            [webica.web-element :as element]))

(defn- read-config
  ([]
   (read-config (str (System/getenv "HOME") "/Dropbox/github.edn")))
  ([config-path]
   (ecf/all-config config-path)))

(defn- starter-page
  "Navigate to the starter page"
  [url]
  (start-chrome-session)
  (browser/get url))

(defn login-github
  [config-file]
  (let [{:keys [url username password]} (read-config config-file)]
    (starter-page url)
    (let [wdriver (cwt/get-instance)]
      (cnv/navigate :wdriver  wdriver
                    :wfn      ec/presence-of-element-located
                    :type     :css-selector
                    :expr     "#login_field"
                    :act-name element/send-keys
                    :act-arg  username)

      (cnv/navigate :wdriver  wdriver
                    :wfn      ec/presence-of-element-located
                    :type     :css-selector
                    :expr     "#password"
                    :act-name element/send-keys
                    :act-arg  password)

      (cnv/navigate :wdriver  wdriver
                    :wfn      ec/presence-of-element-located
                    :type     :css-selector
                    :expr     "#login > form > div.auth-form-body.mt-3 > input.btn.btn-primary.btn-block"
                    :act-name element/click
                    :act-arg  nil))))

(defn -main
  [& args]
  (try
    (login-github (str (System/getenv "HOME") "/Dropbox/github.edn"))
    (catch Exception e
      (.printStackTrace e)
      (println (str "Unexpected errors: " (.getMessage e))))))
