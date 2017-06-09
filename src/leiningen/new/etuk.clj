(ns leiningen.new.etuk
  (:require [leiningen.new.templates :refer [renderer
                                             name-to-path
                                             ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "etuk"))

(defn etuk
  "Create a new etuk automation project"
  [name]
  (let [data {:name      name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' etuk project.")
    (->files data
             [".gitignore" (render "gitignore" data)]
             ["readme.org" (render "readme.org" data)]
             ["build.boot" (render "build.boot" data)]
             ["project.clj" (render "project.clj" data)]
             ["resources/github-config.edn" (render "example-github-config.edn" data)]
             ["resources/gmail-config.edn" (render "example-gmail-config.edn" data)]
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
             ["src/{{sanitized}}/example/download_selenium.clj" (render "download-selenium.clj" data)]
             ["src/{{sanitized}}/example/login_github.clj" (render "login-github.clj" data)]
             ["test/{{sanitized}}/core_test.clj" (render "core_test.clj" data)])))
