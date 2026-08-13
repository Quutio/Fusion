plugins {
	`maven-publish`
	`java-library`

	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.spotless)
}

allprojects {
	group = "io.quut"
	version = "0.1-SNAPSHOT"

	apply(plugin = "com.diffplug.spotless")

	spotless {
		kotlin {
			ktlint()
			leadingSpacesToTabs()
			endWithNewline()
			trimTrailingWhitespace()
		}
		kotlinGradle {
			ktlint()
			leadingSpacesToTabs()
			endWithNewline()
			trimTrailingWhitespace()
		}
	}

	repositories {
		mavenCentral()
		mavenLocal()
	}
}

subprojects {
	apply(plugin = "maven-publish")
	apply(plugin = "java-library")
	apply(plugin = "kotlin")

	java {
		withSourcesJar()
	}

	kotlin {
		jvmToolchain(21)
	}

	publishing {
		publications {
			register("fusion", MavenPublication::class) {
				from(components["java"])

				this.artifactId = project.name.lowercase()

				pom {
					this.name.set(project.name)
					this.description.set(project.description)
				}
			}

			repositories {
				maven {
					this.name = "GitHubPackages"
					this.url = uri("https://maven.pkg.github.com/Quutio/Fusion")
					credentials {
						this.username = System.getenv("GITHUB_ACTOR")
						this.password = System.getenv("GITHUB_TOKEN")
					}
				}
			}
		}
	}
}
