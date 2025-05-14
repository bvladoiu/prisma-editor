# GraalVM Native Image Setup for Kotlin JVM Executables

This guide provides comprehensive instructions for setting up your project and environment to compile the Kotlin JVM application part of your project (`jvmMain`) into a standalone native executable using GraalVM Native Image and the official Gradle plugin. This executable will not require a separate Java Runtime Environment (JRE) to run on the target Windows machine.

We will target **GraalVM Community Edition based on OpenJDK 17**, which is the current Long-Term Support (LTS) version and recommended for new native image development.

## Part 1: Environment Setup (Windows)

This part involves downloading and installing GraalVM Community Edition on a Windows machine and installing the necessary components and prerequisites for Native Image compilation.

### Prerequisites:

1.  **Windows Operating System:** (Windows 10/11 recommended)
2.  **Visual Studio Build Tools with C++ workload:** GraalVM Native Image on Windows requires a C/C++ toolchain (specifically MSVC) for linking the native executable.
   * Download and run the installer for the free "Build Tools for Visual Studio" from the official Visual Studio website ([https://visualstudio.microsoft.com/downloads/](https://visualstudio.microsoft.com/downloads/)).
   * During installation, select the "**Desktop development with C++**" workload.
   * On the right side under "Installation Details", ensure that a recent **MSVC v143 - VS ... C++ x64/x86 build tools** (or compatible) and a recent **Windows SDK** are selected and installed.

### Installation Steps (Manual Download and Setup):

1.  **Download GraalVM Community Edition (Java 17):**
   * Navigate to the GraalVM Community Edition releases page on GitHub: [https://github.com/graalvm/graalvm-ce-builds/releases](https://github.com/graalvm/graalvm-ce-builds/releases)
   * Find a release corresponding to **Java 17** (e.g., `vm-22.3.3` or a newer one aligned with a recent OpenJDK 17 release).
   * Download the **Windows x64** `.zip` archive for the Java 17 version. Look for a file named similar to `graalvm-community-jdk-17...windows-x64.zip`.

2.  **Extract GraalVM:**
   * Extract the downloaded `.zip` archive to a chosen directory on your system (e.g., `C:\Program Files\Java\graalvm-ce-jdk-17`). Choose a path without spaces or special characters if possible, though newer versions are more robust.

3.  **Set Environment Variables:**
    You need to set `JAVA_HOME` to point to the GraalVM installation directory and add its `bin` directory to your system's `PATH`.

   * Open the Windows Start Menu, search for "Environment Variables", and select "Edit the system environment variables".
   * Click the "Environment Variables..." button.
   * Under "System variables", click "New..." to create or modify `JAVA_HOME`:
      * Variable name: `JAVA_HOME`
      * Variable value: The path to your extracted GraalVM directory (e.g., `C:\Program Files\Java\graalvm-ce-jdk-17`)
   * Under "System variables", find the `Path` variable, select it, and click "Edit...".
   * Click "New" and add `%JAVA_HOME%\bin`. **Move this entry up** in the list to ensure it's found before any other JDK installations.
   * Click OK on all windows to apply the changes.

4.  **Verify Java Installation:**
   * Open a **new** Command Prompt or PowerShell window (changes to environment variables require a new shell).
   * Type:
       ```bash
       java -version
       ```
   * You should see output indicating the GraalVM Community Edition and the correct Java version (17).

5.  **Install Native Image Component:**
    GraalVM includes the `gu` (GraalVM Updater) tool. Use it to install the `native-image` component required for ahead-of-time compilation.
   * In the same Command Prompt or PowerShell window, run:
       ```bash
       gu install native-image
       ```
   * This will download and install the Native Image tool into your GraalVM installation.

6.  **Verify Native Image Installation:**
   * In the same Command Prompt or PowerShell window, type:
       ```bash
       native-image --version
       ```
   * You should see output indicating the installed Native Image version.

7.  **Prepare the MSVC Environment:**
   * To compile native images on Windows, the `native-image` tool must be able to find the MSVC compiler and linker. The easiest way to ensure this is to run the native compilation command from the **"x64 Native Tools Command Prompt for VS"** that is installed with Visual Studio Build Tools. You can find this in your Start Menu, usually under a "Visual Studio" folder.

*(Optional Alternative: Installation using Scoop - This is an alternative to steps 1-6 above and simplifies managing JDKs, but requires installing Scoop first.)*
* *Install Scoop (if not present): Open PowerShell and run `Set-ExecutionPolicy RemoteSigned -Scope CurrentUser; irm get.scoop.sh | iex`*
* *Add the Java bucket: `scoop bucket add java`*
* *Install GraalVM CE 17 and Native Image: `scoop install graalvm22-jdk17` and `scoop install graalvm22-native-image`*
* *Verify in a new PowerShell window: `java -version`, `native-image --version`*
* *Still requires the Visual Studio Build Tools prerequisite (step 7 above still applies for the compilation environment).*

### Note on Java 11:

If you have a strong requirement to use Java 11, you would download a GraalVM Community Edition release based on OpenJDK 11 (e.g., `vm-21.3.5`). The environment setup steps remain the same. However, targeting Java 17 is generally recommended for better support with recent tools and libraries.

## Part 2: Project Setup (build.gradle.kts)

This part involves configuring your project's Gradle build file (`build.gradle.kts`) to use the installed GraalVM for creating a native executable using the official plugin.

1.  **Add the GraalVM Native Image Plugin:**
    Open your project's `build.gradle.kts` file and add the `org.graalvm.buildtools.native` plugin to the `plugins` block. Ensure your `kotlin { jvm { ... } }` block correctly specifies your main class.

    ```kotlin
    // build.gradle.kts
    plugins {
        alias(libs.plugins.kotlinMultiplatform)
        // Add the official GraalVM Native Image plugin
        id("org.graalvm.buildtools.native") version "0.9.28" // Check Maven Central for the latest version
    }

    group = "prisma.editor"
    version = "1.0.0"

    kotlin {
        jvm {
            mainRun {
                mainClass.set("prisma.editor.JvmMainKt") // **Ensure this is your main entry point**
            }
             compilations.all {
                kotlinOptions.jvmTarget = "17" // **Match your GraalVM JDK version**
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
                    // Do NOT add graalvm.sdk or graalvm.nativeimage as implementation dependencies here
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

    // **Recommended:** Configure JVM toolchain to point to your GraalVM installation
    // This helps Gradle find and use the correct JDK for the native build task
    java {
       toolchain {
           languageVersion = JavaLanguageVersion.of(17) // **Match your GraalVM JDK version**
           vendor = JvmVendorSpec.matching("GraalVM Community") // Or other vendor if applicable
       }
    }


    // Optional: Configure native build options using the plugin's extension
    graalvmNative {
        binaries {
            main {
                // The plugin usually detects mainClass from jvm { mainRun } or application { mainClass }
                mainClass.set("prisma.editor.JvmMainKt") // Explicitly setting for clarity
                // imageName.set("prisma-editor") // Optional: set a custom output executable name
                // buildArgs.add("--verbose") // Optional: add arguments passed to the native-image tool
                // buildArgs.add("-H:+ReportExceptionStackTraces") // Helpful for debugging
            }
        }
        // **Crucial for complex apps/libraries like Playwright:** Configure the agent for metadata collection
         agent {
             enabled.set(true) // Enable the agent
             modes { standard() } // Use standard mode for executable metadata
             // After running with the agent, metadata will be saved to `build/native/agent-output` by default
             // You will need to review, curate, and manually copy/merge these files
             // into src/jvmMain/resources/META-INF/native-image/ later.
         }
    }
    ```

2.  **Understand Native Image Configuration Files (Reflection, Resources, etc.):**
    Building a native image requires static analysis. Libraries (like Playwright or parts of the JDK) that use dynamic features (reflection, dynamic proxies, JNI, dynamic class loading, accessing resources by name) need configuration metadata so the `native-image` tool knows what to include.

    These configuration files are typically placed in:
    ```
    src/jvmMain/resources/META-INF/native-image/your-group-id/your-artifact-id/
    ```
    (Replace `your-group-id`/`your-artifact-id` with your project's Gradle group/name, or place directly in `META-INF/native-image/` if the plugin is configured that way).

   * `reflect-config.json`: Specifies classes, fields, and methods that need to be accessible via reflection.
   * `resource-config.json`: Specifies resources (files) that need to be included in the native image and accessible by name.
   * `jni-config.json`, `proxy-config.json`, `serialization-config.json`: For other dynamic features.

    **For a complex application using Playwright, you will almost certainly need comprehensive configuration metadata.** Manually writing these files is extremely difficult. The recommended approach is to use the **native-image agent**.

3.  **Generate Comprehensive Configuration using the Agent (Crucial Step):**
    The GraalVM Native Image agent runs alongside your application on the JVM and records all dynamic accesses (reflection, resources, etc.). It then generates the necessary configuration files based on observed runtime behavior.

   * **Build the JVM Jar:** First, build your standard JVM application JAR.
       ```bash
       ./gradlew jvmJar
       ```
   * **Run with the Agent:** Execute your application on the JVM using the GraalVM JDK, with the agent enabled.
      * Open the **"x64 Native Tools Command Prompt for VS"**.
      * Navigate to your project root directory.
      * Run your application JAR with the agent. The output directory for the generated config is typically configured in the `graalvmNative {}` block (default is `build/native/agent-output`).

       ```bash
       # Example command, adjust path to your JAR and output directory if needed
       java -agentlib:native-image-agent=config-output-dir=build/native/agent-output -jar build/libs/prisma-editor-jvm-1.0.0.jar
       ```
     *(Note: The exact command might vary slightly based on how your JAR is built and how you configure the agent output in `build.gradle.kts`)*.

   * **Exercise Application Functionality:** While the application is running with the agent, use all relevant features and code paths that might involve reflection, resource loading, or other dynamic behavior, *especially those related to Playwright*. The more code paths you exercise, the more complete the generated metadata will be.
   * **Collect Metadata:** Once you've exercised the necessary functionality, the agent will save `.json` files (e.g., `reflection-config.json`, `resource-config.json`) in the specified output directory.

4.  **Curate and Integrate Generated Metadata:**
    The generated metadata is often overly broad. You should:
   * **Review** the generated `.json` files.
   * **Curate** them to remove unnecessary entries.
   * **Copy** the curated files into your project's `src/jvmMain/resources/META-INF/native-image/` directory (or the specific subdirectory expected by the plugin, e.g., `your-group-id/your-artifact-id/`).
   * **Merge** with any existing configuration files you might have manually created.

    *Note:* This curation step can be complex. The native-image agent documentation provides more details. You might need to repeat the agent run and curation process if your native image fails at runtime.

5.  **Build the Native Image:**
    After setting up the environment (Part 1) and configuring the project with the plugin (Part 2.1) and necessary metadata files (Part 2.2-2.4), you can build the native executable.

   * Open the **"x64 Native Tools Command Prompt for VS"** (essential for the MSVC environment).
   * Navigate to your project root directory.
   * Run the Gradle task provided by the plugin:
       ```bash
       ./gradlew nativeCompile
       ```

    The resulting executable will be located in your build directory, typically under `build/native/nativeCompile`.

6.  **Troubleshooting Common Issues:**

   * **Build Fails (Unsupported Features):** The `native-image` tool reports features that cannot be compiled ahead-of-time. Add flags like `-H:+ReportExceptionStackTraces` for better reporting. You might need to find workarounds, exclude code, or provide specific configuration.
   * **Runtime Errors (Missing Reflection/Resources/JNI):** This is very common. It means the static analysis or your provided metadata missed something. The error message will often indicate which class or resource was missing. Repeat the agent run (Step 3) ensuring you exercise the code path that failed, then curate/merge the new metadata (Step 4), and rebuild (Step 5).
   * **Large Executable Size:** Native images can be large, but proper configuration can reduce size. Curating metadata helps.
   * **Long Build Times:** Native image compilation is significantly slower than JVM compilation.

## Conclusion

Targeting Java 17 with GraalVM Community Edition is the recommended path for modern native image development. Using the `org.graalvm.buildtools.native` plugin is the best practice for integrating this with Gradle. For a complex project involving libraries like Playwright, manually creating configuration metadata is impractical; you must leverage the **native-image agent** to generate this configuration by running your application on the JVM. Be prepared for an iterative process of building, testing, encountering runtime errors, running the agent, updating metadata, and rebuilding until the executable works correctly.