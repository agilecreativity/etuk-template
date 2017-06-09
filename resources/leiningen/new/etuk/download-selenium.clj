(ns {{name}}.example.download-selenium
  (:gen-class)
  (:require [etuk.browser-utils :as but :refer [start-chrome-session]]
            [etuk.core-navigator :as cnv :refer :all]
            [etuk.core-wait :as cwt :refer :all]
            [webica.expected-conditions :as ec]
            [webica.remote-web-driver :as browser]
            [webica.web-element :as element]))

(defn download-selenium-jars
  []
  (start-chrome-session)
  (browser/get "http://www.seleniumhq.org/")
  (let [wdriver (cwt/get-instance)]
    (cnv/navigate :wdriver  wdriver
                  :wfn      ec/presence-of-element-located
                  :type     :css-selector
                  :expr     "#sidebar > div.downloadBox > a"
                  :act-name element/click
                  :act-arg  nil)

    (cnv/navigate :wdriver  wdriver
                  :wfn      ec/presence-of-element-located
                  :type     :css-selector
                  :expr     "#mainContent > table:nth-child(13) > tbody > tr:nth-child(1) > td:nth-child(4) > a"
                  :act-name element/click
                  :act-arg  nil)))

(defn -main [& args]
  (try
    (download-selenium-jars)
    (browser/quit)
    (catch Exception e
      (.printStackTrace e)
      (println (str "Unexpected errors: " (.getMessage e))))))
