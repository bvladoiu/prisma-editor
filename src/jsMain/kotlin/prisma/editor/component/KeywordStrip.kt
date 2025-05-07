package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.*
import prisma.editor.styles.Theme

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

    /**
     * Creates and returns a stylesheet for the KeywordStrip component.
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
            [keyword-strip] {
                display: flex;
                flex-wrap: wrap;
                gap: 8px;
                justify-content: center;
            }
        """.trimIndent()
        stylesheet.insertRule(containerRule, stylesheet.cssRules.length)

        // Keyword rule
        val keywordRule = """
            [keyword-strip] span {
                background-color: ${Theme.Colors.lightTransparent};
                color: ${Theme.Colors.primary};
                padding: ${Theme.Spacing.xs} 12px;
                border-radius: 16px;
                font-size: ${Theme.Typography.fontXs};
            }
        """.trimIndent()
        stylesheet.insertRule(keywordRule, stylesheet.cssRules.length)

        return stylesheet
    }
}
