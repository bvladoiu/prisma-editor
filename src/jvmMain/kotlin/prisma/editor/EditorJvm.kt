package prisma.editor

import com.microsoft.playwright.ConsoleMessage
import com.microsoft.playwright.Page
import java.io.File

/**
 * JVM counterpart to EditorJs in the JS sourceset.
 * Handles CLI setup, loading and saving logic for the editor.
 */
object EditorJvm {
    const val TAG = "editor"
    private val pages = mutableListOf<Map<String, Any>>()

    private var page: Page? = null

    init {
        load()
    }

    /**
     * Sets up the CLI interface for the editor.
     * This handles console messages for loading and saving data.
     */
    fun setupCli(page: Page) {
        this.page = page

        page.onConsoleMessage { message: ConsoleMessage ->
            val fullCommand = message.text()
            val parts = fullCommand.split(":", limit = 2)
            val command = parts.getOrNull(0)
            val filename = parts.getOrNull(1)

            when (command) {
                "save" -> {
                    if (filename != null) {
                        save(filename, message)
                    }
                }

                "load" -> {
                    if (filename != null) {
                        load(filename)
                    }
                }

                "createPage" -> {
                    if (filename != null) {
                        createPage(filename)
                    }
                }

                "getPages" -> {
                    if (filename != null) {
                        val filenameParts = filename.split(":")
                        if (filenameParts.size == 2) {
                            val site = filenameParts[0]
                            val language = filenameParts[1]
                            sendPageList(site, language)
                        }
                    }
                }
                else -> trace(message)
            }
        }
    }

    /**
     * Saves data to a file.
     */
    private fun save(filename: String, message: ConsoleMessage) {
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

    /**
     * Saves the current configuration.
     */
    private fun commit() {
        val data = mapOf(
            "site" to Config.currentSite,
            "language" to Config.currentLanguage,
            "pageTag" to Config.currentPageTag
        )
        println("save:$TAG ${data}")
    }

    /**
     * Initializes the editor by loading configuration.
     */
    private fun load() {
        println("load:$TAG")
    }

    /**
     * Updates the configuration with values from a map.
     */
    fun set(data: Map<String, Any>) {
        data["site"]?.toString()?.let { Config.currentSite = it }
        data["language"]?.toString()?.let { Config.currentLanguage = it }
        data["pageTag"]?.toString()?.let { Config.currentPageTag = it }
    }

    /**
     * Creates a new page with the given name.
     */
    private fun createPage(pageName: String) {
        val newPageData = mapOf("name" to pageName, "components" to emptyList<Any>())
        pages.add(newPageData)
        println("JVM: New page created with name: $pageName and data: $newPageData")
        page?.evaluate("console.log('refreshPageList')") // Send message to JS to refresh page list
    }

    /**
     * Loads data from a file and sends it to the JS side.
     */
    private fun load(filename: String) {
        val jsonFilename = "$filename.json"

        if (File(jsonFilename).exists()) {
            val jsonString = File(jsonFilename).readText()
            val tagParts = filename.split("_")
            val tag = tagParts.lastOrNull() ?: filename

            val dataToPass = mapOf(
                "tag" to tag,
                "jsonString" to jsonString
            )
            page?.evaluate("(data) => receiveData(data.tag, data.jsonString)", dataToPass)
        }
    }

    /**
     * Sends a list of pages for a given site and language to the JS side.
     */
    private fun sendPageList(site: String, language: String) {
        // For now, return all pages regardless of site and language
        val dataToPass = mapOf("pages" to pages)
        page?.evaluate(\"(data) => receivePageList(data.pages)\", dataToPass)
    }

    /**
     * Traces console messages for debugging.
     */
    private fun trace(message: ConsoleMessage) {
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
}
