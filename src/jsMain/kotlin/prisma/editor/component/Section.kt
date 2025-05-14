package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography
import kotlin.js.JSON

/**
 * Section component that represents a section of a page.
 * Provides a flexible container with optional title, content, and grid layout.
 * @param title The title of the section (optional).
 * @param isDivider Whether to show a divider line below the header.
 * @param marginTop The top margin of the section.
 */
class Section(
    var title: String? = null,
    var isDivider: Boolean = false,
    var marginTop: String = Theme.spacing
) {
    private val contentItems = mutableListOf<HTMLElement>()
    private val gridItems = mutableListOf<HTMLElement>()

    init {
        load()
    }

    /**
     * Turns the component into an editable state.
     * @return The editable HTMLElement
     */
    fun edit(): HTMLElement {
        val element = preview()

        // Make the title editable if it exists
        val headerElement = element.querySelector("[${HEADER_ATTR}] h2")
        headerElement?.setAttribute("contenteditable", "true")

        // Add delete button to the section
        val deleteButton = document.create.button {
            attributes["class"] = "delete-button"
            attributes["onclick"] = "this.parentElement.remove()"
            attributes["title"] = "Delete this section"
            +"-"
        }
        element.insertBefore(deleteButton, element.firstChild)

        // Add "+" button to content area
        val contentElement = element.querySelector("[${CONTENT_ATTR}]")
        if (contentElement != null) {
            val addContentButton = document.create.button {
                attributes["class"] = "add-button"
                attributes["onclick"] = "prisma.editor.component.Section.addNewContent(this)"
                attributes["title"] = "Add new content"
                +"+"
            }
            contentElement.appendChild(addContentButton)
        }

        // Add "+" button to grid area
        val gridElement = element.querySelector("[${GRID_ATTR}]")
        if (gridElement != null) {
            val addGridItemButton = document.create.button {
                attributes["class"] = "add-button"
                attributes["onclick"] = "prisma.editor.component.Section.addNewGridItem(this)"
                attributes["title"] = "Add new grid item"
                +"+"
            }
            gridElement.appendChild(addGridItemButton)
        }

        return element
    }

    /**
     * Adds a content element to the section.
     * @param element The element to add to the content area.
     */
    fun addContent(element: HTMLElement) {
        contentItems.add(element)
    }

    /**
     * Adds content using FlowContent to the section.
     * @param content The FlowContent to add to the content area.
     */
    fun addContent(flowContent: FlowContent.() -> Unit) {
        val element = document.create.div {
            apply(flowContent)
        }
        // If the div only has one child, use that child directly
        if (element.childElementCount == 1) {
            contentItems.add(element.firstElementChild as HTMLElement)
        } else {
            contentItems.add(element)
        }
    }

    /**
     * Adds an item to the grid layout at the bottom of the section.
     * @param element The element to add to the grid.
     */
    fun addGridItem(element: HTMLElement) {
        gridItems.add(element)
    }

    /**
     * Adds multiple items to the grid layout at the bottom of the section.
     * @param elements The elements to add to the grid.
     */
    fun addGridItems(elements: List<HTMLElement>) {
        gridItems.addAll(elements)
    }

    fun preview(): HTMLElement {
        val sectionElement = document.create.section {
            attributes[TAG] = ""
            attributes["data-margin-top"] = marginTop
            asDynamic().kotlinInstance = this@Section
        }

        // Add header if title is provided
        if (title != null) {
            val headerElement = document.create.header {
                attributes[HEADER_ATTR] = ""
                attributes["data-divider"] = isDivider.toString()

                h2 {
                    classes = setOf(Typography.HEADLINE)
                    +title!!
                }
            }
            sectionElement.appendChild(headerElement)
        }

        // Add content items if any
        if (contentItems.isNotEmpty()) {
            val contentElement = document.create.div {
                attributes[CONTENT_ATTR] = ""
                attributes["role"] = "region"
            }

            contentItems.forEach { item ->
                contentElement.appendChild(item)
            }

            sectionElement.appendChild(contentElement)
        }

        // Add grid items if any
        if (gridItems.isNotEmpty()) {
            val gridElement = document.create.div {
                attributes[GRID_ATTR] = ""
            }

            gridItems.forEach { item ->
                gridElement.appendChild(item)
            }

            sectionElement.appendChild(gridElement)
        }

        return sectionElement
    }

    fun commit() {
        val data = mapOf(
            "title" to title,
            "isDivider" to isDivider,
            "marginTop" to marginTop,
            "contentItemCount" to contentItems.size,
            "gridItemCount" to gridItems.size
        )
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
    }

    fun set(data: dynamic) {
        title = data.title ?: title
        if (data.isDivider != null) {
            isDivider = data.isDivider as Boolean
        }
        marginTop = data.marginTop ?: marginTop
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
        const val TAG = "section-container"
        const val HEADER_ATTR = "section-header"
        const val CONTENT_ATTR = "section-content"
        const val GRID_ATTR = "section-grid"

        /**
         * Adds a new content item to the section.
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

        /**
         * Adds a new grid item to the section.
         * This is called from the "+" button in the grid area.
         */
        @JsName("addNewGridItem")
        fun addNewGridItem(button: dynamic) {
            val gridElement = button.parentElement
            val textElement = document.create.p {
                attributes["class"] = Typography.BODY
                attributes["contenteditable"] = "true"
                +"New grid item. Click to edit."
            }

            // Add delete button
            val deleteButton = document.create.button {
                attributes["class"] = "delete-button"
                attributes["onclick"] = "this.parentElement.remove()"
                attributes["title"] = "Delete this element"
                +"-"
            }
            textElement.appendChild(deleteButton)

            gridElement.insertBefore(textElement, button)
        }

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    marginTop = Theme.spacing
                    marginBottom = Theme.spacing
                    padding = Theme.spacing
                    maxWidth = "1200px"
                    marginLeft = "auto"
                    marginRight = "auto"
                    backgroundColor = "var(--color-very-light-transparent)"
                    borderRadius = Theme.spacing
                    position = "relative"  // For positioning delete button
                },

                "[$HEADER_ATTR]" to {
                    marginBottom = Theme.spacing
                },

                "[$HEADER_ATTR][data-divider='true']" to {
                    paddingBottom = Theme.spacing
                    borderBottom = "1px solid var(--color-medium-gray)"
                },

                "[$CONTENT_ATTR]" to {
                    padding = Theme.spacing
                },

                "[$GRID_ATTR]" to {
                    display = "grid"
                    setProperty("grid-template-columns", "repeat(auto-fill, minmax(250px, 1fr))")
                    setProperty("gap", Theme.spacing)
                    marginTop = Theme.spacing
                },

                ".delete-button" to {
                    position = "absolute"
                    top = "5px"
                    right = "5px"
                    width = "24px"
                    height = "24px"
                    borderRadius = "50%"
                    backgroundColor = "var(--color-error)"
                    color = "white"
                    border = "none"
                    cursor = "pointer"
                    display = "flex"
                    justifyContent = "center"
                    alignItems = "center"
                    fontSize = "18px"
                    fontWeight = "bold"
                    zIndex = "1"
                },

                ".add-button" to {
                    width = "30px"
                    height = "30px"
                    borderRadius = "50%"
                    backgroundColor = "var(--color-primary)"
                    color = "white"
                    border = "none"
                    cursor = "pointer"
                    display = "flex"
                    justifyContent = "center"
                    alignItems = "center"
                    fontSize = "20px"
                    fontWeight = "bold"
                    margin = "10px auto"
                }
            )
        }
    }
}
