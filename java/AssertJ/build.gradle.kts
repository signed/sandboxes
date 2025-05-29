import nl.littlerobots.vcu.plugin.resolver.VersionSelectors

plugins {
    `java-library`
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.assertj)
}

buildscript {
    configurations.all {
        resolutionStrategy {
            // https://github.com/assertj/assertj-generator/issues/194
            // https://github.com/assertj/assertj-generator/issues/246
            force("org.assertj:assertj-assertions-generator:3.0.0-M5")
        }
    }
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

sourceSets {
    main {
        assertJ {
            entryPoints {
                //classPackage = "org.example"
            }
            packages {
                //include("org")
                //exclude()
            }
            classes {
                include("org.DomainObject") // include *all* classes under `org.example` including sub-packages
                //exclude("org.example.Foo") // exclude class `org.example.Foo` specifically
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

versionCatalogUpdate {
    versionSelector(VersionSelectors.STABLE)
}

