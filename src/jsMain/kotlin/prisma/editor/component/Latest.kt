package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.*
import prisma.editor.styles.Theme

/**
 * Creates an updates list.
 */
class Latest(var articles: List<ArticleCard> = emptyList()) {
    fun preview(): HTMLElement {
        val ul = document.create.ul {
            attributes["updates-list"] = ""
        }

        articles.forEach { article ->
            ul.appendChild(article.preview())
        }

        return ul
    }

    /**
     * Creates and returns a stylesheet for the Latest component.
     * This method uses CSSOM API to create a stylesheet with rules for the component.
     */
    fun stylesheet(): CSSStyleSheet {
        // Create a new style element
        val styleElement = document.createElement("style")
        document.head?.appendChild(styleElement)

        // Get the stylesheet from the document's styleSheets collection
        val stylesheet = document.styleSheets[document.styleSheets.length - 1] as CSSStyleSheet

        // Add rules to the stylesheet

        // List rule
        val listRule = """
            [updates-list] {
                display: flex;
                flex-direction: column;
                gap: ${Theme.Spacing.md};
                list-style-type: none;
                padding: ${Theme.Spacing.none};
                margin: ${Theme.Spacing.none};
            }
        """.trimIndent()
        stylesheet.insertRule(listRule, stylesheet.cssRules.length)

        return stylesheet
    }
}
