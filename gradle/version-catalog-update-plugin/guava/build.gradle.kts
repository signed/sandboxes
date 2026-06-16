import nl.littlerobots.vcu.plugin.resolver.ModuleVersionCandidate
import nl.littlerobots.vcu.plugin.versionSelector
import nl.littlerobots.vcu.plugin.resolver.VersionSelectors

plugins {
    alias(libs.plugins.version.catalog.update)
}

versionCatalogUpdate {
    sortByKey.set(false)
    keep {
        keepUnusedVersions.set(false)
    }
    pin {
        libraries.add(libs.junit.jupiter)
    }
    versionSelector { it: ModuleVersionCandidate ->
        // just to showcase how to write a custom VersionSelector that handles a special case
        // and for the rest delegates to an existing VersionSelector
        val a = it.currentVersion
        val b = it.candidate.version
        VersionSelectors.PREFER_STABLE.select(it)
    }
}