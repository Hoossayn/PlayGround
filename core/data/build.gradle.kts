plugins {
    id("com.example.playground.android.library")
    id("com.example.playground.android.hilt")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.example.core.data"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

androidComponents.beforeVariants {
    android.sourceSets.getByName(it.name) {
        val buildDir = layout.buildDirectory.get().asFile
        java.srcDir(buildDir.resolve("generated/source/proto/${it.name}/java"))
        kotlin.srcDir(buildDir.resolve("generated/source/proto/${it.name}/kotlin"))
    }
}


dependencies {

    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(projects.core.network)

    // Datastore
    implementation(libs.datastore)
    implementation(libs.protobuf.kotlin.lite)

    // Paging
    api(libs.androidx.paging.runtime)
    api(libs.androidx.paging.compose)
    testImplementation(libs.androidx.paging.common)

    testImplementation(project(":core:test"))
    androidTestImplementation(project(":core:test"))

}
