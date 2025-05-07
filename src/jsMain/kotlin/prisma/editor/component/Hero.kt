package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import web.html.HTMLElement
import org.w3c.dom.HTMLElement as W3CHTMLElement
import org.w3c.dom.css.*
import prisma.editor.styles.Theme

/**
 * Creates a hero content section with the 'hero' attribute.
 */
class Hero(
    var name: String = "",
    var description: String = "",
    var buttonText: String = ""
) : HTMLElement {
    fun create(): W3CHTMLElement {
        return document.create.div {
            // Add the 'hero' attribute for CSS targeting
            attributes["hero"] = ""

            h1 {
                +name
            }

            p {
                +description
            }

            button {
                +buttonText
            }
        }
    }

    /**
     * Creates and returns a stylesheet for the Hero component.
     * This method uses CSSOM API to create a stylesheet with rules for the component.
     */
    fun stylesheet(): CSSStyleSheet {
        // Create a new style element
        val styleElement = document.createElement("style")
        document.head?.appendChild(styleElement)

        // Get the stylesheet from the document's styleSheets collection
        val stylesheet = document.styleSheets[document.styleSheets.length - 1] as CSSStyleSheet

        // Add rules to the stylesheet

        // Container rule
        val containerRule = """
            [hero] {
                text-align: center;
                padding: ${Theme.Spacing.xl} ${Theme.Spacing.md};
            }
        """.trimIndent()
        stylesheet.insertRule(containerRule, stylesheet.cssRules.length)

        // Title rule
        val titleRule = """
            [hero] h1 {
                font-size: ${Theme.Typography.fontXl};
                font-weight: ${Theme.Typography.fontWeightNormal};
                font-family: ${Theme.Typography.defaultFontFamily};
                margin-top: ${Theme.Spacing.rem2};
                margin-bottom: ${Theme.Spacing.md};
                color: ${Theme.Colors.primary};
            }
        """.trimIndent()
        stylesheet.insertRule(titleRule, stylesheet.cssRules.length)

        // Description rule
        val descriptionRule = """
            [hero] p {
                font-size: ${Theme.Typography.fontSm};
                line-height: ${Theme.Typography.lineHeightLarge};
                font-family: ${Theme.Typography.defaultFontFamily};
                margin-bottom: ${Theme.Spacing.lg};
                max-width: 600px;
                margin-left: auto;
                margin-right: auto;
            }
        """.trimIndent()
        stylesheet.insertRule(descriptionRule, stylesheet.cssRules.length)

        // Button rule
        val buttonRule = """
            [hero] button {
                background-color: ${Theme.Colors.secondary};
                color: ${Theme.Colors.white};
                padding: ${Theme.Spacing.sm} ${Theme.Spacing.md};
                border: ${Theme.Spacing.none};
                border-radius: ${Theme.Spacing.borderRadius};
                font-size: ${Theme.Typography.fontSm};
                cursor: pointer;
            }
        """.trimIndent()
        stylesheet.insertRule(buttonRule, stylesheet.cssRules.length)

        return stylesheet
    }
}
