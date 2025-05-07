package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import web.html.HTMLElement
import org.w3c.dom.HTMLElement as W3CHTMLElement
import org.w3c.dom.css.*
import prisma.editor.styles.Theme

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

    /**
     * Creates and returns a stylesheet for the Footer component.
     * This method uses CSSOM API to create a stylesheet with rules for the component.
     */
    fun stylesheet(): CSSStyleSheet {
        // Create a new style element
        val styleElement = document.createElement("style")
        document.head?.appendChild(styleElement)

        // Get the stylesheet from the document's styleSheets collection
        val stylesheet = document.styleSheets[document.styleSheets.length - 1] as CSSStyleSheet

        // Add rules to the stylesheet

        // Footer rule
        val footerRule = """
            [footer] {
                background-color: ${Theme.Colors.darkBackground};
                padding: ${Theme.Spacing.xl} ${Theme.Spacing.none};
                color: ${Theme.Colors.lightGray};
                text-align: center;
                margin-top: ${Theme.Spacing.xl};
                font-family: ${Theme.Typography.defaultFontFamily};
            }
        """.trimIndent()
        stylesheet.insertRule(footerRule, stylesheet.cssRules.length)

        // Content rule
        val contentRule = """
            [footer] .content {
                max-width: ${Theme.Spacing.maxContentWidth};
                margin: 0 auto;
                padding: ${Theme.Spacing.none} ${Theme.Spacing.md};
            }
        """.trimIndent()
        stylesheet.insertRule(contentRule, stylesheet.cssRules.length)

        // Link rule
        val linkRule = """
            [footer] a {
                color: ${Theme.Colors.white};
                text-decoration: underline;
                font-family: ${Theme.Typography.defaultFontFamily};
            }
        """.trimIndent()
        stylesheet.insertRule(linkRule, stylesheet.cssRules.length)

        return stylesheet
    }
}
