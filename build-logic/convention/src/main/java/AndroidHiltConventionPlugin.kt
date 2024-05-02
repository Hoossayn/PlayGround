import com.example.convention.androidTestImplementation
import com.example.convention.implementation
import com.example.convention.kapt
import com.example.convention.kaptAndroidTest
import com.example.convention.kaptTest
import com.example.convention.libs
import com.example.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
                // KAPT must go last to avoid build warnings.
                // See: https://stackoverflow.com/questions/70550883/warning-the-following-options-were-not-recognized-by-any-processor-dagger-f
                apply("org.jetbrains.kotlin.kapt")
            }

            dependencies {
                implementation(libs.findLibrary("androidx-hilt-navigation-compose").get())

                implementation(libs.findLibrary("androidx-hilt-android").get())
                kapt(libs.findLibrary("androidx-hilt-android-compiler").get())

                // robolectric tests
                testImplementation(libs.findLibrary("androidx-hilt-android-testing").get())
                kaptTest(libs.findLibrary("androidx-hilt-android-compiler").get())

                // instrumented tests
                androidTestImplementation(libs.findLibrary("androidx-hilt-android-testing").get())
                kaptAndroidTest(libs.findLibrary("androidx-hilt-android-compiler").get())
            }
        }
    }
}
