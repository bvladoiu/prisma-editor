package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.*
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme.spacing
import kotlin.js.JSON

class Footer(
    var copyright: String = "Prisma-Software © 2024, All rights reserved.",
    var links: List<String> = listOf("Privacy Policy", "Terms of Service")
) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.footer {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@Footer
            p { +copyright }
            nav {
                attributes[CONTENT_TAG] = ""
                links.forEach { link ->
                    a {
                        href = "#"
                        +link
                    }
                }
            }
        }
    }

    fun commit() {
        val data = mapOf(
            "copyright" to copyright,
            "links" to links
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        copyright = data.copyright ?: copyright
        links = data.links?.unsafeCast<List<String>>() ?: links
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
        const val TAG = "footer"
        const val CONTENT_TAG = "footer-content"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    backgroundColor = "var(--color-dark-background)"
                    padding = spacing
                    color = "var(--color-light-gray)"
                    textAlign = "center"
                    marginTop = spacing
                },

                "[$CONTENT_TAG]" to {
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
