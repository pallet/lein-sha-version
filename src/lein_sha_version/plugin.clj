(ns lein-sha-version.plugin
  (:use
   [clojure.java.io :only [file]]
   [leiningen.core.main :only [debug]])
  (:import
   org.eclipse.jgit.storage.file.FileRepositoryBuilder
   [org.eclipse.jgit.lib ObjectId Repository]))

(defn git-sha [{:keys [root version sha]}]
  (debug "Finding SHA for" root)
  (let [^Repository repository (.. (FileRepositoryBuilder.)
                                   (readEnvironment)
                                   (findGitDir (file root))
                                   (build))
        ^ObjectId head (.resolve repository "HEAD")]
    (if head
      (let [abbr (.abbreviate head (:length sha 7))]
        (debug "Found SHA" (.name head) "using" (.name abbr))
        (.name abbr))
      version)))

(defn middleware
  [project]
  (assoc project :version (git-sha project)))
