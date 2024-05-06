import com.example.convention.androidTestImplementation
import com.example.convention.implementation
import com.example.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.example.playground.android.library")
                apply("com.example.playground.android.hilt")
            }

            dependencies {
                implementation(project(":core:common"))
                implementation(project(":core:design-system"))
                implementation(project(":core:ui"))
                //implementation(project(":core:data"))
                //implementation(project(":core:network"))
                //implementation(project(":core:domain"))
                implementation(project(":core:model"))

                testImplementation(kotlin("test"))
                androidTestImplementation(kotlin("test"))
            }
        }
    }
}
