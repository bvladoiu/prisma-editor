package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography

/**
 * Creates a keyword strip with the 'keyword-strip' attribute.
 */
class KeywordStrip(var keywords: List<String> = emptyList()) {

    fun preview(): HTMLElement {
        return document.create.div {
            // Add the 'keyword-strip' attribute for CSS targeting
            attributes["keyword-strip"] = ""

            keywords.forEach { keyword ->
                span {
                    +keyword
                }
            }
        }
    }

    companion object {
        const val TAG = "keyword-strip"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[keyword-strip]" to {
                    display = "flex"
                    flexWrap = "wrap"
                    setProperty("gap", Theme.spacing)
                    justifyContent = "center"
                },

                "[keyword-strip] span" to {
                    backgroundColor = "var(--color-light-transparent)"
                    color = Theme.primary
                    padding = "4px ${Theme.spacing}"
                    borderRadius = "4px"
                    fontSize = Typography.fontXs
                }
            )
        }
    }
}
