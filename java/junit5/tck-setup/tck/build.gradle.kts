plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.reflections:reflections:0.10.2")
    implementation(platform(libs.junit))
    implementation("org.junit.jupiter:junit-jupiter")
    implementation(platform(libs.assertj))
    implementation("org.assertj:assertj-core")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
