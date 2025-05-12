@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

// Task name constants
val jvmRunTask = "jvmRun"
val jsBrowserProductionWebpackTask = "jsBrowserProductionWebpack"

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "prisma.editor"
version = "1.0.0"


kotlin {
    jvm {
        mainRun {
            mainClass.set("prisma.editor.JvmMainKt")
        }
    }
    js(IR) {
        browser {
            binaries.executable()
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.microsoft.playwright)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(libs.kotlin.wrappers.browser)
                implementation(libs.kotlinx.html)
            }
        }
    }
}


/*
gradle.projectsEvaluated {
    tasks.matching { it.name == jvmRunTask }.configureEach {
        dependsOn(jsBrowserProductionWebpackTask)
    }
}
*/
