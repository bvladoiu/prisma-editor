@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

// Task name constants
val jvmRunTask = "jvmRun"
val jsBrowserProductionWebpackTask = "jsBrowserProductionWebpack"

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
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
                implementation(compose.runtime)
                implementation(project.dependencies.platform(libs.compose.bom))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.microsoft.playwright)
                implementation(compose.desktop.currentOs)
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

compose.desktop {
    application {
        mainClass = "prisma.editor.JvmMainKt"
        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb
            )
            packageName = "Browser"
            packageVersion = "1.0.0"
        }
        jvmArgs += listOf("-Xmx1G", "-Dfile.encoding=UTF-8")
    }
}

/*
gradle.projectsEvaluated {
    tasks.matching { it.name == jvmRunTask }.configureEach {
        dependsOn(jsBrowserProductionWebpackTask)
    }
}
*/
