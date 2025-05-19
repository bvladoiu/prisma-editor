package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import kotlin.js.JSON

class Logo(
    var src: String,
    var alt: String, // Needs to be internationalized
    var href: String? = null
) {

    private var rootElement: HTMLElement? = null

    init {
        load()
    }

    fun buildStaticHtml(): String {
        return document.create.div { // Use a div to contain the anchor/image
            if (href != null) {
                a(href = href!!) {
                    img(src = src, alt = alt) {
                        attributes["TAG"] = TAG
                    }
                }
            } else {
                img(src = src, alt = alt) {
                    attributes["TAG"] = TAG
                }
            }
        }.innerHTML // Return the inner HTML to avoid the containing div in the final output
    }

    fun renderTo(parentElement: HTMLElement): HTMLElement {
        val newElement = buildStaticHtml().let { htmlString ->
            document.createElement("div").apply { innerHTML = htmlString }.firstElementChild as HTMLElement // Create element from HTML string
        }


        rootElement?.remove()

        parentElement.appendChild(newElement)
        rootElement = newElement
        return newElement
    }

    fun detach() {
        rootElement?.remove()
        rootElement = null
    }

    fun commit() {
        val data = mapOf(
            "componentType" to "Logo",
            "src" to src,
            "alt" to alt,
            "href" to href
        )
        val jsonData = JSON.stringify(data)
        console.log("commit:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG: (Simulated - component created with default values)")
        // In a real scenario, load data here and call set()
    }

    fun set(data: dynamic) {
        src = data.src as? String ?: src
        alt = data.alt as? String ?: alt
        href = data.href as? String? // href can be null
        // Consider triggering a refresh if the visual representation changes significantly
        console.log("set:$TAG", data)
    }

    companion object {
        const val TAG = "logo"

        fun cssRules(): List<CssRuleDefinition> {
            val selector = "[TAG='$TAG']"
            return listOf(
                selector to {
                    width = Theme.iconSize
                    height = Theme.iconSize
                    display = "block" // Avoid extra space below the image
                }
            )
        }
    }
}