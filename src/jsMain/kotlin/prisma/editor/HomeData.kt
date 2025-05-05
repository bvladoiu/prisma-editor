package prisma.editor

import kotlinx.browser.window
import kotlinx.coroutines.*
import org.w3c.xhr.XMLHttpRequest
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.js.Json
import kotlin.js.json

// Data models for home page content
data class HomeData(
    val navigation: Navigation,
    val hero: Hero,
    val sections: List<Section>,
    val footer: Footer
)

data class Navigation(
    val brand: String,
    val items: List<String>
)

data class Hero(
    val title: String,
    val description: String,
    val buttonText: String
)

data class Section(
    val title: String?,
    val isDivider: Boolean,
    val items: List<SectionItem>? = null,
    val keywordStrip: List<String>? = null,
    val updates: List<UpdateItem>? = null,
    val buttonText: String? = null
)

data class SectionItem(
    val title: String,
    val description: String
)

data class UpdateItem(
    val title: String,
    val author: String,
    val date: String
)

data class Footer(
    val copyright: String,
    val links: List<String>
)

// Function to load home data from JSON file
suspend fun loadHomeData(language: String = "en"): HomeData {
    val jsonString = fetchResource("/data/$language/home.json")
    return parseHomeData(jsonString)
}

// Parse JSON string to HomeData object
private fun parseHomeData(jsonString: String): HomeData {
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
        val items = if (sectionObj["items"] != undefined) {
            sectionObj["items"].unsafeCast<Array<Json>>().map { itemObj ->
                SectionItem(
                    title = itemObj["title"].toString(),
                    description = itemObj["description"].toString()
                )
            }
        } else null

        // Parse keywordStrip if present
        val keywordStrip = if (sectionObj["keywordStrip"] != undefined) {
            sectionObj["keywordStrip"].unsafeCast<Array<String>>().toList()
        } else null

        // Parse updates if present
        val updates = if (sectionObj["updates"] != undefined) {
            sectionObj["updates"].unsafeCast<Array<Json>>().map { updateObj ->
                UpdateItem(
                    title = updateObj["title"].toString(),
                    author = updateObj["author"].toString(),
                    date = updateObj["date"].toString()
                )
            }
        } else null

        // Parse buttonText if present
        val buttonText = if (sectionObj["buttonText"] != undefined) {
            sectionObj["buttonText"].toString()
        } else null

        Section(
            title = if (title != undefined) title.toString() else null,
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

    return HomeData(
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
