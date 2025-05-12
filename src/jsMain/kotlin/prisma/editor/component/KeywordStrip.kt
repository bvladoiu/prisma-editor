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

    companion object {
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
            val containerSelector = "[keyword-strip]"
            val containerRuleIndex = stylesheet.cssRules.length
            stylesheet.insertRule("$containerSelector { }", containerRuleIndex)
            val containerCssRule = stylesheet.cssRules.item(containerRuleIndex) as CSSStyleRule
            containerCssRule.style.display = "flex"
            containerCssRule.style.flexWrap = "wrap"
            containerCssRule.style.asDynamic().gap = Theme.Spacing.sm
            containerCssRule.style.justifyContent = "center"

            // Keyword rule
            val keywordSelector = "$containerSelector span"
            val keywordRuleIndex = stylesheet.cssRules.length
            stylesheet.insertRule("$keywordSelector { }", keywordRuleIndex)
            val keywordCssRule = stylesheet.cssRules.item(keywordRuleIndex) as CSSStyleRule
            keywordCssRule.style.backgroundColor = Theme.Colors.lightTransparent
            keywordCssRule.style.color = Theme.Colors.primary
            keywordCssRule.style.padding = "${Theme.Spacing.xs} ${Theme.Spacing.sm}"
            keywordCssRule.style.borderRadius = Theme.Spacing.borderRadius
            keywordCssRule.style.fontSize = Theme.Typography.fontXs

            return stylesheet
        }
    }
}
