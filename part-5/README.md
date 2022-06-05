# Part 5: distributing configuration and hooks

Now that we've written some useful configuration and hooks, wouldn't it be nice
if others could just re-use that?  Clj-kondo supports copying configurations
from a classpath into its own configuration directory (`.clj-kondo`).  When
executing this on the command line, the only thing clj-kondo will do is scan the
classpath for configuration files and copy them over:

``` clojure
clojure -M:clj-kondo --lint $(clojure -Spath) --copy-configs --skip-lint
```

Any `.clj`, `.clj_kondo` and/or `.edn` file that lives on the classpath under a
`clj-kondo.exports` directory is copied over. The `.clj_kondo` extension is
recommended as to not confuse tools that try to load Clojure code on the
classpath.

The convention inside the `.clj-kondo` directory is that any two-level deep
directory with a `.clj-kondo.edn` file is recognized automatically.

This means that to export configuration which is recognized automatically, your
library has to host the exported files like this, given that `resources` will be
on the classpath:

``` clojure
resources/clj-kondo.exports/org.name/lib.name/config.edn
```

It is also recommended that hook namespaces live inside a two-level deep
directory with a unique name:

``` clojure
resources/clj-kondo.exports/org.name/lib.name/org_name/lib_name.clj
```

This is why you will often see the organization and/or library name repeated _twice_ in this structure.

After copying the files, the `.clj-kondo` directory will look like:

``` clojure
.clj-kondo/org.name/lib.name/config.edn
.clj-kondo/org.name/lib.name/org_name/lib_name.clj
```

The clj-kondo configuration 'classpath' then looks like this:

``` clojure
.clj-kondo:.clj-kondo/org.name/lib.name
```

and any configurations and hook namespaces on the configuration 'classpath' can be found.

Because it is often handy to use the to-be exported configuration in the library
that the config was written for in the first place, inside that library repo's
`.clj-kondo/config.edn`, you can refer to that using:

``` clojure
:config-paths ["../resources/clj-kondo.exports/org.name/lib.name"]
```

## Exercise 5.0

Fork this repository and export either the `:macroexpand` or `:analyze-hook`
configuration for `kdefn`.

Test from another library that the `kdefn` config can be imported and used.

Consult the
[documentation](https://github.com/clj-kondo/clj-kondo/blob/master/doc/config.md#exporting-and-importing-configuration)
on exporting/importing if you need additional information.

You can find lots of existing libraries exporting their configurations
[here](https://github.com/clj-kondo/clj-kondo/discussions/1528).
