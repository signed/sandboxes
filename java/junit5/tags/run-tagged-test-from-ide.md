# Set up a Suite with the Suite Engine

- see `org.example.suite.TagSuite`
- Execute the Suite like any other test

# Use a custom gradle parameter to pass tag expressions

see https://stackoverflow.com/a/78605893 for the idea

```kotlin
tasks.test {
    useJUnitPlatform {
        val includeTags = providers.gradleProperty("includeTags")
        if (includeTags.isPresent) {
            includeTags(includeTags.get())
        }
        val excludeTags = providers.gradleProperty("excludeTags")
        if (excludeTags.isPresent) {
            excludeTags(excludeTags.get())
        }
    }
    // ...
}
```

[-P, --project-prop](https://docs.gradle.org/current/userguide/command_line_interface.html#sec:environment_options)

```shell
./gradlew test -PincludeTags="one | three " -PexcludeTags=two
```

Set up a Intellij gradle execution with the following Run

```shell
:test --tests "org.example.*" -PincludeTags="one | three"
```