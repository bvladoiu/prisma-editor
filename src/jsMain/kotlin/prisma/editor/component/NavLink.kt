package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.*
import prisma.editor.styles.Theme

/**
 * Represents a navigation link component for the drawer.
 */
class NavLink(var text: String, var route: String, var icon: String = "") {
    fun preview(): HTMLElement {
        return document.create.li {
            attributes["nav-item"] = ""
            
            a {
                attributes["nav-link"] = ""
                href = "#"
                onClickFunction = { event ->
                    event.preventDefault()
                    val routeJs = route // Capture the route in a local variable
                    js("navigateTo(arguments[0])")(routeJs)
                }
                
                if (icon.isNotEmpty()) {
                    span {
                        attributes["nav-icon"] = ""
                        attributes["class"] = "material-symbols-outlined"
                        +icon
                    }
                }
                
                span {
                    attributes["nav-text"] = ""
                    +text
                }
            }
        }
    }

    /**
     * Creates and returns a stylesheet for the NavLink component.
     * This method uses CSSOM API to create a stylesheet with rules for the component.
     */
    fun stylesheet(): CSSStyleSheet {
        // Create a new style element
        val styleElement = document.createElement("style")
        document.head?.appendChild(styleElement)

        // Get the stylesheet from the document's styleSheets collection
        val stylesheet = document.styleSheets[document.styleSheets.length - 1] as CSSStyleSheet

        // Add rules to the stylesheet
        
        // List item rule
        val listItemRule = """
            [nav-item] {
                padding: 0;
                margin: 0;
            }
        """.trimIndent()
        stylesheet.insertRule(listItemRule, stylesheet.cssRules.length)
        
        // Link rule
        val linkRule = """
            [nav-link] {
                padding: ${Theme.Spacing.md};
                cursor: pointer;
                background-color: ${Theme.Colors.white};
                display: flex;
                align-items: center;
                text-decoration: none;
                color: black;
                font-family: ${Theme.Typography.defaultFontFamily};
                transition: background-color 0.2s ease;
            }
        """.trimIndent()
        stylesheet.insertRule(linkRule, stylesheet.cssRules.length)
        
        // Link hover rule
        val linkHoverRule = """
            [nav-link]:hover {
                background-color: ${Theme.Colors.lightGray};
            }
        """.trimIndent()
        stylesheet.insertRule(linkHoverRule, stylesheet.cssRules.length)
        
        // Icon rule
        val iconRule = """
            [nav-icon] {
                margin-right: ${Theme.Spacing.sm};
                font-size: ${Theme.Typography.fontMd};
                display: inline-flex;
                align-items: center;
            }
        """.trimIndent()
        stylesheet.insertRule(iconRule, stylesheet.cssRules.length)
        
        // Text rule
        val textRule = """
            [nav-text] {
                font-size: ${Theme.Typography.fontSm};
            }
        """.trimIndent()
        stylesheet.insertRule(textRule, stylesheet.cssRules.length)

        return stylesheet
    }
}