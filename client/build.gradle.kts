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

// Task to copy the JS output to the main project's resources
tasks.register<Copy>("copyJsToMainProject") {
    dependsOn("jsBrowserProductionWebpack")

    from("$buildDir/dist/js/productionExecutable")
    include("*.js")

    // Create js directory if it doesn't exist
    doFirst {
        mkdir("${project.rootDir}/src/jsMain/resources/js")
    }

    // Rename the output file to client.js
    rename { "client.js" }

    into("${project.rootDir}/src/jsMain/resources/js")
}

