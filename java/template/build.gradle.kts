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

