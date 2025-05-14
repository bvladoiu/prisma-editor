plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "prisma.editor.client"
version = "1.0.0"

kotlin {
    js(IR) {
        browser {
            binaries.executable()
        }
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(libs.kotlin.wrappers.browser)
                implementation(libs.kotlinx.html)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}

// Task to copy the JS output to the desktop module's resources
tasks.register<Copy>("copyJsToDesktopModule") {
    dependsOn("jsBrowserProductionWebpack", "jsBrowserDistribution")

    from("$buildDir/dist/js/productionExecutable")
    include("*.js")

    // Create js directory if it doesn't exist
    doFirst {
        mkdir("${project.rootDir}/desktop/src/main/resources/js")
    }

    // Rename the output file to client.js
    rename { "client.js" }

    into("${project.rootDir}/desktop/src/main/resources/js")
}

