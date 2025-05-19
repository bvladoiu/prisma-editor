package prisma.editor.component

import kotlinx.html.FlowContent
import kotlinx.html.span
import prisma.editor.css.FontsLoader
import prisma.editor.css.Typography.ICON
import prisma.editor.css.CssRuleDefinition

/**
 * Icon component that renders a Material Symbols icon.
 * @param name The name of the Material Symbols icon to display.
 */
class Icon(
    var name: String,
) {
    init {
        FontsLoader.registerIcon(name)
    }

    companion object {
        // CSS rules for the embedded icon span
        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[${ICON}]" to {
                    // Add any specific CSS rules for the icon span here if needed
                    // For example:
                    // fontSize = "24px"
                    // color = "blue"
                }
            )
        }
    }
}

/**
 * Renders the icon as a span with the appropriate attributes within the FlowContent.
 * This is an extension function for embedding the icon using the kotlinx.html DSL.
 * @param name The name of the Material Symbols icon.
 */
fun FlowContent.icon(name: String) {
    Icon(name) // Register the icon when used
    span {
        attributes[ICON] = ""
            +name
    }
}
