package prisma.editor

import com.microsoft.playwright.ConsoleMessage
import com.microsoft.playwright.Page
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileNotFoundException

/**
 * JVM counterpart to EditorJs in the JS sourceset.
 * Handles CLI setup, loading and saving logic for the editor.
 */
object EditorJvm {
    const val TAG = "editor"
 private val pageList: MutableList<Link> = mutableListOf()

    private var page: Page? = null

    init {
        load()
 loadPageMetadata() // Load page metadata on startup
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

 "save:pages-metadata" -> {
 try {
 if (message.args().isNotEmpty()) {
 val gson = Gson()
 val type = object : TypeToken<List<Link>>() {}.type
 pageList.clear() // Clear existing list before adding from JS
 pageList.addAll(gson.fromJson(message.args()[0].jsonValue().toString(), type))
 savePageMetadata()
 }
 } catch (e: Exception) {
 console.error("JVM: Error processing save:pages-metadata command: ${e.message}")
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

 "updateLanguage" -> {
 if (filename != null) {
 val filenameParts = filename.split(":")
 if (filenameParts.size == 3) {
 val site = filenameParts[0]
 val oldLanguage = filenameParts[1]
 val newLanguage = filenameParts[2]
 updateLanguage(site, oldLanguage, newLanguage)
 }
 }
 }

 "deleteLanguage" -> {
 if (filename != null) {
 val filenameParts = filename.split(":")
 if (filenameParts.size == 2) {
                    deleteLanguage(filenameParts[0], filenameParts[1])
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
 * Loads page metadata from a file.
 */
 private fun loadPageMetadata() {
 val gson = Gson()
 val pagesMetadataFile = File("pages-metadata.json")
 try {
 if (pagesMetadataFile.exists()) {
 val jsonString = pagesMetadataFile.readText()
 val gson = Gson()
 val type = object : TypeToken<List<Link>>() {}.type
 pageList.addAll(gson.fromJson(jsonString, type))
 println("JVM: Loaded ${pageList.size} pages from pages-metadata.json")
 } else {
 println("JVM: pages-metadata.json not found, starting with empty page list.")
 }
 } catch (e: FileNotFoundException) {
 println("JVM: pages-metadata.json not found (caught exception), starting with empty page list.")
 } catch (e: Exception) {
 console.error("JVM: Error loading page metadata from file: ${e.message}")
 pageList.clear()
 }
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
 * Saves the page metadata to a file.
 */
 private fun savePageMetadata() {
 val gson = Gson()
 val pagesMetadataFile = File("pages-metadata.json")
 pagesMetadataFile.writeText(gson.toJson(pageList))
 println("JVM: Saved page metadata to pages-metadata.json")
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
 // For now, create a simple url and names map
 val url = pageName.lowercase().replace(" ", "-")
 val names = mapOf(Config.currentLanguage to pageName)

 // Create a Link instance
 val newLink = Link(url = url, names = names)
 pageList.add(newLink)

 println("JVM: Created new page data model: $newPageDataModel")
 page?.evaluate("console.log(\"save:pages-metadata\", JSON.stringify(prisma.editor.EditorJs.pageList))") // Trigger save on JS side
        page?.evaluate("console.log('refreshPageList')") // Send message to JS to refresh page list
    }

    /**
     * Updates a language for a given site.
     */
    private fun updateLanguage(site: String, oldLanguage: String, newLanguage: String) {
 Config.sites[site]?.let { languages ->
 val updatedLanguages = languages.map { if (it == oldLanguage) newLanguage else it }
 Config.sites[site] = updatedLanguages
 println("JVM: Updated language \'$oldLanguage\' to \'$newLanguage\' for site \'$site\'")
        saveConfig() // Save the updated configuration
        page?.evaluate("console.log('refreshLanguageList')") // Send message to JS to refresh language list
 }
    }

    /**
     * Deletes a language for a given site.
     */
    private fun deleteLanguage(site: String, language: String) {
 Config.sites[site]?.let { languages ->
 Config.sites[site] = languages.filter { it != language }
 println("JVM: Deleted language \'$language\' for site \'$site\'")
        saveConfig() // Save the updated configuration
        page?.evaluate("console.log('refreshLanguageList')") // Send message to JS to refresh language list
 }
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
     * Saves the current configuration to a file.
     */
    private fun saveConfig() {
 val configData = mapOf("sites" to Config.sites)
 File("config.json").writeText(com.google.gson.Gson().toJson(configData)) // Using Gson for better JSON handling
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
