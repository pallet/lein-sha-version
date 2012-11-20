(ns lein-sha-version.plugin
  (:use
   [clojure.java.io :only [file]])
  (:import
   org.eclipse.jgit.storage.file.FileRepositoryBuilder
   [org.eclipse.jgit.lib ObjectId Repository]))

(defn git-sha [{:keys [root version sha]}]
  (println "Finding SHA for" root)
  (let [^Repository repository (.. (FileRepositoryBuilder.)
                                   (setGitDir (file root ".git"))
                                   (readEnvironment)
                                   (findGitDir)
                                   (build))
        ^ObjectId head (.resolve repository "HEAD")]
    (if head
      (let [abbr (.abbreviate head (:length sha 7))]
        (.name abbr))
      version)))

(defn middleware
  [project]
  (assoc project :version (git-sha project)))
