plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
}

kotlin {
    androidLibrary {
        namespace = "com.fabiocati.aedo.summarizer"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "core:summarizerKit"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.koin.core)
                implementation(project(":core:persistence"))
            }
        }

        androidMain {
            dependencies {
                implementation(libs.koin.android)
                implementation(libs.mlkit.summarization)
                implementation(libs.kotlinx.coroutines.play.services)
                implementation(libs.litertlm.android)
            }
        }
    }
}
