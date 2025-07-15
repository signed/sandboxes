import nl.littlerobots.vcu.plugin.resolver.VersionSelectors
plugins {
    `java-library`
    alias(libs.plugins.version.catalog.update)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.vavr)
    testImplementation(platform(libs.junit))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(platform(libs.assertj))
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.assertj:assertj-guava")
    testImplementation(libs.assertj.vavr)
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("ch.qos.logback:logback-classic:1.5.18")
    implementation("ch.qos.logback:logback-core:1.5.18")
    implementation("net.logstash.logback:logstash-logback-encoder:8.1")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

versionCatalogUpdate{
    versionSelector(VersionSelectors.STABLE)
}

