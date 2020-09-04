(ns lein-sha-version.plugin
  (:use
    [clojure.java.io :only [file]]
    [leiningen.core.main :only [warn debug]])
  (:import
    org.eclipse.jgit.storage.file.FileRepositoryBuilder
    [org.eclipse.jgit.lib ObjectId Repository]))

(defn git-sha [{:keys [root version sha]}]
  (debug "Finding SHA for" root)
  (try
    (let [^Repository repository (.. (FileRepositoryBuilder.)
                                     (readEnvironment)
                                     (findGitDir (file root))
                                     (build))
          ^ObjectId head (.resolve repository "HEAD")]
      (if head
        (let [abbr (.abbreviate head (:length sha 7))]
          (debug "Found SHA" (.name head) "using" (.name abbr))
          (.name abbr))
        version))
    (catch Throwable e
      (warn (format "Unable to get SHA: %s" (.getMessage e)))
      version)))

(defn update-manifest [git-sha {manifest                  :manifest
                                {:keys [manifest-header]} :sha}]
  (if manifest-header
    (merge {manifest-header git-sha} manifest)
    manifest))

(defn middleware
  [project]
  (let [version (git-sha project)
        manifest (update-manifest version project)]
    (-> project
        (assoc :version version)
        (assoc :manifest manifest))))
