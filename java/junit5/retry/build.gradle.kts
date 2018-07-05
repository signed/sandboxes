plugins {
    kotlin("jvm") version "1.2.51"
}

repositories {
    mavenCentral()
}

dependencies {
    testCompile("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testCompile("org.junit.jupiter:junit-jupiter-params:5.2.0")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.2.0")
    compile(kotlin("stdlib"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
