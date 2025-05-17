package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.classes
import kotlinx.html.dom.create
import kotlinx.html.li
import kotlinx.html.ul
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography


class KeywordStrip(
    // Initial properties can be set via constructor
    initialKeywords: List<String> = emptyList()
) {
    var keywords: List<String> = initialKeywords
        private set // Properties are updated via set() method

    private var rootElement: HTMLElement? = null

    init {
        // Load initial data if necessary
        load()
    }

    // Generates the HTML structure for the component based on current properties
    private fun buildHtml(): HTMLElement {
        return document.create.ul {
            attributes["data-component-tag"] = TAG
            // Store a reference to the Kotlin component instance on the DOM element
            this@ul.asDynamic().kotlinInstance = this@KeywordStrip
            keywords.forEach { keyword ->
                li {
                    classes = setOf(Typography.SMALL_TEXT)
                    +keyword
                }
            }
        }
    }

    /**
     * Renders the component into the given parentElement.
     * If the component was already rendered elsewhere, it will be moved.
     * If called on an already rendered component within the same parent, it effectively refreshes it.
     */
    fun renderTo(parentElement: HTMLElement): HTMLElement {
        val newElement = buildHtml()

        // If this component instance is already associated with a DOM element,
        // remove it from its current parent before appending to the new parent.
        rootElement?.remove()

        parentElement.appendChild(newElement)
        rootElement = newElement
        return newElement
    }

    /**
     * Returns a preview of the component without rendering it to the DOM.
     * This is useful for previewing the component before committing it.
     */
    fun preview(): HTMLElement {
        return buildHtml()
    }

    /**
     * Placeholder for committing data, e.g., saving to a backend.
     */
    fun commit() {
        val data = mapOf(
            "keywords" to keywords
        )
        val jsonData = JSON.stringify(data)
        // In a real app, this might be an API call
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    /**
     * Placeholder for loading initial data.
     * This might involve fetching data and then calling set() to update the component.
     */
    fun load() {
        // Example: Fetch data and then call set.
        // val fetchedData = MyApi.fetchKeywordStripData(Config.currentSite, Config.currentLanguage)
        // set(fetchedData)
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG: (Simulated - component created with initial or default values)")
    }

    /**
     * Updates the component's data and refreshes its DOM representation if it's already rendered.
     * @param data A dynamic object expected to have optional keywords property.
     */
    fun set(data: dynamic) {
        var changed = false

        val newKeywords = data.keywords?.unsafeCast<List<String>>()
        if (newKeywords != null && newKeywords != keywords) {
            keywords = newKeywords
            changed = true
        }

        if (changed) {
            refreshDOM()
        }
    }

    /**
     * Refreshes the DOM of the component in place if it has been rendered and is still in the DOM.
     * This is typically called after properties are updated via set().
     */
    fun refreshDOM() {
        val currentElement = rootElement ?: return // Not rendered yet
        val parent = currentElement.parentNode ?: return // Rendered but detached from DOM

        val newElement = buildHtml()
        parent.replaceChild(newElement, currentElement)
        rootElement = newElement // Update the stored reference to the new element
    }

    /**
     * Removes the component's DOM element from the document.
     */
    fun detach() {
        rootElement?.remove()
        rootElement = null
    }

    companion object {
        // TAG is used for styling via data-component-tag and potentially for logging/identification.
        const val TAG = "keyword-strip"

        fun cssRules(): List<CssRuleDefinition> {
            val selectorBase = "[data-component-tag='$TAG']"
            return listOf(
                selectorBase to {
                    display = "flex"
                    flexWrap = "wrap"
                    setProperty("gap", Theme.spacing)
                    justifyContent = "center"
                    listStyleType = "none"
                    padding = "0"
                    margin = "0"
                },

                "$selectorBase li" to {
                    backgroundColor = Theme.lightTransparent
                    color = Theme.primary
                    padding = "4px ${Theme.spacing}"
                    borderRadius = "4px"
                }
            )
        }
    }
}
