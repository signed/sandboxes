import nl.littlerobots.vcu.plugin.resolver.VersionSelectors

plugins {
	`java-library`
	alias(libs.plugins.version.catalog.update)
}
repositories {
	mavenCentral()
}

dependencies {
	api(platform(platform(libs.junit)))
	api("org.junit.jupiter:junit-jupiter-api")
	testImplementation("org.junit.jupiter:junit-jupiter-engine")
	testImplementation("org.junit.platform:junit-platform-launcher")
	testImplementation("org.junit.platform:junit-platform-engine")
	testImplementation("org.junit.platform:junit-platform-testkit")
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