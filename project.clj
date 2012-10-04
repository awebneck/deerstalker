(defproject deerstalker "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [noir "1.3.0-beta10"]
                 [com.novemberain/monger "1.2.0"]]
  :profiles {:dev {:plugins [[lein-midje "2.0.0-SNAPSHOT"]
                             [lein-lesscss "1.2"]
                             [lein-cljsbuild "0.2.7"]]
                   :dependencies [[midje "1.4.0"]]}}
  :main deerstalker.server
  :vimclojure-opts {:repl true}
  :lesscss-paths ["resources/less"]
  :lesscss-output-path "resources/public/css"
  :cljsbuild {:builds {:main {:source-path "src-cljs"
                              :compiler {:output-to "resources/public/js/main.js"
                                         :optimizations :whitespace
                                         :pretty-print true
                                         :externs ["resources/externs/jquery.js"]}}}})
