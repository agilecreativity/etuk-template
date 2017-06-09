(def project '{{name}})
(def version "0.1.0")

(set-env! :resource-paths #{"src" "resources"}
          :source-paths   #{"src" "test"}
          :dependencies   '[[org.clojure/clojure "1.9.0-alpha17"]
                            [etuk "0.1.0"]
                            [easy-config "0.1.2"]
                            [me.raynes/fs "1.4.6"]
                            [easy-config "0.1.2"]
                            [adzerk/boot-test "1.2.0" :scope "test"]
                            [adzerk/bootlaces "0.1.13" :scope "test"]])

(task-options!
  pom {:project     project
       :version     version
       :description "FIXME: automate stuffs with the help of etuk"
       :url         "http://github.com/<github-id>/{{name}}"
       :scm         {:url "https://github.com/agilecreativity/{{name}}"}
       :license     {"Eclipse Public License"
                     "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask build
  "Build and install the project locally."
  []
  (comp (pom) (jar) (install)))

(require '[adzerk.boot-test :refer [test]]
         '[adzerk.bootlaces :refer :all])

(bootlaces! version)

(deftask clj-dev
  "Clojure REPL for CIDER"
  []
  (comp
    (cider)
    (repl :server true)
    (wait)))

(deftask cider-boot
  "Cider boot params task"
  []
  (clj-dev))

(require '[boot.lein :refer :all])

(require '{{name}}.example.download-selenium)
(deftask download-selenium
  "Download Selenium jar file"
  []
  (with-pass-thru _
    ({{name}}.example.download-selenium/-main)))

(require '{{name}}.example.login-github)
(deftask login-github
  "Login to Github.com"
  []
  (with-pass-thru _
    ({{name}}.example.login-github/-main)))

(require '{{name}}.core)
(deftask google-search
  "Run simple Google search"
  []
  (with-pass-thru _
    ({{name}}.core/-main)))
