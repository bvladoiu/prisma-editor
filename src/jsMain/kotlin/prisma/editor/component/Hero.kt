package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.button
import kotlinx.html.dom.create
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.p
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Theme.spacing

class Hero(
    // Initial properties can be set via constructor
    initialName: String = "",
    initialDescription: String = "",
    initialButtonText: String = ""
) {

    var name: String = initialName
        private set // Properties are updated via set() method
    var description: String = initialDescription
        private set
    var buttonText: String = initialButtonText
        private set

    private var rootElement: HTMLElement? = null

    init {
        // Load initial data if necessary.
        // If load() is asynchronous and calls set(), refreshDOM() within set()
        // will correctly do nothing if the component isn't rendered yet.
        load()
    }

    // Generates the HTML structure for the component based on current properties
    private fun buildHtml(): HTMLElement {
        return document.create.header {
            attributes["data-component-tag"] = TAG // Use data-* attributes for custom component identification
            attributes["role"] = "banner"
            // Store a reference to the Kotlin component instance on the DOM element if needed for event handlers or debugging
            this@header.asDynamic().kotlinInstance = this@Hero

            h1 { +name }
            p { +description }
            button { +buttonText }
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
     * Updates the component's data and refreshes its DOM representation if it's already rendered.
     * @param data A dynamic object expected to have optional name, description, and buttonText properties.
     */
    fun set(data: dynamic) {
        var changed = false

        val newName = data.name as? String
        if (newName != null && newName != name) {
            name = newName
            changed = true
        }

        val newDescription = data.description as? String
        if (newDescription != null && newDescription != description) {
            description = newDescription
            changed = true
        }

        val newButtonText = data.buttonText as? String
        if (newButtonText != null && newButtonText != buttonText) {
            buttonText = newButtonText
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
     * Placeholder for committing data, e.g., saving to a backend.
     */
    fun commit() {
        val data = mapOf(
            "name" to name,
            "description" to description,
            "buttonText" to buttonText
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
        // val fetchedData = MyApi.fetchHeroData(Config.currentSite, Config.currentLanguage)
        // set(fetchedData)
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG: (Simulated - component created with initial or default values)")
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
        const val TAG = "hero"

        fun cssRules(): List<CssRuleDefinition> {
            val selectorBase = "[data-component-tag='$TAG']"
            return listOf(
                selectorBase to {
                    textAlign = "center"
                    padding = spacing
                    // Add any other base styles for the header
                },
                "$selectorBase h1" to {
                    margin = spacing // Example: use theme's spacing
                    // Add specific h1 styles
                },
                "$selectorBase p" to {
                    maxWidth = "600px"
                    marginLeft = "auto"
                    marginRight = "auto"
                    // Add specific p styles
                },
                "$selectorBase button" to {
                    backgroundColor = Theme.secondary
                    color = Theme.white
                    padding = spacing
                    borderRadius = spacing // Example: use theme's spacing unit
                    border = "none"
                    cursor = "pointer"
                    // Add specific button styles
                }
            )
        }
    }
}

/*
// How to use:
fun main() {
    // Ensure CSS rules are injected (example, actual mechanism might vary)
    // injectCss(Hero.cssRules())

    document.addEventListener("DOMContentLoaded", {
        val heroContainer = document.getElementById("hero-container") as? HTMLElement

        if (heroContainer == null) {
            console.error("Hero container element not found!")
            return@addEventListener
        }

        // Create and render the Hero component
        val heroComponent = Hero(
            initialName = "Welcome to Our Site!",
            initialDescription = "Discover amazing things.",
            initialButtonText = "Learn More"
        )
        heroComponent.renderTo(heroContainer)

        // Simulate an update after some time
        kotlinx.browser.window.setTimeout({
            val updatedData = js("({ name: 'Updated Welcome!', description: 'Check out our new features.', buttonText: 'Explore Now' })")
            heroComponent.set(updatedData) // This will update properties and refresh the DOM
        }, 3000)

        // To remove the component later:
        // kotlinx.browser.window.setTimeout({
        //     heroComponent.detach()
        // }, 6000)
    })
}
*/
