// :browser:src:jvmMain:JvmMain.kt
package prisma.editor

import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.ConsoleMessage
import com.microsoft.playwright.Playwright
import com.microsoft.playwright.Page
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.concurrent.thread

// Settings map for persisting data
private val settings: MutableMap<String, Any> = mutableMapOf()

fun main() {
    val playwright = Playwright.create()
    val browser = playwright.chromium().launch(
        BrowserType.LaunchOptions().setHeadless(false)
            .setArgs(listOf("--kiosk", "--disable-web-security", "--disable-features=IsolateOrigins,site-per-process"))
    )
    val context = browser.newContext()
    val page = context.newPage()
    page.onConsoleMessage { message ->
        trace(message)
    }

    val devServerUrl = "http://localhost:8080/editor.html"
    println("Navigating to editor page: $devServerUrl")
    page.navigate(devServerUrl)
    println("Editor page loaded successfully")

    // Load settings from file if it exists
    loadSettings(page)

    // Add shutdown hook to save settings when the application is closed
    Runtime.getRuntime().addShutdownHook(thread(start = false) {
        saveSettings(page)
    })
}

fun trace(message: ConsoleMessage) {
    val type = message.type().uppercase()
    val text = try {
        if (message.args().isNotEmpty()) {
            message.args().joinToString(" ") { arg ->
                try {
                    arg.jsonValue()?.toString() ?: "[JSHandle]"
                } catch (e: Exception) {
                    "[Error converting arg: ${e.message}]"
                }
            }
        } else {
            message.text()
        }
    } catch (e: Exception) {
        "[Error getting message text: ${e.message}]"
    }
    val location = message.location()
    println("[Browser $type @ $location]: $text")
}

/**
 * Loads settings from a file and imports them into the editor.
 */
fun loadSettings(page: Page) {
    try {
        val settingsFile = File("settings.json")
        if (settingsFile.exists()) {
            val jsonString = settingsFile.readText()

            // Import settings into the editor
            page.evaluate("(jsonString) => prisma.editor.Editor.import(jsonString)", jsonString)

            println("Settings loaded from file and imported into editor")
        } else {
            println("No settings file found, using default settings")
        }
    } catch (e: Exception) {
        println("Error loading settings: ${e.message}")
    }
}

/**
 * Exports settings from the editor and saves them to a file.
 */
fun saveSettings(page: Page) {
    try {
        // Export settings from the editor
        val jsonString = page.evaluate("() => prisma.editor.Editor.export()").toString()

        // Save settings to file
        val settingsFile = File("settings.json")
        settingsFile.writeText(jsonString)

        println("Settings exported from editor and saved to file")
    } catch (e: Exception) {
        println("Error saving settings: ${e.message}")
    }
}
