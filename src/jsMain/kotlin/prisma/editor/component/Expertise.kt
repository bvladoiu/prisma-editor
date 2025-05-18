package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.article
import kotlinx.html.button
import kotlinx.html.dom.create
import kotlinx.html.h3
import kotlinx.html.p
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography

class Expertise(
    // Initial properties can be set via constructor
    initialName: String = "",
    initialDescription: String = ""
) {
    var name: String = initialName
        private set // Properties are updated via set() method
    var description: String = initialDescription
        private set

    private var rootElement: HTMLElement? = null

    init {
        // Load initial data if necessary
        load()
    }

    /**
     * Turns the component into an editable state.
     * @return The editable HTMLElement
     */
    fun edit(): HTMLElement {
        val element = buildHtml()

        // Make the name editable
        val nameElement = element.querySelector("h3")
        nameElement?.setAttribute("contenteditable", "true")

        // Make the description editable
        val descriptionElement = element.querySelector("p")
        descriptionElement?.setAttribute("contenteditable", "true")

        // Add delete button
        val deleteButton = document.create.button {
            attributes["class"] = "delete-button"
            attributes["onclick"] = "this.parentElement.remove()"
            attributes["title"] = "Delete this expertise"
            +"-"
        }
        element.insertBefore(deleteButton, element.firstChild)

        return element
    }

    // Generates the HTML structure for the component based on current properties
    fun buildHtml(): HTMLElement {
        return document.create.article {
            attributes["data-component-tag"] = TAG
            // Store a reference to the Kotlin component instance on the DOM element
            this@article.asDynamic().kotlinInstance = this@Expertise
            h3 {
                attributes[Typography.TAGLINE] = ""
                +name
            }
            p {
                attributes[Typography.BODY] = ""
                +description
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
     * Placeholder for committing data, e.g., saving to a backend.
     */
    fun commit() {
        val data = mapOf(
            "name" to name,
            "description" to description
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
        // val fetchedData = MyApi.fetchExpertiseData(Config.currentSite, Config.currentLanguage)
        // set(fetchedData)
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG: (Simulated - component created with initial or default values)")
    }

    /**
     * Updates the component's data and refreshes its DOM representation if it's already rendered.
     * @param data A dynamic object expected to have optional name and description properties.
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
        const val TAG = "expertise"

        fun cssRules(): List<CssRuleDefinition> {
            val selectorBase = "[data-component-tag='$TAG']"
            return listOf(
                selectorBase to {
                    padding = Theme.spacing
                    backgroundColor = Theme.lightTransparent
                    borderRadius = "4px"
                    height = "100%"
                },

                "$selectorBase h3" to {
                    marginBottom = "8px"
                    color = Theme.white
                },

                "$selectorBase p" to {
                    margin = "0"
                }
            )
        }
    }
}
