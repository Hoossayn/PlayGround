import com.android.build.gradle.TestExtension
import com.example.convention.Config
import com.example.convention.configureKotlinAndroid
import com.example.convention.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.test")
                apply("org.jetbrains.kotlin.android")
                apply("jacoco")
            }

            extensions.configure<TestExtension> {
                compileSdk = Config.compileSdkVersion
                defaultConfig {
                    targetSdk = Config.targetSdkVersion
                    minSdk = Config.minimumSdkVersion
                }

                configureKotlinAndroid(this)

                buildTypes {
                    debug { enableAndroidTestCoverage = true }
                }
            }

            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
