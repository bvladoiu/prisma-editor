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
        register("prismaEditor") {
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
    classpath = files("build/libs/prisma-editor-jvm-1.0.0.jar")

    jvmArgs = listOf(
        "-agentlib:native-image-agent=config-output-dir=build/native/agent-output"
    )
}

tasks.register<Exec>("nativeCompile") {
    group = "GraalVM"
    description = "Compile the application to a native executable using GraalVM"

    dependsOn("jvmJar")

    doFirst {
        mkdir("build/native")
    }

    commandLine(
        "C:\\Program Files\\Java\\graalvm-community-openjdk-17.0.8+7.1\\bin\\native-image.cmd",
        "--no-fallback",
        "-H:+ReportExceptionStackTraces",
        "-H:+PrintClassInitialization",
        "-cp", "build/libs/prisma-editor-jvm-1.0.0.jar",
        "prisma.editor.JvmMainKt",
        "build/native/prisma-editor"
    )
}
