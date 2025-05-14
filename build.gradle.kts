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
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }

        binaries {
            executable {
                mainClass = "prisma.editor.JvmMainKt"
            }
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
                implementation(libs.graalvm.sdk)
                implementation(libs.graalvm.nativeimage)
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

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

// Task to copy the JS output to replace prisma.js
tasks.register<Copy>("copyJsOutputToPrismaJs") {
    dependsOn("jsBrowserProductionWebpack")

    from("$buildDir/dist/js/productionExecutable")
    include("*.js")

    // Create js directory if it doesn't exist
    doFirst {
        mkdir("${project.rootDir}/src/jsMain/resources/js")
    }

    // Rename the output file to prisma.js
    rename { "prisma.js" }

    into("${project.rootDir}/src/jsMain/resources/js")
}

// Task to copy JS resources to the desktop module
tasks.register<Copy>("copyJsResourcesToDesktop") {
    dependsOn("copyJsOutputToPrismaJs")
    from("${project.rootDir}/src/jsMain/resources/js")
    include("prisma.js")
    into("${project.rootDir}/desktop/src/main/resources/js")
}

// Task to copy index.html to the desktop module
tasks.register<Copy>("copyIndexHtmlToDesktop") {
    from("${project.rootDir}/src/jsMain/resources")
    include("index.html")
    into("${project.rootDir}/desktop/src/main/resources")
}

// Make sure the build task depends on the copy tasks
tasks.named("build") {
    dependsOn("copyJsOutputToPrismaJs", "copyJsResourcesToDesktop", "copyIndexHtmlToDesktop")
}
