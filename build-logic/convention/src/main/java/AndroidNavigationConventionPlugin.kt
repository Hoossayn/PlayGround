import com.example.convention.androidTestImplementation
import com.example.convention.implementation
import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidNavigationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("androidx.navigation.safeargs.kotlin")

            dependencies {
                implementation(libs.findLibrary("androidx-navigation-fragment-ktx").get())
                implementation(libs.findLibrary("androidx-navigation-ui-ktx").get())
                implementation(libs.findLibrary("androidx-navigation-compose").get())
                androidTestImplementation(libs.findLibrary("androidx-navigation-testing").get())
            }
        }
    }
}
