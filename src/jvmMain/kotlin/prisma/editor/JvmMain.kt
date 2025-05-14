package prisma.editor

import com.microsoft.playwright.*
import java.io.File
import kotlin.concurrent.thread

fun main() {
    val playwright = Playwright.create()
    val browser = playwright.chromium().launch(
        BrowserType.LaunchOptions().setHeadless(false)
            .setArgs(listOf("--kiosk", "--disable-web-security", "--disable-features=IsolateOrigins,site-per-process"))
    )
    val context = browser.newContext()
    val page = context.newPage()

    setupCli(page)

    // Add prisma.js as an intrinsic script
    page.addInitScript("function loadPrismaScript() {" +
            "  const script = document.createElement('script');" +
            "  script.src = 'prisma.js';" +
            "  script.type = 'text/javascript';" +
            "  document.body.appendChild(script);" +
            "}" +
            "window.addEventListener('DOMContentLoaded', loadPrismaScript);")

    val devServerUrl = "http://localhost:8080"
    page.navigate(devServerUrl)

    Runtime.getRuntime().addShutdownHook(thread(start = false) {
    })
}

// Store current site and language settings using Config object
// No need for local variables as we use Config properties

fun setupCli(page: Page) {
    page.onConsoleMessage { message: ConsoleMessage ->
        val fullCommand = message.text()
        val parts = fullCommand.split(":", limit = 2)
        val command = parts.getOrNull(0)
        val filename = parts.getOrNull(1)

        when (command) {
            "save" -> {
                if (filename != null) {
                    val jsonValue = message.args()[0].jsonValue()
                    val jsonData = jsonValue.toString()

                    // Update Config if this is the editor tag
                    if (filename.endsWith("bottom-drawer.json")) {
                        if (jsonValue is Map<*, *>) {
                            jsonValue["site"]?.toString()?.let { Config.updateSite(it) }
                            jsonValue["language"]?.toString()?.let { Config.updateLanguage(it) }
                            jsonValue["pageTag"]?.toString()?.let { Config.updatePageTag(it) }
                        }
                    }

                    // Save the data to the file with the filename provided by JS
                    val jsonFilename = "$filename.json"
                    File(jsonFilename).writeText(jsonData)
                }
            }

            "load" -> {
                if (filename != null) {
                    val jsonFilename = "$filename.json"

                    if (File(jsonFilename).exists()) {
                        val jsonString = File(jsonFilename).readText()
                        val tagParts = filename.split("_")
                        val tag = tagParts.lastOrNull() ?: filename

                        val dataToPass = mapOf(
                            "tag" to tag,
                            "jsonString" to jsonString
                        )
                        page.evaluate("(data) => receiveData(data.tag, data.jsonString)", dataToPass)
                    }
                }
            }

            else -> trace(message)
        }
    }
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
