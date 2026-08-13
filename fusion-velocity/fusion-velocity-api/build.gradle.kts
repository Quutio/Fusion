plugins {
	alias(libs.plugins.kotlin.jvm)
}

repositories {
	maven {
		name = "papermc"
		url = uri("https://repo.papermc.io/repository/maven-public/")
	}
}

dependencies {
	api(project(":fusion-api"))

	compileOnlyApi(libs.velocity)
}

kotlin {
	jvmToolchain(25)
}
