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
    testCompile("junit:junit:4.12")
}
