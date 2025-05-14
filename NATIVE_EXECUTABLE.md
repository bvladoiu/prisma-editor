# Native Executable for Prisma Editor

This document describes how to build and use the native executable version of Prisma Editor, which doesn't require a JVM installation on the target machine.

## Building the Native Executable

The project is configured to generate a native executable using Kotlin/Native. To build the native executable, follow these steps:

1. Make sure you have the required tools installed:
   - Kotlin Multiplatform
   - GraalVM (if using the GraalVM approach)

2. Run the build command:
   ```
   .\gradlew build
   ```

3. The native executable will be generated in the following location:
   - Release version: `build\bin\native\releaseExecutable\prisma-editor.exe`
   - Debug version: `build\bin\native\debugExecutable\prisma-editor.exe`

## Using the Native Executable

The native executable can be run directly without requiring a JVM installation:

1. Navigate to the directory containing the executable:
   ```
   cd build\bin\native\releaseExecutable
   ```

2. Run the executable:
   ```
   .\prisma-editor.exe
   ```

## Implementation Details

The native executable was implemented using the Kotlin/Native binaries DSL. The key components are:

1. **Native Target Configuration** in `build.gradle.kts`:
   ```kotlin
   mingwX64("native") {
       binaries {
           executable {
               entryPoint = "prisma.editor.main"
               baseName = "prisma-editor"
               linkerOpts("-luser32", "-lshell32")
           }
       }
   }
   ```

2. **Native Main Entry Point** in `src\nativeMain\kotlin\prisma\editor\NativeMain.kt`:
   ```kotlin
   fun main() {
       println("Starting Prisma Editor Native...")
       // Application initialization code
   }
   ```

3. **Source Set Configuration** in `build.gradle.kts`:
   ```kotlin
   val nativeMain by getting {
       dependsOn(commonMain)
   }
   ```

## Alternative Approach: GraalVM Native Image

The project is also configured to use GraalVM native-image generation as an alternative approach. The configuration for this is in the `graalvmNative` block in `build.gradle.kts`.

To use this approach, you would need to:

1. Make sure GraalVM is installed and configured
2. Run the GraalVM native-image generation task (when available)

## Distribution

To distribute the application:

1. Copy the `prisma-editor.exe` file from the `build\bin\native\releaseExecutable` directory
2. Include any necessary resources or dependencies
3. The executable can be run on any compatible Windows machine without requiring a JVM installation
