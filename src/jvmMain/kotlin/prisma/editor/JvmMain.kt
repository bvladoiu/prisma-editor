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
        val tag = parts.getOrNull(1)

        when (command) {
            "save" -> {
                if (tag != null) {
                    val jsonValue = message.args()[0].jsonValue()
                    val jsonData = jsonValue.toString()

                    // Access the data as a map
                    val dataMap = mutableMapOf<String, Any>()

                    // Extract site, language, and pageTag from the JSON data
                    if (jsonValue is Map<*, *>) {
                        jsonValue["site"]?.let { dataMap["site"] = it.toString() }
                        jsonValue["language"]?.let { dataMap["language"] = it.toString() }
                        jsonValue["pageTag"]?.let { dataMap["pageTag"] = it.toString() }
                    }

                    // Use default values if not found in the map
                    val site = dataMap["site"]?.toString() ?: Config.currentSite
                    val language = dataMap["language"]?.toString() ?: Config.currentLanguage

                    // Update current values if this is the editor tag
                    if (tag == "editor") {
                        dataMap["site"]?.toString()?.let { Config.updateSite(it) }
                        dataMap["language"]?.toString()?.let { Config.updateLanguage(it) }
                        dataMap["pageTag"]?.toString()?.let { Config.updatePageTag(it) }
                    }

                    // Create filename with site, language, and tag
                    val filename = "${site}_${language}_$tag.json"

                    // Save the data to the file
                    File(filename).writeText(jsonData)
                }
            }

            "load" -> {
                if (tag != null) {
                    // For the editor tag, try to load with default values
                    if (tag == "editor") {
                        val filename = "${Config.currentSite}_${Config.currentLanguage}_$tag.json"
                        if (File(filename).exists()) {
                            val jsonString = File(filename).readText()
                            val dataToPass = mapOf(
                                "tag" to tag,
                                "jsonString" to jsonString
                            )
                            page.evaluate("(data) => receiveData(data.tag, data.jsonString)", dataToPass)
                        } else {
                            // Try with default values if file doesn't exist
                            val defaultFilename = "prisma_en_$tag.json"
                            if (File(defaultFilename).exists()) {
                                val jsonString = File(defaultFilename).readText()
                                val dataToPass = mapOf(
                                    "tag" to tag,
                                    "jsonString" to jsonString
                                )
                                page.evaluate("(data) => receiveData(data.tag, data.jsonString)", dataToPass)
                            }
                        }
                    } else {
                        // For other components, use the current site and language
                        val filename = "${Config.currentSite}_${Config.currentLanguage}_$tag.json"

                        if (File(filename).exists()) {
                            val jsonString = File(filename).readText()
                            val dataToPass = mapOf(
                                "tag" to tag,
                                "jsonString" to jsonString
                            )
                            page.evaluate("(data) => receiveData(data.tag, data.jsonString)", dataToPass)
                        }
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
