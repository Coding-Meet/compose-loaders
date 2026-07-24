import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":samples:shared"))
            implementation(libs.compose.ui)
        }

        val jsMain by getting {
            kotlin.srcDirs("src/webMain/kotlin")
            resources.srcDirs("src/webMain/resources")
        }

        val wasmJsMain by getting {
            kotlin.srcDirs("src/webMain/kotlin")
            resources.srcDirs("src/webMain/resources")
        }
    }
}