(ns {{name}}.core
  (:gen-class)
  (:require [etuk.browser-utils :as but :refer [start-chrome-session]]
            [etuk.core-navigator :as cnv :refer :all]
            [etuk.core-wait :as cwt :refer :all]
            [webica.expected-conditions :as ec]
            [webica.remote-web-driver :as browser]
            [webica.web-element :as element]))

(defn google
  [search-term]
  (start-chrome-session)
  (browser/get "https://www.google.com")
  (let [wdriver (cwt/get-instance)]
    (cnv/navigate :wdriver  wdriver
                  :wfn      ec/presence-of-element-located
                  :type     :name
                  :expr     "q"
                  :act-name element/send-keys
                  :act-arg  search-term)
    (cnv/navigate :wdriver  wdriver
                  :wfn      ec/presence-of-element-located
                  :type     :name
                  :expr     "btnG"
                  :act-name element/click
                  :act-arg  nil)))

(defn -main [& args]
  (try
    (google "Clojure Trending")
    ;;(browser/quit)
    (catch Exception e
      (.printStackTrace e)
      (println (str "Unexpected errors: " (.getMessage e))))))
