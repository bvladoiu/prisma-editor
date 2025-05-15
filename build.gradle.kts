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

// Task to copy the JS output to both jsMain and jvmMain resources
tasks.register<Copy>("copyJsToMainProject") {
    dependsOn("jsBrowserProductionWebpack")

    from("$buildDir/dist/js/productionExecutable")
    include("*.js")

    // Create js directories if they don't exist
    doFirst {
        mkdir("${project.projectDir}/src/jsMain/resources/js")
        mkdir("${project.projectDir}/src/jvmMain/resources/js")
    }

    // Copy to both jsMain and jvmMain resources
    into("${project.projectDir}/src/jsMain/resources/js")

    // Create a second copy task for jvmMain
    doLast {
        copy {
            from("${project.projectDir}/src/jsMain/resources/js")
            include("*.js")
            into("${project.projectDir}/src/jvmMain/resources/js")
        }
    }
}
