package prisma.editor.ui.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import web.html.HTMLElement
import org.w3c.dom.HTMLElement as W3CHTMLElement

/**
 * Creates a footer with the 'footer' attribute.
 */
class Footer(
    var copyright: String = "Prisma-Software © 2024, All rights reserved.",
    var links: List<String> = listOf("Privacy Policy", "Terms of Service")
) : HTMLElement {
    fun create(): W3CHTMLElement {
        return document.create.footer {
            // Add the 'footer' attribute for CSS targeting
            attributes["footer"] = ""

            p { +copyright }

            div {
                // Add the 'content' class for CSS targeting
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
}
