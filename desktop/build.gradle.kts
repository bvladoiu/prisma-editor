plugins {
    kotlin("jvm")
    application
    alias(libs.plugins.graalvmNative)
}

group = "prisma.editor.desktop"
version = "1.0.0"

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("prisma.editor.desktop.DesktopMain")
}

dependencies {
    implementation(project(":"))
    implementation(libs.graalvm.sdk)
    implementation(libs.graalvm.nativeimage)
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
