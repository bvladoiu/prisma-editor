package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography

class Section(
    initialTitle: String? = null,
    initialIsDivider: Boolean = false,
    initialMarginTop: String = Theme.spacing
) {
    var title: String? = initialTitle
        private set
    var isDivider: Boolean = initialIsDivider
        private set
    var marginTop: String = initialMarginTop
        private set

    private val contentItems = mutableListOf<HTMLElement>()
    private val gridItems = mutableListOf<HTMLElement>()
    private var rootElement: HTMLElement? = null

    init {
        load()
    }

    fun edit(): HTMLElement {
        val element = preview()

        val headerElement = element.querySelector("[data-component-tag='${HEADER_ATTR}'] h2")
        headerElement?.setAttribute("contenteditable", "true")

        val deleteButton = document.create.button {
            attributes["class"] = "delete-button"
            attributes["onclick"] = "this.parentElement.remove()"
            attributes["title"] = "Delete this section"
            +"-"
        }
        element.insertBefore(deleteButton, element.firstChild)

        val contentElement = element.querySelector("[data-component-tag='${CONTENT_ATTR}']")
        if (contentElement != null) {
            val addContentButton = document.create.button {
                attributes["class"] = "add-button"
                attributes["onclick"] = "prisma.editor.component.Section.addNewContent(this)"
                attributes["title"] = "Add new content"
                +"+"
            }
            contentElement.appendChild(addContentButton)
        }

        val gridElement = element.querySelector("[data-component-tag='${GRID_ATTR}']")
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

    fun addContent(element: HTMLElement) {
        contentItems.add(element)
    }

    fun addContent(flowContent: FlowContent.() -> Unit) {
        val element = document.create.div {
            apply(flowContent)
        }
        if (element.childElementCount == 1) {
            contentItems.add(element.firstElementChild as HTMLElement)
        } else {
            contentItems.add(element)
        }
    }

    fun addGridItem(element: HTMLElement) {
        gridItems.add(element)
    }

    fun addGridItems(elements: List<HTMLElement>) {
        gridItems.addAll(elements)
    }

    fun preview(): HTMLElement {
        val sectionElement = document.create.section {
            attributes[TAG] = ""
            attributes["data-margin-top"] = marginTop
            asDynamic().kotlinInstance = this@Section
        }

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

        @JsName("addNewContent")
        fun addNewContent(button: dynamic) {
            val contentElement = button.parentElement
            val textElement = document.create.p {
                attributes["class"] = Typography.BODY
                attributes["contenteditable"] = "true"
                +"New content item. Click to edit."
            }

            val deleteButton = document.create.button {
                attributes["class"] = "delete-button"
                attributes["onclick"] = "this.parentElement.remove()"
                attributes["title"] = "Delete this element"
                +"-"
            }
            textElement.appendChild(deleteButton)

            contentElement.insertBefore(textElement, button)
        }

        @JsName("addNewGridItem")
        fun addNewGridItem(button: dynamic) {
            val gridElement = button.parentElement
            val textElement = document.create.p {
                attributes["class"] = Typography.BODY
                attributes["contenteditable"] = "true"
                +"New grid item. Click to edit."
            }

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
                    position = "relative"
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
                    margin = "10px auto"
                }
            )
        }
    }
}
