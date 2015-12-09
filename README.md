# lein-sha-version

A Leiningen plugin to set the project version based on the git sha of the
current branch.

## Usage

To be able to access the plugin from any project, put
`[lein-sha-version "0.1.1"]` into the `:plugins` vector of your `:sha`, or other
non-standard, profile. Invoke `lein` with `lein with-profile sha` to use the sha
version number for the project, e.g. `lein with-profile sha deploy clojars` to
deploy the sha based version to clojars.

Put `[lein-sha-version "0.1.1"]` into the `:plugins` vector of your `:dev`
profile in `project.clj` if you always want to use a sha version with a specific
project.

To control the length of the generated SHA, you can set the `:length` key under
`:sha` in your project or profile. The length defaults to seven.

```clj
:sha {:length 8}
```

To add the generated SHA to your artifacts' `MANIFEST.MF`, you can set the
`:manifest-header` to your project or profile. The SHA will be written to that
header in your jar and/or uberjar.

```clj
:sha {:manifest-header "Implementation-Version"}
```

## License

Copyright © 2012 Hugo Duncan

Distributed under the Eclipse Public License.
