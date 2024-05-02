import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.example.convention.androidTestImplementation
import com.example.convention.configureKotlinAndroid
import com.example.convention.disableUnnecessaryAndroidTests
import com.example.convention.libs
import com.example.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("kotlin-android")
                apply("kotlin-parcelize")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner =
                        "me.example.playground.core.test.PlaygroundTestRunner"
                }

                configureKotlinAndroid(this)

                lint {
                    baseline = file("lint-baseline.xml")
//                    quiet = true
                    abortOnError = false // fix your lint issue
                    ignoreWarnings = true
                    checkDependencies = true
                }

                packaging {
                    resources.excludes += "DebugProbesKt.bin"
                }
            }

            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }

            configurations.configureEach {
                resolutionStrategy {
                    force(libs.findLibrary("junit4").get())
                    // Temporary workaround for https://issuetracker.google.com/174733673
                    force("org.objenesis:objenesis:2.6")
                }
            }
            dependencies {
                testImplementation(kotlin("test"))
                androidTestImplementation(kotlin("test"))
            }
        }
    }
}
