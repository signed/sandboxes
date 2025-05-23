plugins {
    `java-library`
    alias(libs.plugins.versions)
    alias(libs.plugins.versions.latest)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.vavr:vavr:0.10.6")
    testImplementation(platform("org.junit:junit-bom:5.12.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(platform("org.assertj:assertj-bom:3.27.3"))
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.assertj:assertj-vavr:0.4.3")
    testImplementation("org.assertj:assertj-guava")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
