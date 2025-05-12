package prisma.editor.styles

import kotlinx.browser.document
import org.w3c.dom.css.*
import prisma.editor.styles.Theme

/**
 * Styles for the Home page.
 */
object HomeStyles {
    /**
     * Creates and returns a stylesheet for the Home page.
     * This method uses CSSOM API to create a stylesheet with rules for the Home page.
     */
    fun stylesheet(): CSSStyleSheet {
        // Create a new style element
        val styleElement = document.createElement("style")
        document.head?.appendChild(styleElement)

        // Get the stylesheet from the document's styleSheets collection
        val stylesheet = document.styleSheets[document.styleSheets.length - 1] as CSSStyleSheet

        // Add rules to the stylesheet

        // Home page container rule
        val homePageSelector = "[home-page]"
        val homePageRuleIndex = stylesheet.cssRules.length
        stylesheet.insertRule("$homePageSelector { }", homePageRuleIndex)
        val homePageCssRule = stylesheet.cssRules.item(homePageRuleIndex) as CSSStyleRule
        homePageCssRule.style.maxWidth = Theme.Spacing.maxContentWidth
        homePageCssRule.style.margin = "0 auto"
        homePageCssRule.style.padding = "0 16px"

        // Grid layouts rule
        val gridSelector = "[expertise-grid], [reasons-grid]"
        val gridRuleIndex = stylesheet.cssRules.length
        stylesheet.insertRule("$gridSelector { }", gridRuleIndex)
        val gridCssRule = stylesheet.cssRules.item(gridRuleIndex) as CSSStyleRule
        gridCssRule.style.display = "grid"
        gridCssRule.style.asDynamic().gridTemplateColumns = "repeat(auto-fit, minmax(300px, 1fr))"
        gridCssRule.style.asDynamic().gap = "16px"
        gridCssRule.style.marginBottom = "16px"

        // Primary button rule
        val buttonSelector = ".primary-button"
        val buttonRuleIndex = stylesheet.cssRules.length
        stylesheet.insertRule("$buttonSelector { }", buttonRuleIndex)
        val buttonCssRule = stylesheet.cssRules.item(buttonRuleIndex) as CSSStyleRule
        buttonCssRule.style.backgroundColor = Theme.Colors.primary
        buttonCssRule.style.color = Theme.Colors.white
        buttonCssRule.style.padding = "${Theme.Spacing.sm} ${Theme.Spacing.md}"
        buttonCssRule.style.border = "0"
        buttonCssRule.style.borderRadius = Theme.Spacing.borderRadius
        buttonCssRule.style.cursor = "pointer"
        buttonCssRule.style.fontSize = Theme.Typography.fontSm
        buttonCssRule.style.marginTop = "16px"

        return stylesheet
    }
}