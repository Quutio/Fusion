import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
	alias(libs.plugins.kotlin.jvm)
	id(libs.plugins.kotlin.kapt.get().pluginId)
	alias(libs.plugins.shadow)
}

repositories {
	maven {
		name = "papermc"
		url = uri("https://repo.papermc.io/repository/maven-public/")
	}
}

dependencies {
	api(project(":fusion-velocity:fusion-velocity-api"))

	kapt(libs.velocity)
}

kotlin {
	jvmToolchain(25)
}

tasks {
	withType<ShadowJar> {
		relocate("kotlin", "io.quut.fusion.libs.kotlin")
		relocate("org", "io.quut.fusion.libs.org")
	}
}
