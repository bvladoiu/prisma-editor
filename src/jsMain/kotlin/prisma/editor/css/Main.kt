package prisma.editor.css

import kotlinx.browser.document
import org.w3c.dom.*
import org.w3c.dom.css.CSSStyleRule
import org.w3c.dom.css.*
import prisma.editor.component.Footer
import prisma.editor.component.Page // Import Page from the correct package
import prisma.editor.component.TopBar

typealias StyleLambda = CSSStyleDeclaration.() -> Unit
typealias CssRuleDefinition = Pair<String, StyleLambda>

object Main {

    fun stylesheet(): CSSStyleSheet {
        // Add critical inline CSS rules first
        val inlineStyleElement = document.createElement("style") as HTMLStyleElement
        inlineStyleElement.id = "critical-css-stylesheet"
        document.head?.appendChild(inlineStyleElement)
        val inlineSheet = inlineStyleElement.sheet as CSSStyleSheet
        for ((selector, cssLambda) in inlineCssRules()) {
            insertRuleInto(inlineSheet, selector, cssLambda)
        }

        // Check user's preferred color scheme and apply dark mode class
        if (window.matchMedia("(prefers-color-scheme: dark)").matches) {
            document.body?.classList?.add("dark")
        }

        // Create and append the main stylesheet
        val styleElement = document.createElement("style") as HTMLStyleElement
        styleElement.id = "main-css-stylesheet"
        document.head?.appendChild(styleElement)
        val mainSheet = styleElement.sheet as CSSStyleSheet
        val rules = mutableListOf<CssRuleDefinition>()

        rules.addAll(Theme.getCssRules())
        rules.addAll(Typography.getCssRules())

        rules.addAll(Footer.cssRules())
        rules.addAll(Page.cssRules()) // Use the moved Page class
        rules.addAll(prisma.editor.component.Section.cssRules())
        rules.addAll(prisma.editor.component.SectionHeader.cssRules())
        // rules.addAll(prisma.editor.component.SectionContent.cssRules()) // Assuming this component might not exist or is handled differently

        // Add all other components' CSS rules
        rules.addAll(prisma.editor.component.EditorScaffold.cssRules())
        rules.addAll(prisma.editor.component.NavLink.cssRules())
        rules.addAll(prisma.editor.component.ArticleCard.cssRules())
        rules.addAll(prisma.editor.component.Drawer.cssRules())
        rules.addAll(prisma.editor.component.Expertise.cssRules())
        rules.addAll(prisma.editor.component.Hero.cssRules())
        rules.addAll(prisma.editor.component.KeywordStrip.cssRules())
        rules.addAll(prisma.editor.component.Latest.cssRules())
        rules.addAll(prisma.editor.component.NavigationMenu.cssRules())
        rules.addAll(prisma.editor.component.Pitch.cssRules())
        rules.addAll(prisma.editor.component.Image.cssRules())
        rules.addAll(TopBar.cssRules()) // Add TopBar CSS rules

        fun insertRuleInto(sheet: CSSStyleSheet, selector: String, css: StyleLambda) {
            try {
                val ruleIndex = sheet.cssRules.length
                sheet.insertRule("$selector {}", ruleIndex)
                val rule = sheet.cssRules.item(ruleIndex) as? CSSStyleRule
                rule?.style?.apply(css)
            } catch (e: Exception) {
                console.error("Error inserting CSS rule '$selector': ${e.message}")
            }
        }

        for ((selector, cssLambda) in rules) {
            insertRuleInto(mainSheet, selector, cssLambda)
        }
        return mainSheet
    }

    /**
     * Returns a list of essential CSS rules to be inlined in the HTML head
     * for basic styling and to prevent a flash of unstyled content.
     */
    private fun inlineCssRules(): List<CssRuleDefinition> {
        return listOf(
            // Basic body styling
            "body" to {
                margin = "0"
                padding = "0"
                fontFamily = "sans-serif" // Or your desired base font
            },
            // Critical theme variables from :root
            ":root" to {
                setProperty("--color-primary", Theme.brandBlue)
                setProperty("--color-secondary", Theme.brandPurple)
                setProperty("--color-white", Theme.pureWhite)
                setProperty("--color-light-gray", Theme.softGray)
                setProperty("--color-dark-background", Theme.deepBlack)
                setProperty("--color-medium-gray", Theme.neutralGray)
                setProperty("--color-light-transparent", Theme.whiteTransparentLight)
                setProperty("--color-very-light-transparent", Theme.whiteTransparentVeryLight)
                setProperty("--color-medium-transparent", Theme.whiteTransparentMedium)
                setProperty("--spacing", Theme.spacing) // Use the clamp value from Theme
                setProperty("--icon-size", Theme.iconSize) // Use the clamp value from Theme
            }
            // Add other critical rules as needed, e.g., for a preloader or critical layout
        )
    }
    fun commit() {
        val styleElement = document.getElementById("app-master-stylesheet") as? HTMLStyleElement
        val masterSheet = styleElement?.sheet as? CSSStyleSheet
        if (masterSheet != null) {
            val data = masterSheet.cssRules.asList().joinToString("\n") { it.cssText }
            console.log("save:main-css-stylesheet", data)
        } else {
            console.warn("Master stylesheet element with ID 'app-master-stylesheet' not found for export.")
            console.error("/* Master stylesheet not found */");
        }
    }

    fun export(): String {
        // First try to find the stylesheet we created
        val styleElement = document.getElementById("main-css-stylesheet") as? HTMLStyleElement
        val sheet = styleElement?.sheet as? CSSStyleSheet

        if (sheet != null) {
            // Convert all CSS rules to a string
            return sheet.cssRules.asList().joinToString("\n") { it.cssText }
        } else {
            // If stylesheet not found, create a new one and export its content
            val newSheet = stylesheet()
            return newSheet.cssRules.asList().joinToString("\n") { it.cssText }
        }
    }
}
