pluginManagement {
    includeBuild("build-logic")
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
        maven(uri("https://androidx.dev/storage/compose-compiler/repository/"))
        maven(uri("https://jitpack.io"))
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")


rootProject.name = "PlayGround"
include(":app")
include(":core:design-system")
include(":core:common")
include(":core:ui")
include(":core:model")
include(":core:data")

include(":screens:movies")


