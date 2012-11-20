{:release
 {:plugins [[lein-set-version "0.2.1"]]
  :set-version
  {:updates [{:path "README.md"
              :no-snapshot true
              :search-regex
              #"lein-sha-version \"\d+\.\d+\.\d+\""}]}}}
