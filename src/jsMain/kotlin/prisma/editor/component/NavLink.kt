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
class NavLink(var text: String, var route: String, var icon: String = "", var selected: Boolean = false) {
    fun preview(): HTMLElement {
        return document.create.li {
            attributes["nav-item"] = ""

            a {
                attributes["nav-link"] = ""
                if (selected) {
                    attributes["nav-link-selected"] = ""
                }
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

        // Nav item rule
        val navItemRule = """
            [nav-item] {
                padding: 0;
                margin: 0;
            }
        """.trimIndent()
        stylesheet.insertRule(navItemRule, stylesheet.cssRules.length)

        // Nav link rule
        val navLinkRule = """
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
        stylesheet.insertRule(navLinkRule, stylesheet.cssRules.length)

        // Nav link hover rule
        val navLinkHoverRule = """
            [nav-link]:hover {
                background-color: ${Theme.Colors.lightGray};
            }
        """.trimIndent()
        stylesheet.insertRule(navLinkHoverRule, stylesheet.cssRules.length)

        // Nav link selected rule
        val navLinkSelectedRule = """
            [nav-link-selected] {
                background-color: ${Theme.Colors.primary};
                color: ${Theme.Colors.white};
            }
        """.trimIndent()
        stylesheet.insertRule(navLinkSelectedRule, stylesheet.cssRules.length)

        // Nav icon rule
        val navIconRule = """
            [nav-icon] {
                margin-right: ${Theme.Spacing.sm};
                font-size: ${Theme.Typography.fontMd};
                display: inline-flex;
                align-items: center;
            }
        """.trimIndent()
        stylesheet.insertRule(navIconRule, stylesheet.cssRules.length)

        // Nav text rule
        val navTextRule = """
            [nav-text] {
                font-size: ${Theme.Typography.fontSm};
            }
        """.trimIndent()
        stylesheet.insertRule(navTextRule, stylesheet.cssRules.length)

        return stylesheet
    }
}
