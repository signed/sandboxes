plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(project(":tck"))
    testImplementation(platform(libs.junit))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(platform(libs.assertj))
    testImplementation("org.assertj:assertj-core")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

