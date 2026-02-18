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
    implementation(libs.jspecific)
    testImplementation(platform(libs.junit))
    testImplementation(platform(libs.jqwik))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("net.jqwik:jqwik-api")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("net.jqwik:jqwik-engine")
    testImplementation("net.jqwik:jqwik-time")
    testImplementation(platform(libs.assertj))
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.assertj:assertj-guava")
    testImplementation(libs.assertj.vavr)
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

