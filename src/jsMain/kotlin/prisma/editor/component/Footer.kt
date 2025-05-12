package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.*
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme.spacing

class Footer(
    var copyright: String = "Prisma-Software © 2024, All rights reserved.",
    var links: List<String> = listOf("Privacy Policy", "Terms of Service")
) {
    fun create(): HTMLElement {
        return document.create.footer {
            attributes["footer"] = ""
            p { +copyright }
            div {
                classes = setOf("content")
                links.forEach { link ->
                    a {
                        href = "#"
                        +link
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "footer"

        fun getCssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    backgroundColor = "var(--color-dark-background)"
                    padding = spacing
                    color = "var(--color-light-gray)"
                    textAlign = "center"
                    marginTop = spacing
                },

                "[$TAG] .content" to {
                    maxWidth = "600px"
                    margin = "0 auto"
                    padding = spacing
                },
                "[$TAG] a" to {
                    color = "var(--color-white)"
                    textDecoration = "underline"
                }
            )
        }
    }
}