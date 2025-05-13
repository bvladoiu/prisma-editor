package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography
import kotlin.js.JSON

class SectionHeader(
    var title: String,
    var isDivider: Boolean = false
) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return if (isDivider) {
            document.create.header {
                attributes[HEADER_WITH_DIVIDER_TAG] = ""
                asDynamic().kotlinInstance = this@SectionHeader
                
                h2 {
                    attributes[HEADING_TAG] = ""
                    +title
                }
            }
        } else {
            document.create.header {
                attributes[HEADER_WITHOUT_DIVIDER_TAG] = ""
                asDynamic().kotlinInstance = this@SectionHeader
                
                h2 {
                    attributes[HEADING_TAG] = ""
                    +title
                }
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
        isDivider = data.isDivider ?: isDivider
        refresh()
    }

    fun refresh() {
        val existingElement = document.querySelector("[$TAG]")
        if (existingElement != null) {
            existingElement.parentElement?.replaceChild(preview(), existingElement)
        } else {
            console.warn("No existing element with attribute [$TAG] found to refresh.")
        }
    }

    companion object {
        const val TAG = "section-header"
        const val HEADER_WITH_DIVIDER_TAG = "section-header-with-divider"
        const val HEADER_WITHOUT_DIVIDER_TAG = "section-header-without-divider"
        const val HEADING_TAG = "section-heading"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$HEADER_WITH_DIVIDER_TAG]" to {
                    fontSize = Typography.fontLg
                    fontWeight = Typography.fontWeightBold
                    fontFamily = Typography.defaultFontFamily
                    marginBottom = Theme.spacing
                    paddingBottom = "8px"
                    borderBottom = "1px solid var(--color-medium-gray)"
                },

                "[$HEADER_WITHOUT_DIVIDER_TAG]" to {
                    marginBottom = Theme.spacing
                },

                "[$HEADING_TAG]" to {
                    fontSize = Typography.fontLg
                    fontWeight = Typography.fontWeightBold
                    fontFamily = Typography.defaultFontFamily
                    margin = "0"
                }
            )
        }
    }
}