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
import prisma.editor.css.Typography
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

    fun buildHtml(isEditing: Boolean = false): HTMLElement {
        val contentElement = document.create.div {
            attributes[TAG] = ""
            attributes["role"] = "region"
            asDynamic().kotlinInstance = this@SectionContent
        }

        // Add all child elements
        if (isEditing) {
            // Make all child elements editable
            children.forEach { child ->
                val component = child.asDynamic().kotlinInstance
                if (component != null && component.buildHtml != null) {
                    val editableElement = component.buildHtml(true)
                    contentElement.appendChild(editableElement)
                } else {
                    contentElement.appendChild(child)
                }
            }

            // Add "+" button to add new content
            val addButton = document.create.button {
                attributes["class"] = "add-button"
                attributes["onclick"] = "prisma.editor.component.SectionContent.addNewContent(this)"
                attributes["title"] = "Add new content"
                +"+"
            }
            contentElement.appendChild(addButton)
        } else {
            children.forEach { child ->
                contentElement.appendChild(child)
            }
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
            existingElement.parentElement?.replaceChild(buildHtml(), existingElement)
        } else {
            console.warn("No existing element with attribute [$TAG] found to refresh.")
            document.body?.appendChild(buildHtml())
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
            val textElement = document.create.p {
                attributes["class"] = Typography.BODY
                attributes["contenteditable"] = "true"
                +"New content item. Click to edit."
            }

            // Add delete button
            val deleteButton = document.create.button {
                attributes["class"] = "delete-button"
                attributes["onclick"] = "this.parentElement.remove()"
                attributes["title"] = "Delete this element"
                +"-"
            }
            textElement.appendChild(deleteButton)

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
