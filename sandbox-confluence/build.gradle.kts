apply<ApplicationPlugin>()

configure<ApplicationPluginConvention> {
    mainClassName = "samples.HelloWorld"
}

configure<JavaPluginConvention> {
    setSourceCompatibility(1.8)
    setTargetCompatibility(1.8)
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compile("com.squareup.retrofit2:retrofit:2.1.0")
    compile("com.squareup.retrofit2:converter-jackson:2.1.0")
    compile("com.squareup.okhttp3:logging-interceptor:3.4.1")
    testCompile("junit:junit:4.12")
}
