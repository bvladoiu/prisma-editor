package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import kotlin.js.JSON


class Section(
    var title: String? = null,
    var isDivider: Boolean = false,
    var marginTop: String = "2rem",
    var content: String = ""
) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        val section = document.create.section {
            attributes[TAG] = ""
            attributes["data-margin-top"] = marginTop
            asDynamic().kotlinInstance = this@Section

            div {
                attributes[CONTENT_CONTAINER_TAG] = ""

                div {
                    attributes[CONTENT_TAG] = ""
                    +content
                }
            }
        }

        title?.let { nonNullTitle ->
            val header = SectionHeader(nonNullTitle, isDivider)
            val headerElement = header.preview()
            val contentContainer = section.querySelector("[$CONTENT_CONTAINER_TAG]")
            contentContainer?.insertBefore(headerElement, contentContainer.firstChild)
        }

        return section
    }

    fun commit() {
        val data = mapOf(
            "title" to title,
            "isDivider" to isDivider,
            "marginTop" to marginTop,
            "content" to content
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        title = data.title ?: title
        isDivider = data.isDivider ?: isDivider
        marginTop = data.marginTop ?: marginTop
        content = data.content ?: content
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
        const val CONTENT_CONTAINER_TAG = "section-content-container"
        const val CONTENT_TAG = "section-content"

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

                "[$CONTENT_CONTAINER_TAG]" to {
                    padding = Theme.spacing
                    backgroundColor = "var(--color-very-light-transparent)"
                    borderRadius = "4px"
                }
            )
        }
    }
}
