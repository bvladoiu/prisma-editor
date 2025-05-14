package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.stream.createHTML
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import kotlin.js.JSON

/**
 * Component for section content.
 * This component serves as a container for section content.
 */
class SectionContent {
    private val children = mutableListOf<HTMLElement>()

    init {
        load()
    }

    /**
     * Adds a child element to the section content.
     * @param element The element to add.
     */
    fun addChild(element: HTMLElement) {
        children.add(element)
    }

    /**
     * Adds a child component using its FlowContent render method.
     * @param content The FlowContent to add.
     */
    fun addContent(content: FlowContent.() -> Unit) {
        val element = document.create.div {
            apply(content)
        }
        // If the div only has one child, use that child directly
        if (element.childElementCount == 1) {
            children.add(element.firstElementChild as HTMLElement)
        } else {
            children.add(element)
        }
    }

    /**
     * Adds multiple child elements to the section content.
     * @param elements The elements to add.
     */
    fun addChildren(elements: List<HTMLElement>) {
        children.addAll(elements)
    }

    /**
     * Turns the component into an editable state.
     * @return The editable HTMLElement
     */
    fun edit(): HTMLElement {
        val element = preview()

        // Make all child elements editable
        val childElements = element.children
        for (i in 0 until childElements.length) {
            val childElement = childElements.item(i) ?: continue
            val component = childElement.asDynamic().kotlinInstance
            if (component != null && component.edit != null) {
                val editableElement = component.edit()
                element.replaceChild(editableElement, childElement)
            }
        }

        // Add "+" button to add new content
        val addButton = document.create.button {
            attributes["class"] = "add-button"
            attributes["onclick"] = "prisma.editor.component.SectionContent.addNewContent(this)"
            attributes["title"] = "Add new content"
            +"+"
        }
        element.appendChild(addButton)

        return element
    }

    fun preview(): HTMLElement {
        val contentElement = document.create.div {
            attributes[TAG] = ""
            attributes["role"] = "region"
            asDynamic().kotlinInstance = this@SectionContent
        }

        // Add all child elements
        children.forEach { child ->
            contentElement.appendChild(child)
        }

        return contentElement
    }

    fun commit() {
        val data = mapOf(
            "childCount" to children.size
        )
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
    }

    fun set(data: dynamic) {
        // Nothing to set for now
        refresh()
    }

    fun refresh() {
        val existingElement = document.querySelector("[$TAG]")
        if (existingElement != null) {
            existingElement.parentElement?.replaceChild(preview(), existingElement)
        } else {
            console.warn("No existing element with attribute [$TAG] found to refresh.")
            document.body?.appendChild(preview())
        }
    }

    companion object {
        const val TAG = "section-content"

        /**
         * Adds a new content item to the section content.
         * This is called from the "+" button in the content area.
         */
        @JsName("addNewContent")
        fun addNewContent(button: dynamic) {
            val contentElement = button.parentElement
            val text = Text("New content item. Click to edit.", Text.TextType.REGULAR)
            val textElement = text.edit()
            contentElement.insertBefore(textElement, button)
        }

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    padding = Theme.spacing
                }
            )
        }
    }
}
