package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.stream.createHTML
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import kotlin.js.JSON

/**
 * Section component that represents a section of a page.
 * Uses SectionHeader and SectionContent components for semantic clarity.
 * @param title The title of the section (optional).
 * @param isDivider Whether to show a divider line below the header.
 * @param marginTop The top margin of the section.
 */
class Section(
    var title: String? = null,
    var isDivider: Boolean = false,
    var marginTop: String = "2rem"
) {
    private val content = SectionContent()

    init {
        load()
    }

    /**
     * Adds a child element to the section content.
     * @param element The element to add.
     */
    fun addChild(element: HTMLElement) {
        content.addChild(element)
    }

    /**
     * Adds content using FlowContent to the section.
     * @param content The FlowContent to add.
     */
    fun addContent(flowContent: FlowContent.() -> Unit) {
        content.addContent(flowContent)
    }

    /**
     * Adds multiple child elements to the section content.
     * @param elements The elements to add.
     */
    fun addChildren(elements: List<HTMLElement>) {
        content.addChildren(elements)
    }

    fun preview(): HTMLElement {
        val sectionElement = document.create.section {
            attributes[TAG] = ""
            attributes["data-margin-top"] = marginTop
            asDynamic().kotlinInstance = this@Section
        }

        // Create article element
        val articleElement = document.create.article {
            attributes["section-article"] = ""
        }

        // Add header if title is provided
        if (title != null) {
            val header = SectionHeader(title!!, isDivider)
            articleElement.appendChild(header.preview())
        }

        // Add content
        articleElement.appendChild(content.preview())

        // Add article to section
        sectionElement.appendChild(articleElement)

        return sectionElement
    }

    fun commit() {
        val data = mapOf(
            "title" to title,
            "isDivider" to isDivider,
            "marginTop" to marginTop
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)

        // Also commit content
        content.commit()
    }

    fun load() {
        console.log("load:$TAG")
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

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    marginTop = "2rem"
                    marginBottom = "2rem"
                    padding = "0 ${Theme.spacing}"
                    maxWidth = "1200px"
                    marginLeft = "auto"
                    marginRight = "auto"
                },

                "[section-article]" to {
                    padding = Theme.spacing
                    backgroundColor = "var(--color-very-light-transparent)"
                    borderRadius = "4px"
                }
            )
        }
    }
}
