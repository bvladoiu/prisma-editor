plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.graalvmNative)
    // REMOVE the application plugin: application
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
        vendor.set(JvmVendorSpec.matching("GraalVM Community"))
    }
}
graalvmNative {
    toolchainDetection.set(true)
}