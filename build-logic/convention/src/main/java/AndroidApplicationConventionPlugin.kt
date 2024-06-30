import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.Config
import com.example.convention.androidTestImplementation
import com.example.convention.configureKotlinAndroid
import com.example.convention.libs
import com.example.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(target.pluginManager) {
                apply("com.android.application")
                apply("kotlin-android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                defaultConfig.targetSdk = Config.targetSdkVersion

                buildTypes {
                    getByName("release") {
                        // disable coverage report on release build
                        enableUnitTestCoverage = false
                        enableAndroidTestCoverage = false
                    }
                }

                lint {
                    baseline = file("lint-baseline.xml")
                    // quiet = true
                    abortOnError = false // fix your lint issue
                    ignoreWarnings = true
                    checkDependencies = true
                }

                packaging {
                    resources.excludes += "DebugProbesKt.bin"
                }

                target.tasks.register("versionCode") {
                    println(defaultConfig.versionCode)
                }

                target.tasks.register("versionName") {
                    println(defaultConfig.versionName)
                }
            }

            configurations.configureEach {
                resolutionStrategy {
                    force(libs.findLibrary("junit4").get())
                    // Temporary workaround for https://issuetracker.google.com/174733673
                    force("org.objenesis:objenesis:2.6")
                }
            }
            dependencies {
                androidTestImplementation(kotlin("test"))
                testImplementation(kotlin("test"))
            }
        }
    }
}
