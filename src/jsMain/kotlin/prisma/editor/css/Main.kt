package prisma.editor.css

import kotlinx.browser.document
import org.w3c.dom.*
import org.w3c.dom.css.*
import prisma.editor.component.Footer

typealias StyleLambda = CSSStyleDeclaration.() -> Unit
typealias CssRuleDefinition = Pair<String, StyleLambda>

object Main {

    fun stylesheet(): CSSStyleSheet {
        val styleElement = document.createElement("style") as HTMLStyleElement
        styleElement.id = "main-css-stylesheet"
        document.head?.appendChild(styleElement)

        val mainSheet = styleElement.sheet as CSSStyleSheet
        val rules = mutableListOf<CssRuleDefinition>()

        rules.addAll(Theme.getCssRules())
        rules.addAll(Typography.getCssRules())

        rules.addAll(Footer.cssRules())
        rules.addAll(prisma.editor.pages.Page.cssRules())
        rules.addAll(prisma.editor.component.Section.cssRules())
        rules.addAll(prisma.editor.component.SectionHeader.cssRules())
        rules.addAll(prisma.editor.component.SectionContent.cssRules())

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
