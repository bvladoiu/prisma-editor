package prisma.editor.css

import kotlinx.browser.document
import org.w3c.dom.css.*
import org.w3c.dom.*
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

        // Collect Component rules (assuming each Component.companion has getCssRules())
        // Call each component companion object's method to get their rule definitions
        // allRuleDefinitions.addAll(ArticleCard.getCssRules())
        // allRuleDefinitions.addAll(Latest.getCssRules())
        //  allRuleDefinitions.addAll(Footer.getCssRules())
        // allRuleDefinitions.addAll(Hero.getCssRules()) // Use Hero.getCssRules()
        // allRuleDefinitions.addAll(KeywordStrip.getCssRules())
        // allRuleDefinitions.addAll(NavigationMenu.getCssRules())
        // allRuleDefinitions.addAll(Pitch.getCssRules())
        // allRuleDefinitions.addAll(Section.getCssRules())
        // allRuleDefinitions.addAll(Expertise.getCssRules())
        // allRuleDefinitions.addAll(EditorScaffold.getCssRules())
        rules.addAll(Footer.getCssRules())

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
}