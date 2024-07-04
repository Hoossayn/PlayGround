import com.example.convention.implementation
import com.example.convention.libs
import com.example.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidScreenshotConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("io.github.takahirom.roborazzi")
            }

            dependencies {
                // Generate multiple unit tests
                implementation(libs.findLibrary("com-google-testparameterinjector").get())

                // Screenshot Tests on JVM
                testImplementation(libs.findLibrary("org-robolectric").get())
                testImplementation(libs.findLibrary("io-github-takahirom-roborazzi").get())
            }
        }
    }
}
