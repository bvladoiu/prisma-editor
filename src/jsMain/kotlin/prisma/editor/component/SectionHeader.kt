package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import kotlin.js.JSON

/**
 * Component for section headers.
 * @param title The title text to display in the header.
 * @param isDivider Whether to show a divider line below the header.
 */
class SectionHeader(
    var title: String,
    var isDivider: Boolean = false
) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.header {
            attributes[TAG] = ""
            attributes["data-divider"] = isDivider.toString()
            asDynamic().kotlinInstance = this@SectionHeader

            // Use typography class directly for the heading
            h2 {
                classes = setOf(prisma.editor.css.Typography.HEADLINE)
                +title
            }
        }
    }

    fun commit() {
        val data = mapOf(
            "title" to title,
            "isDivider" to isDivider
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        title = data.title ?: title
        if (data.isDivider != null) {
            isDivider = data.isDivider as Boolean
        }
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
        const val TAG = "section-header"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    marginBottom = Theme.spacing
                },
                "[$TAG][data-divider='true']" to {
                    paddingBottom = "8px"
                    borderBottom = "1px solid var(--color-medium-gray)"
                }
            )
        }
    }
}
