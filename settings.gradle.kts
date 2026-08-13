pluginManagement {
	repositories {
		gradlePluginPortal()
	}
	plugins {
		id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
		id("com.diffplug.spotless") version "8.9.0"
	}
}

rootProject.name = "Fusion"

include("fusion-api")
include("fusion-velocity")
include("fusion-velocity:fusion-velocity-api")
