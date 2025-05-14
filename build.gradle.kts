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

    binaries {
        register("main") {
            mainClass.set("prisma.editor.JvmMainKt")
            buildArgs.add("--no-fallback")
            buildArgs.add("-H:+ReportExceptionStackTraces")
            buildArgs.add("-H:+PrintClassInitialization")

            resources.autodetect()

            agent {
                enabled.set(true)
            }
        }
    }

    metadataRepository {
        enabled.set(true)
    }
}

tasks.register<JavaExec>("runWithAgent") {
    group = "GraalVM"
    description = "Run the application with the GraalVM native image agent"

    dependsOn("jvmJar")

    mainClass.set("prisma.editor.JvmMainKt")
    classpath = kotlin.jvm().compilations["main"].output.allOutputs + kotlin.jvm().compilations["main"].runtimeDependencyFiles

    jvmArgs = listOf(
        "-agentlib:native-image-agent=config-output-dir=build/native/agent-output"
    )
}
