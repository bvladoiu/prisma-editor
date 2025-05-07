package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.*
import prisma.editor.styles.Theme

class Expertise(var name: String, var description: String) {

    fun preview(): HTMLElement {
        return document.create.div {
            {
                attributes["expertise"] = ""
                h3 {
                    +name
                }
                p {
                    +description
                }
            }
        }
    }

    /**
     * Creates and returns a stylesheet for the Expertise component.
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
            [expertise] {
                padding: ${Theme.Spacing.md};
                background-color: ${Theme.Colors.lightTransparent};
                border-radius: ${Theme.Spacing.borderRadius};
                height: ${Theme.Spacing.fullWidth};
            }
        """.trimIndent()
        stylesheet.insertRule(containerRule, stylesheet.cssRules.length)

        // Title rule
        val titleRule = """
            [expertise] h3 {
                font-size: ${Theme.Typography.fontMd};
                font-family: ${Theme.Typography.defaultFontFamily};
                margin-bottom: ${Theme.Spacing.sm};
                color: ${Theme.Colors.white};
            }
        """.trimIndent()
        stylesheet.insertRule(titleRule, stylesheet.cssRules.length)

        // Description rule
        val descriptionRule = """
            [expertise] p {
                margin: ${Theme.Spacing.none};
                line-height: ${Theme.Typography.lineHeightNormal};
                font-family: ${Theme.Typography.defaultFontFamily};
            }
        """.trimIndent()
        stylesheet.insertRule(descriptionRule, stylesheet.cssRules.length)

        return stylesheet
    }
}
