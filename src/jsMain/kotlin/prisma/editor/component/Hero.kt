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
    initialName: String = "",
    initialDescription: String = "",
    initialButtonText: String = ""
) {

    var name: String = initialName
        private set
    var description: String = initialDescription
        private set
    var buttonText: String = initialButtonText
        private set

    private var rootElement: HTMLElement? = null

    init {
        load()
    }

    /**
     * Generates the HTML structure for the editor's live view.
     * Includes editor-specific attributes and elements based on `Config.isEditing`.
     */
    fun buildEditorDom(): HTMLElement {
        return document.create.header {
            attributes["data-component-tag"] = TAG
            attributes["role"] = "banner"

            // Store a reference to the Kotlin component instance on the DOM element in editor mode
            if (Config.isEditing) {
                 asDynamic().kotlinInstance = this@Hero
            }

            h1 { +name }
            p { +description }
            button { +buttonText }
        }
    }

    /**
     * Generates the static HTML string for the published site.
     * Does NOT include any editor-specific attributes or elements.
     */
    fun buildStaticHtml(): String {
         return document.create.header {
            attributes["data-component-tag"] = TAG
            attributes["role"] = "banner"
            h1 { +name }
            p { +description }
            button { +buttonText }
        }.outerHTML // Get the HTML string
    }

            h1 { +name }
            p { +description }
            button { +buttonText }
        }
    }

    fun renderTo(parentElement: HTMLElement): HTMLElement {
        val newElement = buildEditorDom()

        rootElement?.remove()

        parentElement.appendChild(newElement)
        rootElement = newElement
        return newElement
    }

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
     * Returns the component's state as a serializable object.
     */
    fun toData(): Any {
        return mapOf(
            "name" to name,
            "description" to description,
            "buttonText" to buttonText
        )
    }

    fun refreshDOM() {
        val currentElement = rootElement ?: return
        val parent = currentElement.parentNode ?: return

        val newElement = buildHtml()
        // Pass the current editing state implicitly via Config.isEditing
        val newElement = buildEditorDom()
        parent.replaceChild(newElement, currentElement)
        rootElement = newElement // Update the stored reference to the new element
    }

    /**
     * Placeholder for committing data, e.g., saving to a backend.
     * Uses the toData() method to get the current state.
     */
    fun commit() {
        val data = toData()
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG: (Simulated - component created with initial or default values)")
    }

    fun detach() {
        rootElement?.remove()
        rootElement = null
    }

    companion object {
        const val TAG = "hero"

        fun cssRules(): List<CssRuleDefinition> {
            val selectorBase = "[data-component-tag='$TAG']"
            return listOf(
                selectorBase to {
                    textAlign = "center"
                    padding = spacing
                },
                "$selectorBase h1" to {
                    margin = spacing
                },
                "$selectorBase p" to {
                    maxWidth = "600px"
                    marginLeft = "auto"
                    marginRight = "auto"
                },
                "$selectorBase button" to {
                    backgroundColor = Theme.secondary
                    color = Theme.white
                    padding = spacing
                    borderRadius = spacing
                    border = "none"
                    cursor = "pointer"
                }
            )
        }
    }
}
