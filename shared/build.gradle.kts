plugins {
    alias(libs.plugins.shopping.kotlinMultiplatform)
    alias(libs.plugins.shopping.shared)
    alias(libs.plugins.ktlint)
}

ktlint {
    android = true
    outputToConsole = true
    outputColorName = "RED"
}

kotlin {

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {

        commonTest {
            dependencies {

            }
        }
        commonMain {
            dependencies {

            }
        }

        androidMain {
            dependencies {
                implementation(libs.maps.compose)
                implementation(libs.play.services.maps)
                implementation(libs.play.services.location)
                implementation(libs.accompanist.permissions)
            }
        }
        iosMain {
            dependencies {
                
            }
        }

    }
}


