plugins {
    `kotlin-dsl`
}

group = "com.rk.shopping.buildlogic"

dependencies {
    compileOnly(libs.plugins.kotlin.serialization.toDep())
    compileOnly(libs.plugins.androidApplication.toDep())
    compileOnly(libs.plugins.androidLibrary.toDep())
    compileOnly(libs.plugins.composeMultiplatform.toDep())
    compileOnly(libs.plugins.kotlinMultiplatform.toDep())
    compileOnly(libs.plugins.compose.compiler.toDep())
}

fun Provider<PluginDependency>.toDep() = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform") {
            id = "com.rk.shopping.kotlinMultiplatform"
            implementationClass = "com.rk.shopping.KotlinMultiplatformConventionPlugin"
        }
        register("shared") {
            id = "com.rk.shopping.shared"
            implementationClass = "com.rk.shopping.SharedConventionPlugin"
        }
        register("androidApp") {
            id = "com.rk.shopping.androidApp"
            implementationClass = "com.rk.shopping.AndroidAppConventionPlugin"
        }
    }
}