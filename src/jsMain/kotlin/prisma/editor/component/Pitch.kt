package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.*
import prisma.editor.styles.Theme

/**
 * Creates a reason item with the 'pitch' attribute.
 */
class Pitch(var title: String = "", var description: String = "") {
    fun preview(): HTMLElement {
        return document.create.div {
            // Add the 'pitch' attribute for CSS targeting
            attributes["pitch"] = ""

            h3 {
                +title
            }

            p {
                +description
            }
        }
    }

    /**
     * Creates and returns a stylesheet for the Pitch component.
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
            [pitch] {
                padding: ${Theme.Spacing.md};
                background-color: ${Theme.Colors.lightTransparent};
                border-radius: ${Theme.Spacing.borderRadius};
                font-family: ${Theme.Typography.defaultFontFamily};
            }
        """.trimIndent()
        stylesheet.insertRule(containerRule, stylesheet.cssRules.length)

        // Title rule
        val titleRule = """
            [pitch] h3 {
                font-size: ${Theme.Typography.fontMd};
                font-weight: ${Theme.Typography.fontWeightBold};
                font-family: ${Theme.Typography.defaultFontFamily};
                margin-top: 0;
                margin-bottom: ${Theme.Spacing.sm};
                color: ${Theme.Colors.primary};
            }
        """.trimIndent()
        stylesheet.insertRule(titleRule, stylesheet.cssRules.length)

        // Description rule
        val descriptionRule = """
            [pitch] p {
                margin: 0;
                font-family: ${Theme.Typography.defaultFontFamily};
            }
        """.trimIndent()
        stylesheet.insertRule(descriptionRule, stylesheet.cssRules.length)

        return stylesheet
    }
}
