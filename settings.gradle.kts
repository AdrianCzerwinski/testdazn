pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TestDazn"
include(":app")
include(":core:ui")
include(":core:common")
include(":domain")
include(":presentation")
include(":data:events")
include(":presentation:events")
include(":domain:events")
