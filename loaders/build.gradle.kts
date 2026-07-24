import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.vanniktech.mavenPublish)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Loaders"
            isStatic = true
        }
    }

    jvm()

    js(IR) {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    android {
        namespace = "com.meet.compose.loaders.lib"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates("io.github.coding-meet", "compose-loaders", "1.0.0")


    pom {
        name = "Compose Loaders"
        description =
            "A growing collection of handcrafted pixel loading animations for Compose Multiplatform."
        inceptionYear = "2026"
        url = "https://github.com/Coding-Meet/compose-loaders"
        licenses {
            license {
                name = "The MIT License"
                url = "https://opensource.org/licenses/MIT"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "Coding-Meet"
                name = "Meet"
                email = "meetb2602@gmail.com"
                organization = "Coding Meet"
                organizationUrl = "https://codingmeet.com"

                url = "https://github.com/Coding-Meet"
            }
        }
        scm {
            url = "https://github.com/Coding-Meet/compose-loaders"
            connection = "scm:git:git://github.com/Coding-Meet/compose-loaders.git"
            developerConnection = "scm:git:ssh://github.com/Coding-Meet/compose-loaders.git"
        }
    }
}

