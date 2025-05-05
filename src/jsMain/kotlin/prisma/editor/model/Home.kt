package prisma.editor.model

import kotlinx.browser.window
import kotlinx.coroutines.*
import org.w3c.xhr.XMLHttpRequest
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.js.Json
import kotlin.js.json

@JsName("undefined")
external val undefined: dynamic

// Data model for home page content
data class Home(
    val navigation: Navigation,
    val hero: Hero,
    val sections: List<Section>,
    val footer: Footer
)

// Function to load home data from JSON file
suspend fun loadHome(language: String = "en"): Home {
    val jsonString = fetchResource("/data/$language/home.json")
    return parseHome(jsonString)
}

// Parse JSON string to Home object
private fun parseHome(jsonString: String): Home {
    val jsonObj = JSON.parse<Json>(jsonString)

    // Parse navigation
    val navObj = jsonObj["navigation"].unsafeCast<Json>()
    val navigation = Navigation(
        brand = navObj["brand"].toString(),
        items = navObj["items"].unsafeCast<Array<String>>().toList()
    )

    // Parse hero
    val heroObj = jsonObj["hero"].unsafeCast<Json>()
    val hero = Hero(
        title = heroObj["title"].toString(),
        description = heroObj["description"].toString(),
        buttonText = heroObj["buttonText"].toString()
    )

    // Parse sections
    val sectionsArray = jsonObj["sections"].unsafeCast<Array<Json>>()
    val sections = sectionsArray.map { sectionObj ->
        val title = sectionObj["title"]
        val isDivider = sectionObj["isDivider"].unsafeCast<Boolean>()

        // Parse items if present
        val items = if (sectionObj["items"] !== undefined) {
            sectionObj["items"].unsafeCast<Array<Json>>().map { itemObj ->
                SectionItem(
                    title = itemObj["title"].toString(),
                    description = itemObj["description"].toString()
                )
            }
        } else null

        // Parse keywordStrip if present
        val keywordStrip = if (sectionObj["keywordStrip"] !== undefined) {
            sectionObj["keywordStrip"].unsafeCast<Array<String>>().toList()
        } else null

        // Parse updates if present
        val updates = if (sectionObj["updates"] !== undefined) {
            sectionObj["updates"].unsafeCast<Array<Json>>().map { updateObj ->
                Update(
                    title = updateObj["title"].toString(),
                    author = updateObj["author"].toString(),
                    date = updateObj["date"].toString()
                )
            }
        } else null

        // Parse buttonText if present
        val buttonText = if (sectionObj["buttonText"] !== undefined) {
            sectionObj["buttonText"].toString()
        } else null

        Section(
            title = if (title !== undefined) title.toString() else null,
            isDivider = isDivider,
            items = items,
            keywordStrip = keywordStrip,
            updates = updates,
            buttonText = buttonText
        )
    }

    // Parse footer
    val footerObj = jsonObj["footer"].unsafeCast<Json>()
    val footer = Footer(
        copyright = footerObj["copyright"].toString(),
        links = footerObj["links"].unsafeCast<Array<String>>().toList()
    )

    return Home(
        navigation = navigation,
        hero = hero,
        sections = sections,
        footer = footer
    )
}

// Utility function to fetch a resource
suspend fun fetchResource(path: String): String = suspendCoroutine { continuation ->
    val xhr = XMLHttpRequest()
    xhr.open("GET", path, true)
    xhr.onload = {
        if (xhr.status.toInt() in 200..299) {
            continuation.resume(xhr.responseText)
        } else {
            continuation.resumeWithException(Exception("Failed to load resource: $path, status: ${xhr.status}"))
        }
    }
    xhr.onerror = {
        continuation.resumeWithException(Exception("Failed to load resource: $path"))
    }
    xhr.send()
}
