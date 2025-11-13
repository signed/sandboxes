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
    implementation(platform(libs.jackson))
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    testImplementation(platform(libs.jackson))
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