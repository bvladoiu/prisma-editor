package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.*
import prisma.editor.styles.Theme

/**
 * Represents an article card component.
 */
class ArticleCard(var title: String, var author: String, var date: String) {
    fun preview(): HTMLElement {
        return document.create.li {
            attributes["update-item"] = ""

            div {
                attributes["update-header"] = ""

                h3 {
                    +title
                }

                span {
                    +date
                }
            }

            div {
                attributes["update-author"] = ""
                +"By $author"
            }
        }
    }

    /**
     * Creates and returns a stylesheet for the ArticleCard component.
     * This method uses CSSOM API to create a stylesheet with rules for the component.
     */
    fun stylesheet(): CSSStyleSheet {
        // Create a new style element
        val styleElement = document.createElement("style")
        document.head?.appendChild(styleElement)

        // Get the stylesheet from the document's styleSheets collection
        val stylesheet = document.styleSheets[document.styleSheets.length - 1] as CSSStyleSheet

        // Add rules to the stylesheet

        // List item rule
        val listItemRule = """
            [update-item] {
                padding: ${Theme.Spacing.sm} ${Theme.Spacing.none};
                border-bottom: 1px solid ${Theme.Colors.lightTransparent};
                font-family: ${Theme.Typography.defaultFontFamily};
            }
        """.trimIndent()
        stylesheet.insertRule(listItemRule, stylesheet.cssRules.length)

        // Header rule
        val headerRule = """
            [update-header] {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: ${Theme.Spacing.xs};
            }
        """.trimIndent()
        stylesheet.insertRule(headerRule, stylesheet.cssRules.length)

        // Title rule
        val titleRule = """
            [update-header] h3 {
                margin: 0;
                font-size: ${Theme.Typography.fontSm};
                font-family: ${Theme.Typography.defaultFontFamily};
                color: ${Theme.Colors.white};
            }
        """.trimIndent()
        stylesheet.insertRule(titleRule, stylesheet.cssRules.length)

        // Date rule
        val dateRule = """
            [update-header] span {
                font-size: ${Theme.Typography.fontXs};
                font-family: ${Theme.Typography.defaultFontFamily};
                color: ${Theme.Colors.mediumTransparent};
            }
        """.trimIndent()
        stylesheet.insertRule(dateRule, stylesheet.cssRules.length)

        // Author rule
        val authorRule = """
            [update-author] {
                font-size: ${Theme.Typography.fontXs};
                font-family: ${Theme.Typography.defaultFontFamily};
                color: ${Theme.Colors.mediumTransparent};
            }
        """.trimIndent()
        stylesheet.insertRule(authorRule, stylesheet.cssRules.length)

        return stylesheet
    }
}
