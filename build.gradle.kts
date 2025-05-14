plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.graalvmNative)
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

    mingwX64("native") {
        binaries {
            executable {
                entryPoint = "prisma.editor.main"
                baseName = "prisma-editor"

                // Link with Windows libraries
                linkerOpts("-luser32", "-lshell32")
            }
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
        val nativeMain by getting {
            dependsOn(commonMain)
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
        vendor.set(JvmVendorSpec.matching("GraalVM Community"))
    }
}
graalvmNative {
    toolchainDetection.set(true)

    binaries {
        all {
            imageName.set("prisma-editor")
            mainClass.set("prisma.editor.JvmMainKt")
            debug.set(false)
            verbose.set(true)
            fallback.set(false)

            buildArgs.add("--no-fallback")
            buildArgs.add("-H:+ReportExceptionStackTraces")
            buildArgs.add("--initialize-at-build-time=org.slf4j,ch.qos.logback")
            buildArgs.add("--initialize-at-run-time=com.microsoft.playwright")
            buildArgs.add("-H:IncludeResources=.*\\.js")
            buildArgs.add("-H:IncludeResources=.*\\.html")
            buildArgs.add("-H:IncludeResources=.*\\.css")
            buildArgs.add("-H:IncludeResources=.*\\.json")
        }
    }
}
